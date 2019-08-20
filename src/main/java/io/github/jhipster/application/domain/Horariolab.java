package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Horariolab.
 */
@Entity
@Table(name = "horariolab")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "horariolab")
public class Horariolab implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "dia")
    private String dia;

    @Column(name = "hinicio")
    private String hinicio;

    @Column(name = "hfin")
    private String hfin;

    @ManyToOne
    @JsonIgnoreProperties("horariolabs")
    private Plaza plaza;

    @ManyToOne
    @JsonIgnoreProperties("horariolabs")
    private Subciclo subsiclo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public Horariolab dia(String dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHinicio() {
        return hinicio;
    }

    public Horariolab hinicio(String hinicio) {
        this.hinicio = hinicio;
        return this;
    }

    public void setHinicio(String hinicio) {
        this.hinicio = hinicio;
    }

    public String getHfin() {
        return hfin;
    }

    public Horariolab hfin(String hfin) {
        this.hfin = hfin;
        return this;
    }

    public void setHfin(String hfin) {
        this.hfin = hfin;
    }

    public Plaza getPlaza() {
        return plaza;
    }

    public Horariolab plaza(Plaza plaza) {
        this.plaza = plaza;
        return this;
    }

    public void setPlaza(Plaza plaza) {
        this.plaza = plaza;
    }

    public Subciclo getSubsiclo() {
        return subsiclo;
    }

    public Horariolab subsiclo(Subciclo subciclo) {
        this.subsiclo = subciclo;
        return this;
    }

    public void setSubsiclo(Subciclo subciclo) {
        this.subsiclo = subciclo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Horariolab)) {
            return false;
        }
        return id != null && id.equals(((Horariolab) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Horariolab{" +
            "id=" + getId() +
            ", dia='" + getDia() + "'" +
            ", hinicio='" + getHinicio() + "'" +
            ", hfin='" + getHfin() + "'" +
            "}";
    }
}
