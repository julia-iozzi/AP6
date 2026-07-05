package br.unicamp.padroesestruturais.legacy.domain.ajuste;

public class TaxaInternacionalAjuste implements AjusteValor {

    private static final double TAXA_OPERACAO_INTERNACIONAL = 0.05;

    @Override
    public double aplicar(double valor) {
        return valor + (valor * TAXA_OPERACAO_INTERNACIONAL);
    }
}
