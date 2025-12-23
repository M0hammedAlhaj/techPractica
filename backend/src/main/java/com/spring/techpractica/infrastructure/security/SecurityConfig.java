package com.spring.techpractica.infrastructure.security;


import com.spring.techpractica.infrastructure.security.filter.JwtFilter;
import com.spring.techpractica.infrastructure.security.oauth.config.CustomOAuth2UserService;
import com.spring.techpractica.infrastructure.security.oauth.config.OAuth2SuccessHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CorsConfig corsConfig;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/oauth2/**",
                                "/login/oauth2/**"
                        ).permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/sessions/").authenticated()
                        .requestMatchers("api/v1/sessions/requirements/**").authenticated()
                        .requestMatchers("api/v1/profile/").authenticated()
                        .requestMatchers("api/v1/auth/active-account", "api/v1/auth/change-password").authenticated()
                        .requestMatchers("/api/v1/roles/**").authenticated()
                        .requestMatchers("api/v1/sessions/start/**").authenticated()
                        .requestMatchers("/api/v1/sessions/requests/**").authenticated()
                        .anyRequest().permitAll())
                .oauth2Login(oauth -> oauth
                        .loginPage("/oauth2/authorization/github")
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService)
                        )
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler((request, response, exception) -> {
                            response.sendRedirect("http://localhost:3000/login");
                        })
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}