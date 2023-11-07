package com.example.foodsuggestion.Data.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "store")
public class StoreEntity {

    @Column(name = "id_store")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_store;
    private String name_store;
    private Double distance_store;
    private String typeofstore;
    private String foodindetail;

    // 생성자, 게터 및 세터

    public StoreEntity() {
    }

    public StoreEntity(String name_store, Double distance_store) {
        this.name_store = name_store;
        this.distance_store = distance_store;
    }

    public Long getId_store() {
        return id_store;
    }

    public void setId_store(Long id_store) {
        this.id_store = id_store;
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

    public String getTypeofstore() {
        return typeofstore;
    }

    public void setTypeofstore(String typeofstore) { this.typeofstore = typeofstore; }

    public String getFoodindetail() {
        return foodindetail;
    }

    public void setFoodindetail(String foodindetail) { this.foodindetail = foodindetail; }
}
