package com.metrica.hipotecas.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "caracteristicas_terreno")
public class CaracteristicasTerreno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "superficie")
    private Integer superficie;

    @Column(name = "tipo")
    private Integer tipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSuperficie() {
        return superficie;
    }

    public CaracteristicasTerreno superficie(Integer superficie) {
        this.superficie = superficie;
        return this;
    }

    public void setSuperficie(Integer superficie) {
        this.superficie = superficie;
    }

    public Integer getTipo() {
        return tipo;
    }

    public CaracteristicasTerreno tipo(Integer tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CaracteristicasTerreno caracteristicasTerreno = (CaracteristicasTerreno) o;
        if (caracteristicasTerreno.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, caracteristicasTerreno.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CaracteristicasTerreno{" +
            "id=" + id +
            ", superficie='" + superficie + "'" +
            ", tipo='" + tipo + "'" +
            '}';
    }
}
