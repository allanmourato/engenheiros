package br.com.sistema.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;
import org.jrimum.texgit.Texgit;
import org.jrimum.utilix.text.DateFormat;

import br.com.sistema.dao.CobrancaAnuidadeDao;
import br.com.sistema.dao.UsuarioDao;
import br.com.sistema.modelo.CobrancaAnuidade;
import br.com.sistema.modelo.Lancamento;
import br.com.sistema.modelo.StatusContas;
import br.com.sistema.modelo.Usuario;
import br.com.sistema.util.DataAtual;
import br.com.sistema.util.GeradorDigitoVerificador;


@Named
@SessionScoped
public class ArquivoRemessaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	CobrancaAnuidade cobrancaAnuidade = new CobrancaAnuidade();
	
	@Inject
	private CobrancaAnuidadeDao cobrancaAnuidadeDao;
	
	@Inject
	private UsuarioDao usuarioDao;
	
	@Inject
	private GeradorDigitoVerificador geradorDigitoVerificador;
	
	@Inject
	private DataAtual dataAtual;
	
	
	List<CobrancaAnuidade> cobrancasSelecionadas;
	
	
	
	
	
	

	public CobrancaAnuidade getCobrancaAnuidade() {
		return cobrancaAnuidade;
	}
	public void setCobrancaAnuidade(CobrancaAnuidade cobrancaAnuidade) {
		this.cobrancaAnuidade = cobrancaAnuidade;
	}
	
	public List<CobrancaAnuidade> getCobrancasSelecionadas() {
		return cobrancasSelecionadas;
	}
	public void setCobrancasSelecionadas(
			List<CobrancaAnuidade> cobrancasSelecionadas) {
		this.cobrancasSelecionadas = cobrancasSelecionadas;
	}
	public StatusContas[] getStatusContas() {
		return StatusContas.values();
	}
	
	
	
	
	public void gerarRemessa() throws FileNotFoundException, IOException {
		
		String caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/sicoob/Remessa");
		File arquivoRemessa = new File(caminho);
		
		String stringArquivo = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/sicoob/remessa_sicoob_400.txg.xml");
		File arquivo = new File(stringArquivo);
		FlatFile<Record> arquivoTexto = Texgit.createFlatFile(arquivo);
		
		//FlatFile<Record> arquivoTexto = Texgit.createFlatFile("remessa_sicoob_400.txg.xml");
		
		int i = 1;
		arquivoTexto.addRecord(createHeader(arquivoTexto, i));
		i++;
		
		
		System.out.println("Chamando for");
		
		
		for(int b = 0; b < cobrancasSelecionadas.size(); b++) {
			
			System.out.println(cobrancasSelecionadas.get(b).getId());
			
			CobrancaAnuidade cobrancaAnuidadeSelecionada = cobrancaAnuidadeDao.buscaPorId(cobrancasSelecionadas.get(b).getId());
			arquivoTexto.addRecord(createTransacaoTitulo(arquivoTexto, i, cobrancaAnuidadeSelecionada));
			cobrancaAnuidadeSelecionada.setLancamento(Lancamento.LANCADAS);
			cobrancaAnuidadeDao.atualiza(cobrancaAnuidadeSelecionada);
			i++;
		}
		
		System.out.println("Saiu do for");
		arquivoTexto.addRecord(createTrailler(arquivoTexto, i));
		

		
		try {
			//FileUtils.writeLines(, arquivoTexto.write());
			FileUtils.writeLines(arquivoRemessa,"windows-1252", arquivoTexto.write());
			byte [] rem = IOUtils.toByteArray(new FileInputStream(arquivoRemessa));
			
			enviarRemessa(rem);
			
			//FileUtils.writeLines(new File("/home/allan/Remessa Sistema/arquivos/Remessa.CED"), arquivoTexto.write());
		} catch (IOException|NullPointerException e) {
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistema Aeasc", "Erro ao gerar arquivo de remessa!"));
			e.printStackTrace();
		}
		
		System.out.println("executou o método");
		
		
		
		
	}
	
	
	public Record createHeader (FlatFile<Record> arquivoTexto, int seq) {
		Record header = arquivoTexto.createRecord("Header");
		header.setValue("DataGravacao", DateFormat.DDMMYY.format(dataAtual.pegaDataAtual()));
		header.setValue("NumeroSequencialRemessa", seq );
		
		return header;
		
	}
	
	public Record createTrailler(FlatFile<Record> arquivoTexto, int seq) {
		Record trailler = arquivoTexto.createRecord("Trailler");
		trailler.setValue("NumeroSequencialRegistro", seq);
		
		return trailler;
	}
	
	public Record createTransacaoTitulo(FlatFile<Record> arquivoTexto, int seq, CobrancaAnuidade cobrancaAnuidade) {
		
		CobrancaAnuidade cobrancaBuscada = cobrancaAnuidadeDao.buscaPorId(cobrancaAnuidade.getId());
		Usuario usuarioBuscado = usuarioDao.buscaPorId(cobrancaBuscada.getIdUsuarioCobranca());
		
		Record transacaoTitulo = arquivoTexto.createRecord("TransacaoTitulo");
		
		String tipoInscricao;
		
		if(usuarioBuscado.getPerfil() == 1 || usuarioBuscado.getPerfil() == 3) {
			
			tipoInscricao = "01";
			
			String nossoNumero = geradorDigitoVerificador.completaComZero(String.valueOf((cobrancaBuscada.getId()*1000)));
			String DV = geradorDigitoVerificador.geraDigitoVerificador(nossoNumero);
			
			
			transacaoTitulo.setValue("NossoNumeroComDV", nossoNumero+DV);
			transacaoTitulo.setValue("DataGeracao", DateFormat.DDMMYY.format(cobrancaBuscada.getDataEmissao()));
			transacaoTitulo.setValue("DataVencimento", DateFormat.DDMMYY.format(cobrancaBuscada.getDataVencimento()));
			transacaoTitulo.setValue("ValorTitulo", cobrancaBuscada.getValor());
			transacaoTitulo.setValue("NumeroBancoDeDados", cobrancaBuscada.getId());
			transacaoTitulo.setValue("NomePagador", SemAcento(determinaTamanho(usuarioBuscado.getNome(), 40)));
			transacaoTitulo.setValue("TipoInscricaoDois", tipoInscricao);
			transacaoTitulo.setValue("CPFCNPJInscricaoDois", usuarioBuscado.getCpf().replace(".", "").replace("-", ""));
			transacaoTitulo.setValue("EnderecoPagador",SemAcento(determinaTamanho(usuarioBuscado.getPostagemCorreioNumero()+", " + usuarioBuscado.getPostagemCorreioRua(), 37)));
			transacaoTitulo.setValue("BairroPagador", SemAcento(determinaTamanho(usuarioBuscado.getPostagemCorreioBairro(), 13)));
			transacaoTitulo.setValue("CepPagador", usuarioBuscado.getPostagemCorreioCep());
			transacaoTitulo.setValue("CidadePagador", SemAcento(determinaTamanho(usuarioBuscado.getPostagemCorreioCidade(), 15)));
			transacaoTitulo.setValue("UfPagador", SemAcento(usuarioBuscado.getPostagemCorreioUf()));
			transacaoTitulo.setValue("NumeroSequencialRegistro", seq);
			
			
			
		}
		
		if(usuarioBuscado.getPerfil() == 2) {
			
			tipoInscricao = "02";
			
			String nossoNumero = geradorDigitoVerificador.completaComZero(String.valueOf((cobrancaBuscada.getId()*1000)));
			String DV = geradorDigitoVerificador.geraDigitoVerificador(nossoNumero);
			
			
			transacaoTitulo.setValue("NossoNumeroComDV", nossoNumero+DV);
			transacaoTitulo.setValue("DataGeracao", DateFormat.DDMMYY.format(cobrancaBuscada.getDataEmissao()));
			transacaoTitulo.setValue("DataVencimento", DateFormat.DDMMYY.format(cobrancaBuscada.getDataVencimento()));
			transacaoTitulo.setValue("ValorTitulo", cobrancaBuscada.getValor());
			transacaoTitulo.setValue("NumeroBancoDeDados", cobrancaBuscada.getId());
			transacaoTitulo.setValue("NomePagador", SemAcento(determinaTamanho(usuarioBuscado.getNome(), 40)));
			transacaoTitulo.setValue("TipoInscricaoDois", tipoInscricao);
			transacaoTitulo.setValue("CPFCNPJInscricaoDois", usuarioBuscado.getCnpj().replace(".", "").replace("-", "").replace("/", ""));
			transacaoTitulo.setValue("EnderecoPagador", SemAcento(determinaTamanho(usuarioBuscado.getPostagemCorreioNumero()+", " + usuarioBuscado.getPostagemCorreioRua(), 37)));
			transacaoTitulo.setValue("BairroPagador", SemAcento(determinaTamanho(usuarioBuscado.getPostagemCorreioBairro(), 13)));
			transacaoTitulo.setValue("CepPagador", usuarioBuscado.getPostagemCorreioCep());
			transacaoTitulo.setValue("CidadePagador",SemAcento(determinaTamanho(usuarioBuscado.getPostagemCorreioCidade(), 15)));
			transacaoTitulo.setValue("UfPagador", SemAcento(usuarioBuscado.getPostagemCorreioUf()));
			transacaoTitulo.setValue("NumeroSequencialRegistro", seq);
			
			
		}
		
		return transacaoTitulo;
		
	}
	
	private void enviarRemessa(byte[] rem) {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", "attachment; filename=48020424000144_102483"+".REM");
		
		try {
			OutputStream output = response.getOutputStream();
			output.write(rem);
			response.flushBuffer();
		} catch (Exception e) {
			throw new RuntimeException("Erro gerando remessa", e);
		}
		
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	public static String SemAcento(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("").toUpperCase();
    }
	
	public String determinaTamanho(String valor, int tamanhoMaximo) {
		
		if(valor.length() >= tamanhoMaximo) {
			String valorReduzido = valor.substring(0, tamanhoMaximo);
			
			return valorReduzido;	
		}else{
			return valor;
		}
		
		
	}
	
	
	    

}
