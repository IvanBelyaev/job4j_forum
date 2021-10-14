package ru.job4j.forum.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.forum.exception.UserAlreadyExistException;
import ru.job4j.forum.model.Authority;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.AuthorityRepository;
import ru.job4j.forum.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }

    @Transactional
    public void save(User user) throws UserAlreadyExistException {
        if (userRepository.existsUserByUsername(user.getUsername())) {
            throw new UserAlreadyExistException("User named " + user.getUsername() + " already exists");
        }
        Authority authority = authorityRepository.findByAuthority("ROLE_USER");
        user.setAuthority(authority);
        userRepository.save(user);
    }
}
