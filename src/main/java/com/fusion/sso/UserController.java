//package com.fusion.sso;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.oidc.user.OidcUser;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//public class UserController {
//
//    @Autowired
//    private DomainValidationService domainValidationService;
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/user")
//    public ResponseEntity<String> addUser(@RequestBody UserDto userDto, @AuthenticationPrincipal OidcUser user) {
//        String adminEmail = user.getEmail();
//        boolean isAdded = userService.addUser(adminEmail, userDto.getEmail());
//        if (isAdded) {
//            return ResponseEntity.ok("User added successfully");
//        } else {
//            return ResponseEntity.badRequest().body("Invalid domain");
//        }
//    }
//
//    @PostMapping("/identity-provider/link")
//    public ResponseEntity<String> linkIdentityProvider(@RequestBody LinkIdentityProviderDto linkDto) {
//        userService.linkIdentityProvider(linkDto.getUserId(), linkDto.getProviderId());
//        return ResponseEntity.ok("Identity provider linked successfully");
//    }
//}
