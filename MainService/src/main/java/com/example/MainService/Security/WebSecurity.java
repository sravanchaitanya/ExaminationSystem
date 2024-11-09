package com.example.MainService.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurity {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable().
                authorizeHttpRequests((requests)->requests.requestMatchers("users").hasRole("ADMIN").
                        requestMatchers("tests").hasRole("ADMIN").
                        anyRequest().authenticated()).
                formLogin((form)->form.permitAll()).
                logout((logout)->logout.permitAll());
        return http.build();
    }

    @Bean
    public RoleHierarchy MyRoleHierarchy(){
        return RoleHierarchyImpl.withDefaultRolePrefix().role("ADMIN").implies("STAFF").
                role("STAFF").implies("USER").build();
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
