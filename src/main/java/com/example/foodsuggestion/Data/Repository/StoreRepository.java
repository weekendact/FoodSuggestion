package com.example.foodsuggestion.Data.Repository;

import com.example.foodsuggestion.Data.Entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    List<StoreEntity> findByTypeofstoreAndFoodindetail(String typeofstore, String foodindetail);
}
