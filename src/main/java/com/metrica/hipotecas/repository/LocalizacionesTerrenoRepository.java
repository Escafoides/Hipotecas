package com.metrica.hipotecas.repository;

import com.metrica.hipotecas.domain.LocalizacionesTerreno;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LocalizacionesTerreno entity.
 */
@SuppressWarnings("unused")
public interface LocalizacionesTerrenoRepository extends JpaRepository<LocalizacionesTerreno,Long> {

}
