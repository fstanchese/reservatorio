package br.com.stanchese.reservatorio.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.Connection;

import br.com.stanchese.reservatorio.model.Represa;
import br.com.stanchese.reservatorio.model.Sistema;
import br.com.stanchese.reservatorio.properties.SistemaPropertyEditor;
import br.com.stanchese.reservatorio.service.RepresaService;
import br.com.stanchese.reservatorio.service.SistemaService;
import br.com.stanchese.reservatorio.util.ConexaoDB;
import br.com.stanchese.reservatorio.util.GeradorRelatorio;
import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/represas")
public class RepresaController {

	private RepresaService serviceRepresa;
	private SistemaService serviceSistema;
	private SistemaPropertyEditor sistemaPropertyEditor;
	
	@Autowired
	public RepresaController(RepresaService serviceRepresa, 
			SistemaPropertyEditor sistemaPropertyEditor, SistemaService serviceSistema) {
		this.serviceRepresa = serviceRepresa;
		this.sistemaPropertyEditor = sistemaPropertyEditor;
		this.serviceSistema = serviceSistema;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String listarTodos(Model model) {
		model.addAttribute("represas",serviceRepresa.listar());
		return "views/represa/lista";
	}
	
	@RequestMapping(value="/novo")
	public String add(Model model) {
		model.addAttribute("represa",new Represa());
		model.addAttribute("sistemas",serviceSistema.listar());
		return "views/represa/crud";
	}

	@RequestMapping(value="/edit/{idRepresa}",method = RequestMethod.GET)
	public String edit(Model model, @PathVariable("idRepresa") Long idRepresa) {
		model.addAttribute("represa",serviceRepresa.buscaPorId(idRepresa));
		model.addAttribute("sistemas",serviceSistema.listar());
		return "views/represa/crud";
	}

	@RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("id") Long id) {
		serviceRepresa.remover(id);
		model.addAttribute("represas",serviceRepresa.listar());
		return "redirect:/represas";
	}
	
	@RequestMapping(value="/crudRepresa",method = RequestMethod.POST)
	public String crud(@Valid Represa represa,BindingResult result, Model model) {
		if (!result.hasErrors()) {
			serviceRepresa.salvar(represa);
			return "redirect:/represas";
		} else {
			model.addAttribute("sistemas",serviceSistema.listar());
			model.addAttribute("represa",represa);
			return "views/represa/crud";
		}
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(Sistema.class, sistemaPropertyEditor);
	}

	@RequestMapping(value = "/relatorio", method = RequestMethod.GET)
	@ResponseBody
	public void getRelatorio(HttpServletRequest request,HttpServletResponse response) throws JRException, IOException, SQLException, ClassNotFoundException {
		String nome = request.getServletContext().getRealPath("/WEB-INF/report/represas.jasper");
        Map<String, Object> parametros = new HashMap<String, Object>();
		Connection connection = (Connection) ConexaoDB.getConnection();
        
        GeradorRelatorio gerador = new GeradorRelatorio(nome, parametros, connection);
        gerador.geraPDFParaOutputStream(response.getOutputStream());
	}
}
