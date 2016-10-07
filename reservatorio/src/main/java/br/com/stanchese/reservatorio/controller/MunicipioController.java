package br.com.stanchese.reservatorio.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.Connection;

import br.com.stanchese.reservatorio.model.Municipio;
import br.com.stanchese.reservatorio.service.MunicipioService;
import br.com.stanchese.reservatorio.util.ConexaoDB;
import br.com.stanchese.reservatorio.util.GeradorRelatorio;
import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/municipios")
public class MunicipioController {

	private MunicipioService serviceMunicipio;
	
	@Autowired
	public MunicipioController(MunicipioService serviceMunicipio) {
		this.serviceMunicipio = serviceMunicipio;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String listarTodos(Model model) {
		model.addAttribute("municipios", serviceMunicipio.listar());
		return "views/municipio/lista";
	}

	@RequestMapping(value = "/novo")
	public String add(Model model) {
		model.addAttribute("municipio", new Municipio());
		return "views/municipio/crud";
	}

	@RequestMapping(value = "/edit/{idMunicipio}", method = RequestMethod.GET)
	public String edit(Model model, @PathVariable("idMunicipio") Long idMunicipio) {
		model.addAttribute("municipio", serviceMunicipio.buscaPorId(idMunicipio));
		return "views/municipio/crud";
	}

	@RequestMapping(value = "/delete/{idMunicipio}", method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("idMunicipio") Long idMunicipio) {
		serviceMunicipio.remover(idMunicipio);
		model.addAttribute("municipios", serviceMunicipio.listar());
		return "redirect:/municipios";
	}

	@RequestMapping(value = "/crudMunicipio", method = RequestMethod.POST)
	public String crud(@Valid Municipio municipio, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			serviceMunicipio.salvar(municipio);
			return "redirect:/municipios";
		} else {
			model.addAttribute("municipio", municipio);
			return "views/municipio/crud";
		}
	}

	@RequestMapping(value = "/relatorio", method = RequestMethod.GET)
	@ResponseBody
	public void getRelatorio(HttpServletRequest request,HttpServletResponse response) throws JRException, IOException, SQLException, ClassNotFoundException {
		String nome = request.getServletContext().getRealPath("/WEB-INF/report/municipios.jasper");
		Map<String, Object> parametros = new HashMap<String, Object>();
		Connection connection = (Connection) ConexaoDB.getConnection();
        GeradorRelatorio gerador = new GeradorRelatorio(nome, parametros, connection);
        gerador.geraPDFParaOutputStream(response.getOutputStream());
	}

}
