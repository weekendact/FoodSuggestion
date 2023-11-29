package com.example.foodsuggestion.Service;

import com.example.foodsuggestion.Data.Entity.MenuEntity;
import com.example.foodsuggestion.Data.Repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public String getMenuNameFromCategory(String category) {
        MenuEntity menu = menuRepository.findByCategory(category);
        return menu != null ? menu.getMenu_name() : null;
    }
}
