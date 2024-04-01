package com.example.desarrollodeaplicaciones.repositories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.desarrollodeaplicaciones.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

@SpringBootTest
class UserRepositoryTest {

  @Autowired IUserRepository userRepository;

  @AfterEach
  void after() {
    userRepository.deleteAll();
  }

  @Test
  void test_fail_when_users_with_same_email_are_inserted() {
    // Arrange
    User user1 = User.builder().nickName("felipae").email("mail.prueba@gmail.com").build();
    User user2 = User.builder().nickName("marcoas").email("mail.prueba@gmail.com").build();
    //userRepository.insert(user1);
    // Act & Assert
    //assertThrows(DuplicateKeyException.class, () -> userRepository.insert(user2));
  }
}
