package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Plaza.
 */
@Entity
@Table(name = "plaza")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "plaza")
public class Plaza implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "horas")
    private String horas;

    @Column(name = "fechaini")
    private LocalDate fechaini;

    @Column(name = "fechafin")
    private LocalDate fechafin;

    @ManyToOne
    @JsonIgnoreProperties("plazas")
    private Categoria categoria;

    @ManyToOne
    @JsonIgnoreProperties("plazas")
    private Funcionlaboral funcionlaboral;

    @ManyToOne
    @JsonIgnoreProperties("plazas")
    private Escuela escuela;

    @ManyToOne
    @JsonIgnoreProperties("plazas")
    private Tipomov tipomov;

    @ManyToOne
    @JsonIgnoreProperties("plazas")
    private Persona persona;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHoras() {
        return horas;
    }

    public Plaza horas(String horas) {
        this.horas = horas;
        return this;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public LocalDate getFechaini() {
        return fechaini;
    }

    public Plaza fechaini(LocalDate fechaini) {
        this.fechaini = fechaini;
        return this;
    }

    public void setFechaini(LocalDate fechaini) {
        this.fechaini = fechaini;
    }

    public LocalDate getFechafin() {
        return fechafin;
    }

    public Plaza fechafin(LocalDate fechafin) {
        this.fechafin = fechafin;
        return this;
    }

    public void setFechafin(LocalDate fechafin) {
        this.fechafin = fechafin;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Plaza categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Funcionlaboral getFuncionlaboral() {
        return funcionlaboral;
    }

    public Plaza funcionlaboral(Funcionlaboral funcionlaboral) {
        this.funcionlaboral = funcionlaboral;
        return this;
    }

    public void setFuncionlaboral(Funcionlaboral funcionlaboral) {
        this.funcionlaboral = funcionlaboral;
    }

    public Escuela getEscuela() {
        return escuela;
    }

    public Plaza escuela(Escuela escuela) {
        this.escuela = escuela;
        return this;
    }

    public void setEscuela(Escuela escuela) {
        this.escuela = escuela;
    }

    public Tipomov getTipomov() {
        return tipomov;
    }

    public Plaza tipomov(Tipomov tipomov) {
        this.tipomov = tipomov;
        return this;
    }

    public void setTipomov(Tipomov tipomov) {
        this.tipomov = tipomov;
    }

    public Persona getPersona() {
        return persona;
    }

    public Plaza persona(Persona persona) {
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
        if (!(o instanceof Plaza)) {
            return false;
        }
        return id != null && id.equals(((Plaza) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Plaza{" +
            "id=" + getId() +
            ", horas='" + getHoras() + "'" +
            ", fechaini='" + getFechaini() + "'" +
            ", fechafin='" + getFechafin() + "'" +
            "}";
    }
}
