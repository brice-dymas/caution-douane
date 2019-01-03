package com.cami.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TypeVehicule.
 */
@Entity
@Table(name = "type_vehicule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeVehicule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_type")
    private String numeroType;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "nom_type", length = 30, nullable = false)
    private String nomType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroType() {
        return numeroType;
    }

    public TypeVehicule numeroType(String numeroType) {
        this.numeroType = numeroType;
        return this;
    }

    public void setNumeroType(String numeroType) {
        this.numeroType = numeroType;
    }

    public String getNomType() {
        return nomType;
    }

    public TypeVehicule nomType(String nomType) {
        this.nomType = nomType;
        return this;
    }

    public void setNomType(String nomType) {
        this.nomType = nomType;
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
        TypeVehicule typeVehicule = (TypeVehicule) o;
        if (typeVehicule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeVehicule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeVehicule{" +
            "id=" + getId() +
            ", numeroType='" + getNumeroType() + "'" +
            ", nomType='" + getNomType() + "'" +
            "}";
    }
}
