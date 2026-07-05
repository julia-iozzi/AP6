package br.unicamp.padroesestruturais.legacy.domain.ajuste;

public class AntecipacaoRecebiveisAjuste implements AjusteValor {

    private static final double TAXA_ANTECPACAO_RECEBIVEIS = 0.015;

    @Override
    public double aplicar(double valor) {
        return valor + (valor * TAXA_ANTECPACAO_RECEBIVEIS);
    }
}
