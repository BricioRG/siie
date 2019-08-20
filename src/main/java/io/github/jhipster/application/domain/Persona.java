package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Persona.
 */
@Entity
@Table(name = "persona")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "persona")
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "primerape")
    private String primerape;

    @Column(name = "segundoape")
    private String segundoape;

    @Column(name = "fechanac")
    private LocalDate fechanac;

    @Column(name = "entidadnac")
    private String entidadnac;

    @Column(name = "genero")
    private String genero;

    @Column(name = "rfc")
    private String rfc;

    @Column(name = "curp")
    private String curp;

    @Column(name = "edocivil")
    private String edocivil;

    @Column(name = "empleado")
    private Boolean empleado;

    @Column(name = "nuempleado")
    private String nuempleado;

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

    public Persona nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerape() {
        return primerape;
    }

    public Persona primerape(String primerape) {
        this.primerape = primerape;
        return this;
    }

    public void setPrimerape(String primerape) {
        this.primerape = primerape;
    }

    public String getSegundoape() {
        return segundoape;
    }

    public Persona segundoape(String segundoape) {
        this.segundoape = segundoape;
        return this;
    }

    public void setSegundoape(String segundoape) {
        this.segundoape = segundoape;
    }

    public LocalDate getFechanac() {
        return fechanac;
    }

    public Persona fechanac(LocalDate fechanac) {
        this.fechanac = fechanac;
        return this;
    }

    public void setFechanac(LocalDate fechanac) {
        this.fechanac = fechanac;
    }

    public String getEntidadnac() {
        return entidadnac;
    }

    public Persona entidadnac(String entidadnac) {
        this.entidadnac = entidadnac;
        return this;
    }

    public void setEntidadnac(String entidadnac) {
        this.entidadnac = entidadnac;
    }

    public String getGenero() {
        return genero;
    }

    public Persona genero(String genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getRfc() {
        return rfc;
    }

    public Persona rfc(String rfc) {
        this.rfc = rfc;
        return this;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public Persona curp(String curp) {
        this.curp = curp;
        return this;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getEdocivil() {
        return edocivil;
    }

    public Persona edocivil(String edocivil) {
        this.edocivil = edocivil;
        return this;
    }

    public void setEdocivil(String edocivil) {
        this.edocivil = edocivil;
    }

    public Boolean isEmpleado() {
        return empleado;
    }

    public Persona empleado(Boolean empleado) {
        this.empleado = empleado;
        return this;
    }

    public void setEmpleado(Boolean empleado) {
        this.empleado = empleado;
    }

    public String getNuempleado() {
        return nuempleado;
    }

    public Persona nuempleado(String nuempleado) {
        this.nuempleado = nuempleado;
        return this;
    }

    public void setNuempleado(String nuempleado) {
        this.nuempleado = nuempleado;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Persona)) {
            return false;
        }
        return id != null && id.equals(((Persona) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Persona{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", primerape='" + getPrimerape() + "'" +
            ", segundoape='" + getSegundoape() + "'" +
            ", fechanac='" + getFechanac() + "'" +
            ", entidadnac='" + getEntidadnac() + "'" +
            ", genero='" + getGenero() + "'" +
            ", rfc='" + getRfc() + "'" +
            ", curp='" + getCurp() + "'" +
            ", edocivil='" + getEdocivil() + "'" +
            ", empleado='" + isEmpleado() + "'" +
            ", nuempleado='" + getNuempleado() + "'" +
            "}";
    }
}
