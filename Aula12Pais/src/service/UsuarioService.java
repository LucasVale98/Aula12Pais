package service;

import dao.UsuarioDAO;
import model.Usuario;

public class UsuarioService {
	
	public boolean validar(Usuario usuario) throws Exception {
		UsuarioDAO dao = new UsuarioDAO();
		return dao.validar(usuario);
	}

}
