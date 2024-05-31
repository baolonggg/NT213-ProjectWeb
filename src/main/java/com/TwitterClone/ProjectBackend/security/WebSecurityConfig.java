package com.TwitterClone.ProjectBackend.security;

import com.TwitterClone.ProjectBackend.userManagement.RepositoryUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * In this method we disable the CSRF protection for know so our app can accept any request from any user.
     *
     * @param http
     * @throws Exception
     */

    @Autowired
    private RepositoryUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
//    @Bean
//    public FilterRegistrationBean<XSSFilter> filterRegistrationBean() {
//        // Create a new FilterRegistrationBean for XSSFilter
//        FilterRegistrationBean<XSSFilter> registrationBean = new FilterRegistrationBean<>();
//
//        // Set the XSSFilter instance as the filter to be registered
//        registrationBean.setFilter(new XSSFilter());
//
//        // Specify the URL patterns to which the filter should be applied
//        registrationBean.addUrlPatterns("/testt/*");
//
//        // Return the configured FilterRegistrationBean
//        return registrationBean;
//    }
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration cfg = new CorsConfiguration();
//        cfg.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://twitterclon.vercel.app","https://twitterclonn.vercel.app"));
//        cfg.setAllowedMethods(Collections.singletonList("*"));
//        cfg.setAllowCredentials(true);
//        cfg.setAllowedHeaders(Collections.singletonList("*"));
//        cfg.setExposedHeaders(Arrays.asList("Authorization"));
//        cfg.setMaxAge(3600L);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", cfg);
//        return source;
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       // http.addFilterBefore(new CSPNonceFilter(), BasicAuthenticationFilter.class);
        String REPORT_TO = "{\"group\":\"csp-violation-report\",\"max_age\":2592000,\"endpoints\":[{\"url\":\"http://localhost:8080/report\"}]}";
        //http.headers().contentSecurityPolicy("script-src 'self' https://trustedscripts.example.com; object-src https://trustedplugins.example.com; report-uri /csp-report-endpoint/");

        //http.headers().contentSecurityPolicy(Customizer.withDefaults());//.and().addHeaderWriter(new StaticHeadersWriter("Report-To", REPORT_TO));

        //http.headers().contentSecurityPolicy("script-src 'self'; form-action 'self';  script-src-attr 'unsafe-inline'; script-src-elem 'unsafe-inline'; report-uri /report; report-to csp-violation-report").and().addHeaderWriter(new StaticHeadersWriter("Report-To", REPORT_TO));
        //http.headers().xssProtection(Customizer.withDefaults());



        http.headers()
                .contentSecurityPolicy("default-src 'self'; form-action 'self'; style-src 'self' 'unsafe-inline'; script-src-attr 'self' 'unsafe-inline'; report-uri /report; report-to csp-violation-report")
                .and()
                .addHeaderWriter(new StaticHeadersWriter("Report-To", REPORT_TO));

        http.headers().xssProtection(Customizer.withDefaults());
        http.headers().contentTypeOptions().disable();

        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/signup").permitAll();
        http.authorizeRequests().antMatchers("/error").permitAll();
        http.authorizeRequests().antMatchers("/logout").permitAll();
        http.authorizeRequests().antMatchers("/test").permitAll();
        http.authorizeRequests().antMatchers("/report").permitAll();
        http.authorizeRequests().antMatchers("/testt/*").permitAll();



        // Login form
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/home");
        http.formLogin().failureUrl("/error");

        // Private pages
        http.authorizeRequests().antMatchers("/home").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/notifications").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/bookmarks").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/write-tweet").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/edit-profile").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/follow/**").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/write-tweet/comment/**").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/tweets/reply-tweet/**").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/tweets/delete/**").hasAnyRole("USER", "ADMIN");
        //ADMIN Page
        http.authorizeRequests().antMatchers("/dashboard").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/ban/**").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/unbanned/**").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/verify/**").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/unverify/**").hasAnyRole("ADMIN");

        // Logout
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");
        //http.csrf().disable();
        // Allow H2 console
        // http.authorizeRequests().antMatchers("/h2-console/**").permitAll();

        //sor
        //http.headers().frameOptions().sameOrigin();
        http.cors();
    }
}