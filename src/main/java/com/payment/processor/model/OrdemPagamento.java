package com.payment.processor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
@Entity
public class OrdemPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long idVendedor;
    @ManyToOne
    private Produto produto;
    private Double quantidade;
    private BigDecimal valor;
    private Date added_at;
}
