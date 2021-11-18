package com.algaworks.algafoodapi.domain.exception;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public FotoProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public FotoProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
        this(String.format("Não existe um cadastro de foto do produto " +
                "com o código %d para o restaurante de código %d", produtoId, restauranteId));
    }
}
