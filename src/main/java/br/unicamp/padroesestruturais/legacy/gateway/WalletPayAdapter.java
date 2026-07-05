package br.unicamp.padroesestruturais.legacy.gateway;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;
import br.unicamp.padroesestruturais.legacy.externo.ChargeRequest;
import br.unicamp.padroesestruturais.legacy.externo.ChargeResponse;
import br.unicamp.padroesestruturais.legacy.externo.ChargeStatus;
import br.unicamp.padroesestruturais.legacy.externo.WalletPaySDK;

public class WalletPayAdapter implements GatewayPagamento {

    private final WalletPaySDK walletPay = new WalletPaySDK();

    @Override
    public ResultadoCobranca cobrar(Pedido pedido, double valor, FormaPagamento forma) {

        // O SDK recebe o valor em centavos
        long valorEmCentavos = Math.round(valor * 100);

        ChargeRequest request = new ChargeRequest(
                pedido.getId(),
                pedido.getCliente(),
                valorEmCentavos
        );

        ChargeResponse response = walletPay.charge(request);

        String status;

        if (response.getStatus() == ChargeStatus.CONFIRMED) {
            status = "APROVADA";
        } else {
            status = "RECUSADA";
        }

        return new ResultadoCobranca(
                pedido.getId(),
                valor,
                status,
                response.getWalletTransactionId(),
                forma
        );
    }
}