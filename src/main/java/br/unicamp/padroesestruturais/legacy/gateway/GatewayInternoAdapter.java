package br.unicamp.padroesestruturais.legacy.gateway;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;

public class GatewayInternoAdapter implements GatewayPagamento {

    private final GatewayPagamentoInterno gateway = new GatewayPagamentoInterno();

    @Override
    public ResultadoCobranca cobrar(Pedido pedido,
                                    double valor,
                                    FormaPagamento forma) {

        return gateway.cobrar(
                pedido.getId(),
                pedido.getCliente(),
                valor,
                forma
        );
    }
}