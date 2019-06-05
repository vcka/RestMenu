package ml.penkisimtai.restMenu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${app.login}")
    private String login;

    @Value("${app.password}")
    private String password;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.headers().frameOptions().disable();
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/h2/**").permitAll()
//                .antMatchers("/**").authenticated()
//                .and()
//                .httpBasic();
//    }
//
//        @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth)
//            throws Exception
//    {
//        auth
//                .inMemoryAuthentication()
//                .withUser(login)
//                .password(password)
//                .roles("ADMIN");
//    }
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(login).password(passwordEncoder().encode(password)).roles("ADMIN");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/items/**").permitAll()
                .antMatchers("/**").permitAll()
                .antMatchers("/images/**").permitAll()
//                .antMatchers("/menuItems/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/api/remove**").hasRole("ADMIN")
                .antMatchers("/api/edit**").hasRole("ADMIN")
                .antMatchers("/api/comment**").permitAll()
                .antMatchers("/api/add**").hasRole("ADMIN")
                .antMatchers("/api/upload**").hasRole("ADMIN")
                .antMatchers("/login*").permitAll()

//                .antMatchers(HttpMethod.POST, "/api**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/api**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
//                .loginPage("/login.html")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/admin", true)
                .and()
                .httpBasic();
                //.failureUrl("/login.html?error=true")
//                .failureHandler()

//                .and()
//                .logout()
//                .logoutUrl("/perform_logout")
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessHandler(new SimpleUrlLogoutSuccessHandler());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
