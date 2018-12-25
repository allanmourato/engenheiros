package br.com.sistema.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import br.com.sistema.dao.UsuarioDao;
import br.com.sistema.modelo.StatusUsuario;
import br.com.sistema.modelo.Usuario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@SessionScoped
public class EtiquetasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuario usuario;
	
	private int selecaoStatus;
	
	@Inject
	private UsuarioDao dao;
	
	private long categoriaId;
	private List<Usuario> usuariosCategoriaEtiquetas;
	private List<Usuario> usuarioTodos;

	
	
	public int getSelecaoStatus() {
		return selecaoStatus;
	}

	public void setSelecaoStatus(int selecaoStatus) {
		this.selecaoStatus = selecaoStatus;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(long categoriaId) {
		this.categoriaId = categoriaId;
	}
	
	
	public List<Usuario> getUsuariosCategoriaEtiquetas() {
		dao.listaUsuarioPorCategoriaParaEtiquetas(categoriaId);
		return usuariosCategoriaEtiquetas;
	}

	public void setUsuariosCategoriaEtiquetas(List<Usuario> usuariosCategoriaEtiquetas) {
		this.usuariosCategoriaEtiquetas = usuariosCategoriaEtiquetas;
	}
	
	public List<Usuario> getUsuarioTodos() {
		return usuarioTodos;
	}

	public void setUsuarioTodos(List<Usuario> usuarioTodos) {
		this.usuarioTodos = usuarioTodos;
	}

	//Lista de Usuarios por Categoria para geração das etiquetas
	public List<Usuario> getUsuariosPorCategoriaParaGerarEtiquetas() {
		if(usuariosCategoriaEtiquetas == null) {
			this.usuariosCategoriaEtiquetas = dao.listaUsuarioPorCategoriaParaEtiquetas(categoriaId);
			System.out.println("Lista de usuarios por categoria para gerar etiquetas");
		}
		return usuariosCategoriaEtiquetas;
	}
	
	public List<Usuario> usuariosPorPerfil() {
		
		if(selecaoStatus == 1) {
			this.usuarioTodos = dao.lista();
			System.out.println("Chamou Todos");
		}
		if(selecaoStatus == 2) {
			this.usuarioTodos = dao.listaSelecaoAtivosInativos(StatusUsuario.ATIVO);
			System.out.println("Chamou Ativos");
		}
		if(selecaoStatus == 3) {
			this.usuarioTodos = dao.listaSelecaoAtivosInativos(StatusUsuario.INATIVO);
			System.out.println("Chamou Inativo");
		}
		
		return usuarioTodos;
	}
	
	
	//Metodo que Gera Etiquetas por Categoria.
	public void geraEtiquetasPorCategoria () throws JRException, IOException {
		
			this.usuariosCategoriaEtiquetas = dao.listaUsuarioPorCategoriaParaEtiquetas(categoriaId);
			System.out.println("Dados Atualizados !");
			
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(usuariosCategoriaEtiquetas, false);  
	     	String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/jasper/etiquetas_todos.jasper");  
	        JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);  
	       
	        byte[] b = JasperExportManager.exportReportToPdf(jasperPrint);   
	        
	        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  
	        res.setContentType("application/pdf");  
	        //Código abaixo gerar o relatório e disponibiliza diretamente na página   
	        res.setHeader("Content-disposition", "inline;filename=arquivo.pdf");  
	        //Código abaixo gerar o relatório e disponibiliza para o cliente baixar ou salvar   
	        //res.setHeader("Content-disposition", "attachment;filename=arquivo.pdf");  
	        res.getOutputStream().write(b);  
	        res.getCharacterEncoding();  
	        FacesContext.getCurrentInstance().responseComplete();  
	        System.out.println("saiu do visualizar relatorio");  
    }  
	
	
	public void geraEtiquetasParaTodos() throws JRException, IOException {
		
			this.usuarioTodos = dao.lista();
			System.out.println("Dados Atualizados !");
		
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(usuariosPorPerfil(), false);  
	     	String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/jasper/etiquetas_todos.jasper");  
	        JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);  
	       
	        byte[] b = JasperExportManager.exportReportToPdf(jasperPrint);   
	        
            HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  
            res.setContentType("application/pdf");  
            //Código abaixo gerar o relatório e disponibiliza diretamente na página   
            res.setHeader("Content-disposition", "inline;filename=arquivo.pdf");  
            //Código abaixo gerar o relatório e disponibiliza para o cliente baixar ou salvar   
            //res.setHeader("Content-disposition", "attachment;filename=arquivo.pdf");  
            res.getOutputStream().write(b);  
            res.getCharacterEncoding();  
            FacesContext.getCurrentInstance().responseComplete();  
            System.out.println("saiu do visualizar relatorio"); 
	}
}
