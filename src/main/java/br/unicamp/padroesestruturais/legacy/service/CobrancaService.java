package br.unicamp.padroesestruturais.legacy.service;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;
import br.unicamp.padroesestruturais.legacy.domain.ajuste.AjusteValor;
import br.unicamp.padroesestruturais.legacy.domain.ajuste.AjustesValor;
import br.unicamp.padroesestruturais.legacy.domain.ajuste.DescontoFidelidadeAjuste;
import br.unicamp.padroesestruturais.legacy.domain.ajuste.JurosParcelamentoAjuste;
import br.unicamp.padroesestruturais.legacy.domain.ajuste.SeguroAjuste;
import br.unicamp.padroesestruturais.legacy.domain.ajuste.TaxaInternacionalAjuste;
import br.unicamp.padroesestruturais.legacy.gateway.GatewayInternoAdapter;
import br.unicamp.padroesestruturais.legacy.gateway.GatewayPagamento;
import br.unicamp.padroesestruturais.legacy.gateway.PaySecureAdapter;
import br.unicamp.padroesestruturais.legacy.gateway.WalletPayAdapter;

import java.util.ArrayList;
import java.util.List;

public class CobrancaService {

    public ResultadoCobranca cobrar(Pedido pedido, FormaPagamento forma,
                                    boolean aplicarDescontoFidelidade,
                                    boolean aplicarJurosParcelamento,
                                    boolean aplicarTaxaInternacional,
                                    boolean aplicarSeguro) {

        AjustesValor ajustes = criarAjustes(
                aplicarDescontoFidelidade,
                aplicarJurosParcelamento,
                aplicarTaxaInternacional,
                aplicarSeguro
        );

        double valorFinal = calcularValorFinal(pedido.getValorBase(), ajustes);

        GatewayPagamento gateway = obterGateway(forma);

        return gateway.cobrar(
                pedido,
                valorFinal,
                forma
        );
    }

    public ResultadoCobranca cobrar(Pedido pedido, FormaPagamento forma, AjusteValor... ajustes) {
        double valorFinal = calcularValorFinal(pedido.getValorBase(), ajustes);

        GatewayPagamento gateway = obterGateway(forma);

        return gateway.cobrar(
                pedido,
                valorFinal,
                forma
        );
    }

    public List<ResultadoCobranca> cobrarEmLote(List<Pedido> pedidos,
                                                FormaPagamento forma,
                                                boolean aplicarDescontoFidelidade,
                                                boolean aplicarJurosParcelamento,
                                                boolean aplicarTaxaInternacional,
                                                boolean aplicarSeguro) {

        List<ResultadoCobranca> resultados = new ArrayList<>();

        GatewayPagamento gateway = obterGateway(forma);

        AjustesValor ajustes = criarAjustes(
                aplicarDescontoFidelidade,
                aplicarJurosParcelamento,
                aplicarTaxaInternacional,
                aplicarSeguro
        );

        for (Pedido pedido : pedidos) {

            double valorFinal = calcularValorFinal(pedido.getValorBase(), ajustes);

            resultados.add(
                    gateway.cobrar(pedido, valorFinal, forma)
            );
        }

        return resultados;
    }

    private GatewayPagamento obterGateway(FormaPagamento forma) {

        if (forma == null) {
            throw new IllegalArgumentException("Forma de pagamento não suportada.");
        }
    
        switch (forma) {
    
            case BOLETO:
            case PIX:
                return new GatewayInternoAdapter();
    
            case CARTAO_CREDITO:
                return new PaySecureAdapter();
    
            case CARTEIRA_DIGITAL:
                return new WalletPayAdapter();
    
            default:
                throw new IllegalArgumentException(
                        "Forma de pagamento não suportada: " + forma
                );
        }
    }

    public List<ResultadoCobranca> cobrarEmLote(List<Pedido> pedidos,
                                                FormaPagamento forma,
                                                AjusteValor... ajustes) {

        List<ResultadoCobranca> resultados = new ArrayList<>();

        GatewayPagamento gateway = obterGateway(forma);

        for (Pedido pedido : pedidos) {
            double valorFinal = calcularValorFinal(pedido.getValorBase(), ajustes);
            resultados.add(gateway.cobrar(pedido, valorFinal, forma));
        }

        return resultados;
    }

    public double calcularValorFinal(double valorBase,
                                     boolean aplicarDescontoFidelidade,
                                     boolean aplicarJurosParcelamento,
                                     boolean aplicarTaxaInternacional,
                                     boolean aplicarSeguro) {
        return calcularValorFinal(valorBase, criarAjustes(
                aplicarDescontoFidelidade,
                aplicarJurosParcelamento,
                aplicarTaxaInternacional,
                aplicarSeguro
        ));
    }

    public double calcularValorFinal(double valorBase, AjusteValor... ajustes) {
        return new AjustesValor(ajustes).aplicar(valorBase);
    }

    public double calcularValorFinal(double valorBase, AjustesValor ajustes) {
        return ajustes.aplicar(valorBase);
    }

    private AjustesValor criarAjustes(boolean aplicarDescontoFidelidade,
                                      boolean aplicarJurosParcelamento,
                                      boolean aplicarTaxaInternacional,
                                      boolean aplicarSeguro) {
        List<AjusteValor> ajustes = new ArrayList<>();

        if (aplicarDescontoFidelidade) {
            ajustes.add(new DescontoFidelidadeAjuste());
        }

        if (aplicarJurosParcelamento) {
            ajustes.add(new JurosParcelamentoAjuste());
        }

        if (aplicarTaxaInternacional) {
            ajustes.add(new TaxaInternacionalAjuste());
        }

        if (aplicarSeguro) {
            ajustes.add(new SeguroAjuste());
        }

        return new AjustesValor(ajustes.toArray(new AjusteValor[0]));
    }
}