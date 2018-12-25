package br.com.sistema.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistema.dao.ProdutoDao;
import br.com.sistema.modelo.Produto;

@Named
@RequestScoped
public class ProdutoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Produto produto = new Produto();
	
	@Inject
	private ProdutoDao dao;
	
	private List<Produto> produtos;


	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	
	

	public void adicionaProduto() {
		dao.adiciona(produto);
		System.out.println("Produto adicionado");
	}
	
	public List<Produto> getProdutos() {
		System.out.println("Listando produtos");
		this.produtos = dao.lista();
		return produtos;
	}
	
	

}
