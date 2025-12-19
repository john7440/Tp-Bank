package service;

import java.util.regex.Pattern;

public class ValidationService {
    private static final Pattern PATTERN_NUMERO_COMPTE = Pattern.compile("^FR-\\d{4}-\\d{4}$");

    public static boolean validerNumeroCompte(String numero) {
        return numero != null && PATTERN_NUMERO_COMPTE.matcher(numero).matches();
    }

    public static boolean validerMontant(double montant) {
        return montant > 0;
    }
}
