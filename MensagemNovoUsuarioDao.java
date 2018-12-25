package br.com.sistema.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.sistema.modelo.MensagemNovoUsuario;

@Stateless
public class MensagemNovoUsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;
	
	public void adiciona(MensagemNovoUsuario mensagemNovoUsuario) {
		manager.persist(mensagemNovoUsuario);
	}
	
	public void remove(MensagemNovoUsuario mensagemNovoUsuario) {
		MensagemNovoUsuario mensagemNovoUsuarioRemover = this.manager.find(MensagemNovoUsuario.class, mensagemNovoUsuario.getId());
		this.manager.remove(mensagemNovoUsuarioRemover);
	}
	
	public List<MensagemNovoUsuario> buscaMensagemNovoUsuario() {
		return manager.createQuery("select m from MensagemNovoUsuario m " + "order by m.dataMensagem DESC", MensagemNovoUsuario.class).getResultList();
	}

}
