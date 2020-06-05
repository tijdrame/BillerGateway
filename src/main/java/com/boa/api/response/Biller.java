package com.boa.api.response;

/**
 * Biller
 */
public class Biller {

    private Long billerId;
    private String billerCode;
    private String name;
    private String pays;
    private String channel;
    private String email;
    private String telephone;
    private String status;
    private String address;
    private String logo;
    private String website;
    private String billerCategory;

    public Long getBillerId() {
        return this.billerId;
    }

    public void setBillerId(Long billerId) {
        this.billerId = billerId;
    }

    public String getBillerCode() {
        return this.billerCode;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPays() {
        return this.pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBillerCategory() {
        return this.billerCategory;
    }

    public void setBillerCategory(String billerCategory) {
        this.billerCategory = billerCategory;
    }

    @Override
    public String toString() {
        return "{" +
            " billerId='" + billerId + "'" +
            ", billerCode='" + billerCode + "'" +
            ", name='" + name + "'" +
            ", pays='" + pays + "'" +
            ", channel='" + channel + "'" +
            ", email='" + email + "'" +
            ", telephone='" + telephone + "'" +
            ", status='" + status + "'" +
            ", address='" + address + "'" +
            //", logo='" + logo + "'" +
            ", website='" + website + "'" +
            ", billerCategory='" + billerCategory + "'" +
            "}";
    }
}