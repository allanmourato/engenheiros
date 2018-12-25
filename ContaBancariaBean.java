package br.com.sistema.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistema.dao.ContaBancariaDao;
import br.com.sistema.modelo.ContaBancaria;

@Named
@SessionScoped
public class ContaBancariaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	ContaBancaria contaBancaria = new ContaBancaria();
	
	@Inject
	private ContaBancariaDao dao;
		
	private List<ContaBancaria> contas;
	
	
	
	
	public ContaBancaria getContaBancaria() {
		return contaBancaria;
	}

	public void setContaBancaria(ContaBancaria contaBancaria) {
		this.contaBancaria = contaBancaria;
	}


	
	

	public void adicionaContaBancaria () {
		
		if (contaBancaria.getId() == 0) {
			dao.adiciona(contaBancaria);
			contas = dao.listatodas();
			limpaFormularioDoJSF();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sistema Aurun", "Conta Bancaria adicionada"));
		}else{
			dao.atualiza(contaBancaria);
			contas = dao.listatodas();
			limpaFormularioDoJSF();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sistema Aurun", "Conta Bancaria Atualizada"));

		}
	}
	
	public void removeContaBancaria() {
		dao.remove(contaBancaria);
		contas = dao.listatodas();
		limpaFormularioDoJSF();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Sistema Aurun", "Conta Bancaria Removida"));

	}
	
	public List<ContaBancaria> getContas() {
		this.contas = dao.listatodas();
		
		return contas;
	}
	
	private void limpaFormularioDoJSF() {
		this.contaBancaria = new ContaBancaria();
	}

}
