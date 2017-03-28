package com.metrica.hipotecas.repository;

import com.metrica.hipotecas.domain.ImporteFinal;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ImporteFinal entity.
 */
@SuppressWarnings("unused")
public interface ImporteFinalRepository extends JpaRepository<ImporteFinal,Long> {

}
