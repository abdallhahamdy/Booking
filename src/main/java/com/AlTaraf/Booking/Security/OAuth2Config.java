package com.AlTaraf.Booking.Security;

import com.AlTaraf.Booking.Service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class OAuth2Config  {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth ->
//                        auth.requestMatchers("/login/oauth2/google").permitAll()
//                            .requestMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**", "/bezkoder-documentation/**", "/bezkoder-api-docs/**").permitAll()
//                .anyRequest().authenticated())
////                .oauth2Login()
////                .userInfoEndpoint()
////                .userService(customOAuth2UserService);
//    }
}