package com.cami.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PieceJointe.
 */
@Entity
@Table(name = "piece_jointe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PieceJointe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(name = "nom", length = 255, nullable = false)
    private String nom;

    @Lob
    @Column(name = "fichier")
    private byte[] fichier;

    @Column(name = "fichier_content_type")
    private String fichierContentType;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("")
    private DemandeImportation demandeImportation;

    @ManyToOne
    @JsonIgnoreProperties("")
    private RVC rvc;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Declaration declaration;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public PieceJointe nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public byte[] getFichier() {
        return fichier;
    }

    public PieceJointe fichier(byte[] fichier) {
        this.fichier = fichier;
        return this;
    }

    public void setFichier(byte[] fichier) {
        this.fichier = fichier;
    }

    public String getFichierContentType() {
        return fichierContentType;
    }

    public PieceJointe fichierContentType(String fichierContentType) {
        this.fichierContentType = fichierContentType;
        return this;
    }

    public void setFichierContentType(String fichierContentType) {
        this.fichierContentType = fichierContentType;
    }

    public String getDescription() {
        return description;
    }

    public PieceJointe description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DemandeImportation getDemandeImportation() {
        return demandeImportation;
    }

    public PieceJointe demandeImportation(DemandeImportation demandeImportation) {
        this.demandeImportation = demandeImportation;
        return this;
    }

    public void setDemandeImportation(DemandeImportation demandeImportation) {
        this.demandeImportation = demandeImportation;
    }

    public RVC getRvc() {
        return rvc;
    }

    public PieceJointe rvc(RVC rVC) {
        this.rvc = rVC;
        return this;
    }

    public void setRvc(RVC rVC) {
        this.rvc = rVC;
    }

    public Declaration getDeclaration() {
        return declaration;
    }

    public PieceJointe declaration(Declaration declaration) {
        this.declaration = declaration;
        return this;
    }

    public void setDeclaration(Declaration declaration) {
        this.declaration = declaration;
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
        PieceJointe pieceJointe = (PieceJointe) o;
        if (pieceJointe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pieceJointe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PieceJointe{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", fichier='" + getFichier() + "'" +
            ", fichierContentType='" + getFichierContentType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
