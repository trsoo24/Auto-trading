package com.project.configuration.security;

import com.project.reference.CheckUserReference;
import com.project.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CheckUserReference checkUserReference;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = checkUserReference.findUserByEmail(username);

        return new UserDetailImpl(user, user.getEmail(), user.getPassword());
    }
}
