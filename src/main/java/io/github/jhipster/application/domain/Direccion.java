package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Direccion.
 */
@Entity
@Table(name = "direccion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "direccion")
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "calle")
    private String calle;

    @Column(name = "numeroint")
    private String numeroint;

    @Column(name = "numeroext")
    private String numeroext;

    @Column(name = "colonia")
    private String colonia;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "municipio")
    private String municipio;

    @Column(name = "estado")
    private String estado;

    @Column(name = "referencia")
    private String referencia;

    @Column(name = "entrecalle")
    private String entrecalle;

    @ManyToOne
    @JsonIgnoreProperties("direccions")
    private Persona persona;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public Direccion calle(String calle) {
        this.calle = calle;
        return this;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroint() {
        return numeroint;
    }

    public Direccion numeroint(String numeroint) {
        this.numeroint = numeroint;
        return this;
    }

    public void setNumeroint(String numeroint) {
        this.numeroint = numeroint;
    }

    public String getNumeroext() {
        return numeroext;
    }

    public Direccion numeroext(String numeroext) {
        this.numeroext = numeroext;
        return this;
    }

    public void setNumeroext(String numeroext) {
        this.numeroext = numeroext;
    }

    public String getColonia() {
        return colonia;
    }

    public Direccion colonia(String colonia) {
        this.colonia = colonia;
        return this;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public Direccion ciudad(String ciudad) {
        this.ciudad = ciudad;
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getMunicipio() {
        return municipio;
    }

    public Direccion municipio(String municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public Direccion estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getReferencia() {
        return referencia;
    }

    public Direccion referencia(String referencia) {
        this.referencia = referencia;
        return this;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getEntrecalle() {
        return entrecalle;
    }

    public Direccion entrecalle(String entrecalle) {
        this.entrecalle = entrecalle;
        return this;
    }

    public void setEntrecalle(String entrecalle) {
        this.entrecalle = entrecalle;
    }

    public Persona getPersona() {
        return persona;
    }

    public Direccion persona(Persona persona) {
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
        if (!(o instanceof Direccion)) {
            return false;
        }
        return id != null && id.equals(((Direccion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Direccion{" +
            "id=" + getId() +
            ", calle='" + getCalle() + "'" +
            ", numeroint='" + getNumeroint() + "'" +
            ", numeroext='" + getNumeroext() + "'" +
            ", colonia='" + getColonia() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            ", estado='" + getEstado() + "'" +
            ", referencia='" + getReferencia() + "'" +
            ", entrecalle='" + getEntrecalle() + "'" +
            "}";
    }
}
