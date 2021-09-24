package com.scp.patientmanagement.security;

import com.scp.patientmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Component
@Transactional
class ImplementsUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        com.scp.patientmanagement.model.User userEntity = userRepository.findByUserLogin(login);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User: "+ login +" not found!");
        }

        return new User(userEntity.getUserLogin(), userEntity.getUserPassword(), true, true, true, true, new ArrayList<>());
    }
}
