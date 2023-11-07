package com.example.foodsuggestion.Data.Repository;

import com.example.foodsuggestion.Data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.name_user = :name_user")
    UserEntity findByName_user(@Param("name_user") String name_user);
}
