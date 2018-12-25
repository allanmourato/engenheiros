package br.com.sistema.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.sistema.modelo.Categoria;

@Stateless
public class CategoriaDao implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;
	
	public void adiciona(Categoria categoria) {
		this.manager.persist(categoria);
	}
	
	public void remover(Categoria categoria) {
		Categoria categoriaParaRemover = this.manager.find(Categoria.class, categoria.getId());
		this.manager.remove(categoriaParaRemover);
	}
	
	public List<Categoria> lista() {
		return manager.createQuery("select c from Categoria c",Categoria.class).getResultList();
	}
	
	public Categoria busca (long id) {
		return this.manager.find(Categoria.class, id);
	}
	
	public Categoria buscaPorCategoria(Categoria categoria) {
		this.manager.find(Categoria.class, categoria.getId());
		
		return categoria;
	}
	
	public void atualiza(Categoria categoria) {
		this.manager.merge(categoria);
	}
	
	
}
