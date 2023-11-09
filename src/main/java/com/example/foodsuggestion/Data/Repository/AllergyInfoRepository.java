package com.example.foodsuggestion.Data.Repository;

import com.example.foodsuggestion.Data.Entity.AllergyInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyInfoRepository extends JpaRepository<AllergyInfoEntity, Long> {
    // 여기에 필요한 사용자별 알레르기 정보 조회 등 다양한 메서드를 추가할 수 있습니다.
}
