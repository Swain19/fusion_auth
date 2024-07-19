//package com.fusion.sso;
//
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Component
//public class CustomJwtAuthenticationConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
//
//    @Override
//    public Collection<GrantedAuthority> convert(Jwt jwt) {
//        return extractAuthorities(jwt);
//    }
//
//    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
//        Map<String, Object> claims = jwt.getClaims();
//        List<String> roles = (List<String>) claims.get("roles");
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                .collect(Collectors.toList());
//    }
//}
