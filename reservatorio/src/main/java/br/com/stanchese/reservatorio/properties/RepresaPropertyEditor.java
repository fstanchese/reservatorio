package br.com.stanchese.reservatorio.properties;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.stanchese.reservatorio.model.Represa;
import br.com.stanchese.reservatorio.repository.RepresaRepositorio;

@Component
public class RepresaPropertyEditor extends PropertyEditorSupport {

	@Autowired 
	private RepresaRepositorio repositorio;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id;
		Represa represa;
		try {
			id = Long.parseLong(text);
			represa = repositorio.findOne(id);
		} catch (Exception e) {
			represa = null;
		}
		setValue(represa);
	}
}
