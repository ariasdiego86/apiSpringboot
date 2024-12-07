package com.company.books.backend.response.token;

public class TokenResponse {

    private String jwToken;

    public TokenResponse(String jwToken){
        this.jwToken = jwToken;
    }

    public String getJwToken() { return jwToken; }

    public void setJwToken(String jwToken) { this.jwToken = jwToken;}

}
