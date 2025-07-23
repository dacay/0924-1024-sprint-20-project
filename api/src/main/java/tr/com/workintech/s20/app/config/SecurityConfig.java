package tr.com.workintech.s20.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tr.com.workintech.s20.app.security.JwtAuthenticationProvider;
import tr.com.workintech.s20.app.security.JwtFilter;

@Configuration
public class SecurityConfig {

  @Autowired
  private JwtFilter jwtFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager() {

    AuthenticationProvider provider = new JwtAuthenticationProvider();

    return new ProviderManager(provider);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    return http
            .csrf((config) -> config.disable())
            .authorizeHttpRequests(auth -> {

                auth.requestMatchers("/auth/**").permitAll();

                auth.anyRequest().authenticated();
            })
            .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
  }
}
