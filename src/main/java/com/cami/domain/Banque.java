package com.cami.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Banque.
 */
@Entity
@Table(name = "banque")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Banque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 30)
    @Column(name = "numero_banque", length = 30)
    private String numeroBanque;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "nom_banque", length = 30, nullable = false)
    private String nomBanque;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "numero_compte", length = 50, nullable = false)
    private String numeroCompte;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroBanque() {
        return numeroBanque;
    }

    public Banque numeroBanque(String numeroBanque) {
        this.numeroBanque = numeroBanque;
        return this;
    }

    public void setNumeroBanque(String numeroBanque) {
        this.numeroBanque = numeroBanque;
    }

    public String getNomBanque() {
        return nomBanque;
    }

    public Banque nomBanque(String nomBanque) {
        this.nomBanque = nomBanque;
        return this;
    }

    public void setNomBanque(String nomBanque) {
        this.nomBanque = nomBanque;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public Banque numeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
        return this;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
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
        Banque banque = (Banque) o;
        if (banque.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), banque.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Banque{" +
            "id=" + getId() +
            ", numeroBanque='" + getNumeroBanque() + "'" +
            ", nomBanque='" + getNomBanque() + "'" +
            ", numeroCompte='" + getNumeroCompte() + "'" +
            "}";
    }
}
