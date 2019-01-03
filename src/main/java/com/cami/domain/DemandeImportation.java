package com.cami.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DemandeImportation.
 */
@Entity
@Table(name = "demande_importation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DemandeImportation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "numero_dordre", length = 30, nullable = false)
    private String numeroDordre;

    @NotNull
    @Size(max = 30)
    @Column(name = "numero_facture_proforma", length = 30, nullable = false)
    private String numeroFactureProforma;

    @Column(name = "date_reception_facture")
    private LocalDate dateReceptionFacture;

    @Column(name = "date_depot_facture")
    private LocalDate dateDepotFacture;

    @NotNull
    @Size(max = 15)
    @Column(name = "numero_pr", length = 15, nullable = false)
    private String numeroPR;

    @Column(name = "date_reception_pr")
    private LocalDate dateReceptionPR;

    @Size(max = 15)
    @Column(name = "numero_di", length = 15)
    private String numeroDI;

    @Min(value = 0L)
    @Column(name = "montant_fob")
    private Long montantFOB;

    @Min(value = 0L)
    @Column(name = "montant_fret")
    private Long montantFRET;

    @Column(name = "observation")
    private String observation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroDordre() {
        return numeroDordre;
    }

    public DemandeImportation numeroDordre(String numeroDordre) {
        this.numeroDordre = numeroDordre;
        return this;
    }

    public void setNumeroDordre(String numeroDordre) {
        this.numeroDordre = numeroDordre;
    }

    public String getNumeroFactureProforma() {
        return numeroFactureProforma;
    }

    public DemandeImportation numeroFactureProforma(String numeroFactureProforma) {
        this.numeroFactureProforma = numeroFactureProforma;
        return this;
    }

    public void setNumeroFactureProforma(String numeroFactureProforma) {
        this.numeroFactureProforma = numeroFactureProforma;
    }

    public LocalDate getDateReceptionFacture() {
        return dateReceptionFacture;
    }

    public DemandeImportation dateReceptionFacture(LocalDate dateReceptionFacture) {
        this.dateReceptionFacture = dateReceptionFacture;
        return this;
    }

    public void setDateReceptionFacture(LocalDate dateReceptionFacture) {
        this.dateReceptionFacture = dateReceptionFacture;
    }

    public LocalDate getDateDepotFacture() {
        return dateDepotFacture;
    }

    public DemandeImportation dateDepotFacture(LocalDate dateDepotFacture) {
        this.dateDepotFacture = dateDepotFacture;
        return this;
    }

    public void setDateDepotFacture(LocalDate dateDepotFacture) {
        this.dateDepotFacture = dateDepotFacture;
    }

    public String getNumeroPR() {
        return numeroPR;
    }

    public DemandeImportation numeroPR(String numeroPR) {
        this.numeroPR = numeroPR;
        return this;
    }

    public void setNumeroPR(String numeroPR) {
        this.numeroPR = numeroPR;
    }

    public LocalDate getDateReceptionPR() {
        return dateReceptionPR;
    }

    public DemandeImportation dateReceptionPR(LocalDate dateReceptionPR) {
        this.dateReceptionPR = dateReceptionPR;
        return this;
    }

    public void setDateReceptionPR(LocalDate dateReceptionPR) {
        this.dateReceptionPR = dateReceptionPR;
    }

    public String getNumeroDI() {
        return numeroDI;
    }

    public DemandeImportation numeroDI(String numeroDI) {
        this.numeroDI = numeroDI;
        return this;
    }

    public void setNumeroDI(String numeroDI) {
        this.numeroDI = numeroDI;
    }

    public Long getMontantFOB() {
        return montantFOB;
    }

    public DemandeImportation montantFOB(Long montantFOB) {
        this.montantFOB = montantFOB;
        return this;
    }

    public void setMontantFOB(Long montantFOB) {
        this.montantFOB = montantFOB;
    }

    public Long getMontantFRET() {
        return montantFRET;
    }

    public DemandeImportation montantFRET(Long montantFRET) {
        this.montantFRET = montantFRET;
        return this;
    }

    public void setMontantFRET(Long montantFRET) {
        this.montantFRET = montantFRET;
    }

    public String getObservation() {
        return observation;
    }

    public DemandeImportation observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DemandeImportation demandeImportation = (DemandeImportation) o;
        if (demandeImportation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), demandeImportation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DemandeImportation{" +
            "id=" + getId() +
            ", numeroDordre='" + getNumeroDordre() + "'" +
            ", numeroFactureProforma='" + getNumeroFactureProforma() + "'" +
            ", dateReceptionFacture='" + getDateReceptionFacture() + "'" +
            ", dateDepotFacture='" + getDateDepotFacture() + "'" +
            ", numeroPR='" + getNumeroPR() + "'" +
            ", dateReceptionPR='" + getDateReceptionPR() + "'" +
            ", numeroDI='" + getNumeroDI() + "'" +
            ", montantFOB=" + getMontantFOB() +
            ", montantFRET=" + getMontantFRET() +
            ", observation='" + getObservation() + "'" +
            "}";
    }
}
