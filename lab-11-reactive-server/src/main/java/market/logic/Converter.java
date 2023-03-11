package market.logic;

import market.model.Wallet;

public class Converter {

    public static final double RUBBLE_DOLLAR_COEF = 75.94f;
    public static final double RUBBLE_EURO_COEF = 80.40f;

    public static double convertTo(Wallet walletFrom, double value, Wallet walletTo) {
        if (walletFrom == walletTo) {
            return value;
        }
        double valueInRubbles = value;
        if (walletFrom == Wallet.DOLLAR) {
            valueInRubbles = value * RUBBLE_DOLLAR_COEF;
        } else if (walletFrom == Wallet.EURO) {
            valueInRubbles = value * RUBBLE_EURO_COEF;
        }

        if (walletTo == Wallet.DOLLAR) {
            return valueInRubbles / RUBBLE_DOLLAR_COEF;
        } else if (walletTo == Wallet.EURO) {
            return valueInRubbles / RUBBLE_EURO_COEF;
        }
        return valueInRubbles;
    }
}
