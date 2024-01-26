package com.payment.processor.repository;

import com.payment.processor.model.OrdemPagamento;
import com.payment.processor.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemPagamentoRepository extends JpaRepository<OrdemPagamento, Long> {
}