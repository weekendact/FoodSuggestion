package com.example.foodsuggestion.Controller;

import com.example.foodsuggestion.Data.DTO.StoreInfoDTO;
import com.example.foodsuggestion.Data.Entity.MenuEntity;
import com.example.foodsuggestion.Data.Entity.StoreEntity;
import com.example.foodsuggestion.Data.Repository.MenuRepository;
import com.example.foodsuggestion.Data.Repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stores")
public class StoreController {
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public StoreController(StoreRepository storeRepository, MenuRepository menuRepository) {

        this.storeRepository = storeRepository;
        this.menuRepository = menuRepository;
    }

    // http://localhost:8080/stores/getStoreInfoByCategories?foodindetail=치킨&?foodindetail=피자
    // 커피가 주 메뉴인 음식점 이름, 거리, 전화번호 반환 GET방식
    @GetMapping(value = "/getStoreInfoByCategories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StoreInfoDTO>> getStoreInfoByCategories(@RequestParam(name = "foodindetail") List<String> foodInDetails) {
        // store 테이블에서 선택한 카테고리에 속하는 매장 정보 검색
        List<StoreEntity> storeEntities = storeRepository.findStoreInfoByFoodindetailIn(foodInDetails);

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



    @PostMapping("/searchStores")
    public ResponseEntity<List<StoreInfoDTO>> searchStores(@RequestBody List<String> searchKeywords) {
        // store 테이블에서 typeofstore로 검색
        List<StoreEntity> stores = storeRepository.findByTypeofstoreIn(searchKeywords);

        if (stores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // 중복된 음식점 이름을 찾기 위한 Set
        Set<String> uniqueStoreNames = new HashSet<>();

        // store 테이블에서 검색된 각각의 매장에 대해 추가 정보 검색
        List<StoreInfoDTO> storeInfoDTOs = new ArrayList<>();

        for (StoreEntity store : stores) {
            // 중복된 음식점 이름인 경우 건너뜀
            if (!uniqueStoreNames.add(store.getName_store())) {
                continue;
            }

            // menu_table 테이블에서 해당 음식점의 메뉴들 가져오기
            List<MenuEntity> menus = menuRepository.findByStore(store);

            // menu_table 테이블에서 검색된 각각의 메뉴에 대해 추가 정보 검색
            List<String> foodInDetails = menus.stream()
                    .map(MenuEntity::getFoodindetail)
                    .collect(Collectors.toList());

            // 필요한 정보를 담아 StoreInfoDTO 객체로 반환
            StoreInfoDTO storeInfoDTO = new StoreInfoDTO();
            storeInfoDTO.setName_store(store.getName_store());
            storeInfoDTO.setDistance_store(store.getDistance_store());
            storeInfoDTO.setPhonenum(store.getPhonenum());
            storeInfoDTO.setFoodIndetail(foodInDetails);

            storeInfoDTOs.add(storeInfoDTO);
        }

        return ResponseEntity.ok(storeInfoDTOs);
    }

}




