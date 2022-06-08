package ru.ft.exchangerate.giphy;

public class GiphyException extends RuntimeException {
    public static final GiphyException NOT_FOUND = new GiphyException("No gifs found");

    public GiphyException() {
        super();
    }

    public GiphyException(String message) {
        super(message);
    }

    public GiphyException(String message, Throwable cause) {
        super(message, cause);
    }

    public GiphyException(Throwable cause) {
        super(cause);
    }

    protected GiphyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
