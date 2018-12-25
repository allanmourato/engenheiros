package br.com.sistema.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistema.dao.DesligamentoUsuarioDao;
import br.com.sistema.dao.UsuarioDao;
import br.com.sistema.modelo.DesligamentoUsuario;
import br.com.sistema.modelo.Usuario;
import br.com.sistema.util.FacesUtil;

@Named
@SessionScoped
public class DesligamentoUsuarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DesligamentoUsuario desligamentoUsuario = new DesligamentoUsuario();
	
	@Inject
	private DesligamentoUsuarioDao dao;
	
	@Inject
	private UsuarioDao usuarioDao;
	
	private List<DesligamentoUsuario> todos;

	
	
	
	public DesligamentoUsuario getDesligamentoUsuario() {
		return desligamentoUsuario;
	}

	public void setDesligamentoUsuario(DesligamentoUsuario desligamentoUsuario) {
		this.desligamentoUsuario = desligamentoUsuario;
	}
	
	public List<DesligamentoUsuario> getTodos() {
		return todos;
	}

	public void setTodos(List<DesligamentoUsuario> todos) {
		this.todos = todos;
	}

	
	
	//lista todos Desligados
	public List<DesligamentoUsuario> lista() {
		this.todos = dao.lista();
		
		return todos;
	}
	
	
	public void religamentoUsuario() {
		
		Usuario usuario = new Usuario();
		
		usuario.setPerfil(desligamentoUsuario.getPerfil());
		usuario.setSenha(desligamentoUsuario.getSenha());
		usuario.setDataDoCadastro(desligamentoUsuario.getDataDoCadastro());
		usuario.setNome(desligamentoUsuario.getNome());
		usuario.setEmail(desligamentoUsuario.getEmail());
		usuario.setSexo(desligamentoUsuario.getSexo());
		usuario.setStatusUsuario(desligamentoUsuario.getStatusUsuario());
		usuario.setCategoria(desligamentoUsuario.getCategoria());
		usuario.setCreaCau(desligamentoUsuario.getCreaCau());
		usuario.setCpf(desligamentoUsuario.getCpf());
		usuario.setCnpj(desligamentoUsuario.getCnpj());
		usuario.setRg(desligamentoUsuario.getRg());
		usuario.setTelResidencial(desligamentoUsuario.getTelResidencial());
		usuario.setCelular(desligamentoUsuario.getCelular());
		usuario.setTelComercial(desligamentoUsuario.getTelComercial());
		usuario.setDiaNascimento(desligamentoUsuario.getDiaNascimento());
		usuario.setMesNascimento(desligamentoUsuario.getMesNascimento());
		usuario.setAnoNascimento(desligamentoUsuario.getAnoNascimento());
		usuario.setCep(desligamentoUsuario.getCep());
		usuario.setRua(desligamentoUsuario.getRua());
		usuario.setNumero(desligamentoUsuario.getNumero());
		usuario.setComplemento(desligamentoUsuario.getComplemento());
		usuario.setBairro(desligamentoUsuario.getBairro());
		usuario.setCidade(desligamentoUsuario.getCidade());
		usuario.setUf(desligamentoUsuario.getUf());
		usuario.setCorreioRua(desligamentoUsuario.getCorreioRua());
		usuario.setCorreioCep(desligamentoUsuario.getCorreioCep());
		usuario.setCorreioNumero(desligamentoUsuario.getCorreioNumero());
		usuario.setCorreioComplemento(desligamentoUsuario.getCorreioComplemento());
		usuario.setCorreioBairro(desligamentoUsuario.getCorreioBairro());
		usuario.setCorreioCidade(desligamentoUsuario.getCorreioCidade());
		usuario.setCorreioUf(desligamentoUsuario.getCorreioUf());
		usuario.setPostagemCorreioRua(desligamentoUsuario.getPostagemCorreioRua());
		usuario.setPostagemCorreioNumero(desligamentoUsuario.getPostagemCorreioNumero());
		usuario.setPostagemCorreioBairro(desligamentoUsuario.getPostagemCorreioBairro());
		usuario.setPostagemCorreioComplemento(desligamentoUsuario.getPostagemCorreioComplemento());
		usuario.setPostagemCorreioCep(desligamentoUsuario.getPostagemCorreioCep());
		usuario.setPostagemCorreioCidade(desligamentoUsuario.getPostagemCorreioCidade());
		usuario.setPostagemCorreioUf(desligamentoUsuario.getPostagemCorreioUf());
		usuario.setPostagem(desligamentoUsuario.getPostagem());
		usuario.setNewsletter(desligamentoUsuario.getNewsletter());
		usuario.setAutorizacao(desligamentoUsuario.getAutorizacao());
		
		usuarioDao.adiciona(usuario);
		dao.RemoveDesligamento(desligamentoUsuario);
		usuarioDao.lista();
		
		FacesUtil.addSucessMessage("Usuário Religado");
		
		
		
	}
	
	
	

}
