package com.oficinadobrito.api.utils.validators.cpf;

public class CPFUtils {
    public static boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11) {
            return false;
        }
        if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") || cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999")) {
            return false;
        }
        int sum1 = 0;
        for (int i = 0; i < 9; i++) {
            sum1 += Integer.parseInt(String.valueOf(cpf.charAt(i))) * (10 - i);
        }
        int firstDigit = 11 - (sum1 % 11);
        if (firstDigit == 10 || firstDigit == 11) {
            firstDigit = 0;
        }
        if (firstDigit != Integer.parseInt(String.valueOf(cpf.charAt(9)))) {
            return false;
        }

        int sum2 = 0;
        for (int i = 0; i < 10; i++) {
            sum2 += Integer.parseInt(String.valueOf(cpf.charAt(i))) * (11 - i);
        }
        int secondDigit = 11 - (sum2 % 11);
        if (secondDigit == 10 || secondDigit == 11) {
            secondDigit = 0;
        }
        return secondDigit == Integer.parseInt(String.valueOf(cpf.charAt(10)));
    }
}
