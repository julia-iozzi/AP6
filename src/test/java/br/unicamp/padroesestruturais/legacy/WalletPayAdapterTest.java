package br.unicamp.padroesestruturais.legacy;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;
import br.unicamp.padroesestruturais.legacy.gateway.WalletPayAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletPayAdapterTest {

    @Test
    void deveProcessarPagamentoComCarteiraDigital() {

        WalletPayAdapter adapter = new WalletPayAdapter();

        Pedido pedido = new Pedido(
                "PED-003",
                "Maria Souza",
                "Assinatura",
                300.0
        );

        ResultadoCobranca resultado = adapter.cobrar(
                pedido,
                300.0,
                FormaPagamento.CARTEIRA_DIGITAL
        );

        assertNotNull(resultado);
    }

    @Test
    void deveProcessarPagamentoValorAlto() {

        WalletPayAdapter adapter = new WalletPayAdapter();

        Pedido pedido = new Pedido(
                "PED-004",
                "Empresa Wallet",
                "Pagamento grande",
                5000.0
        );

        ResultadoCobranca resultado = adapter.cobrar(
                pedido,
                5000.0,
                FormaPagamento.CARTEIRA_DIGITAL
        );

        assertNotNull(resultado);
    }
}