package mk.gp.emt.userbase.config;

import lombok.AllArgsConstructor;
import mk.gp.emt.userbase.config.filters.JWTAuthenticationFilter;
import mk.gp.emt.userbase.config.filters.JWTAuthorizationFilter;
import mk.gp.emt.userbase.services.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("jwt")
@AllArgsConstructor
public class JWTWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final CustomUsernamePasswordAuthenticationProvider authenticationProvider;
    private final UserDetailsService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**", "/h2/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(),userService,passwordEncoder))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(),userService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}

