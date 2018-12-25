package br.com.sistema.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.sistema.modelo.Funcionario;

@Stateless
public class FuncionarioDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;
	
	public void adiciona(Funcionario funcionario){
		this.manager.persist(funcionario);
	}
	
	public void atualiza(Funcionario funcionario){
		//Funcionario funcionarioAtualiza = this.manager.find(Funcionario.class, funcionario.getId());
		this.manager.merge(funcionario);
	}
	
	public void remove(Funcionario funcionario){;
		Funcionario funcionarioRemover = this.manager.find(Funcionario.class, funcionario.getId());
		this.manager.remove(funcionarioRemover);
	}
	
	public List<Funcionario> buscaFuncionarios() {
		return this.manager.createQuery("select f from Funcionario f",Funcionario.class).getResultList();
	}
	
	public boolean existe(Funcionario funcionario) {
		String jpql = ("select f from Funcionario f where f.email = " + ":pEmail and f.senha = :pSenha");
		Query query = this.manager.createQuery(jpql);
		query.setParameter("pEmail", funcionario.getEmail());
		query.setParameter("pSenha", funcionario.getSenha());
		
		boolean encontrado = !query.getResultList().isEmpty();
		return encontrado;
	}
	
}
