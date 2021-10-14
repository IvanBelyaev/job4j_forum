package ru.job4j.forum.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.forum.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("select u from User u join fetch u.authority where u.username = :username")
    User findByUsername(@Param("username") String username);

    boolean existsUserByUsername(String username);
}
