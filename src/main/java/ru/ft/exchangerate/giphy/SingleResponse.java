package ru.ft.exchangerate.giphy;

public class SingleResponse {
    public GifObject data;
    public MetaObject meta;

    public static class GifObject {
        public String id;
    }

    public static class MetaObject {
        public String msg;
        public Integer status;
    }
}
