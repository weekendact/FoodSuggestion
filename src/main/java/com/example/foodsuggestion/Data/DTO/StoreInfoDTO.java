package com.example.foodsuggestion.Data.DTO;

import java.util.List;

public class StoreInfoDTO {
    private String name_store;
    private Double distance_store;
    private String phonenum;
    private List<String> foodIndetail;

//    private String foodindetail;

    public StoreInfoDTO() {
        // 기본 생성자
    }

    public String getName_store() {
        return name_store;
    }

    public void setName_store(String name_store) {
        this.name_store = name_store;
    }

    public Double getDistance_store() {
        return distance_store;
    }

    public void setDistance_store(Double distance_store) {
        this.distance_store = distance_store;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public List<String> getFoodIndetail() { return foodIndetail; }

    public void setFoodIndetail(List<String> foodIndetail) { this.foodIndetail = foodIndetail; }
}
