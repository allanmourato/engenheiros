package br.com.sistema.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.sistema.modelo.Cedente;

@Stateless
public class CedenteDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;
	
	public void adicionaCedente(Cedente cedente) {
		this.manager.persist(cedente);
	}
	
	public void atualizaCedente(Cedente cedente) {
		this.manager.merge(cedente);
	}
	
	public void removeCedente (Cedente cedente) {
		Cedente cedenteRemover = this.manager.find(Cedente.class, cedente.getId());
		this.manager.remove(cedenteRemover);
	}
	
	public List<Cedente> pegaTodos() {
		return manager.createQuery("select c from Cedente c",Cedente.class).getResultList();
	}
	
	public Cedente pegaCedente() {
		return manager.createQuery("select c from Cedente c",Cedente.class).getSingleResult();
	}


}
