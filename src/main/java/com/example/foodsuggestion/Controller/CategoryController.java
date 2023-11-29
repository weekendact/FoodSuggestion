package com.example.foodsuggestion.Controller;

import com.example.foodsuggestion.Data.Entity.MenuEntity;
import com.example.foodsuggestion.Data.Entity.StoreEntity;
import com.example.foodsuggestion.Data.Repository.MenuRepository;
import com.example.foodsuggestion.Data.Repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
// http://localhost:8080/api/processCategories Post방식
// {
//  "categoryfirst": ["일식"],
//  "categorysecond": ["면"]
// }
public class CategoryController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MenuRepository menuRepository;

    @PostMapping("/processCategories")
    public ResponseEntity<List<String>> processCategories(@RequestBody CategoryRequest request) {
        List<String> categoryfirst = request.getCategoryfirst();
        List<String> categorysecond = request.getCategorysecond();
        Set<String> matchingResults = new HashSet<>();

        // Search the Store table using typeofstore from categoryfirst
        List<StoreEntity> stores = storeRepository.findByTypeofstoreIn(categoryfirst);

        // Check if stores is empty and return appropriate response
        if (stores.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonList("No stores found for the given categories."));
        }

        for (StoreEntity store : stores) {
            // Extract foodindetail from each matching store
            String foodInDetail = store.getFoodindetail().toLowerCase().replaceAll("\\s", ""); // Convert to lowercase and remove whitespaces

            // Search the Menu table using category from categorysecond
            List<MenuEntity> menus = menuRepository.findByCategoryIn(categorysecond);

            // Check if menus is empty and return appropriate response
            if (menus.isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonList("No menus found for the given categories."));
            }

            // Check if any menu matches the foodindetail
            for (MenuEntity menu : menus) {
                String matchingMenuName = menu.getMenu_name().toLowerCase().replaceAll("\\s", ""); // Convert to lowercase and remove whitespaces

                if (foodInDetail.equals(matchingMenuName)) {
                    matchingResults.add(foodInDetail);
                }
            }
        }

        // If matches are found
        if (!matchingResults.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>(matchingResults));
        }

        // If no match is found
        return ResponseEntity.ok(Collections.singletonList("No match found between foodindetail and menu_name."));
    }
}

class CategoryRequest {
    private List<String> categoryfirst;
    private List<String> categorysecond;

    public List<String> getCategoryfirst() {
        return categoryfirst;
    }

    public void setCategoryfirst(List<String> categoryfirst) {
        this.categoryfirst = categoryfirst;
    }

    public List<String> getCategorysecond() {
        return categorysecond;
    }

    public void setCategorysecond(List<String> categorysecond) {
        this.categorysecond = categorysecond;
    }
}