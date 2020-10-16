package io.chorok2.infra.config;

import io.chorok2.modules.security.CustomAuthenticationProvider;
import io.chorok2.modules.security.JwtAuthenticationEntryPoint;
import io.chorok2.modules.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .authenticationProvider(customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/*/auth/signin", "/*/auth/signup", "/h2-console/*").permitAll()
                .anyRequest()
                    .authenticated();

        /* H2 콘솔을 위한 설정 */
        http
                .headers()
                .addHeaderWriter(
                        new XFrameOptionsHeaderWriter(
                                new WhiteListedAllowFromStrategy(Collections.singletonList("localhost"))
                        )
                )
                .frameOptions().sameOrigin();

        http.
                addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
