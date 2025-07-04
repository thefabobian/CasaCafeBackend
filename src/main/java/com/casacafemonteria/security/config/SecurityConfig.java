package com.casacafemonteria.security.config;

import com.casacafemonteria.constants.EndPointsConstants;
import com.casacafemonteria.security.utils.jwt.JwtTokenProvider;
import com.casacafemonteria.security.utils.jwt.JwtTokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(http -> {

                    // Endpoints públicos
                    http.requestMatchers(HttpMethod.POST, EndPointsConstants.ENDPOINT_SIGNUP).permitAll();
                    http.requestMatchers(HttpMethod.POST, EndPointsConstants.ENDPOINT_LOGIN).permitAll();

                    //post
                    http.requestMatchers(HttpMethod.POST, EndPointsConstants.ENDPOINT_ADMIN).permitAll();
                    http.requestMatchers(HttpMethod.POST, EndPointsConstants.ENDPOINT_EMPLOYEE + "/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, EndPointsConstants.ENDPOINT_CUSTOMER + "/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, EndPointsConstants.ENDPOINT_PRODUCT + "/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, EndPointsConstants.ENDPOINT_CART + "/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, EndPointsConstants.ENDPOINT_ITEM_CART + "/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, EndPointsConstants.ENDPOINT_BILL + "/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, EndPointsConstants.ENDPOINT_DETAIL_BILL + "/**").permitAll();

                    //GETS
                    http.requestMatchers(HttpMethod.GET, EndPointsConstants.ENDPOINT_ADMIN).permitAll();
                    http.requestMatchers(HttpMethod.GET, EndPointsConstants.ENDPOINT_EMPLOYEE + "/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, EndPointsConstants.ENDPOINT_CUSTOMER + "/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, EndPointsConstants.ENDPOINT_PRODUCT + "/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, EndPointsConstants.ENDPOINT_CART + "/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, EndPointsConstants.ENDPOINT_ITEM_CART + "/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, EndPointsConstants.ENDPOINT_BILL + "/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, EndPointsConstants.ENDPOINT_DETAIL_BILL + "/**").permitAll();

                    //delete
                    http.requestMatchers(HttpMethod.DELETE, EndPointsConstants.ENDPOINT_ADMIN).permitAll();
                    http.requestMatchers(HttpMethod.DELETE, EndPointsConstants.ENDPOINT_EMPLOYEE + "/**").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, EndPointsConstants.ENDPOINT_CUSTOMER + "/**").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, EndPointsConstants.ENDPOINT_PRODUCT + "/**").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, EndPointsConstants.ENDPOINT_CART + "/**").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, EndPointsConstants.ENDPOINT_ITEM_CART + "/**").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, EndPointsConstants.ENDPOINT_BILL + "/**").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, EndPointsConstants.ENDPOINT_DETAIL_BILL + "/**").permitAll();

                    //update
                    http.requestMatchers(HttpMethod.PUT, EndPointsConstants.ENDPOINT_ADMIN).permitAll();
                    http.requestMatchers(HttpMethod.PUT, EndPointsConstants.ENDPOINT_EMPLOYEE + "/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, EndPointsConstants.ENDPOINT_CUSTOMER + "/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, EndPointsConstants.ENDPOINT_PRODUCT + "/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, EndPointsConstants.ENDPOINT_CART + "/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, EndPointsConstants.ENDPOINT_ITEM_CART + "/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, EndPointsConstants.ENDPOINT_BILL + "/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, EndPointsConstants.ENDPOINT_DETAIL_BILL + "/**").permitAll();


                    //http.requestMatchers(HttpMethod.POST, "/api/v1/auth/refresh").permitAll();

                    // Endpoints Swagger
                    http.requestMatchers(
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/v3/api-docs/**",
                            "/v3/api-docs.yaml",
                            "/v3/api-docs.json"
                    ).permitAll();

                    // Endpoints solo para ADMIN
                    http.requestMatchers("/admin/**").hasRole("ADMIN");

                    // Endpoints solo para USER
                    http.requestMatchers("/user/**").hasRole("USER");

                    // Endpoints accesibles por USER y ADMIN
                    http.requestMatchers("/common/**").hasAnyRole("USER", "ADMIN");

                    // Otras reglas personalizadas por método y permisos
                    http.requestMatchers(HttpMethod.GET, "/method/get").hasAuthority("READ");
                    http.requestMatchers(HttpMethod.POST, "/method/post").hasAuthority("CREATE");
                    http.requestMatchers(HttpMethod.DELETE, "/method/delete").hasAuthority("DELETE");
                    http.requestMatchers(HttpMethod.PUT, "/method/put").hasAuthority("UPDATE");

                    // Cualquier otra petición requiere autenticación
                    http.anyRequest().authenticated();
                })

                .addFilterBefore(new JwtTokenValidator(jwtTokenProvider), BasicAuthenticationFilter.class)
                .build();
    }
}
