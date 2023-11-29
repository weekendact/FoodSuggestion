package com.example.foodsuggestion.Service;
import com.example.foodsuggestion.Data.Entity.StoreEntity;
import com.example.foodsuggestion.Data.Repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    public List<StoreEntity> getStoreByFoodInDetailAndTypeofstore(String foodInDetail, String typeofstore) {
        return storeRepository.findByFoodindetailAndTypeofstore(foodInDetail, typeofstore);
    }
}
