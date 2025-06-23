package com.example.RezerwacjaWizyt1;

import com.example.RezerwacjaWizyt1.Services.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final CustomUserDetailService userDetailService;
    public SecurityConfig(CustomUserDetailService userDetailService){
        this.userDetailService=userDetailService;
    }
    @Bean("customSecurityFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        http.authorizeHttpRequests(auth->auth.requestMatchers("/","/register","/css/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/rezerwacjawizyt",true).permitAll()
                )
                .logout(logout-> logout.logoutSuccessUrl("/").permitAll()
                )
                .csrf(csrf ->csrf.disable())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
