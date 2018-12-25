package br.com.sistema.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistema.dao.MensagemNovoUsuarioDao;
import br.com.sistema.modelo.MensagemNovoUsuario;

@Named
@SessionScoped
public class MensagemNovoUsuarioBean implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	MensagemNovoUsuario mensagemNovoUsuario = new MensagemNovoUsuario();
	
	@Inject
	private MensagemNovoUsuarioDao dao;
	
	
	public MensagemNovoUsuario getMensagemNovoUsuario() {
		return mensagemNovoUsuario;
	}

	public void setMensagemNovoUsuario(MensagemNovoUsuario mensagemNovoUsuario) {
		this.mensagemNovoUsuario = mensagemNovoUsuario;
	}
	
	public void removeMensagem() {
		dao.remove(mensagemNovoUsuario);
		buscaTodas();
	}
	
	public List<MensagemNovoUsuario> buscaTodas() {
		List<MensagemNovoUsuario> buscaMensagemNovoUsuario = dao.buscaMensagemNovoUsuario();
		
		return buscaMensagemNovoUsuario;
	}

}
