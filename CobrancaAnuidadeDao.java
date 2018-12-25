package br.com.sistema.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.sistema.modelo.CobrancaAnuidade;
import br.com.sistema.modelo.Lancamento;
import br.com.sistema.modelo.StatusContas;
import br.com.sistema.modelo.Usuario;

@Stateless
public class CobrancaAnuidadeDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;
	
	public void adiciona(CobrancaAnuidade cobranca) {
		manager.persist(cobranca);
	}
	
	public List<CobrancaAnuidade> lista() {
		return manager.createQuery("select c from CobrancaAnuidade c ", CobrancaAnuidade.class).getResultList();
	}
	
	public void atualiza (CobrancaAnuidade cobranca) {
		this.manager.merge(cobranca);	
	}
	
	public void remove(CobrancaAnuidade cobranca) {
		CobrancaAnuidade cobrancaRemover = this.manager.find(CobrancaAnuidade.class, cobranca.getId());
		this.manager.remove(cobrancaRemover);
	}
	
	public List<CobrancaAnuidade> buscaCobrancaAnuidadePorUsuario(long usuarioId) {
		String jpql = ("select c from CobrancaAnuidade c where c.idUsuarioCobranca = :usuarioId");
		
		Query query = manager.createQuery(jpql,CobrancaAnuidade.class);
		query.setParameter("usuarioId", usuarioId);
		
		return query.getResultList();
	
	}
	
	public CobrancaAnuidade buscaPorId(long id) {
		CobrancaAnuidade cobrancaAnuidade = this.manager.find(CobrancaAnuidade.class, id);
		return cobrancaAnuidade;
	}
	
	public List<CobrancaAnuidade> buscaCobrancaAnuidadePorStatus(StatusContas status,Date dataInicial,Date dataFinal) {
		String jpql = ("select c from CobrancaAnuidade c where c.statusContas = :status and c.dataEmissao between :dataInicial and :dataFinal");
		
		Query query = manager.createQuery(jpql,CobrancaAnuidade.class);
		query.setParameter("status",status) ;
		query.setParameter("dataInicial",dataInicial) ;
		query.setParameter("dataFinal",dataFinal) ;
		
		return query.getResultList();
	
	}
	
	public List<CobrancaAnuidade> buscaCobrancaPorUsuarioComStatus(long idUsuario, StatusContas status) {
		
		String jpql = ("select c from CobrancaAnuidade c where c.idUsuarioCobranca = :idUsuario and c.statusContas = :status");
		
		Query query = manager.createQuery(jpql, CobrancaAnuidade.class);
		query.setParameter("idUsuario", idUsuario);
		query.setParameter("status", status);
		
		return query.getResultList();
	}
	
	
	public List<CobrancaAnuidade> buscaCobrancaAnuidadePorStatusNaoLancadas(StatusContas status,Date dataInicial,Date dataFinal,Lancamento lancamento) {
		String jpql = ("select c from CobrancaAnuidade c "
				+ "where c.statusContas = :status and c.lancamento = :lancamento and c.dataEmissao between :dataInicial and :dataFinal");
		
		Query query = manager.createQuery(jpql,CobrancaAnuidade.class);
		query.setParameter("status",status) ;
		query.setParameter("lancamento", lancamento);
		query.setParameter("dataInicial",dataInicial) ;
		query.setParameter("dataFinal",dataFinal) ;
		
		return query.getResultList();
	
	}
	
	public List<CobrancaAnuidade> buscaCobrancaAnuidadePagasPorPeriodo(StatusContas statusContas, Date dataInicial, Date dataFinal) {
		String jpql = ("select c from CobrancaAnuidade c " 
				+ "where c.statusContas = :statusContas and c.dataPagamento between :dataInicial and :dataFinal order by c.dataPagamento desc");
		
		Query query = manager.createQuery(jpql, CobrancaAnuidade.class);
		query.setParameter("statusContas", statusContas);
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", dataFinal);
		
		return query.getResultList();
	}
	
	public List<CobrancaAnuidade> buscaCobrancaAnuidadePorStatusSemData(StatusContas status){
		String jpql = ("select c from CobrancaAnuidade c where c.statusContas = :status order by c.dataPagamento desc");
		
		Query query = manager.createQuery(jpql,CobrancaAnuidade.class);
		query.setParameter("status", status);
		
		return query.getResultList();
	}
	
	public BigDecimal calculaTotalAnuidade(StatusContas statusContas) {
		String jpql = "select sum(c.desconto) from CobrancaAnuidade c where c.statusContas = :statusContas";

				
		Query query = this.manager.createQuery(jpql);
		query.setParameter("statusContas", statusContas);
		
		return (BigDecimal) query.getSingleResult();
	}
	
	public BigDecimal calculaAReceberAnuidade(StatusContas statusContas) {
		String jpql = "select sum(c.valor) from CobrancaAnuidade c where c.statusContas = :statusContas";
		
		Query query = this.manager.createQuery(jpql);
		query.setParameter("statusContas", statusContas);
		
		return (BigDecimal) query.getSingleResult();
	}


}
