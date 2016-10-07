package br.com.stanchese.reservatorio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.stanchese.reservatorio.model.Municipio;

public interface MunicipioRepositorio extends JpaRepository<Municipio, Long> {

	@Query("select m from Municipio m order by m.nome")
	List<Municipio> findAllOrderByNome();
}
