package com.flexdevit.relay42.iot.server.service.security;

import com.flexdevit.relay42.iot.server.exception.ResourceAlreadyExistsException;
import com.flexdevit.relay42.iot.server.model.security.User;
import com.flexdevit.relay42.iot.server.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService  implements UserDetailsService  {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(User user) {
        if (userRepository.existsByUsername(user.getUsername()))
            throw new ResourceAlreadyExistsException("User", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
