package com.example.foodsuggestion.Data.Repository;

import com.example.foodsuggestion.Data.Entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    List<StoreEntity> findStoreInfoByFoodindetail(@Param("foodindetail") String foodindetail);
}

