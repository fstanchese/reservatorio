package br.com.stanchese.reservatorio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.stanchese.reservatorio.model.Represa;
import br.com.stanchese.reservatorio.model.Sistema;
import br.com.stanchese.reservatorio.repository.RepresaRepositorio;

@Service
public class RepresaService {
	
	private RepresaRepositorio repositorio;

	@Autowired
	public RepresaService(RepresaRepositorio represaRepositorio) {
		this.repositorio = represaRepositorio;
	}
	
	public void salvar(Represa represa) {
		repositorio.save(represa);
	}
	
	public Represa buscaPorId(Long id) {
		return repositorio.findOne(id);
	}
	
	public void remover(Long id) {
		Represa represa = this.buscaPorId(id);
		repositorio.delete(represa);
	}
	
	public List<Represa> listar() {
		return repositorio.findAllOrderByNome();
	}

	public List<Represa> buscaPorSistema(Sistema sistema) {
		return repositorio.findAllBySistemaOrderByNome(sistema);
	}
	
}