package com.fliurkevych.pdp.pdpspringcore.service;

import static com.fliurkevych.pdp.pdpspringcore.util.UserTestUtils.EMAIL_1;
import static com.fliurkevych.pdp.pdpspringcore.util.UserTestUtils.EMAIL_2;
import static com.fliurkevych.pdp.pdpspringcore.util.UserTestUtils.NAME_1;
import static com.fliurkevych.pdp.pdpspringcore.util.UserTestUtils.NAME_2;
import static com.fliurkevych.pdp.pdpspringcore.util.UserTestUtils.USER_ID_1;
import static com.fliurkevych.pdp.pdpspringcore.util.UserTestUtils.USER_ID_2;
import static org.mockito.Mockito.when;

import com.fliurkevych.pdp.pdpspringcore.exception.NotFoundException;
import com.fliurkevych.pdp.pdpspringcore.exception.ValidationException;
import com.fliurkevych.pdp.pdpspringcore.repository.UserRepository;
import com.fliurkevych.pdp.pdpspringcore.util.UserTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author Oleh Fliurkevych
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  public static final Pageable PAGEABLE = PageRequest.of(1, 10);

  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private UserService userService;

  @Test
  public void getUserByIdTest() {
    var user = UserTestUtils.buildUser(USER_ID_1, NAME_1, EMAIL_1);

    when(userRepository.getUserById(USER_ID_1)).thenReturn(Optional.of(user));

    var result = userService.getUserById(USER_ID_1);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(USER_ID_1, result.getId());
  }

  @Test
  public void getUserByIdShouldThrowNotFoundExceptionTest() {
    when(userRepository.getUserById(USER_ID_1)).thenReturn(Optional.empty());

    Assertions.assertThrows(NotFoundException.class, () -> userService.getUserById(USER_ID_1));
  }

  @Test
  public void getUserByEmailTest() {
    var user = UserTestUtils.buildUser(USER_ID_1, NAME_1, EMAIL_1);

    when(userRepository.getUserByEmail(EMAIL_1)).thenReturn(Optional.of(user));

    var result = userService.getUserByEmail(EMAIL_1);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(EMAIL_1, result.getEmail());
  }

  @Test
  public void getUserByEmailShouldThrowNotFoundExceptionTest() {
    when(userRepository.getUserByEmail(EMAIL_1)).thenReturn(Optional.empty());

    Assertions.assertThrows(NotFoundException.class, () -> userService.getUserByEmail(EMAIL_1));
  }

  @Test
  public void getUsersByNameTest() {
    var user1 = UserTestUtils.buildUser(USER_ID_1, NAME_1, EMAIL_1);
    var user2 = UserTestUtils.buildUser(USER_ID_2, NAME_2, EMAIL_2);

    when(userRepository.getUsersByName("name 11", 10, 1)).thenReturn(List.of(user1, user2));

    var result = userService.getUsersByName("name 11", PAGEABLE);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(2, result.size());
  }

  @Test
  public void createUserTest() {
    var user1 = UserTestUtils.buildUser(USER_ID_1, NAME_1, EMAIL_1);

    when(userRepository.getUserById(USER_ID_1)).thenReturn(Optional.empty());
    when(userRepository.save(user1)).thenReturn(user1);

    var result = userService.createUser(user1);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(USER_ID_1, result.getId());
  }

  @Test
  public void createUserShouldThrowValidationTest() {
    var user1 = UserTestUtils.buildUser(USER_ID_1, NAME_1, EMAIL_1);
    when(userRepository.getUserById(USER_ID_1)).thenReturn(Optional.of(user1));

    Assertions.assertThrows(ValidationException.class, () -> userService.createUser(user1));
  }

  @Test
  public void updateUserTest() {
    var user1 = UserTestUtils.buildUser(USER_ID_1, NAME_1, EMAIL_1);
    var userUpdated = UserTestUtils.buildUser(USER_ID_1, NAME_1, EMAIL_2);

    when(userRepository.getUserById(USER_ID_1)).thenReturn(Optional.of(user1));
    when(userRepository.update(user1)).thenReturn(userUpdated);

    var result = userService.updateUser(user1);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(USER_ID_1, result.getId());
    Assertions.assertEquals(EMAIL_2, result.getEmail());
  }

  @Test
  public void updateUserShouldThrowNotFoundTest() {
    var user1 = UserTestUtils.buildUser(USER_ID_1, NAME_1, EMAIL_1);
    when(userRepository.getUserById(USER_ID_1)).thenReturn(Optional.empty());

    Assertions.assertThrows(NotFoundException.class, () -> userService.updateUser(user1));
  }

  @Test
  public void deleteUserTest() {
    var user1 = UserTestUtils.buildUser(USER_ID_1, NAME_1, EMAIL_1);

    when(userRepository.getUserById(USER_ID_1)).thenReturn(Optional.of(user1));
    when(userRepository.delete(USER_ID_1)).thenReturn(true);

    var result = userService.deleteUser(USER_ID_1);
    Assertions.assertTrue(result);
  }

  @Test
  public void deleteUserShouldThrowNotFoundTest() {
    when(userRepository.getUserById(USER_ID_1)).thenReturn(Optional.empty());

    Assertions.assertThrows(NotFoundException.class, () -> userService.deleteUser(USER_ID_1));
  }

}
