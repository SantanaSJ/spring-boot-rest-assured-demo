package com.example.sampleproject.security;

import com.example.sampleproject.model.entities.UserEntity;
import com.example.sampleproject.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            UserEntity user = userRepository.findByName(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            user.getRoles().forEach(role -> {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().name()));
            });

            return new User(user.getName(), "{noop}" + user.getPassword(), grantedAuthorities);
        }
    }
