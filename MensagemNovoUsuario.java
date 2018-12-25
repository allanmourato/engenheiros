package br.com.sistema.modelo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("MENSAGEM_NOVO_USUARIO")
public class MensagemNovoUsuario extends Mensagem  {


	private static final long serialVersionUID = 1L;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
	

}
