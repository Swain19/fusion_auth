//package com.fusion.sso;
//
//import com.google.api.services.admin.directory.Directory;
//import com.google.api.services.admin.directory.model.User;
//import com.google.api.services.admin.directory.model.Users;
//import org.springframework.stereotype.Service;
//
//@Service
//public class GoogleDirectoryService {
//
//    private Directory directoryService;
//
//    // Initialize directoryService with proper credentials
//
//    public Users getUsersInDomain(String domain) {
//        try {
//            return directoryService.users().list().setDomain(domain).execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
