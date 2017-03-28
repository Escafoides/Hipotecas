package com.metrica.hipotecas.repository;

import com.metrica.hipotecas.domain.Terreno;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Terreno entity.
 */
@SuppressWarnings("unused")
public interface TerrenoRepository extends JpaRepository<Terreno,Long> {

}
