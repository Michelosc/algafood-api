package com.algaworks.algafoodapi.domain.repository;

import com.algaworks.algafoodapi.domain.model.FormaPagamento;
import lombok.ast.For;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormaPagamenroRepository extends JpaRepository<FormaPagamento, Long> {

}
