package br.com.sistema.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.sistema.modelo.CobrancaAnuidade;
import br.com.sistema.modelo.StatusUsuario;
import br.com.sistema.modelo.Usuario;

@Stateless
public class UsuarioDao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;
	
	public void adiciona (Usuario usuario) {
		manager.persist(usuario);
	}
	
	public void remover(Usuario usuario) {
		Usuario usuarioParaRemover = this.manager.find(Usuario.class, usuario.getId());
		this.manager.remove(usuarioParaRemover);
	}
	
	public void atualiza(Usuario usuario) {
		manager.merge(usuario);
	}
	
	public Usuario buscaPorId(long id) {
		Usuario usuario = this.manager.find(Usuario.class, id);
		return usuario;
	}
	
	public List<Usuario> lista() {
		return manager.createQuery("select u from Usuario u " + "order by u.nome ASC", Usuario.class).getResultList();
	}
	
	public List<Usuario> listaSelecaoAtivosInativos(StatusUsuario statusUsuario) {
		 String jpql = "select u from Usuario u where u.statusUsuario = :statusUsuario order by u.nome ASC";
		 
		 Query query = this.manager.createQuery(jpql, Usuario.class);
		 query.setParameter("statusUsuario", statusUsuario);
		 
		 return query.getResultList();
	}
	
	public List<Usuario> listaUsuarioPorCategoriaSemStatus (long categoriaId) {
		String jpql = "select u from Usuario u  where u.categoria.id = :categoriaId order by u.nome ASC";
		
		Query query = this.manager.createQuery(jpql);
		query.setParameter("categoriaId",categoriaId);
		return query.getResultList();
	}
	
	
	public List<Usuario> listaUsuarioPorCategoria (long categoriaId, StatusUsuario statusUsuario) {
		String jpql = "select u from Usuario u  where u.categoria.id = :categoriaId and u.statusUsuario = :statusUsuario order by u.nome ASC";
		
		Query query = this.manager.createQuery(jpql);
		query.setParameter("statusUsuario", statusUsuario);
		query.setParameter("categoriaId",categoriaId);
		return query.getResultList();
	}
	
	public List<Usuario> listaUsuarioPorCpf (String recebeCpf) {
		return manager.createQuery("select u from Usuario u where u.cpf = :recebeCpf",Usuario.class).
		setParameter("recebeCpf", recebeCpf).getResultList();
	}
	
	public List<Usuario> listaUsuarioPorEmail (String recebeEmail) {
		return manager.createQuery("select u from Usuario u where u.email = :recebeEmail",Usuario.class).
		setParameter("recebeEmail", recebeEmail).getResultList();
	}
	
	public List<Usuario> listaUsuarioPorCnpj (String recebeCnpj) {
		return manager.createQuery("select u from Usuario u where u.cnpj = :recebeCnpj",Usuario.class).
		setParameter("recebeCnpj", recebeCnpj).getResultList();
	}
	
	public List<Usuario> listaUsuarioPorMesDeNascimento (int recebeMes) {
		return manager.createQuery("select u from Usuario u where u.mesNascimento = :recebeMes order by u.diaNascimento ASC"  ,Usuario.class).
		setParameter("recebeMes", recebeMes).getResultList();
	}
	
	public List<Usuario> listaUsuariosPorMesDeNascimentoPeloStatus(int recebeMes, StatusUsuario statusUsuario) {
		String jpql = "select u from Usuario u where u.mesNascimento = :recebeMes and u.statusUsuario = :statusUsuario";
		Query query = this.manager.createQuery(jpql,Usuario.class);
		query.setParameter("recebeMes", recebeMes);
		query.setParameter("statusUsuario", statusUsuario);
		
		return query.getResultList();
	}
	
	public List<Usuario> buscaPorNome(String recebeNome) {
		String jpql = "select u from Usuario u where lower(u.nome) like :recebeNome order by u.nome";
		TypedQuery<Usuario> query = this.manager.createQuery(jpql, Usuario.class);
		query.setParameter("recebeNome", recebeNome+"%");
		return query.getResultList();	
	}
	
	public boolean existe(Usuario usuario) {
		
		Query query = manager.createQuery("from Usuario u where u.email = "
				+ ":pLogin and u.senha = :pSenha");
		
		query.setParameter("pLogin", usuario.getEmail());
		query.setParameter("pSenha", usuario.getSenha());
		
		boolean encontrado = !query.getResultList().isEmpty();
		
		return encontrado;
	}
	
	public List<Usuario> usuarioLogado (Usuario usuario) {
		
		Query query = manager.createQuery("from Usuario u where u.email = "
				+ ":pLogin and u.senha = :pSenha",Usuario.class);
		
		query.setParameter("pLogin", usuario.getEmail());
		query.setParameter("pSenha", usuario.getSenha());
		
		return query.getResultList();
	}
	
	public List<Usuario> listaUsuarioPorCategoriaParaEtiquetas (long categoriaId) {
		String jpql = "select u from Usuario u  where u.categoria.id = :categoriaId order by u.nome ASC";
		
		Query query = this.manager.createQuery(jpql);
		query.setParameter("categoriaId",categoriaId);
		return query.getResultList();
	}
	
	public List<CobrancaAnuidade> listaTodasCobrancasDoUsuario(Usuario usuario) {
		String jpql = "select c from Cobranca c where c.usuarioCobranca = :usuario";
		
		Query query = this.manager.createQuery(jpql);
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}
	
	public Usuario usuarioPorCpf (String recebeCpf) {
		return manager.createQuery("select u from Usuario u where u.cpf = :recebeCpf",Usuario.class).
		setParameter("recebeCpf", recebeCpf).getSingleResult();
	}
	
	public boolean jaEhCadastradoCPF(String cpf) {
		
		String jpql = "select u from Usuario u where u.cpf = :cpf";
		
		Query query = this.manager.createQuery(jpql);
		query.setParameter("cpf", cpf);
		
		boolean encontrado = !query.getResultList().isEmpty();
		
		System.out.println(encontrado);
		
		return encontrado;
	}
	
	public boolean jaEhCadastradoCNPJ(String cnpj) {

		String jpql = "select u from Usuario u where u.cnpj = :cnpj";
		
		Query query = this.manager.createQuery(jpql);
		query.setParameter("cnpj", cnpj);
		
		boolean encontrado = !query.getResultList().isEmpty();
		
		System.out.println(encontrado);
		
		return encontrado;
	}
	
	public Usuario buscaPorEmailESenha (String email, String senha) {
		
		String jpql = "select u from Usuario u where u.email = :email and u.senha = :senha";
		Query query = this.manager.createQuery(jpql,Usuario.class);
		query.setParameter("email", email);
		query.setParameter("senha", senha);
		
		return (Usuario) query.getSingleResult();
				
	}
	
	public List<Usuario> buscaPorEmail (String email) {
			
		String jpql = "select u from Usuario u where u.email = :email";
		Query query = this.manager.createQuery(jpql, Usuario.class);
		query.setParameter("email", email);
			
		return query.getResultList();
		
	}
	
	public Usuario usuarioPorCnpj (String recebeCnpj) {
		return manager.createQuery("select u from Usuario u where u.cnpj = :recebeCnpj",Usuario.class).
		setParameter("recebeCnpj", recebeCnpj).getSingleResult();
	}
	
	public List<Usuario> usuariosPorDataDeCadastro(Date dataInicial, Date dataFinal) {
		String jpql = "select u from Usuario u where u.dataDoCadastro between :dataInicial and :dataFinal";
		Query query = this.manager.createQuery(jpql);
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", dataFinal);
		
		return query.getResultList();
	}

	
	
}
