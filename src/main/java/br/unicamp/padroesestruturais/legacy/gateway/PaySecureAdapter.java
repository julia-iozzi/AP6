package br.unicamp.padroesestruturais.legacy.gateway;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;
import br.unicamp.padroesestruturais.legacy.externo.GatewayIndisponivelException;
import br.unicamp.padroesestruturais.legacy.externo.PaySecureGateway;
import br.unicamp.padroesestruturais.legacy.externo.TransacaoExterna;

import java.util.HashMap;
import java.util.Map;

public class PaySecureAdapter implements GatewayPagamento {

    private final PaySecureGateway gateway = new PaySecureGateway();

    @Override
    public ResultadoCobranca cobrar(Pedido pedido,
        double valor,
        FormaPagamento forma) {
        Map<String, Object> dados = new HashMap<>();

        dados.put("orderId", pedido.getId());
        dados.put("customerName", pedido.getCliente());
        dados.put("amount", valor);
        dados.put("currency", "BRL");

        try {

            TransacaoExterna transacao =
                    gateway.processarTransacao(dados);

            String status =
                    transacao.getCodigoStatus() == 200 ?
                            "APROVADA" :
                            "RECUSADA";

            return new ResultadoCobranca(
                    pedido.getId(),
                    valor,
                    status,
                    transacao.getReferenciaExterna(),
                    FormaPagamento.CARTAO_CREDITO
            );

        } catch (GatewayIndisponivelException e) {

            return new ResultadoCobranca(
                    pedido.getId(),
                    valor,
                    "RECUSADA",
                    null,
                    FormaPagamento.CARTAO_CREDITO
            );
        }
    }
}