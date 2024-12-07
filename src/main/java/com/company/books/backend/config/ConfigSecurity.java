package com.company.books.backend.config;

import com.company.books.backend.filter.JwtReqFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
public class ConfigSecurity {

    private JwtReqFilter jwtReqFilter;

    @Autowired
    @Lazy
    public ConfigSecurity(JwtReqFilter jwtReqFilter) {
        this.jwtReqFilter = jwtReqFilter;
    }

    /*alt + J: para selecionar multiples variables, presionar de nuevo para seguir avanzado en el mismo nombre,
        y primero seleccionar con el cursor el nombre o varibles.
     */

    /* TODO es solo para crear usuarios cuando no tenemos usuarios en la base de datos
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails resi = User.builder().username("resi").password("{noop}resi123").roles("BOSS").build();
        UserDetails bresi = User.builder().username("bresi").password("{noop}bresi123").roles("EMPLOYEE").build();
        UserDetails cresi = User.builder().username("cresi").password("{noop}cresi123").roles("SCHOLAR").build();

        return new InMemoryUserDetailsManager(resi, bresi, cresi);
    }*/

    //Usa los usuarios de la base de datos
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){

        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(configure -> {
            configure  // TODO Tiene que estar escritos igual en la base de datos (es case-sensetive),en la base puede anteponerse ROLE_ antes de lo escrito aquí
                    .requestMatchers(HttpMethod.GET, "/v1/libros").hasAnyRole("EMPLOYEE", "BOSS", "SCHOLAR")
                    .requestMatchers(HttpMethod.GET, "/v1/libros/**").hasAnyRole("EMPLOYEE", "BOSS")
                    .requestMatchers(HttpMethod.POST, "/v1/libros").hasRole("BOSS")
                    .requestMatchers(HttpMethod.PUT, "/v1/libros/**").hasRole("BOSS")
                    .requestMatchers(HttpMethod.DELETE, "/v1/libros/**").hasRole("BOSS")
                    .requestMatchers(HttpMethod.GET, "/v1/categorias").hasAnyRole("EMPLOYEE", "BOSS", "SCHOLAR")
                    .requestMatchers(HttpMethod.GET, "/v1/categorias/**").hasAnyRole("BOSS", "EMPLOYEE")
                    .requestMatchers(HttpMethod.POST, "/v1/categorias").hasRole("BOSS")
                    .requestMatchers(HttpMethod.PUT, "/v1/categorias/**").hasRole("BOSS")
                    .requestMatchers(HttpMethod.DELETE, "/v1/categorias/**").hasRole("BOSS")
                    .requestMatchers("/v1/authenticate","/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll();
        }) //TODO los asteriscos quieren decir que todo está permitido a partir de ellos (los usuarios que ya "entraron" tienen acceso si la url sigue)
                        .addFilterBefore(jwtReqFilter, UsernamePasswordAuthenticationFilter.class)
                                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        http.httpBasic(Customizer.withDefaults());
        http.csrf( csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }
}
