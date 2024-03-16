package com.AlTaraf.Booking.Security;


import com.AlTaraf.Booking.Security.jwt.AuthEntryPointJwt;
import com.AlTaraf.Booking.Security.jwt.AuthTokenFilter;
import com.AlTaraf.Booking.Security.service.UserDetailsServiceImpl;
import com.AlTaraf.Booking.Service.CustomOAuth2UserService;
import com.AlTaraf.Booking.Service.user.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.Authentication;

import java.io.IOException;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    @Lazy
    private UserService userService; // Ensure this is autowired

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/users/Register/**").permitAll()
                                .requestMatchers("/api/units/filter-unit-by-name").permitAll()
                                .requestMatchers("/api/cities/**").permitAll() // Adjust roles as needed
                                .requestMatchers("/regions/byCity/**").permitAll() // Adjust roles as needed
                                .requestMatchers("/api/roles/**").permitAll()  // Adjust roles as needed
                                .requestMatchers("/api/users/**").permitAll()  // Adjust roles as needed
                                .requestMatchers("/image/**").permitAll()
                                .requestMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**", "/bezkoder-documentation/**", "/bezkoder-api-docs/**").permitAll()
                                .anyRequest().authenticated()
                ) .oauth2Login(oauth2Login -> oauth2Login
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .oidcUserService(this.oidcUserService())
                                .userService(this.oauthUserService()) // Ensure you have a bean for OAuth2UserService
                        )
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                                Authentication authentication) throws IOException, ServletException {

                                OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
                                String email = oauthUser.getAttribute("email");
                                String name = oauthUser.getAttribute("name");
                                String phone = oauthUser.getAttribute("phone");
                                // Assuming userService.processOAuthPostLogin(email) updates user info
                                userService.processOAuthPostLogin(email, name, phone);
                                response.sendRedirect("/list");
                            }
                        })
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauthUserService() {
        // Return your CustomOAuth2UserService instance
        return customOAuth2UserService;
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        // Return an instance of OidcUserService if needed
        return new OidcUserService();
    }

}