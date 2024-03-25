package fr.but3.ctp;


import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

// Les import délicats
import jakarta.servlet.DispatcherType;


@Configuration
@EnableWebSecurity
public class Security {
    @Bean
    public SecurityFilterChain mesautorisations(HttpSecurity http, HandlerMappingIntrospector introspector)
    throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector); // pour H2    
	RequestMatcher h2Matcher = antMatcher("/h2-console/**");                     // pour H2    
        return http.authorizeHttpRequests((authorize) -> authorize
        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
        .requestMatchers(mvc.pattern("/public")).permitAll()
            .requestMatchers(mvc.pattern("/h2")).permitAll()
            .requestMatchers( antMatcher("/h2-console/**") ).hasRole("ADMIN") // voir la bdd en admin
            .requestMatchers( antMatcher("/error/**") ).permitAll()
            .requestMatchers(mvc.pattern("/private")).hasRole("ADMIN")
            .requestMatchers( antMatcher("/private/**") ).hasRole("ADMIN")
            .anyRequest().authenticated()
        )
                .csrf( customizer -> customizer.ignoringRequestMatchers(h2Matcher) )         // pour H2    
                .headers( headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin) )  // pour H2   
                .csrf( customizer -> customizer.ignoringRequestMatchers(antMatcher("/private/**")) )         // pour private  
                .headers( headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin) )  // pour private   
                .csrf( customizer -> customizer.ignoringRequestMatchers(antMatcher("/public/**")) )         // pour public  
                .headers( headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin) )  // pour public   
        .formLogin( Customizer.withDefaults() )
        .logout( configurer -> 
            configurer.logoutSuccessUrl("/public") 
        )
        .rememberMe( configurer -> 
            configurer.rememberMeParameter("remember")
            .useSecureCookie(true)
        )
        .build();
    }

    @Bean
    public JdbcUserDetailsManager mesutilisateurs(DataSource datasource) {
         
        UserDetails user1 = User.withUsername("admin")
                .password(encoder().encode("admin"))
                .roles("ADMIN")
                .build();
        
        UserDetails user2 = User.withUsername("user1")
                .password(encoder().encode("user1"))
                .roles("USER")
                .build();
        UserDetails user3 = User.withUsername("user2")
                .password(encoder().encode("user2"))
                .roles("USER")
                .build();
        UserDetails user4 = User.withUsername("user3")
                .password(encoder().encode("user3"))
                .roles("USER")
                .build();
        
        
        JdbcUserDetailsManager udm = new JdbcUserDetailsManager(datasource);
        udm.createUser(user2);
        udm.createUser(user3);
        udm.createUser(user4);
        udm.createUser(user1); 
        return udm;
    }
         
    
    @Bean
    public PasswordEncoder encoder() {
            return new BCryptPasswordEncoder();

    }
    
}
