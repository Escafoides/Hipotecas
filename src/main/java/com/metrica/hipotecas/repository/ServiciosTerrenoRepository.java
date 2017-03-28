package com.metrica.hipotecas.repository;

import com.metrica.hipotecas.domain.ServiciosTerreno;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ServiciosTerreno entity.
 */
@SuppressWarnings("unused")
public interface ServiciosTerrenoRepository extends JpaRepository<ServiciosTerreno,Long> {

}
