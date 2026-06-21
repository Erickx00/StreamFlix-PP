package factory;

import java.util.Locale;

public class PlanoFactory {

    public static Plano criarPlano(String tipo) {

        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("Tipo de plano não informado.");
        }

        return switch (tipo.trim().toLowerCase()) {
            case "basico", "básico", "1" -> new PlanoBasico();
            case "standard", "2" -> new PlanoStandard();
            case "premium", "3" -> new PlanoPremium();
            default -> throw new IllegalArgumentException(
                    "Plano desconhecido: " + tipo);
        };
    }
}
