package me.nguyenn.AuthenticationTest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import me.nguyenn.AuthenticationTest.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}
