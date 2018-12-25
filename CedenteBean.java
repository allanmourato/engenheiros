package br.com.sistema.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistema.dao.CedenteDao;
import br.com.sistema.dao.ContaBancariaDao;
import br.com.sistema.modelo.Cedente;
import br.com.sistema.modelo.ContaBancaria;

@Named
@SessionScoped
public class CedenteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Cedente cedente = new Cedente();
	
	@Inject
	private CedenteDao dao;
	
	private List<Cedente> cedentes;
	
	@Inject
	private ContaBancariaDao contaBancariaDao;
	
	private long pegaIdContaBancaria;
	
	

	
	
	
	
	public long getPegaIdContaBancaria() {
		return pegaIdContaBancaria;
	}

	public void setPegaIdContaBancaria(long pegaIdContaBancaria) {
		this.pegaIdContaBancaria = pegaIdContaBancaria;
	}

	public Cedente getCedente() {
		return cedente;
	}

	public void setCedente(Cedente cedente) {
		this.cedente = cedente;
	}
	
	

	public void adiciona () {
		if(cedente.getId() == 0) {
			ContaBancaria contaBancariaRelacionada = contaBancariaDao.busca(pegaIdContaBancaria);
			cedente.setContaBancaria(contaBancariaRelacionada);
			dao.adicionaCedente(cedente);
			this.cedentes = dao.pegaTodos();
			limpaFormularioDoJSF();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cedente Adicionado", "Sistema Aurun"));

		}else{
			dao.atualizaCedente(cedente);
			this.cedentes = dao.pegaTodos();
			limpaFormularioDoJSF();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sistema Aeasc", "Cedente Atualizado"));

		}
	}
	
	public void remove() {
		this.dao.removeCedente(cedente);
		this.cedentes = dao.pegaTodos();
		limpaFormularioDoJSF();
	}
	
	public List<Cedente> todos() {
		this.cedentes = dao.pegaTodos();
		return cedentes;
	}
	
	private void limpaFormularioDoJSF() {
		this.cedente = new Cedente();
	}
	
	

}
