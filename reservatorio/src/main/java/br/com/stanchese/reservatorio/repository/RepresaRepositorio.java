package br.com.stanchese.reservatorio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.stanchese.reservatorio.model.Represa;
import br.com.stanchese.reservatorio.model.Sistema;

public interface RepresaRepositorio extends JpaRepository<Represa, Long> {

	@Query("select r from Represa r join r.sistema s order by s.nome,r.nome")
	List<Represa> findAllOrderByNome();

	List<Represa> findAllBySistemaOrderByNome(Sistema sistema);

}
