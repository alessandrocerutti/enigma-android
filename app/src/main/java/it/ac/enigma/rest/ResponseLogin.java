package it.ac.enigma.rest;


public class ResponseLogin {
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ResponseLogin{" +
                "token='" + token + '\'' +
                '}';
    }
}
