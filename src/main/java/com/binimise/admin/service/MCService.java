package com.binimise.admin.service;

import com.binimise.admin.dtos.request.CreateMCRequest;
import com.binimise.admin.models.Address;
import com.binimise.admin.models.MC;
import com.binimise.admin.models.POC;
import com.binimise.admin.repositories.MCRepository;
import com.binimise.admin.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MCService {

    @Autowired
    private MCRepository mcRepository;

    @Autowired
    private BinimiseAclService binimiseAclService;


    public void create(CreateMCRequest createMcRequest) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MC mc = MC.builder()
                .gst(createMcRequest.getGst())
                .pan(createMcRequest.getPan())
                .businessName(createMcRequest.getBusinessName())
                .listingName(createMcRequest.getListingName())
                .fssai(createMcRequest.getFssai())
                .address(Address.builder().addressLine1(createMcRequest.getAddressLine1()).addressLine2(createMcRequest.getAddressLine2())
                    .city(createMcRequest.getCity()).state(createMcRequest.getState()).pin(createMcRequest.getPin()).build())
                .poc(POC.builder().name(createMcRequest.getPocName()).phoneNumber(createMcRequest.getPocPhoneNumber())
                        .alternates(new ArrayList<>() {{add(POC.builder().name(createMcRequest.getAlternatePocName()).phoneNumber(createMcRequest.getAlternatePocNumber()).build());}}).build())
                .build();
        mc.setCreatedBy(principal.getId().intValue());mc.setUpdatedBy(principal.getId().intValue());mc.setCreatedOn(LocalDateTime.now());mc.setUpdatedOn(LocalDateTime.now());
        mcRepository.save(mc);
        binimiseAclService.populatePermissions(mc, principal.getUsername());
    }

//    public void update(UpdateMCRequest updateMCRequest) {
//        Optional<MC> m = mcRepository.findById(updateMCRequest.getId());
//        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(m.isPresent()) {
//            MC mc = m.get();
//            mc.setListingName(updateMCRequest.getListingName());
//            mc.setFssai(updateMCRequest.getFssai());
//            mc.setAddress(Address.builder().addressLine1(updateMCRequest.getAddressLine1()).addressLine2(updateMCRequest.getAddressLine2())
//                            .state(updateMCRequest.getState()).city(updateMCRequest.getCity()).pin(updateMCRequest.getPin()).build());
//            mc.setUpdatedBy(principal.getId().intValue()); mc.setUpdatedOn(LocalDateTime.now());
//            mcRepository.save(mc);
//        }
//    }

    public List<MC> getAll() {
       return mcRepository.findAll();
    }

}
