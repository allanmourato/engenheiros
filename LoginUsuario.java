package br.com.sistema.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.sistema.dao.UsuarioDao;
import br.com.sistema.modelo.CobrancaAnuidade;
import br.com.sistema.modelo.Usuario;


@Named
@SessionScoped
public class LoginUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuario usuario;
	
	@Inject
	private UsuarioDao dao;
	
	private List<Usuario> usuariosExistente;
	
	private List<CobrancaAnuidade> cobrancaDoUsuario;
	
	
	
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Usuario> getUsuariosExistente() {
		return usuariosExistente;
	}

	public void setUsuariosExistente(List<Usuario> usuariosExistente) {
		this.usuariosExistente = usuariosExistente;
	}




	public String autentica() {
		
		FacesContext fc = FacesContext.getCurrentInstance ();
		boolean loginValido = dao.existe(this.usuario);
		if (loginValido) {
			usuariosExistente = dao.usuarioLogado(usuario);
			return "dashboard?faces-redirect=true";
		}else {
			FacesMessage fm = new FacesMessage (" usuário e / ou senha inválidos");
			fm.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			fc.addMessage(null ,fm) ;
			return "login-usuario";
		}
	}
	
	
	
	
	public String registraSaida() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);
		session.removeAttribute(usuario.getEmail());
		
		System.out.println("logout de " + "---_" + usuario.getEmail());
		
		return "/login-usuario?faces-redirect=true";
	}
	
	
	
	public List<CobrancaAnuidade> pegaTodasCobrancaDoUsuario() {
		this.cobrancaDoUsuario = dao.listaTodasCobrancasDoUsuario(usuario);
		System.out.println("listando Cobranças");
		return cobrancaDoUsuario;
		
	}
	

}
