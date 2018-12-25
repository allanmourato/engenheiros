package br.com.sistema.bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistema.dao.FuncionarioDao;
import br.com.sistema.modelo.Funcionario;
import br.com.sistema.util.Menssagens;

@Named
@RequestScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Funcionario funcionario = new Funcionario();
	
	@Inject
	private Menssagens mensagens;
	
	@Inject
	private FuncionarioDao dao;
	
	@Inject
	private FuncionarioLogadoBean funcionarioLogadoBean;
	
	public String efetuaLogin() {
		
		boolean loginValido = dao.existe(this.funcionario);
		System.out.println("O login era valido? " + loginValido);
		
		if(loginValido) {
			funcionarioLogadoBean.logar(funcionario);
			return "index?faces-redirect=true";
		}else {
			mensagens.error("Email ou senha não encontrado.");
			funcionarioLogadoBean.deslogar();
			return "login";
		}
	}
	
	public String logout () {
		System.out.println("Lougout de :" + funcionarioLogadoBean.getFuncionario().getEmail());
		funcionarioLogadoBean.deslogar();
		return "login?faces-redirect=true";
	}
	
	public Funcionario getFuncionario() {
		return this.funcionario;
	}
}


