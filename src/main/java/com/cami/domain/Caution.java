package com.cami.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Caution.
 */
@Entity
@Table(name = "caution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Caution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 30)
    @Column(name = "numero_caution", length = 30)
    private String numeroCaution;

    @Size(max = 30)
    @Column(name = "numero_dossier", length = 30)
    private String numeroDossier;

    @Size(max = 15)
    @Column(name = "reference", length = 15)
    private String reference;

    @NotNull
    @Min(value = 0L)
    @Column(name = "montant", nullable = false)
    private Long montant;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Banque banque;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCaution() {
        return numeroCaution;
    }

    public Caution numeroCaution(String numeroCaution) {
        this.numeroCaution = numeroCaution;
        return this;
    }

    public void setNumeroCaution(String numeroCaution) {
        this.numeroCaution = numeroCaution;
    }

    public String getNumeroDossier() {
        return numeroDossier;
    }

    public Caution numeroDossier(String numeroDossier) {
        this.numeroDossier = numeroDossier;
        return this;
    }

    public void setNumeroDossier(String numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public String getReference() {
        return reference;
    }

    public Caution reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getMontant() {
        return montant;
    }

    public Caution montant(Long montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
    }

    public Banque getBanque() {
        return banque;
    }

    public Caution banque(Banque banque) {
        this.banque = banque;
        return this;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
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
        Caution caution = (Caution) o;
        if (caution.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), caution.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Caution{" +
            "id=" + getId() +
            ", numeroCaution='" + getNumeroCaution() + "'" +
            ", numeroDossier='" + getNumeroDossier() + "'" +
            ", reference='" + getReference() + "'" +
            ", montant=" + getMontant() +
            "}";
    }
}
