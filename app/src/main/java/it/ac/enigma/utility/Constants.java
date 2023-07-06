package it.ac.enigma.utility;

public class Constants {

    public static class Activity {
        public static final String QRCODE = "QRCODE";
        public static final String PUNTEGGI_PROVE = "PUNTEGGI_PROVE";
        public static final String PUNTEGGI_EXTRA = "PUNTEGGI_EXTRA";
        public static final String PENALITA= "PENALITA";
    }
    public static class EXTRA {
        public static final String IS_PUNTEGGIO_PROVA = "IS_PUNTEGGIO_PROVA";
        public static final String IS_PENALITA = "IS_PENALITA";

    }

    public static class HTTP_PATH {
        public static final String LOGIN = "/auth/login";
        public static final String SQUADRE_IDCACCIA = "/squadra/caccia/%d";
        public static final String PATH_QRCODE = "/step/qrcode/scan";
        public static final String SAVE_PUNTEGGIO_EXTRA = "/punteggio";
    }

    public static class Header {
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String ACCEPT = "Accept";
        public static final String ACCEPT_ENCODING = "Accept-Encoding";
        public static final String JWT_AUTH = "x-jwt-auth";
        public static class Value {
            public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
            public static final String CONTENT_TYPE_ALL = "*/*";
            public static final String ACCEPT_ENCODING_VALUE = "gzip, deflate";
            public static final String ACCEPT_ENCODING_VALUE_LOCAL = "gzip, deflate, br";
        }
    }
}
