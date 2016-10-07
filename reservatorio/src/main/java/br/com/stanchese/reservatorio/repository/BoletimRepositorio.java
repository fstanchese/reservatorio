package br.com.stanchese.reservatorio.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.stanchese.reservatorio.model.Boletim;
import br.com.stanchese.reservatorio.model.Represa;
import br.com.stanchese.reservatorio.model.Sistema;

public interface BoletimRepositorio extends JpaRepository<Boletim, Long> {

	@Query("select b from Boletim b order by dataBoletim")
	List<Boletim> findAllOrderByData();
	
	@Query("select b from Boletim b join b.represa r join r.sistema s "
			+ "where b.represa = :represa or (r.sistema = :sistema and :represa is null) "
			+ "order by s.nome,r.nome,b.dataBoletim")
	List<Boletim> findAllBySistemaOrRepresaOrderByData(@Param("sistema") Sistema sistema,
													   @Param("represa") Represa represa);
	
	@Query("select b from Boletim b where b.represa = :represa and b.dataBoletim = :dataBoletim")
	Boletim findByDataBoletimAndRepresa(@Param("dataBoletim") Date dataBoletim, 
							   			@Param("represa") Represa represa);
}
