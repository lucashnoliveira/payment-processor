package com.payment.processor.repository;

import com.payment.processor.model.MidiaDigital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MidiaDigitalRepository extends JpaRepository<MidiaDigital, Long> {
    Optional<MidiaDigital> findByNome(String nome);
}