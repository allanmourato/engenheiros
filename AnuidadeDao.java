package br.com.sistema.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.sistema.modelo.Anuidade;
import br.com.sistema.modelo.Usuario;


@Stateless
public class AnuidadeDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;
	
	public void adiciona(Anuidade anuidade) {
		manager.persist(anuidade);
	}
	
	public void remover(Anuidade anuidade) {
		Anuidade anuidadeParaRemover = this.manager.find(Anuidade.class, anuidade.getId());
		this.manager.remove(anuidadeParaRemover);
	}
	
	public void atualiza(Anuidade anuidade) {
		manager.merge(anuidade);
	}
	
	public Anuidade buscaAnuidade(Anuidade anuidade) {
		this.manager.find(Anuidade.class, anuidade.getId());
		
		return anuidade;
	}
	
	public List<Anuidade> lista() {
		return manager.createQuery("select a from Anuidade a " + "order by a.titulo ASC", Anuidade.class).getResultList();
	}
	
	public  Anuidade anuidadePorId (long id) {
		return this.manager.find(Anuidade.class, id);
	}
	
	public Anuidade buscaAnuidadePorId(long id) {
		String jpql = ("select a from Anuidade a where a.anuidade.id = :id");
		
		Query query = manager.createQuery(jpql,Anuidade.class);
		query.setParameter("id", id);
		
		return (Anuidade)query.getSingleResult();
		
	}

	

}
