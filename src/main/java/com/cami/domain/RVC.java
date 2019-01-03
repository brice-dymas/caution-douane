package com.cami.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A RVC.
 */
@Entity
@Table(name = "rvc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RVC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 30)
    @Column(name = "numero_rvc", length = 30)
    private String numeroRVC;

    @NotNull
    @Min(value = 0L)
    @Column(name = "montant_rvc", nullable = false)
    private Long montantRVC;

    @Size(max = 10)
    @Column(name = "numero_navire", length = 10)
    private String numeroNavire;

    @NotNull
    @Size(max = 30)
    @Column(name = "nom_navire", length = 30, nullable = false)
    private String nomNavire;

    @NotNull
    @Size(max = 30)
    @Column(name = "pays_provenance", length = 30, nullable = false)
    private String paysProvenance;

    @Column(name = "heure_depart")
    private ZonedDateTime heureDepart;

    @Column(name = "heure_arrivee")
    private ZonedDateTime heureArrivee;

    @ManyToOne
    @JsonIgnoreProperties("")
    private DemandeImportation demandeImportation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroRVC() {
        return numeroRVC;
    }

    public RVC numeroRVC(String numeroRVC) {
        this.numeroRVC = numeroRVC;
        return this;
    }

    public void setNumeroRVC(String numeroRVC) {
        this.numeroRVC = numeroRVC;
    }

    public Long getMontantRVC() {
        return montantRVC;
    }

    public RVC montantRVC(Long montantRVC) {
        this.montantRVC = montantRVC;
        return this;
    }

    public void setMontantRVC(Long montantRVC) {
        this.montantRVC = montantRVC;
    }

    public String getNumeroNavire() {
        return numeroNavire;
    }

    public RVC numeroNavire(String numeroNavire) {
        this.numeroNavire = numeroNavire;
        return this;
    }

    public void setNumeroNavire(String numeroNavire) {
        this.numeroNavire = numeroNavire;
    }

    public String getNomNavire() {
        return nomNavire;
    }

    public RVC nomNavire(String nomNavire) {
        this.nomNavire = nomNavire;
        return this;
    }

    public void setNomNavire(String nomNavire) {
        this.nomNavire = nomNavire;
    }

    public String getPaysProvenance() {
        return paysProvenance;
    }

    public RVC paysProvenance(String paysProvenance) {
        this.paysProvenance = paysProvenance;
        return this;
    }

    public void setPaysProvenance(String paysProvenance) {
        this.paysProvenance = paysProvenance;
    }

    public ZonedDateTime getHeureDepart() {
        return heureDepart;
    }

    public RVC heureDepart(ZonedDateTime heureDepart) {
        this.heureDepart = heureDepart;
        return this;
    }

    public void setHeureDepart(ZonedDateTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    public ZonedDateTime getHeureArrivee() {
        return heureArrivee;
    }

    public RVC heureArrivee(ZonedDateTime heureArrivee) {
        this.heureArrivee = heureArrivee;
        return this;
    }

    public void setHeureArrivee(ZonedDateTime heureArrivee) {
        this.heureArrivee = heureArrivee;
    }

    public DemandeImportation getDemandeImportation() {
        return demandeImportation;
    }

    public RVC demandeImportation(DemandeImportation demandeImportation) {
        this.demandeImportation = demandeImportation;
        return this;
    }

    public void setDemandeImportation(DemandeImportation demandeImportation) {
        this.demandeImportation = demandeImportation;
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
        RVC rVC = (RVC) o;
        if (rVC.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rVC.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RVC{" +
            "id=" + getId() +
            ", numeroRVC='" + getNumeroRVC() + "'" +
            ", montantRVC=" + getMontantRVC() +
            ", numeroNavire='" + getNumeroNavire() + "'" +
            ", nomNavire='" + getNomNavire() + "'" +
            ", paysProvenance='" + getPaysProvenance() + "'" +
            ", heureDepart='" + getHeureDepart() + "'" +
            ", heureArrivee='" + getHeureArrivee() + "'" +
            "}";
    }
}
