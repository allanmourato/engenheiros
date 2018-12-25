package br.com.sistema.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistema.dao.FuncionarioDao;
import br.com.sistema.modelo.Funcionario;
import br.com.sistema.util.Menssagens;

@Named
@ViewScoped
public class FuncionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Funcionario funcionario = new Funcionario();
	
	@Inject
	private FuncionarioDao dao;
	
	@Inject
	private Menssagens menssagens;
	
	private List<Funcionario> funcionarios;
	
	private String confirmaSenha;
	
	private long idParaAtualizar;
	
	
	

	public long getIdParaAtualizar() {
		return idParaAtualizar;
	}
	public void setIdParaAtualizar(long idParaAtualizar) {
		this.idParaAtualizar = idParaAtualizar;
	}
	public String getConfirmaSenha() {
		return confirmaSenha;
	}
	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	


	public void adicionaFuncionario() {
		if(verificaSenha() == false){
			menssagens.error("Senha Divergente");
		}else{
			if (funcionario.getId() == 0) {
				dao.adiciona(funcionario);
				menssagens.info("Funcionário Adicionado");
				this.funcionarios = dao.buscaFuncionarios();
				limpaFormularioJSF();
			}else{
				atualizaFuncionario();
				limpaFormularioJSF();
			}
		}
		
	}
	
	public void removeFuncionario () {
		dao.remove(funcionario);
		menssagens.info("Funcionário Removido");
	}
	
	public void atualizaFuncionario () {
		dao.atualiza(funcionario);
		this.funcionarios = dao.buscaFuncionarios();
		menssagens.info("Funcionário Atualizado");
	}
	
	
	public List<Funcionario> getFuncionarios (){
		this.funcionarios = dao.buscaFuncionarios();
		return funcionarios;
	}
	
	public boolean verificaSenha() {
		if(funcionario.getSenha().equals(confirmaSenha)) {
			return true;
		}
		return false;
	}
	
	public void limpaFormularioJSF() {
		this.funcionario = new Funcionario();
	}

}
