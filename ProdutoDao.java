package br.com.sistema.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.sistema.modelo.Produto;

@Stateless
public class ProdutoDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;
	
	public void adiciona(Produto produto) {
		manager.persist(produto);
	}
	
	public void remover(Produto produto) {
		Produto produtoParaRemover = this.manager.find(Produto.class, produto.getId());
		this.manager.remove(produtoParaRemover);
	}
	
	public void atualiza(Produto produto) {
		manager.merge(produto);
	}
	
	public List<Produto> lista() {
		return manager.createQuery("select p from Produto p " + "order by p.titulo ASC", Produto.class).getResultList();
	}

}
