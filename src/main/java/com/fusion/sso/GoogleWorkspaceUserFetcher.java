//package com.fusion.sso;
//
//
//import com.google.api.services.admin.directory.Directory;
//import com.google.api.services.admin.directory.DirectoryScopes;
//import com.google.api.services.admin.directory.model.Users;
//import com.google.api.services.admin.directory.model.User;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.json.jackson2.JacksonFactory;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.Collections;
//
//public class GoogleWorkspaceUserFetcher {
//
//    public static void main(String[] args) throws GeneralSecurityException, IOException {
//
//        String serviceAccountKeyFile = "src/main/resources/your-service-account-key.json";
//
//        // Domain to fetch users from
//        String domain = "example.com";
//
//        // Load the service account credentials
//        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(serviceAccountKeyFile))
//                .createScoped(Collections.singleton(DirectoryScopes.ADMIN_DIRECTORY_USER_READONLY));
//
//        // Build the Directory API client
//        Directory directory = new Directory.Builder(
//                GoogleNetHttpTransport.newTrustedTransport(),
//                JacksonFactory.getDefaultInstance(),
//                credential)
//                .setApplicationName("Your Application Name")
//                .build();
//
//        // Fetch users from the domain
//        Users result = directory.users().list().setDomain(domain).execute();
//        for (User user : result.getUsers()) {
//            System.out.println("User: " + user.getPrimaryEmail());
//        }
//    }
//}
//
