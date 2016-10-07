package br.com.stanchese.reservatorio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.stanchese.reservatorio.model.Sistema;
import br.com.stanchese.reservatorio.repository.SistemaRepositorio;

@Service
public class SistemaService {
	
	private SistemaRepositorio repositorio;

	@Autowired
	public SistemaService(SistemaRepositorio SistemaRepositorio) {
		this.repositorio = SistemaRepositorio;
	}
	
	public void salvar(Sistema Sistema) {
		repositorio.save(Sistema);
	}
	
	public Sistema buscaPorId(Long id) {
		return repositorio.findOne(id);
	}
	
	public void remover(Long id) {
		Sistema Sistema = this.buscaPorId(id);
		repositorio.delete(Sistema);
	}
	
	public List<Sistema> listar() {
		return repositorio.findAllOrderByNome();
	}

}
