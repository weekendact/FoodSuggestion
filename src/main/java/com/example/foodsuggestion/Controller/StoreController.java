package com.example.foodsuggestion.Controller;

import com.example.foodsuggestion.Data.Entity.StoreEntity;
import com.example.foodsuggestion.Data.Repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stores")
public class StoreController {
    private final StoreRepository storeRepository;

    @Autowired
    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @GetMapping(value = "/getStoreInfoh", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public StoreEntity getStoreInfoh(@RequestParam(name = "id_store") Long idStore) {
        return storeRepository.findById(idStore).orElse(null);
    }

    @GetMapping("/getStoreInfo")
    public ResponseEntity<List<Long>> getStoreInfo(
            @RequestParam(name = "typeofstore") String typeOfStore,
            @RequestParam(name = "foodindetail") String foodInDetail) {

        // 검색 조건: typeofstore와 foodindetail이 일치하는 가게들을 찾는다.
        List<StoreEntity> stores = storeRepository.findByTypeofstoreAndFoodindetail(typeOfStore, foodInDetail);

        if (stores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // stores에서 id_store만 추출하여 새로운 리스트를 만듭니다.
        List<Long> ids = stores.stream()
                .map(StoreEntity::getId_store)
                .collect(Collectors.toList());

        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

}