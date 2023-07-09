package com.magnilsonti.treinamentoudemy.services.impl;

import com.magnilsonti.treinamentoudemy.domain.User;
import com.magnilsonti.treinamentoudemy.domain.dto.UserDTO;
import com.magnilsonti.treinamentoudemy.repositories.UserRepository;
import com.magnilsonti.treinamentoudemy.services.UserService;
import com.magnilsonti.treinamentoudemy.services.exeptions.DataIntegratyViolationException;
import com.magnilsonti.treinamentoudemy.services.exeptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


  @Autowired
  private UserRepository repository;

  @Autowired
  private ModelMapper mapper;

  @Override
  public User findById(Integer id) {
    Optional<User> user = repository.findById(id);
    return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
  }

  public List<User> findAll() {
    return repository.findAll();
  }

  @Override
  public User create(UserDTO user) {
    findByEmail(user);
    return repository.save(mapper.map(user, User.class));
  }

  @Override
  public User update(UserDTO userDTO) {
    findByEmail(userDTO);
    return repository.save(mapper.map(userDTO, User.class));
  }

  @Override
  public void delete(Integer id) {
    findById(id);
    repository.deleteById(id);
  }

  private void findByEmail(UserDTO userDTO) {
    Optional<User> user = repository.findByEmail(userDTO.getEmail());
    if (user.isPresent()) {
      throw new DataIntegratyViolationException("Email já cadastrado");
    }
  }


}
