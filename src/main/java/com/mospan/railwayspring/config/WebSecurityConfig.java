package com.mospan.railwayspring.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;


@Configuration
@EnableWebSecurity
@ComponentScan("com.mospan.railwayspring.service.sec")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
                .authorizeRequests()
                .antMatchers("/stations/**", "/routes/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/", "/trips", "/trips/**", "/auth/register", "/language").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .defaultSuccessUrl("/index", true)
                .failureUrl("/auth/login?error=true")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new MessageDigestPasswordEncoder("SHA-256"));
        return provider;
    }

}
