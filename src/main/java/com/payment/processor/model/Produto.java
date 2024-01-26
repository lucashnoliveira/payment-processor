package com.payment.processor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@Entity
public class Produto {
    public Produto(){}
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    String nome;
    String tipo;
    @Pattern(regexp = "(?i)fisico|online", message = "A categoria deve ser 'fisico' ou 'online'")
    String categoria;
    BigDecimal valor;
}
