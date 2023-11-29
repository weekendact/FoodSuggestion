package com.example.foodsuggestion.Data.Repository;

import com.example.foodsuggestion.Data.Entity.MenuEntity;
import com.example.foodsuggestion.Data.Entity.StoreEntity;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

    MenuEntity findByCategory(String category);

    List<MenuEntity> findByCategoryIn(List<String> category);

    List<MenuEntity> findByStore(StoreEntity store);

}