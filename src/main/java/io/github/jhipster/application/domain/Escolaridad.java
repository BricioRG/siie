package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Escolaridad.
 */
@Entity
@Table(name = "escolaridad")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "escolaridad")
public class Escolaridad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "periodo")
    private String periodo;

    @Column(name = "concluyo")
    private Boolean concluyo;

    @Column(name = "clavedoc")
    private String clavedoc;

    @Column(name = "documento")
    private String documento;

    @ManyToOne
    @JsonIgnoreProperties("escolaridads")
    private Persona persona;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Escolaridad nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Escolaridad descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPeriodo() {
        return periodo;
    }

    public Escolaridad periodo(String periodo) {
        this.periodo = periodo;
        return this;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Boolean isConcluyo() {
        return concluyo;
    }

    public Escolaridad concluyo(Boolean concluyo) {
        this.concluyo = concluyo;
        return this;
    }

    public void setConcluyo(Boolean concluyo) {
        this.concluyo = concluyo;
    }

    public String getClavedoc() {
        return clavedoc;
    }

    public Escolaridad clavedoc(String clavedoc) {
        this.clavedoc = clavedoc;
        return this;
    }

    public void setClavedoc(String clavedoc) {
        this.clavedoc = clavedoc;
    }

    public String getDocumento() {
        return documento;
    }

    public Escolaridad documento(String documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Persona getPersona() {
        return persona;
    }

    public Escolaridad persona(Persona persona) {
        this.persona = persona;
        return this;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Escolaridad)) {
            return false;
        }
        return id != null && id.equals(((Escolaridad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Escolaridad{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", periodo='" + getPeriodo() + "'" +
            ", concluyo='" + isConcluyo() + "'" +
            ", clavedoc='" + getClavedoc() + "'" +
            ", documento='" + getDocumento() + "'" +
            "}";
    }
}
