package com.cami.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Declaration.
 */
@Entity
@Table(name = "declaration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Declaration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "numero_declaration", length = 10, nullable = false)
    private String numeroDeclaration;

    @NotNull
    @Size(max = 30)
    @Column(name = "reference", length = 30, nullable = false)
    private String reference;

    @NotNull
    @Size(max = 30)
    @Column(name = "numero_im_7", length = 30, nullable = false)
    private String numeroIM7;

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

    public String getNumeroDeclaration() {
        return numeroDeclaration;
    }

    public Declaration numeroDeclaration(String numeroDeclaration) {
        this.numeroDeclaration = numeroDeclaration;
        return this;
    }

    public void setNumeroDeclaration(String numeroDeclaration) {
        this.numeroDeclaration = numeroDeclaration;
    }

    public String getReference() {
        return reference;
    }

    public Declaration reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNumeroIM7() {
        return numeroIM7;
    }

    public Declaration numeroIM7(String numeroIM7) {
        this.numeroIM7 = numeroIM7;
        return this;
    }

    public void setNumeroIM7(String numeroIM7) {
        this.numeroIM7 = numeroIM7;
    }

    public DemandeImportation getDemandeImportation() {
        return demandeImportation;
    }

    public Declaration demandeImportation(DemandeImportation demandeImportation) {
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
        Declaration declaration = (Declaration) o;
        if (declaration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), declaration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Declaration{" +
            "id=" + getId() +
            ", numeroDeclaration='" + getNumeroDeclaration() + "'" +
            ", reference='" + getReference() + "'" +
            ", numeroIM7='" + getNumeroIM7() + "'" +
            "}";
    }
}
