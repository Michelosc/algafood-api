package com.algaworks.algafoodapi;

import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CadastroCozinhaIntegrationTests {
	
	@Autowired
	CadastroCozinhaService cadastroCozinha;

	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {

		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
		
	}

	@Test
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {

		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado =
				Assertions.assertThrows(ConstraintViolationException.class, () -> {
			cadastroCozinha.salvar(novaCozinha);
		});
		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		EntidadeEmUsoException erroEsperado =
				Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
					cadastroCozinha.excluir(1L);
				});
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		EntidadeNaoEncontradaException erroEsperado =
				Assertions.assertThrows(EntidadeNaoEncontradaException.class, () -> {
					cadastroCozinha.excluir(1000L);
				});
	}

}
