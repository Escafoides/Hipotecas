package com.metrica.hipotecas.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Terreno.
 */
@Entity
@Table(name = "terreno")
public class Terreno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "precio")
    private Float precio;

    @OneToOne
    @JoinColumn(unique = true)
    private CaracteristicasTerreno caracteristicasTerreno;

    @OneToOne
    @JoinColumn(unique = true)
    private ServiciosTerreno serviciosTerreno;

    @OneToOne
    @JoinColumn(unique = true)
    private LocalizacionesTerreno localizacionesTerreno;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Terreno nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public Terreno precio(Float precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public CaracteristicasTerreno getCaracteristicasTerreno() {
        return caracteristicasTerreno;
    }

    public Terreno caracteristicasTerreno(CaracteristicasTerreno caracteristicasTerreno) {
        this.caracteristicasTerreno = caracteristicasTerreno;
        return this;
    }

    public void setCaracteristicasTerreno(CaracteristicasTerreno caracteristicasTerreno) {
        this.caracteristicasTerreno = caracteristicasTerreno;
    }

    public ServiciosTerreno getServiciosTerreno() {
        return serviciosTerreno;
    }

    public Terreno serviciosTerreno(ServiciosTerreno serviciosTerreno) {
        this.serviciosTerreno = serviciosTerreno;
        return this;
    }

    public void setServiciosTerreno(ServiciosTerreno serviciosTerreno) {
        this.serviciosTerreno = serviciosTerreno;
    }

    public LocalizacionesTerreno getLocalizacionesTerreno() {
        return localizacionesTerreno;
    }

    public Terreno localizacionesTerreno(LocalizacionesTerreno localizacionesTerreno) {
        this.localizacionesTerreno = localizacionesTerreno;
        return this;
    }

    public void setLocalizacionesTerreno(LocalizacionesTerreno localizacionesTerreno) {
        this.localizacionesTerreno = localizacionesTerreno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Terreno terreno = (Terreno) o;
        if (terreno.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, terreno.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Terreno{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", precio='" + precio + "'" +
            '}';
    }
}
