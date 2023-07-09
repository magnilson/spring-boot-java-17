package com.magnilsonti.treinamentoudemy.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import com.magnilsonti.treinamentoudemy.domain.User;
import com.magnilsonti.treinamentoudemy.domain.dto.UserDTO;
import com.magnilsonti.treinamentoudemy.repositories.UserRepository;
import com.magnilsonti.treinamentoudemy.services.exeptions.DataIntegratyViolationException;
import com.magnilsonti.treinamentoudemy.services.exeptions.ObjectNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {

  public static final Integer ID = 1;
  public static final String NAME = "teste";
  public static final String MAIL = "teste@teste.com";
  public static final String PASSWORD = "123";
  public static final String EMAIL_JA_CADASTRADO = "Email já cadastrado";
  public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";

  @InjectMocks
  private UserServiceImpl service;

  @Mock
  private UserRepository repository;

  @Mock
  private ModelMapper mapper;

  private User user;
  private UserDTO userDTO;
  private Optional<User> optionalUser;

  @BeforeEach
  void setUp() {
    openMocks(this);
    startUser();
  }

  private void startUser() {
    user = new User(ID, NAME, MAIL, PASSWORD);
    userDTO = new UserDTO(ID, NAME, MAIL, PASSWORD);
    optionalUser = Optional.of(new User(ID, NAME, MAIL, PASSWORD));
  }

  @Test
  void whenFindByIdThenReturnAnUserInstance() {

    when(repository.findById(anyInt())).thenReturn(optionalUser);

    User response = service.findById(ID);

    assertNotNull(response);
    assertEquals(User.class, response.getClass());
    assertEquals(ID, response.getId());
    assertEquals(NAME, response.getName());
    assertEquals(MAIL, response.getEmail());
    assertEquals(PASSWORD, response.getPassword());

  }

  @Test
  void wheFindByIdThenReturnAnObjectNotFoundException() {
    when(repository.findById(anyInt())).thenThrow(
        new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

    Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
      service.findById(ID);
    });

    assertEquals(OBJETO_NAO_ENCONTRADO, exception.getMessage());

  }


  @Test
  void whenFindAllThenReturnAnListOfUsers() {
    when(repository.findAll()).thenReturn(List.of(user));
    List<User> response = service.findAll();

    assertNotNull(response);
    assertEquals(1, response.size());
    assertEquals(User.class, response.get(0).getClass());
    assertEquals(ID, response.get(0).getId());
    assertEquals(NAME, response.get(0).getName());
    assertEquals(MAIL, response.get(0).getEmail());
    assertEquals(PASSWORD, response.get(0).getPassword());

  }

  @Test
  void whenCreateThenReturnSuccess() {
    when(repository.save(any())).thenReturn(user);

    User response = service.create(userDTO);

    assertNotNull(response);
    assertEquals(User.class, response.getClass());
    assertEquals(ID, response.getId());
    assertEquals(NAME, response.getName());
    assertEquals(MAIL, response.getEmail());
    assertEquals(PASSWORD, response.getPassword());

  }

  @Test
  void whenCreateThenReturnAnDataIntegrityViolationException() {
    when(repository.findByEmail(anyString())).thenThrow(
        new DataIntegratyViolationException(EMAIL_JA_CADASTRADO));

    Exception exception = assertThrows(DataIntegratyViolationException.class, () -> {
      service.create(userDTO);
    });

    assertEquals(EMAIL_JA_CADASTRADO, exception.getMessage());

  }

  @Test
  void whenUpdateThenReturnSuccess() {
    when(repository.save(any())).thenReturn(user);

    User response = service.update(userDTO);

    assertNotNull(response);
    assertEquals(User.class, response.getClass());
    assertEquals(ID, response.getId());
    assertEquals(NAME, response.getName());
    assertEquals(MAIL, response.getEmail());
    assertEquals(PASSWORD, response.getPassword());

  }

  @Test
  void whenUpdateThenReturnAnDataIntegrityViolationException() {
    when(repository.findByEmail(anyString())).thenThrow(
        new DataIntegratyViolationException(EMAIL_JA_CADASTRADO));

    Exception exception = assertThrows(DataIntegratyViolationException.class, () -> {
      service.update(userDTO);
    });

    assertEquals(EMAIL_JA_CADASTRADO, exception.getMessage());

  }

  @Test
  void deleteWithSuccess() {
    when(repository.findById(anyInt())).thenReturn(optionalUser);
    doNothing().when(repository).deleteById(anyInt());
    service.delete(ID);
    verify(repository, times(1)).deleteById(anyInt());
  }

  @Test
  void deleteWithObjectNotFoundException() {
    when(repository.findById(anyInt())).thenThrow(
        new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

    Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
      service.delete(ID);
    });

    assertEquals(OBJETO_NAO_ENCONTRADO, exception.getMessage());
  }

  @Test
  void whenFindByEmailThenThrowsDataIntegrityViolationException() {
    when(repository.findByEmail(anyString())).thenReturn(optionalUser);
    DataIntegratyViolationException exception = assertThrows(DataIntegratyViolationException.class,
        () -> service.create(userDTO));
    assertEquals(EMAIL_JA_CADASTRADO, exception.getMessage());
  }
}