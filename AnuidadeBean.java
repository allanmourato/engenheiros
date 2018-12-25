package br.com.sistema.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistema.dao.AnuidadeDao;
import br.com.sistema.modelo.Anuidade;
import br.com.sistema.util.DataAtual;
import br.com.sistema.util.Menssagens;


@Named
@RequestScoped
public class AnuidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Anuidade anuidade = new Anuidade();
	
	private List<Anuidade> anuidades;
	
	@Inject
	private AnuidadeDao dao;
	
	@Inject
	private DataAtual dataAtual;
	
	@Inject
	private Menssagens menssagens;
	
	

	public Anuidade getAnuidade() {
		return anuidade;
	}

	public void setAnuidade(Anuidade anuidade) {
		this.anuidade = anuidade;
	}
	
	public void adicionaAnuidade() {
		
		if(anuidade.getId() == 0){
			
			anuidade.setDataLancamento(dataAtual.pegaDataAtual());
			dao.adiciona(anuidade);
			menssagens.info("Anuidade cadastrada");
			this.anuidades = dao.lista();
			limpaFormularioJsf();
			System.out.println("Anuidade adicionada com sucesso");
			
		}else {
			dao.atualiza(anuidade);
			menssagens.info("Anuidade atualizada");
			this.anuidades = dao.lista();
			limpaFormularioJsf();
		}	
	}
	
	public void removeAnuidade() {
		dao.remover(anuidade);
		this.anuidades = dao.lista();
		limpaFormularioJsf();
	}
	
	public List<Anuidade> getAnuidades() {
		System.out.println("Listando anuidades");
		this.anuidades = dao.lista();
		return anuidades;
		
	}
	
	public void limpaFormularioJsf() {
		this.anuidade = new Anuidade();
	}

}
