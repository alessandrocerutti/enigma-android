package it.ac.enigma.rest;

import it.ac.enigma.utility.Utility;

public class LoginRequestBody {
    String username;
    String password;

    public LoginRequestBody(String username, String password) {
        setUsername(username);
        setPassword(Utility.hashPassword(password));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequestBody{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
