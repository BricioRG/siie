package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Subciclo.
 */
@Entity
@Table(name = "subciclo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "subciclo")
public class Subciclo implements Serializable {

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

    @Column(name = "fecini")
    private LocalDate fecini;

    @Column(name = "fechafin")
    private LocalDate fechafin;

    @ManyToOne
    @JsonIgnoreProperties("subciclos")
    private Ciclo ciclo;

    @ManyToOne
    @JsonIgnoreProperties("subciclos")
    private Escuela escuela;

    @ManyToOne
    @JsonIgnoreProperties("subciclos")
    private Tipoperiodo tipoperiodo;

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

    public Subciclo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Subciclo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecini() {
        return fecini;
    }

    public Subciclo fecini(LocalDate fecini) {
        this.fecini = fecini;
        return this;
    }

    public void setFecini(LocalDate fecini) {
        this.fecini = fecini;
    }

    public LocalDate getFechafin() {
        return fechafin;
    }

    public Subciclo fechafin(LocalDate fechafin) {
        this.fechafin = fechafin;
        return this;
    }

    public void setFechafin(LocalDate fechafin) {
        this.fechafin = fechafin;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public Subciclo ciclo(Ciclo ciclo) {
        this.ciclo = ciclo;
        return this;
    }

    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    public Escuela getEscuela() {
        return escuela;
    }

    public Subciclo escuela(Escuela escuela) {
        this.escuela = escuela;
        return this;
    }

    public void setEscuela(Escuela escuela) {
        this.escuela = escuela;
    }

    public Tipoperiodo getTipoperiodo() {
        return tipoperiodo;
    }

    public Subciclo tipoperiodo(Tipoperiodo tipoperiodo) {
        this.tipoperiodo = tipoperiodo;
        return this;
    }

    public void setTipoperiodo(Tipoperiodo tipoperiodo) {
        this.tipoperiodo = tipoperiodo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subciclo)) {
            return false;
        }
        return id != null && id.equals(((Subciclo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Subciclo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fecini='" + getFecini() + "'" +
            ", fechafin='" + getFechafin() + "'" +
            "}";
    }
}
