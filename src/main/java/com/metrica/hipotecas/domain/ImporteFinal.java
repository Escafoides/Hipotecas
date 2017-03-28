package com.metrica.hipotecas.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ImporteFinal.
 */
@Entity
@Table(name = "importe_final")
public class ImporteFinal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "intervalo")
    private Integer intervalo;

    @Column(name = "capital_inicial")
    private Float capitalInicial;

    @Column(name = "interes_anual")
    private Float interesAnual;

    @Column(name = "importe_anual")
    private Float importeAnual;

    @Column(name = "importe_mes")
    private Float importeMes;

    @Column(name = "importe_total")
    private Float importeTotal;

    @OneToOne
    @JoinColumn(unique = true)
    private Terreno terreno;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIntervalo() {
        return intervalo;
    }

    public ImporteFinal intervalo(Integer intervalo) {
        this.intervalo = intervalo;
        return this;
    }

    public void setIntervalo(Integer intervalo) {
        this.intervalo = intervalo;
    }

    public Float getCapitalInicial() {
        return capitalInicial;
    }

    public ImporteFinal capitalInicial(Float capitalInicial) {
        this.capitalInicial = capitalInicial;
        return this;
    }

    public void setCapitalInicial(Float capitalInicial) {
        this.capitalInicial = capitalInicial;
    }

    public Float getInteresAnual() {
        return interesAnual;
    }

    public ImporteFinal interesAnual(Float interesAnual) {
        this.interesAnual = interesAnual;
        return this;
    }

    public void setInteresAnual(Float interesAnual) {
        this.interesAnual = interesAnual;
    }

    public Float getImporteAnual() {
        return importeAnual;
    }

    public ImporteFinal importeAnual(Float importeAnual) {
        this.importeAnual = importeAnual;
        return this;
    }

    public void setImporteAnual(Float importeAnual) {
        this.importeAnual = importeAnual;
    }

    public Float getImporteMes() {
        return importeMes;
    }

    public ImporteFinal importeMes(Float importeMes) {
        this.importeMes = importeMes;
        return this;
    }

    public void setImporteMes(Float importeMes) {
        this.importeMes = importeMes;
    }

    public Float getImporteTotal() {
        return importeTotal;
    }

    public ImporteFinal importeTotal(Float importeTotal) {
        this.importeTotal = importeTotal;
        return this;
    }

    public void setImporteTotal(Float importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Terreno getTerreno() {
        return terreno;
    }

    public ImporteFinal terreno(Terreno terreno) {
        this.terreno = terreno;
        return this;
    }

    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImporteFinal importeFinal = (ImporteFinal) o;
        if (importeFinal.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, importeFinal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ImporteFinal{" +
            "id=" + id +
            ", intervalo='" + intervalo + "'" +
            ", capitalInicial='" + capitalInicial + "'" +
            ", interesAnual='" + interesAnual + "'" +
            ", importeAnual='" + importeAnual + "'" +
            ", importeMes='" + importeMes + "'" +
            ", importeTotal='" + importeTotal + "'" +
            '}';
    }
}
