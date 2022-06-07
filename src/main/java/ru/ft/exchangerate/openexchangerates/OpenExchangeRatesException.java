package ru.ft.exchangerate.openexchangerates;

public class OpenExchangeRatesException extends RuntimeException {
    public static final OpenExchangeRatesException NO_CURRENCY_FOUND = new OpenExchangeRatesException("No currency found");

    public OpenExchangeRatesException() {
        super();
    }

    public OpenExchangeRatesException(String message) {
        super(message);
    }

    public OpenExchangeRatesException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenExchangeRatesException(Throwable cause) {
        super(cause);
    }

    protected OpenExchangeRatesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
