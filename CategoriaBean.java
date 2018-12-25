package br.com.sistema.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistema.dao.CategoriaDao;
import br.com.sistema.modelo.Categoria;

@Named
@SessionScoped
public class CategoriaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	CategoriaDao dao;
	
	private Categoria categoria = new Categoria();
	private List<Categoria> categorias;
	
	
	
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
	
	
	//MÉTODOS

	public void adicionaCategoria() {
		
		if (categoria.getId() == 0) {
			dao.adiciona(categoria);
			this.categorias = dao.lista();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sistema Aeasc", "Categoria cadastrada com sucesso"));
			System.out.println("categoria adicionada com sucesso" + "---" + categoria.getNome());
		} else {
			dao.atualiza(categoria);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sistema Aeasc", "Categoria atualizada com sucesso"));
			System.out.println("categoria atualizada com sucesso");
		}
		
		limpaFormularioDoJSF();
	}
	
	public void removeCategoria() {
		dao.remover(categoria);
		this.categorias = dao.lista();
		System.out.println("Removendo Categoria");
		
		
		limpaFormularioDoJSF();
	}
	
	public List<Categoria> getCategorias() {
		if(categorias == null){
			this.categorias = dao.lista();
			System.out.println("listando categorias");
		}
		return categorias;
	}
	

	
	/**
	 * Esse metodo apenas limpa o formulario da forma com que o JSF espera.
	 * Invoque-o no momento em que precisar do formulario vazio.
	 */
	private void limpaFormularioDoJSF() {
		this.categoria = new Categoria();
	}
	
	

}
