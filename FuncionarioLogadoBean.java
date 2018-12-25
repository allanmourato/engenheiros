package br.com.sistema.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.sistema.modelo.Funcionario;

@Named
@SessionScoped
public class FuncionarioLogadoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Funcionario funcionario;
	
	public void logar(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public void deslogar() {
		this.funcionario = null;
	}
	
	public boolean isLogado() {
		return funcionario != null;
	}

	
	
	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	

}
