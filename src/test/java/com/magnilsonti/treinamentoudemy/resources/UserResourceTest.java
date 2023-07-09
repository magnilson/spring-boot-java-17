package com.magnilsonti.treinamentoudemy.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.magnilsonti.treinamentoudemy.domain.User;
import com.magnilsonti.treinamentoudemy.domain.dto.UserDTO;
import com.magnilsonti.treinamentoudemy.services.UserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
class UserResourceTest {

  public static final Integer ID = 1;
  public static final String NAME = "teste";
  public static final String MAIL = "teste@teste.com";
  public static final String PASSWORD = "123";
  public static final String EMAIL_JA_CADASTRADO = "Email já cadastrado";
  public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";

  @InjectMocks
  private UserResource userResource;

  @Mock
  private ModelMapper mapper;

  @Mock
  private UserService userService;

  private User user;
  private UserDTO userDTO;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startUser();
  }

  private void startUser() {
    user = new User(ID, NAME, MAIL, PASSWORD);
    userDTO = new UserDTO(ID, NAME, MAIL, PASSWORD);
  }

  @Test
  void whenFindByIdThenReturnSuccess() {

    when(userService.findById(anyInt())).thenReturn(user);
    when(mapper.map(any(), any())).thenReturn(userDTO);
    ResponseEntity<UserDTO> response = userResource.findById(ID);

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(UserDTO.class, response.getBody().getClass());

    assertEquals(ID, response.getBody().getId());
    assertEquals(NAME, response.getBody().getName());
    assertEquals(MAIL, response.getBody().getEmail());
    assertEquals(PASSWORD, response.getBody().getPassword());

  }

  @Test
  void whenFindAllThenReturnAListOfUserDTO() {
    when(userService.findAll()).thenReturn(List.of(user));
    when(mapper.map(any(), any())).thenReturn(userDTO);

    ResponseEntity<List<UserDTO>> response = userResource.findAll();

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());

    assertEquals(UserDTO.class, response.getBody().get(0).getClass());
  }

  @Test
  void whenCreateThenReturnCreated() {
    when(userService.create(any())).thenReturn(user);
    ResponseEntity<UserDTO> response = userResource.create(userDTO);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void whenUpdateThenReturnSuccess() {
    when(userService.update(userDTO)).thenReturn(user);
    when(mapper.map(any(), any())).thenReturn(userDTO);

    ResponseEntity<UserDTO> response = userResource.update(ID, userDTO);
    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());

    assertEquals(ID, response.getBody().getId());
    assertEquals(NAME, response.getBody().getName());
    assertEquals(MAIL, response.getBody().getEmail());

  }

  @Test
  void whenDeleteThenReturnSuccess() {
    doNothing().when(userService).delete(anyInt());
    ResponseEntity<UserDTO> response = userResource.delete(ID);

    assertNotNull(response);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(userService, times(1)).delete(anyInt());

  }
}