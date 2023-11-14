package com.example.foodsuggestion.Controller;

import com.example.foodsuggestion.Data.DTO.StoreInfoDTO;
import com.example.foodsuggestion.Data.Entity.StoreEntity;
import com.example.foodsuggestion.Data.Repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {
    private final StoreRepository storeRepository;

    @Autowired
    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    // http://localhost:8080/stores/getStoreInfo?foodindetail=커피
    // 커피가 주 메뉴인 음식점 이름, 거리, 전화번호 반환 GET방식
    @GetMapping(value = "/getStoreInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StoreInfoDTO>> getStoreInfo(@RequestParam(name = "foodindetail") String foodindetail) {
        List<StoreEntity> storeEntities = storeRepository.findStoreInfoByFoodindetail(foodindetail);

        if (storeEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // 선택한 필드를 가지고 있는 DTO를 생성하고, 응답을 위해 필드를 설정
        List<StoreInfoDTO> storeInfoDTOs = new ArrayList<>();
        for (StoreEntity entity : storeEntities) {
            StoreInfoDTO dto = new StoreInfoDTO();
            dto.setName_store(entity.getName_store());
            dto.setDistance_store(entity.getDistance_store());
            dto.setPhonenum(entity.getPhonenum());
            storeInfoDTOs.add(dto);
        }

        return ResponseEntity.ok(storeInfoDTOs);
    }
}



