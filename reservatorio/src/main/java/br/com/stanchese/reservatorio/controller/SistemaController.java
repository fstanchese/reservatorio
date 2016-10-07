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

import br.com.stanchese.reservatorio.model.Sistema;
import br.com.stanchese.reservatorio.service.MunicipioService;
import br.com.stanchese.reservatorio.service.SistemaService;
import br.com.stanchese.reservatorio.util.ConexaoDB;
import br.com.stanchese.reservatorio.util.GeradorRelatorio;
import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/sistemas")
public class SistemaController {

	private SistemaService serviceSistema;
	private MunicipioService serviceMunicipio;
	
	@Autowired
	public SistemaController(SistemaService serviceSistema, MunicipioService serviceMunicipio) {
		this.serviceSistema = serviceSistema;
		this.serviceMunicipio = serviceMunicipio;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String listarTodos(Model model) {
		model.addAttribute("sistemas",serviceSistema.listar());
		return "views/sistema/lista";
	}
	
	@RequestMapping(value="/novo")
	public String add(Model model) {
		model.addAttribute("sistema",new Sistema());
		model.addAttribute("municipioLista",serviceMunicipio.listar());
		return "views/sistema/crud";
	}

	@RequestMapping(value="/edit/{idSistema}",method = RequestMethod.GET)
	public String edit(Model model, @PathVariable("idSistema") Long idSistema) {
		model.addAttribute("sistema",serviceSistema.buscaPorId(idSistema));
		model.addAttribute("municipioLista",serviceMunicipio.listar());
		return "views/sistema/crud";
	}

	@RequestMapping(value="/delete/{idSistema}",method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("idSistema") Long idSistema) {
		serviceSistema.remover(idSistema);
		model.addAttribute("sistemas",serviceSistema.listar());
		return "redirect:/sistemas";
	}
	
	@RequestMapping(value="/crudSistema",method = RequestMethod.POST)
	public String crud(@Valid Sistema sistema,BindingResult result, Model model) {
		if (!result.hasErrors()) {
			serviceSistema.salvar(sistema);
			return "redirect:/sistemas";
		} else {
			model.addAttribute("sistema",sistema);
			model.addAttribute("municipioLista",serviceMunicipio.listar());
			return "views/sistema/crud";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "edit/sistemas/{id}")
	@ResponseBody
	public Sistema buscarSistema(@PathVariable Long id) {
		Sistema sistema = serviceSistema.buscaPorId(id);
		return sistema;
	}
	
	@RequestMapping(value = "/relatorio", method = RequestMethod.GET)
	@ResponseBody
	public void getRelatorio(HttpServletRequest request,HttpServletResponse response) throws JRException, IOException, SQLException, ClassNotFoundException {
		String nome = request.getServletContext().getRealPath("/WEB-INF/report/sistemas.jasper");
        Map<String, Object> parametros = new HashMap<String, Object>();
		Connection connection = (Connection) ConexaoDB.getConnection();
        GeradorRelatorio gerador = new GeradorRelatorio(nome, parametros, connection);
        gerador.geraPDFParaOutputStream(response.getOutputStream());
	}
}
