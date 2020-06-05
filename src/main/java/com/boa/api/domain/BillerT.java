package com.boa.api.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
//import java.time.LocalDate;

/**
 * A BillerT.
 */
@Entity
@Table(name = "BOA_BILM_BILLER_T")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BillerT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "BILLER_ID")
    private Long id;

    @Column(name = "BILLER_CODE")
    private String billerCode;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PAYS")
    private String pays;

    @Column(name = "CHANNEL")
    private String channel;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "ADDRESS")
    private String address;

    @Lob
    @Column(name = "LOGO")
    private String logo;

    @Column(name = "WEBSITE")
    private String website;

    /*@Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATION_DATE")
    private LocalDate createdDate;

    @Column(name = "LAST_UPDATED_BY")
    private LocalDate lastUpdatedBy;

    @Column(name = "LAST_UPDATED_DATE")
    private LocalDate lastUpdatedDate;

    @Column(name = "LOCK_NUMBER")
    private Long lockNumber;*/

    @Column(name = "BILLER_CATEGORY")
    private String billerCategory;

    @Column(name = "DEVISE")
    private String devise;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillerCode() {
        return billerCode;
    }

    public BillerT billerCode(String billerCode) {
        this.billerCode = billerCode;
        return this;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }

    public String getName() {
        return name;
    }

    public BillerT name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPays() {
        return pays;
    }

    public BillerT pays(String pays) {
        this.pays = pays;
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getChannel() {
        return channel;
    }

    public BillerT channel(String channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getEmail() {
        return email;
    }

    public BillerT email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public BillerT telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStatus() {
        return status;
    }

    public BillerT status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public BillerT address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public BillerT logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public BillerT website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    /*public Long getCreatedBy() {
        return createdBy;
    }

    public BillerT createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public BillerT createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public BillerT lastUpdatedBy(LocalDate lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public void setLastUpdatedBy(LocalDate lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public BillerT lastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getLockNumber() {
        return lockNumber;
    }

    public BillerT lockNumber(Long lockNumber) {
        this.lockNumber = lockNumber;
        return this;
    }

    public void setLockNumber(Long lockNumber) {
        this.lockNumber = lockNumber;
    }*/

    public String getBillerCategory() {
        return billerCategory;
    }

    public BillerT billerCategory(String billerCategory) {
        this.billerCategory = billerCategory;
        return this;
    }

    public void setBillerCategory(String billerCategory) {
        this.billerCategory = billerCategory;
    }

    public String getDevise() {
        return devise;
    }

    public BillerT devise(String devise) {
        this.devise = devise;
        return this;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BillerT)) {
            return false;
        }
        return id != null && id.equals(((BillerT) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BillerT{" +
            "id=" + getId() +
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
            /*", createdBy=" + getCreatedBy() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", lockNumber=" + getLockNumber() +*/
            ", billerCategory='" + getBillerCategory() + "'" +
            ", devise='" + getDevise() + "'" +
            "}";
    }
}
