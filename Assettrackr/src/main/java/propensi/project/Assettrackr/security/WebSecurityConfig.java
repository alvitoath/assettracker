package propensi.project.Assettrackr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import propensi.project.Assettrackr.security.jwt.JwtTokenFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(this::configureAuthorization)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private void configureAuthorization(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry requests) {
        requests
                .requestMatchers(new AntPathRequestMatcher("/api/v1/server/create")).hasAnyAuthority("Admin", "Operational")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/server/update/**")).hasAnyAuthority("Admin", "Operational")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/server/delete/**")).hasAnyAuthority("Admin", "Operational")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/server/divisi/**")).hasAnyAuthority("Admin", "Operational", "Security")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/server/**")).hasAnyAuthority("Admin", "Operational", "Security")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/user/create")).hasAuthority("Admin")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/user/delete/**")).hasAuthority("Admin")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/user/update/**")).hasAuthority("Admin")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/admin/**")).hasAuthority("Admin")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/divisi/all")).hasAnyAuthority("Admin", "Operational", "Security")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/divisi/divisi/**")).hasAnyAuthority("Admin", "Operational", "Security", "Anggota")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/divisi/**")).hasAnyAuthority("Admin", "Operational")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/user/all")).hasAuthority("Admin")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/user/login")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/user/login-active")).permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000/"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder())
                .and()
                .build();
    }
}
