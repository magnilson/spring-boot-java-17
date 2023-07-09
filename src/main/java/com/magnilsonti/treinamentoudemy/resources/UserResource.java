package com.magnilsonti.treinamentoudemy.resources;

import com.magnilsonti.treinamentoudemy.domain.User;
import com.magnilsonti.treinamentoudemy.domain.dto.UserDTO;
import com.magnilsonti.treinamentoudemy.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@Tag(name = "Todo API", description = "euismod in pellentesque ...")
public class UserResource {

  public static final String ID = "/{id}";

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private UserService userService;

  @GetMapping(value = "/{id}")
  public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
    return ResponseEntity.ok().body(mapper.map(userService.findById(id), UserDTO.class));
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> findAll() {
    return ResponseEntity.ok()
        .body(userService.findAll().stream().map(x -> mapper.map(x, UserDTO.class)).toList());
  }

  @PostMapping
  public ResponseEntity<UserDTO> create(@RequestBody UserDTO user) {
    return ResponseEntity.ok().body(mapper.map(userService.create(user), UserDTO.class));
  }

  @PostMapping(value = "/{id}")
  public ResponseEntity<UserDTO> update(@PathVariable Integer userId,
      @RequestBody UserDTO userDTO) {
    userDTO.setId(userId);
    User newUserDTO = userService.update(userDTO);
    return ResponseEntity.ok().body(mapper.map(newUserDTO, UserDTO.class));
  }

  @DeleteMapping(value = ID)
  public ResponseEntity<UserDTO> delete(@PathVariable Integer userId) {
    userService.delete(userId);
    return ResponseEntity.noContent().build();
  }
}
