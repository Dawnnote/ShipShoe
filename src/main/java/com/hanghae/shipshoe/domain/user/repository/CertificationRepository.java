package com.hanghae.shipshoe.domain.user.repository;

import com.hanghae.shipshoe.domain.user.entity.CertificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CertificationRepository extends JpaRepository<CertificationEntity, Long> {


    CertificationEntity findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
