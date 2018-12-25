package br.com.sistema.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.sistema.modelo.ContaBancaria;

@Stateless
public class ContaBancariaDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;
	
	public void adiciona (ContaBancaria contaBancaria) {
		manager.persist(contaBancaria);
	}
	
	public void remove (ContaBancaria contaBancaria) {
		ContaBancaria contaBancariaARemover = this.manager.find(ContaBancaria.class, contaBancaria.getId());
		this.manager.remove(contaBancariaARemover);
	}
	
	public void atualiza (ContaBancaria contaBancaria) {
		manager.merge(contaBancaria);
	}
	
	public ContaBancaria pegaUnicaConta() {
		return manager.createQuery("select c from ContaBancaria c",ContaBancaria.class).getSingleResult();
	}
	
	public List<ContaBancaria> listatodas() {
		return manager.createQuery("select c from ContaBancaria c", ContaBancaria.class).getResultList();
	}
	
	public ContaBancaria busca (long id) {
		return this.manager.find(ContaBancaria.class, id);
	}

}
