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
    private String libelleCategory;


    public Biller() {
    }

    public Biller(Long billerId, String billerCode, String name, String pays, String channel, String email, String telephone, String status, String address, String logo, String website, String billerCategory, String libelleCategory) {
        this.billerId = billerId;
        this.billerCode = billerCode;
        this.name = name;
        this.pays = pays;
        this.channel = channel;
        this.email = email;
        this.telephone = telephone;
        this.status = status;
        this.address = address;
        this.logo = logo;
        this.website = website;
        this.billerCategory = billerCategory;
        this.libelleCategory = libelleCategory;
    }

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

    public String getLibelleCategory() {
        return this.libelleCategory;
    }

    public void setLibelleCategory(String libelleCategory) {
        this.libelleCategory = libelleCategory;
    }

    public Biller billerId(Long billerId) {
        setBillerId(billerId);
        return this;
    }

    public Biller billerCode(String billerCode) {
        setBillerCode(billerCode);
        return this;
    }

    public Biller name(String name) {
        setName(name);
        return this;
    }

    public Biller pays(String pays) {
        setPays(pays);
        return this;
    }

    public Biller channel(String channel) {
        setChannel(channel);
        return this;
    }

    public Biller email(String email) {
        setEmail(email);
        return this;
    }

    public Biller telephone(String telephone) {
        setTelephone(telephone);
        return this;
    }

    public Biller status(String status) {
        setStatus(status);
        return this;
    }

    public Biller address(String address) {
        setAddress(address);
        return this;
    }

    public Biller logo(String logo) {
        setLogo(logo);
        return this;
    }

    public Biller website(String website) {
        setWebsite(website);
        return this;
    }

    public Biller billerCategory(String billerCategory) {
        setBillerCategory(billerCategory);
        return this;
    }

    public Biller libelleCategory(String libelleCategory) {
        setLibelleCategory(libelleCategory);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " billerId='" + getBillerId() + "'" +
            ", billerCode='" + getBillerCode() + "'" +
            ", name='" + getName() + "'" +
            ", pays='" + getPays() + "'" +
            ", channel='" + getChannel() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", status='" + getStatus() + "'" +
            ", address='" + getAddress() + "'" +
            ", logo='" + getLogo() + "'" +
            ", website='" + getWebsite() + "'" +
            ", billerCategory='" + getBillerCategory() + "'" +
            ", libelleCategory='" + getLibelleCategory() + "'" +
            "}";
    }
    
}