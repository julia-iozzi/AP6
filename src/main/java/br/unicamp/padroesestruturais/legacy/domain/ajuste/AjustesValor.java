package br.unicamp.padroesestruturais.legacy.domain.ajuste;

import java.util.Arrays;
import java.util.List;

public class AjustesValor {

    private final List<AjusteValor> ajustes;

    public AjustesValor(AjusteValor[] ajustes) {
        this.ajustes = Arrays.asList(ajustes);
    }

    public double aplicar(double valorBase) {
        double valor = valorBase;
        for (AjusteValor ajuste : ajustes) {
            valor = ajuste.aplicar(valor);
        }
        return valor;
    }
}
