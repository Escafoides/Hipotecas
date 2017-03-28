package com.metrica.hipotecas.repository;

import com.metrica.hipotecas.domain.CaracteristicasTerreno;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CaracteristicasTerreno entity.
 */
@SuppressWarnings("unused")
public interface CaracteristicasTerrenoRepository extends JpaRepository<CaracteristicasTerreno,Long> {

}
