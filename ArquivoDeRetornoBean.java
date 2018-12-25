package br.com.sistema.bean;

import java.io.IOException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import br.com.sistema.dao.ArquivoDeRetornoDao;
import br.com.sistema.exception.ArquivoRetornoException;
import br.com.sistema.util.FacesUtil;

@Named
@RequestScoped
public class ArquivoDeRetornoBean {

	private UploadedFile arquivo;
	
	@Inject
	private ArquivoDeRetornoDao arquivoDeRetornoDao;

	
	public UploadedFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}
	
	public void upload() {
		
		try{
			List<String> mensagens = arquivoDeRetornoDao.carregar(arquivo.getFileName(),arquivo.getInputstream());
			for(String mensagem : mensagens) {
				FacesUtil.addSucessMessage(mensagem);
			}
		}catch (ArquivoRetornoException |IOException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
		
	}	
	
}
