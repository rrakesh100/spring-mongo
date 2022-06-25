package com.binimise.admin.service;

import com.binimise.admin.models.AclEntry;
import com.binimise.admin.models.AclObjectIdentity;
import com.binimise.admin.models.AclSid;
import com.binimise.admin.models.MC;
import com.binimise.admin.repositories.AclEntryRepository;
import com.binimise.admin.repositories.AclObjectIdentityRepository;
import com.binimise.admin.repositories.AclSidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BinimiseAclService {

    @Autowired
    private AclSidRepository aclSidRepository;

    @Autowired
    private AclObjectIdentityRepository aclObjectIdentityRepository;

    @Autowired
    private AclEntryRepository aclEntryRepository;


    public void populatePermissions(MC mc, String  userName ) {
         Optional<AclSid> sd = aclSidRepository.findBySid(userName);
         AclSid sid = null;
         if(sd !=null && sd.isPresent()) {
             sid = sd.get();
         }else {
             sid = aclSidRepository.save(AclSid.builder().principal(true).sid(userName).build());

         }
         AclObjectIdentity aclObjectIdentity = aclObjectIdentityRepository.save(AclObjectIdentity.builder().objectIdClass(1L).objectIdIdentity(String.valueOf(mc.getId()))
                     .ownerSid(sid.getId())
                     .entriesInheriting(false)
                    .build());
         AclEntry aclEntry = aclEntryRepository.save(AclEntry.builder()
                 .aclObjectIdentity(aclObjectIdentity.getId())
                 .sid(sid.getId())
                 .aceOrder(1)
                 .mask(2)
                 .granting(true)
                 .auditSuccess(true)
                 .auditFailure(true)
                 .build());
        System.out.println(aclEntry);

    }
}
