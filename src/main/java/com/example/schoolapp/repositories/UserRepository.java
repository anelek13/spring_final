package com.example.schoolapp.repositories;

import com.example.schoolapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByDeletedAtIsNull();

    List<User> findAllByDeletedAtIsNullAndRole_Id(Long id);

    Optional<User> findByLoginAndDeletedAtIsNull(String login);
}
