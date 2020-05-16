package com.hobbydobby.config

import com.hobbydobby.handler.HobbyDobbyAuthProvider
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
        var authProvider : HobbyDobbyAuthProvider
) : WebSecurityConfigurerAdapter() {
    /**
     * http configure
     */
    override fun configure(http : HttpSecurity) {
        http.csrf().ignoringAntMatchers("/gui","/graphql").and()
                .authorizeRequests()
                .antMatchers("/js/**","/member/**","/graphql","/gui","/main","/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/main")
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutUrl("/member/logout")
                .logoutSuccessUrl("/main")
                .and()
                .authenticationProvider(authProvider)
    }
}