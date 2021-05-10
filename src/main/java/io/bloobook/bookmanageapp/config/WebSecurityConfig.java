package io.bloobook.bookmanageapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/08
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure ( WebSecurity web ) throws Exception {
        web
            .ignoring()
            .antMatchers("/api/**");
    }

    @Override
    protected void configure ( HttpSecurity http ) throws Exception {
        http
            .formLogin().disable()
            .authorizeRequests().antMatchers("/api/**").permitAll();
    }
}
