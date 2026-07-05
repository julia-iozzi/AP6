package br.unicamp.padroesestruturais.legacy.domain.ajuste;

public class SeguroAjuste implements AjusteValor {

    private static final double VALOR_SEGURO = 4.90;

    @Override
    public double aplicar(double valor) {
        return valor + VALOR_SEGURO;
    }
}
