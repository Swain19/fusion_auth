//package com.fusion.sso;
//
//import com.microsoft.graph.models.User;
//import com.microsoft.graph.requests.GraphServiceClient;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class MicrosoftGraphService {
//
//    private GraphServiceClient<?> graphClient;
//
//    // Initialize graphClient with proper credentials
//
//    public List<User> getUsersInDomain(String domain) {
//        try {
//            return graphClient.users().buildRequest().get().getCurrentPage();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
