package br.com.stanchese.reservatorio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.stanchese.reservatorio.model.Municipio;
import br.com.stanchese.reservatorio.repository.MunicipioRepositorio;

@Service
public class MunicipioService {
	
	private MunicipioRepositorio repositorio;

	@Autowired
	public MunicipioService(MunicipioRepositorio municipioRepositorio) {
		this.repositorio = municipioRepositorio;
	}
	
	public void salvar(Municipio municipio) {
		repositorio.save(municipio);
	}
	
	public Municipio buscaPorId(Long id) {
		return repositorio.findOne(id);
	}
	
	public void remover(Long id) {
		Municipio municipio = this.buscaPorId(id);
		repositorio.delete(municipio);
	}
	
	public List<Municipio> listar() {
		return repositorio.findAllOrderByNome();
	}
}
