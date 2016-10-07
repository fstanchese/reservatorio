package br.com.stanchese.reservatorio.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.stanchese.reservatorio.model.Boletim;
import br.com.stanchese.reservatorio.model.Represa;
import br.com.stanchese.reservatorio.model.Sistema;
import br.com.stanchese.reservatorio.repository.BoletimRepositorio;

@Service
public class BoletimService {
	
	private BoletimRepositorio repositorio;

	@Autowired
	public BoletimService(BoletimRepositorio represaRepositorio) {
		this.repositorio = represaRepositorio;
	}
	
	public void salvar(Boletim boletim) {
		repositorio.save(boletim);
	}
	
	public Boletim buscaPorId(Long id) {
		return repositorio.findOne(id);
	}
	
	public void remover(Long id) {
		Boletim boletim = this.buscaPorId(id);
		repositorio.delete(boletim);
	}
	
	public List<Boletim> listar() {
		return repositorio.findAllOrderByData();
	}

	public List<Boletim> findAllBySistemaOrRepresaOrderByData(Sistema sistema, Represa represa) {
		List<Boletim> boletins = repositorio.findAllBySistemaOrRepresaOrderByData(sistema,represa);
		if (boletins.size() > 0) {
			Boletim boletimInicio = boletins.get(0);
			
			Date dataInicio 	= boletimInicio.getDataBoletim();
			Date dataFim		= boletimInicio.getDataBoletim();
			Represa represaIni 	= boletimInicio.getRepresa();
			
			for (Boletim boletim : boletins) {
				if (!boletim.getDataBoletim().equals(dataInicio) &&	boletim.getRepresa().getId() != represaIni.getId()) {		
					dataInicio 	= boletim.getDataBoletim();
					dataFim		= boletim.getDataBoletim();
					represaIni 	= boletim.getRepresa();					
				}
				if (boletim.getDataBoletim().equals(dataInicio) && boletim.getRepresa().getId() != represaIni.getId()) {
					dataFim 	= boletim.getDataBoletim();
					represaIni 	= boletim.getRepresa();					
				}
				if (!boletim.getDataBoletim().equals(dataInicio) && boletim.getRepresa().getId() == represaIni.getId()) {
					dataFim = boletim.getDataBoletim();
				}
				Double variacao = this.calculaVariacaoPorRepresa(dataInicio, dataFim, represaIni);
				boletim.setVariacao(variacao);
				if (!boletim.getDataBoletim().equals(dataInicio) && boletim.getRepresa().getId() == represaIni.getId()) {
					dataInicio 	= dataFim;
				}
			}
		}
		return boletins;
	}
	
	public double calculaVariacaoPorRepresa(Date dataInicio, Date dataFim, Represa represa) {
		Boletim boletimInicio = repositorio.findByDataBoletimAndRepresa(dataInicio,represa);
		Boletim boletimFim = repositorio.findByDataBoletimAndRepresa(dataFim,represa);
		
		Double volumeInicio = boletimInicio.getVolumeDia();
		Double volumeFim	= boletimFim.getVolumeDia();
		
		Double valor = ((100*(volumeFim - volumeInicio))/volumeInicio);
        DecimalFormat formato = new DecimalFormat("##.##");
		String dx = formato.format(valor);
		return Double.parseDouble(dx.replace(",", "."));
	}
}