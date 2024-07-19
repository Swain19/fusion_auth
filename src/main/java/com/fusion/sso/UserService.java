//package com.fusion.sso;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private DomainValidationService domainValidationService;
//
//    public boolean addUser(String adminEmail, String newUserEmail) {
//        String adminDomain = adminEmail.substring(adminEmail.indexOf("@") + 1);
//        return domainValidationService.isValidDomain(newUserEmail, adminDomain);
//    }
//
//    public void linkIdentityProvider(String userId, String providerId) {
//        // Logic to link user's account to Google/Microsoft using their API
//    }
//}
//
