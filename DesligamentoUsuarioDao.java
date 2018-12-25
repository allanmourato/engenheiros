package br.com.sistema.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.sistema.modelo.DesligamentoUsuario;

@Stateless
public class DesligamentoUsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;
	
	public void AdicionaDesligamento(DesligamentoUsuario desligamentoUsuario) {
		this.manager.persist(desligamentoUsuario);
	}
	
	public void RemoveDesligamento(DesligamentoUsuario desligamentoUsuario) {
		DesligamentoUsuario removeDesligamento = manager.find(DesligamentoUsuario.class, desligamentoUsuario.getId());
		this.manager.remove(removeDesligamento);
	}
	
	public List<DesligamentoUsuario> lista() {
		return manager.createQuery("select d from DesligamentoUsuario d " + "order by d.nome ASC", DesligamentoUsuario.class).getResultList();
	}

}
