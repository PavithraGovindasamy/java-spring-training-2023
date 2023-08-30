package com.sirius.springenablement.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


@Configuration
public class DemoSecurityConfig {

    //nomore hard code values-->the sptring security will fetch the users
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager=new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery("select user_id,pw,active from members where user_id=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user_id,role from roles where user_id=?");
        return  jdbcUserDetailsManager;

    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        UserDetails pavi = User.builder()
//                .username("pavi")
//                .password("{noop}user123")
//                .roles("EMPLOYEE")
//                .build();
//
//        UserDetails sandy = User.builder()
//                .username("sandy")
//                .password("{noop}user123")
//                .roles("EMPLOYEE","MANAGER")
//                .build();
//
//        UserDetails laks = User.builder()
//                .username("laksr")
//                .password("{noop}user123")
//                .roles("EMPLOYEE","MANAGER","ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(pavi,sandy,laks);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{
        http.authorizeHttpRequests(configurer->
          configurer
                  .requestMatchers(HttpMethod.GET,"/employees").hasRole("EMPLOYEE")
                  .requestMatchers(HttpMethod.GET,"/employees/**").hasRole("EMPLOYEE")
                  .requestMatchers(HttpMethod.POST,"/employees").hasRole("MANAGER")
                  .requestMatchers(HttpMethod.PUT,"/employees").hasRole("MANAGER")
                  .requestMatchers(HttpMethod.DELETE,"/employees/**").hasRole("ADMIN")


        );
        // use http basic auth
        http.httpBasic(Customizer.withDefaults());

        // disable csrf
        http.csrf(csrf->csrf.disable());
        return http.build();

    }
}
