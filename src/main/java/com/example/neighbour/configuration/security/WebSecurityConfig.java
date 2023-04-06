package com.example.neighbour.configuration.security;


import com.example.neighbour.configuration.security.filter.EmailPasswordFilter;
import com.example.neighbour.configuration.security.filter.JwtFilter;
import com.example.neighbour.configuration.security.jwt.JwtUtils;
import com.example.neighbour.configuration.security.provider.EmailPasswordAuthProvider;
import com.example.neighbour.configuration.security.provider.JwtAuthProvider;
import com.example.neighbour.configuration.security.provider.PreAuthenticationCheck;
import com.example.neighbour.utils.ApiConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity()
@EnableMethodSecurity()
@AllArgsConstructor
@Slf4j
public class WebSecurityConfig {

    private final JwtUtils jwtUtils;

    private final UserDetailsService userService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public EmailPasswordAuthProvider emailPasswordAuthProvider() {
        return new EmailPasswordAuthProvider(userService, passwordEncoder, preAuthenticationCheck());
    }

    @Bean
    public PreAuthenticationCheck preAuthenticationCheck() {
        return new PreAuthenticationCheck();
    }

    @Bean
    public JwtAuthProvider jwtAuthProvider() {
        return new JwtAuthProvider(userService, jwtUtils, preAuthenticationCheck());
    }

    @Bean
    public EmailPasswordFilter emailPasswordFilter(AuthenticationManager authenticationManager) {
        return new EmailPasswordFilter(authenticationManager);
    }

    @Bean
    public ProviderManager providerManager() {
        return new ProviderManager(jwtAuthProvider());
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(providerManager());
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> {
                    try {
                        configureRequestMatchers(auth);
                    } catch (Exception e) {
                        log.error("Error configuring security filter chain", e);
                    }
                }
        );
        configureHttpSecurity(http);

        return http.build();
    }


    /**
     * Configures filters to be used for authentication with other configurations
     */
    private void configureHttpSecurity(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager(
                http.getSharedObject(
                        AuthenticationConfiguration.class));

        http.addFilterAt(emailPasswordFilter(authenticationManager), BasicAuthenticationFilter.class);
        http.addFilterAfter(jwtFilter(), EmailPasswordFilter.class);

        http.authenticationProvider(emailPasswordAuthProvider())
                .authenticationProvider(jwtAuthProvider());


//        http.csrf().disable();

        http.cors().and().csrf().disable();

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

        http.headers().frameOptions().disable();
    }

    /**
     * Configure the request matchers for the security filter chain
     */
    private static void configureRequestMatchers(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>
                    .AuthorizationManagerRequestMatcherRegistry auth) throws Exception {
        auth
                .requestMatchers(new AntPathRequestMatcher(ApiConstants.API_VERSION_1 + "user/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher(ApiConstants.API_VERSION_1 + "authenticate")).permitAll()
                .requestMatchers(new AntPathRequestMatcher(ApiConstants.API_VERSION_1 + "webhook/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher(ApiConstants.API_VERSION_1 + "address")).permitAll()
                .requestMatchers(new AntPathRequestMatcher(ApiConstants.API_VERSION_1 + "webhook/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui")).permitAll()
                .requestMatchers(ApiConstants.AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
