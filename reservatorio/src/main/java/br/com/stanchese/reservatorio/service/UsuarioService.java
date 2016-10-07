package br.com.stanchese.reservatorio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.stanchese.reservatorio.model.Usuario;
import br.com.stanchese.reservatorio.repository.UsuarioRepositorio;

@Service
public class UsuarioService {
	
	private UsuarioRepositorio repositorio;

	@Autowired
	public UsuarioService(UsuarioRepositorio usuarioRepositorio) {
		this.repositorio = usuarioRepositorio;
	}

	public Usuario buscaUsuario(Usuario usuario) {
		usuario = repositorio.findByLoginAndSenha(usuario.getLogin(),usuario.getSenha());
		return usuario;
	}	
	

}
