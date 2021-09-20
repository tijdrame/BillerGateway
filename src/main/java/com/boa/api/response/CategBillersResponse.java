package com.boa.api.response;

import java.util.HashSet;
import java.util.Set;

public class CategBillersResponse extends GenericResponse {
    private Set<String> categories = new HashSet<>();

    

    public CategBillersResponse() {
    }

    public CategBillersResponse(Set<String> categories) {
        this.categories = categories;
    }

    public Set<String> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public CategBillersResponse categories(Set<String> categories) {
        setCategories(categories);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " categories='" + getCategories() + "'" +
            "}";
    }

}
