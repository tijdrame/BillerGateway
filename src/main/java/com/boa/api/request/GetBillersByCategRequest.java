package com.boa.api.request;

public class GetBillersByCategRequest {
    private String country;
    private String categorie;

    public GetBillersByCategRequest() {
    }

    public GetBillersByCategRequest(String country, String categorie) {
        this.country = country;
        this.categorie = categorie;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public GetBillersByCategRequest country(String country) {
        setCountry(country);
        return this;
    }

    public GetBillersByCategRequest categorie(String categorie) {
        setCategorie(categorie);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " country='" + getCountry() + "'" +
            ", categorie='" + getCategorie() + "'" +
            "}";
    }

}
