package com.binimise.admin.controllers;


import com.binimise.admin.dtos.request.CreateMCRequest;
import com.binimise.admin.dtos.response.MessageResponse;
import com.binimise.admin.service.MCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mc")
public class MCController {

    @Autowired
    private MCService mcService;

    @PostMapping("")
    public ResponseEntity<?> createMC(@Valid @RequestBody CreateMCRequest mc) {

        mcService.create(mc);
        return ResponseEntity.ok(new MessageResponse("MC successfully created!"));
}


//    @PutMapping("")
//    public ResponseEntity<?> updateMC(@Valid @RequestBody UpdateMCRequest mc) {
//
//        mcService.update(mc);
//        return ResponseEntity.ok(new MessageResponse("MC Successfully updated !"));
//    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {

        return ResponseEntity.ok(mcService.getAll());
    }
}
