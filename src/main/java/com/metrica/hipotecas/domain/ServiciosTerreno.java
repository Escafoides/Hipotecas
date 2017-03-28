package com.metrica.hipotecas.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ServiciosTerreno.
 */
@Entity
@Table(name = "servicios_terreno")
public class ServiciosTerreno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agua")
    private Boolean agua;

    @Column(name = "luz")
    private Boolean luz;

    @Column(name = "telefono")
    private Boolean telefono;

    @Column(name = "carretera")
    private Boolean carretera;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAgua() {
        return agua;
    }

    public ServiciosTerreno agua(Boolean agua) {
        this.agua = agua;
        return this;
    }

    public void setAgua(Boolean agua) {
        this.agua = agua;
    }

    public Boolean isLuz() {
        return luz;
    }

    public ServiciosTerreno luz(Boolean luz) {
        this.luz = luz;
        return this;
    }

    public void setLuz(Boolean luz) {
        this.luz = luz;
    }

    public Boolean isTelefono() {
        return telefono;
    }

    public ServiciosTerreno telefono(Boolean telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(Boolean telefono) {
        this.telefono = telefono;
    }

    public Boolean isCarretera() {
        return carretera;
    }

    public ServiciosTerreno carretera(Boolean carretera) {
        this.carretera = carretera;
        return this;
    }

    public void setCarretera(Boolean carretera) {
        this.carretera = carretera;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiciosTerreno serviciosTerreno = (ServiciosTerreno) o;
        if (serviciosTerreno.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, serviciosTerreno.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ServiciosTerreno{" +
            "id=" + id +
            ", agua='" + agua + "'" +
            ", luz='" + luz + "'" +
            ", telefono='" + telefono + "'" +
            ", carretera='" + carretera + "'" +
            '}';
    }
}
