package com.example.InvatareInteractivaBackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ORIGIN = "http://localhost:4200";
    private static final String ANY_HEADER = "*";
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                /*.authorizeRequests().anyRequest().permitAll();*/
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/download/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user/deactivateUser").permitAll()
                .antMatchers(HttpMethod.POST, "/user/isActive").permitAll()
                .antMatchers(HttpMethod.POST, "/user/login").permitAll()
                .antMatchers(HttpMethod.GET, "/users").permitAll()
                .antMatchers(HttpMethod.POST, "/user/checkUser").permitAll()
                .antMatchers(HttpMethod.POST, "/user/checkPass").permitAll()
                .antMatchers(HttpMethod.GET, "/getAllUsers").permitAll()
                .antMatchers(HttpMethod.GET, "/sticky-notes").permitAll()
                .antMatchers(HttpMethod.POST, "/saveStickyNote").permitAll()
                .antMatchers(HttpMethod.PUT, "/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/{id}").permitAll()
                .anyRequest().authenticated();
        http.cors().configurationSource(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedMethods(List.of(HttpMethod.GET.name(),
                    HttpMethod.POST.name(),
                    HttpMethod.PUT.name(),
                    HttpMethod.PATCH.name(),
                    HttpMethod.DELETE.name())
            );
            corsConfiguration.setAllowedOriginPatterns(List.of(ORIGIN));
            corsConfiguration.setAllowedOrigins(List.of(ORIGIN));
            corsConfiguration.setAllowedHeaders(List.of(ANY_HEADER));
            return corsConfiguration;
        });
    }

}

