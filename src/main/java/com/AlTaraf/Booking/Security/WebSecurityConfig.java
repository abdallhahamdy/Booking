package com.AlTaraf.Booking.Security;


import com.AlTaraf.Booking.Security.jwt.AuthEntryPointJwt;
import com.AlTaraf.Booking.Security.jwt.AuthTokenFilter;
import com.AlTaraf.Booking.Security.service.UserDetailsServiceImpl;
import com.AlTaraf.Booking.Service.CustomOAuth2UserService;
import com.AlTaraf.Booking.Service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

//    @Value("${app.allowed-origins}")
//    private List<String> allowedOrigins;

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

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder().encode("userPass"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Disabling CSRF
                .authorizeRequests(authz -> authz
                        .anyRequest().permitAll()
                );
        return http.build();


//        http
//                .cors(cors -> cors
//                        .configurationSource(request -> {
//                            CorsConfiguration config = new CorsConfiguration();
//                            config.setAllowedOrigins(Arrays.asList("https://env-5463350.fin.libyanspider.cloud/swagger-ui/index.html", "https://dbweb-f725d.web.app/")); // Adjust this to your front-end app's domain
//                            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                            config.setAllowedHeaders(Arrays.asList("*"));
//                            return config;
//                        })
//                )
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().permitAll()
//                )
//                .csrf(AbstractHttpConfigurer::disable);
//
//        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
////                .cors(cors -> {}) // Correctly configures CORS with default settings without returning a value
////                .exceptionHandling(exceptionHandling ->
////                        exceptionHandling.authenticationEntryPoint(unauthorizedHandler) // Configures the authentication entry point
////                )
////                .sessionManagement(sessionManagement ->
////                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sets session management to stateless
////                )
////                .authorizeHttpRequests(authorize -> authorize
////                        .requestMatchers(HttpMethod.POST,"/api/users/Register/**").permitAll()
////                        .requestMatchers("/api/units/filter-unit-by-name").permitAll()
////                        .requestMatchers("/api/cities/**").permitAll()
////                        .requestMatchers("/regions/byCity/**").permitAll()
////                        .requestMatchers("/api/roles/**").permitAll()
////                        .requestMatchers(HttpMethod.POST,"/api/users/**").permitAll()
////                        .requestMatchers("/image/**").permitAll()
////                        .requestMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**", "/bezkoder-documentation/**", "/bezkoder-api-docs/**").permitAll()
////                        .anyRequest().authenticated()
////                );
//
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().permitAll() // This makes all requests permitted without authentication
//                ).csrf(AbstractHttpConfigurer::disable); // Disables CSRF protection
//
//
////        http
////                .authenticationProvider(authenticationProvider())
////                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> {})
//                .csrf(AbstractHttpConfigurer::disable)// Disable CSRF protection
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//        // other configurations
//        ;
//        return http.build();
//    }

}