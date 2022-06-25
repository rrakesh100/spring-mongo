package com.binimise.admin.controllers;


import com.binimise.admin.dtos.request.LoginRequest;
import com.binimise.admin.dtos.request.ResetPasswordRequest;
import com.binimise.admin.dtos.request.SignupRequest;
import com.binimise.admin.dtos.response.JWTResponse;
import com.binimise.admin.dtos.response.MessageResponse;
import com.binimise.admin.models.ERole;
import com.binimise.admin.models.Role;
import com.binimise.admin.models.User;
import com.binimise.admin.models.VerificationToken;
import com.binimise.admin.repositories.RoleRepository;
import com.binimise.admin.repositories.UserRepository;
import com.binimise.admin.repositories.VerificationTokenRepository;
import com.binimise.admin.security.JWTUtils;
import com.binimise.admin.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    VerificationTokenRepository tokenRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JWTUtils jwtUtils;

    @PostMapping("/reset_password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {

        if (!resetPasswordRequest.getPassword().equals(resetPasswordRequest.getConfirmPassword())) {
            //TODO add more validations
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: passwords do not match!"));
        }

        String token = resetPasswordRequest.getToken();
        //TODO validate the token

        String newPassword = encoder.encode(resetPasswordRequest.getPassword());
        Optional<User> user = userRepository.findByEmail(resetPasswordRequest.getEmail());
        if (!user.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: user does not exist !"));
        } else {
            User u = user.get();
            u.setPassword(newPassword);
            userRepository.save(u);
            return ResponseEntity.ok().body(new MessageResponse("Success: please login"));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication == null || authentication.getPrincipal() == null)
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: user does not exist !"));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<User> u = userRepository.findById(userDetails.getId());
        if (u.isPresent() && u.get().isActive()) {
            String jwt = jwtUtils.generateJwtToken(authentication);

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JWTResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    roles));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User email is not confirmed !"));
        }

    }

    @GetMapping("/confirm_signup")
    public ResponseEntity<?> confirmRegistration(@RequestParam(value = "token", required = true) String token) {

        Optional<VerificationToken> verificationToken = tokenRepository.findByToken(token);
        if (verificationToken.isPresent()) {
            User user = verificationToken.get().getUser();
            user.setActive(true);
            userRepository.save(user);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Token is expired, regenerate link!"));
        }
        return ResponseEntity
                .ok()
                .body(new MessageResponse("Success: Please login !"));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);

        userRepository.save(user);
        generateToken(user);
        //TODO Send email async
        return ResponseEntity.ok(new MessageResponse("User registered successfully, click on the registration link sent on your email to confirm !"));
    }

    private VerificationToken generateToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpriyDate(verificationToken.calculateExpiryDate());
        return tokenRepository.save(verificationToken);
    }
}