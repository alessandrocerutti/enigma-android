package it.ac.enigma.rest;


import java.util.List;

import it.ac.enigma.model.CacciaDto;

public class ResponseLogin {
    String token;

    List<CacciaDto> cacciaList;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<CacciaDto> getCacciaList() {
        return cacciaList;
    }

    public void setCacciaList(List<CacciaDto> cacciaList) {
        this.cacciaList = cacciaList;
    }

    @Override
    public String toString() {
        return "ResponseLogin{" +
                "token='" + token + '\'' +
                '}';
    }
}
