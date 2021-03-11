package config;

import io.tech1.demo.service.MyUserDetailsService;
import io.tech1.demo.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class TestConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
