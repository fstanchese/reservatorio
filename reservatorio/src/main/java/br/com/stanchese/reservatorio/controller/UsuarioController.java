package br.com.stanchese.reservatorio.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.stanchese.reservatorio.model.Usuario;
import br.com.stanchese.reservatorio.service.UsuarioService;

@Controller
public class UsuarioController {

	private UsuarioService usuarioSerice;

	@Autowired
	public UsuarioController(UsuarioService usuarioSerice) {
		this.usuarioSerice = usuarioSerice;
	}

	@RequestMapping("loginForm")
	public String loginForm() {
		return "formulario-login";
	}

	@RequestMapping("menu")
	public String menuForm() {
		return "menuPrincipal";
	}
	
	@RequestMapping("efetuaLogin")
	public String efetuaLogin(Usuario usuario, HttpSession sessao) {
		Usuario login = usuarioSerice.buscaUsuario(usuario);
		if (login != null) {
			sessao.setAttribute("usuarioLogado", usuario);
			return "redirect:menu";
		}
		return "redirect:loginForm";
	}

	@RequestMapping("logout")
	public String logout(HttpSession sessao) {
		sessao.invalidate();
		return "redirect:loginForm";
	}
}
