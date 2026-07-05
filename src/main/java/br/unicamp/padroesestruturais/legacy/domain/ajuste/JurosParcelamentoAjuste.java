package br.unicamp.padroesestruturais.legacy.domain.ajuste;

public class JurosParcelamentoAjuste implements AjusteValor {

    private static final double TAXA_JUROS_PARCELAMENTO = 0.0299;

    @Override
    public double aplicar(double valor) {
        return valor + (valor * TAXA_JUROS_PARCELAMENTO);
    }
}
