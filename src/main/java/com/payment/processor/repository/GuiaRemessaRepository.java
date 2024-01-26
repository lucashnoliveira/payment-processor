package com.payment.processor.repository;

import com.payment.processor.model.GuiaRemessa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuiaRemessaRepository extends JpaRepository<GuiaRemessa, Long> {
}