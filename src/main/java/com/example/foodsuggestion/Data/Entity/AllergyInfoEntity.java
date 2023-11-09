package com.example.foodsuggestion.Data.Entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "allergy_info")
public class AllergyInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_allergy")
    private Long idAllergy;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "allergy_name")
    private String allergyName;


    public Long getIdAllergy() {
        return idAllergy;
    }

    public void setIdAllergy(Long idAllergy) {
        this.idAllergy = idAllergy;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAllergyName() {
        return allergyName;
    }

    public void setAllergyName(String allergyName) {
        this.allergyName = allergyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllergyInfoEntity that = (AllergyInfoEntity) o;
        return Objects.equals(idAllergy, that.idAllergy) && Objects.equals(userId, that.userId) && Objects.equals(allergyName, that.allergyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAllergy, userId, allergyName);
    }
}
