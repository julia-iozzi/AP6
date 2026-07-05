package br.unicamp.padroesestruturais.legacy.domain.ajuste;

public class TaxaEmissaoNotaFiscalAjuste implements AjusteValor {

    private static final double VALOR_EMISSAO_NOTA_FISCAL = 2.50;

    @Override
    public double aplicar(double valor) {
        return valor + VALOR_EMISSAO_NOTA_FISCAL;
    }
}
