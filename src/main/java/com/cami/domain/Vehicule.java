package com.cami.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Vehicule.
 */
@Entity
@Table(name = "vehicule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vehicule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "numero_chassis", length = 30, nullable = false)
    private String numeroChassis;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Marque marque;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Caution caution;

    @ManyToOne
    @JsonIgnoreProperties("")
    private RVC rvc;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Appurement appurement;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TypeVehicule typeVehicule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroChassis() {
        return numeroChassis;
    }

    public Vehicule numeroChassis(String numeroChassis) {
        this.numeroChassis = numeroChassis;
        return this;
    }

    public void setNumeroChassis(String numeroChassis) {
        this.numeroChassis = numeroChassis;
    }

    public Marque getMarque() {
        return marque;
    }

    public Vehicule marque(Marque marque) {
        this.marque = marque;
        return this;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Caution getCaution() {
        return caution;
    }

    public Vehicule caution(Caution caution) {
        this.caution = caution;
        return this;
    }

    public void setCaution(Caution caution) {
        this.caution = caution;
    }

    public RVC getRvc() {
        return rvc;
    }

    public Vehicule rvc(RVC rVC) {
        this.rvc = rVC;
        return this;
    }

    public void setRvc(RVC rVC) {
        this.rvc = rVC;
    }

    public Appurement getAppurement() {
        return appurement;
    }

    public Vehicule appurement(Appurement appurement) {
        this.appurement = appurement;
        return this;
    }

    public void setAppurement(Appurement appurement) {
        this.appurement = appurement;
    }

    public TypeVehicule getTypeVehicule() {
        return typeVehicule;
    }

    public Vehicule typeVehicule(TypeVehicule typeVehicule) {
        this.typeVehicule = typeVehicule;
        return this;
    }

    public void setTypeVehicule(TypeVehicule typeVehicule) {
        this.typeVehicule = typeVehicule;
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
        Vehicule vehicule = (Vehicule) o;
        if (vehicule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vehicule{" +
            "id=" + getId() +
            ", numeroChassis='" + getNumeroChassis() + "'" +
            "}";
    }
}
