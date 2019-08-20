package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Ciclo.
 */
@Entity
@Table(name = "ciclo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ciclo")
public class Ciclo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "clave")
    private String clave;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecini")
    private LocalDate fecini;

    @Column(name = "fecfin")
    private LocalDate fecfin;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public Ciclo clave(String clave) {
        this.clave = clave;
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Ciclo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecini() {
        return fecini;
    }

    public Ciclo fecini(LocalDate fecini) {
        this.fecini = fecini;
        return this;
    }

    public void setFecini(LocalDate fecini) {
        this.fecini = fecini;
    }

    public LocalDate getFecfin() {
        return fecfin;
    }

    public Ciclo fecfin(LocalDate fecfin) {
        this.fecfin = fecfin;
        return this;
    }

    public void setFecfin(LocalDate fecfin) {
        this.fecfin = fecfin;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ciclo)) {
            return false;
        }
        return id != null && id.equals(((Ciclo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ciclo{" +
            "id=" + getId() +
            ", clave='" + getClave() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fecini='" + getFecini() + "'" +
            ", fecfin='" + getFecfin() + "'" +
            "}";
    }
}
