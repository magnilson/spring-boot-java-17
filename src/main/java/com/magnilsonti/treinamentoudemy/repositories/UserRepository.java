package com.magnilsonti.treinamentoudemy.repositories;

import com.magnilsonti.treinamentoudemy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
}
