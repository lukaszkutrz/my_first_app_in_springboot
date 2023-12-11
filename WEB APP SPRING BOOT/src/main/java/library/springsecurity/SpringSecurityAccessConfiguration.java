package library.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import library.enums.LibraryUserRoleType;
import library.model.LibraryUser;
import library.repository.LibraryUserRepository;

@Configuration
@EnableWebSecurity
public class SpringSecurityAccessConfiguration {

    @Autowired
    private LibraryUserRepository libraryUserRepository;

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        List<LibraryUser> users = new ArrayList<LibraryUser>();
        users.addAll((Collection<? extends LibraryUser>) libraryUserRepository.findAll());

        // first admin:
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("adminpass3728"))
                .roles("READER", "LIBRARIAN").build();

        InMemoryUserDetailsManager inMemoryManager = new InMemoryUserDetailsManager(admin);

        for (LibraryUser user : users) {
            UserDetails userDetails = null;
            if (user.getLibraryUserRole().equals(LibraryUserRoleType.LIBRARIAN)) {
                userDetails = User.withUsername(user.getLogin()).password(passwordEncoder.encode("adminpass3728"))
                        .roles("READER", "LIBRARIAN").build();
            } else if (user.getLibraryUserRole().equals(LibraryUserRoleType.READER)) {
                userDetails = User.withUsername(user.getLogin()).password(passwordEncoder.encode("mypassword3728"))
                        .roles("READER").build();
            }
            if (userDetails != null) {
                inMemoryManager.createUser(userDetails);
            }
        }
        return inMemoryManager;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/users/**").access("hasRole('ROLE_LIBRARIAN')")
        .antMatchers("/books/new/**").access("hasRole('ROLE_LIBRARIAN')") 
        .antMatchers("/users/new/**").access("hasRole('ROLE_LIBRARIAN')") 
        .antMatchers("/rented-books/**").access("hasRole('ROLE_LIBRARIAN')")
        .antMatchers("/return-books/**").access("hasRole('ROLE_LIBRARIAN')")
        .antMatchers("/books/**/edit").access("hasRole('ROLE_LIBRARIAN')") 
        .antMatchers("/users/**/edit").access("hasRole('ROLE_LIBRARIAN')")
        .antMatchers("/rent-books/**").access("hasRole('ROLE_LIBRARIAN')")
        .antMatchers("/delete/users/**").access("hasRole('ROLE_LIBRARIAN')")
        
        .antMatchers("/my-books/**").access("hasRole('ROLE_READER')")
        .antMatchers("/books/**").access("hasRole('ROLE_READER')") 
        .antMatchers("/books-rented-by-user/**").access("hasRole('ROLE_READER')") 
        .antMatchers("/js/**").permitAll()
        .antMatchers("/css/**").permitAll()
        
        .antMatchers("/**").authenticated()        
        .and().formLogin().permitAll();
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }
}

