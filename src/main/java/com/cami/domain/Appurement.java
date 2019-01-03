package com.cami.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Appurement.
 */
@Entity
@Table(name = "appurement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Appurement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 30)
    @Column(name = "numero_appurement", length = 30)
    private String numeroAppurement;

    @Min(value = 0L)
    @Column(name = "montant_appure")
    private Long montantAppure;

    @Min(value = 0L)
    @Column(name = "nombre_vehicule")
    private Long nombreVehicule;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Caution caution;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroAppurement() {
        return numeroAppurement;
    }

    public Appurement numeroAppurement(String numeroAppurement) {
        this.numeroAppurement = numeroAppurement;
        return this;
    }

    public void setNumeroAppurement(String numeroAppurement) {
        this.numeroAppurement = numeroAppurement;
    }

    public Long getMontantAppure() {
        return montantAppure;
    }

    public Appurement montantAppure(Long montantAppure) {
        this.montantAppure = montantAppure;
        return this;
    }

    public void setMontantAppure(Long montantAppure) {
        this.montantAppure = montantAppure;
    }

    public Long getNombreVehicule() {
        return nombreVehicule;
    }

    public Appurement nombreVehicule(Long nombreVehicule) {
        this.nombreVehicule = nombreVehicule;
        return this;
    }

    public void setNombreVehicule(Long nombreVehicule) {
        this.nombreVehicule = nombreVehicule;
    }

    public Caution getCaution() {
        return caution;
    }

    public Appurement caution(Caution caution) {
        this.caution = caution;
        return this;
    }

    public void setCaution(Caution caution) {
        this.caution = caution;
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
        Appurement appurement = (Appurement) o;
        if (appurement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appurement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Appurement{" +
            "id=" + getId() +
            ", numeroAppurement='" + getNumeroAppurement() + "'" +
            ", montantAppure=" + getMontantAppure() +
            ", nombreVehicule=" + getNombreVehicule() +
            "}";
    }
}
