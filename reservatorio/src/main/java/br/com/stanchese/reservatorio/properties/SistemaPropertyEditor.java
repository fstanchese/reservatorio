package br.com.stanchese.reservatorio.properties;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.stanchese.reservatorio.model.Sistema;
import br.com.stanchese.reservatorio.repository.SistemaRepositorio;

@Component
public class SistemaPropertyEditor extends PropertyEditorSupport {

	@Autowired 
	private SistemaRepositorio repositorio;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id;
		Sistema sistema;
		try {
			id = Long.parseLong(text);
			sistema = repositorio.findOne(id);
		} catch (Exception e) {
			sistema = null;
		}
		setValue(sistema);
	}
}
