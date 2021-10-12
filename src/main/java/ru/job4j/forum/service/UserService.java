package ru.job4j.forum.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.forum.exception.UserAlreadyExistException;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.ForumRepository;

@Service
public class UserService implements UserDetailsService {
    private ForumRepository forumRepository;

    public UserService(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = forumRepository.getUserByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }

    public void save(User user) throws UserAlreadyExistException {
        if (forumRepository.checkIfUserExist(user)) {
            throw new UserAlreadyExistException("User named " + user.getUsername() + " already exists");
        }
        forumRepository.saveUser(user);
    }
}
