package br.com.stanchese.reservatorio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.stanchese.reservatorio.model.Sistema;

public interface SistemaRepositorio extends JpaRepository<Sistema, Long> {

	@Query("select s from Sistema s order by s.nome")
	List<Sistema> findAllOrderByNome();

}
