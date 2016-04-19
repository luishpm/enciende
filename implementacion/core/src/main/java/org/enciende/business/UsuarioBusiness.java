package org.enciende.business;

import org.enciende.model.Usuario;

public interface UsuarioBusiness {
	public Usuario getUsuarioByTokenFacebook(String token);

	public Usuario registrarFacebook(String tokenFacebook, String tokenEvento);
}
