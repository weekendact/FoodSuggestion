package com.example.foodsuggestion.Data.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;

    @Column(name = "name_user")
    private String name_user;

    private String passwd_user; // 평문 비밀번호 필드

    private boolean allergy_user;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AllergyInfoEntity> allergyInfoList;

    // id_user의 getter 및 setter
    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    // name_user의 getter 및 setter
    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    // passwd_user의 getter 및 setter
    public String getPasswd_user() {
        return passwd_user;
    }

    public void setPasswd_user(String passwd_user) {
        this.passwd_user = passwd_user;
    }

    // allergy_user의 getter 및 setter
    public boolean isAllergy_user() {
        return allergy_user;
    }

    public void setAllergy_user(boolean allergy_user) {
        this.allergy_user = allergy_user;
    }

    @ElementCollection
    @CollectionTable(name = "user_allergies", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "allergy")
    private List<String> allergies;

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public void addAllergy(String allergy) {
        if (allergies == null) {
            allergies = new ArrayList<>();
        }
        allergies.add(allergy);
    }

    public void removeAllergy(String allergy) {
        if (allergies != null) {
            allergies.remove(allergy);
        }
    }
}
