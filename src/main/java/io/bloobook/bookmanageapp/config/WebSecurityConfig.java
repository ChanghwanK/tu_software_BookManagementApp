package io.bloobook.bookmanageapp.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
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
           .ignoring ()
           .requestMatchers ( PathRequest.toH2Console () );
    }
}
