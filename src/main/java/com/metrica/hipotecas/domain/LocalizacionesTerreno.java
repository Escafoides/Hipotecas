package com.metrica.hipotecas.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LocalizacionesTerreno.
 */
@Entity
@Table(name = "localizaciones_terreno")
public class LocalizacionesTerreno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude")
    private Integer latitude;

    @Column(name = "longitude")
    private Integer longitude;

    @Column(name = "pais")
    private String pais;

    @Column(name = "comunidad")
    private String comunidad;

    @Column(name = "provincia")
    private String provincia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public LocalizacionesTerreno latitude(Integer latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public LocalizacionesTerreno longitude(Integer longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public String getPais() {
        return pais;
    }

    public LocalizacionesTerreno pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getComunidad() {
        return comunidad;
    }

    public LocalizacionesTerreno comunidad(String comunidad) {
        this.comunidad = comunidad;
        return this;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public LocalizacionesTerreno provincia(String provincia) {
        this.provincia = provincia;
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocalizacionesTerreno localizacionesTerreno = (LocalizacionesTerreno) o;
        if (localizacionesTerreno.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, localizacionesTerreno.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LocalizacionesTerreno{" +
            "id=" + id +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            ", pais='" + pais + "'" +
            ", comunidad='" + comunidad + "'" +
            ", provincia='" + provincia + "'" +
            '}';
    }
}
