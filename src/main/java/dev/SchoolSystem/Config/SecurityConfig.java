package dev.SchoolSystem.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login", "/auth/refreshToken").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/auth/user/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/auth/user/**").hasAnyAuthority("ROLE_MANAGER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/auth/role").hasAnyAuthority("ROLE_MANAGER");
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean(), getJwtProvider()));
        http.addFilterBefore(new CustomAuthorizationFilter(getJwtProvider()), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtProvider getJwtProvider(){
        return new JwtProvider();
    }

}
