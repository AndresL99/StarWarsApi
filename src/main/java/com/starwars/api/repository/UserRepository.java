package com.starwars.api.repository;

import com.starwars.api.domain.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import static com.starwars.api.utils.Query.FIND_BY_USERNAME_AND_PASSWORD;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = FIND_BY_USERNAME_AND_PASSWORD, nativeQuery = true)
    User getByUsernameAndPassword(String username, String password);
}
