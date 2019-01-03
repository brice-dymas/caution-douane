package com.cami.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DIQteTypeVehicule.
 */
@Entity
@Table(name = "di_qte_type_vehicule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DIQteTypeVehicule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 0L)
    @Column(name = "quantite", nullable = false)
    private Long quantite;

    @ManyToOne
    @JsonIgnoreProperties("")
    private DemandeImportation demandeImportation;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TypeVehicule typeVehicule;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Marque marque;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantite() {
        return quantite;
    }

    public DIQteTypeVehicule quantite(Long quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public DemandeImportation getDemandeImportation() {
        return demandeImportation;
    }

    public DIQteTypeVehicule demandeImportation(DemandeImportation demandeImportation) {
        this.demandeImportation = demandeImportation;
        return this;
    }

    public void setDemandeImportation(DemandeImportation demandeImportation) {
        this.demandeImportation = demandeImportation;
    }

    public TypeVehicule getTypeVehicule() {
        return typeVehicule;
    }

    public DIQteTypeVehicule typeVehicule(TypeVehicule typeVehicule) {
        this.typeVehicule = typeVehicule;
        return this;
    }

    public void setTypeVehicule(TypeVehicule typeVehicule) {
        this.typeVehicule = typeVehicule;
    }

    public Marque getMarque() {
        return marque;
    }

    public DIQteTypeVehicule marque(Marque marque) {
        this.marque = marque;
        return this;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
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
        DIQteTypeVehicule dIQteTypeVehicule = (DIQteTypeVehicule) o;
        if (dIQteTypeVehicule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dIQteTypeVehicule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DIQteTypeVehicule{" +
            "id=" + getId() +
            ", quantite=" + getQuantite() +
            "}";
    }
}
