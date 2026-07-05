package br.unicamp.padroesestruturais.legacy;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;
import br.unicamp.padroesestruturais.legacy.gateway.PaySecureAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaySecureAdapterTest {

    @Test
    void deveProcessarPagamentoAprovado() {

        PaySecureAdapter adapter = new PaySecureAdapter();

        Pedido pedido = new Pedido(
                "PED-001",
                "Joao Silva",
                "Compra teste",
                500.0
        );

        ResultadoCobranca resultado = adapter.cobrar(
                pedido,
                500.0,
                FormaPagamento.CARTAO_CREDITO
        );

        assertNotNull(resultado);
    }

    @Test
    void deveProcessarPagamentoRecusado() {

        PaySecureAdapter adapter = new PaySecureAdapter();

        Pedido pedido = new Pedido(
                "PED-002",
                "Empresa X",
                "Compra alta",
                15000.0
        );

        ResultadoCobranca resultado = adapter.cobrar(
                pedido,
                15000.0,
                FormaPagamento.CARTAO_CREDITO
        );

        assertNotNull(resultado);
    }
}