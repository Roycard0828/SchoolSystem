package dev.SchoolSystem.Auth.Repository;

import dev.SchoolSystem.Auth.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository underTest;

    @Test
    void itShouldFindByUsername() {
        //given
        String username = "username01";
        User user = new User(
                "Test1",
                "Last_name",
                username,
                "password"
        );
        //when
        underTest.save(user);
        Optional<User> userResult = underTest.findByUsername(username);
        //then
        assertTrue(userResult.isPresent());
        assertEquals(user, userResult.get());
    }

    @Test
    void itShouldReturnNullForNonExistUsername(){
        String username = "non_exist_username";
        Optional<User> userResult = underTest.findByUsername(username);
        assertTrue(userResult.isEmpty());
    }
}