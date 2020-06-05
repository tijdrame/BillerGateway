package com.boa.api.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A TransactionGlobal.
 */
@Entity
@Table(name = "BOA_BILM_TRANSACTION_GLOBAL_T")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransactionGlobal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "transac_seq_gen")
    @SequenceGenerator(name = "transac_seq_gen", sequenceName = "BOA_BILM_TRANS_GLOB_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "TRANSACTION_ID")
    private Long id;

    @Column(name = "REFERENCE_FACTURE")
    private String referenceFacture;

    @Column(name = "NUMERO_FACTURE")
    private String numeroFacture;

    @Column(name = "DATE_PAIEMENT")
    private LocalDate datePaiement;

    @Column(name = "ECHEANCE")
    private String echeance;

    @Column(name = "CHANNEL")
    private String channel;

    @Column(name = "DEVICE_ID")
    private String deviceId;

    @Column(name = "CLIENT")
    private String client;

    @Column(name = "PRENOM")
    private String prenom;

    @Column(name = "NOM")
    private String nom;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "MONTANT")
    private Double montant;

    @Column(name = "DEBIT_ACCOUNT")
    private String debitAccount;

    @Column(name = "CREDIT_ACCOUNT")
    private String creditAccount;

    @Column(name = "FRAIS")
    private Double frais;

    @Column(name = "CODE_RETOUR")
    private String codeRetour;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATION_DATE")
    private LocalDate createdDate;

    @ManyToOne
    @JsonIgnoreProperties("transactionGlobals")
    @JoinColumn(name = "BILLER_CODE")
    private BillerT billerT;

    @Column(name = "BENEFICIAIRE")
    private String beneficiaire;

    @Column(name = "REFERENCE_TRANSACTION")
    private String referenceTransaction;

    @Column(name = "MESSAGE_FR")
    private String messageFr;

    @Column(name = "MESSAGE_EN")
    private String messageEn;
    
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BillerT getBillerT() {
        return billerT;
    }

    public TransactionGlobal billerT(BillerT billerT) {
        this.billerT = billerT;
        return this;
    }

    public void setBillerT(BillerT billerT) {
        this.billerT = billerT;
    }

    public String getReferenceFacture() {
        return referenceFacture;
    }

    public TransactionGlobal referenceFacture(String referenceFacture) {
        this.referenceFacture = referenceFacture;
        return this;
    }

    public void setReferenceFacture(String referenceFacture) {
        this.referenceFacture = referenceFacture;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public TransactionGlobal numeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
        return this;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public TransactionGlobal datePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
        return this;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getEcheance() {
        return echeance;
    }

    public TransactionGlobal echeance(String echeance) {
        this.echeance = echeance;
        return this;
    }

    public void setEcheance(String echeance) {
        this.echeance = echeance;
    }

    public String getChannel() {
        return channel;
    }

    public TransactionGlobal channel(String channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public TransactionGlobal deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getClient() {
        return client;
    }

    public TransactionGlobal client(String client) {
        this.client = client;
        return this;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPrenom() {
        return prenom;
    }

    public TransactionGlobal prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public TransactionGlobal nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public TransactionGlobal telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Double getMontant() {
        return montant;
    }

    public TransactionGlobal montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getDebitAccount() {
        return debitAccount;
    }

    public TransactionGlobal debitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
        return this;
    }

    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public TransactionGlobal creditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
        return this;
    }

    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }

    public Double getFrais() {
        return frais;
    }

    public TransactionGlobal frais(Double frais) {
        this.frais = frais;
        return this;
    }

    public void setFrais(Double frais) {
        this.frais = frais;
    }

    public String getCodeRetour() {
        return codeRetour;
    }

    public TransactionGlobal codeRetour(String codeRetour) {
        this.codeRetour = codeRetour;
        return this;
    }

    public void setCodeRetour(String codeRetour) {
        this.codeRetour = codeRetour;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public TransactionGlobal createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public TransactionGlobal createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public String getBeneficiaire() {
        return this.beneficiaire;
    }

    public void setBeneficiaire(String beneficiaire) {
        this.beneficiaire = beneficiaire;
    }

    public String getReferenceTransaction() {
        return this.referenceTransaction;
    }

    public void setReferenceTransaction(String referenceTransaction) {
        this.referenceTransaction = referenceTransaction;
    }

    public String getMessageFr() {
        return this.messageFr;
    }

    public void setMessageFr(String messageFr) {
        this.messageFr = messageFr;
    }

    public String getMessageEn() {
        return this.messageEn;
    }

    public void setMessageEn(String messageEn) {
        this.messageEn = messageEn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionGlobal)) {
            return false;
        }
        return id != null && id.equals(((TransactionGlobal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransactionGlobal{" +
            "id=" + getId() +
            ", referenceFacture=" + getReferenceFacture() +
            ", numeroFacture='" + getNumeroFacture() + "'" +
            ", datePaiement='" + getDatePaiement() + "'" +
            ", echeance='" + getEcheance() + "'" +
            ", channel='" + getChannel() + "'" +
            ", deviceId='" + getDeviceId() + "'" +
            ", client='" + getClient() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", montant=" + getMontant() +
            ", debitAccount='" + getDebitAccount() + "'" +
            ", creditAccount='" + getCreditAccount() + "'" +
            ", frais=" + getFrais() +
            ", codeRetour='" + getCodeRetour() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", beneficiaire='" + getBeneficiaire() + "'" +
            ", referenceTransaction='" + getReferenceTransaction() + "'" +
            ", messageFr='" + getMessageFr() + "'" +
            ", messageEn='" + getMessageEn() + "'" +
            "}";
    }
}
