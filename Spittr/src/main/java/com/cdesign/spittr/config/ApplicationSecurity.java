package com.cdesign.spittr.config;

import com.cdesign.spittr.security.SpitterDetailsService;
import com.cdesign.spittr.security.StatelessAuthenticationFilter;
import com.cdesign.spittr.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

/**
 * Created by Ageev Evgeny on 29.08.2016.
 */
@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private SpitterDetailsService spitterDetailsService;
    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("1234").roles("SPITTER").and()
//                .withUser("admin").password("admin").roles("SPITTER", "ADMIN");

        auth
                .userDetailsService(spitterDetailsService)
                .and()
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, active " +
                        "from Spitter where username=?")
                .authoritiesByUsernameQuery("select username, authorities " +
                        "from Spitter were username=?")
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                    .loginPage("/login")
                .and()
                .rememberMe()
                    .tokenValiditySeconds(2419200)
                    .key("spittrKey")
                .and()
                .httpBasic()
                    .realmName("Spittr")
                .and()
                .authorizeRequests()
                    .antMatchers("/spitter/me").authenticated()
                    .antMatchers(HttpMethod.POST, "/spittles").authenticated()
                    .antMatchers("/admin", "/admin/**").hasRole("ADMIN")
                    .anyRequest().permitAll()
                .and()
                .requiresChannel()
                    .antMatchers("/spitter/register").requiresSecure()
                    .antMatchers("/").requiresInsecure()
                .and()
                .logout()
                    .logoutSuccessUrl("/")
                .and()
                // custom Token based authentication based on the header previously given to the client
                .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder("S3cr3t");
    }
}

