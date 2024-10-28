package spring.authentication.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_USER");
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails bastien = (UserDetails) User.withDefaultPasswordEncoder()
            .username("bastien")
            .password("tacostacos")
            .roles("USER")
            .build();
        UserDetails michel = (UserDetails) User.withDefaultPasswordEncoder()
            .username("michel")
            .password("vivelefoot")
            .roles("USER")
            .build();
        UserDetails laure = (UserDetails) User.withDefaultPasswordEncoder()
            .username("laure")
            .password("Driver-Goal3-Confess-Luckily")
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(bastien,laure, michel);
    }
    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/").permitAll();
                auth.requestMatchers("/admin/**").hasRole("ADMIN");
                auth.requestMatchers("/user/profile").hasRole("USER");
                auth.anyRequest().authenticated();
            })
            .formLogin(Customizer.withDefaults())
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .build();
    };
}

