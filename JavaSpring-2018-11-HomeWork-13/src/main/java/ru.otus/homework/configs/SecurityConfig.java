package ru.otus.homework.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.homework.configs.YamlApplicationProperties;
import ru.otus.homework.security.UserProfileDetailsService;

import static ru.otus.homework.controllers.Constants.*;
import static ru.otus.homework.security.Constants.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final UserDetailsService userDetailsService;

    private final YamlApplicationProperties yap;

    @Autowired
    public SecurityConfig(UserProfileDetailsService userDetailsService, YamlApplicationProperties yap)
    {
        this.userDetailsService = userDetailsService;
        this.yap = yap;
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(yap.getStrength());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests().antMatchers("/css/*", "/img/*", "/h2-console/**").permitAll()
            .and()
            .authorizeRequests().antMatchers(REQUEST_LOGIN, REQUEST_LOGIN_PROCESS).anonymous()
            .and()
            .authorizeRequests().antMatchers("/**").authenticated()
            .and()
            .formLogin()
            .loginPage(REQUEST_LOGIN)
            .loginProcessingUrl(REQUEST_LOGIN_PROCESS)
            .usernameParameter(PARAMETER_USERNAME)
            .passwordParameter(PARAMETER_PASSWORD)
            .defaultSuccessUrl("/", true)
            .failureUrl(URL_FAILURE)
            .and()
            .rememberMe()
            .key(yap.getRememberKey())
            .userDetailsService(userDetailsService)
            .alwaysRemember(true)
            .tokenValiditySeconds(60)
        ;
    }
}
