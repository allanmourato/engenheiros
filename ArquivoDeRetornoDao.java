package br.com.sistema.dao;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.jrimum.utilix.text.DateFormat;

import br.com.sistema.exception.ArquivoRetornoException;
import br.com.sistema.modelo.CobrancaAnuidade;
import br.com.sistema.modelo.StatusContas;
import br.com.sistema.modelo.StatusUsuario;
import br.com.sistema.modelo.Usuario;
import br.com.sistema.sicoob.ArquivoRetorno;
import br.com.sistema.sicoob.Cabecalho;
import br.com.sistema.sicoob.Sumario;
import br.com.sistema.sicoob.TransacaoTitulo;


public class ArquivoDeRetornoDao  {
	

	@Inject
	private CobrancaAnuidadeDao cobrancaAnuidadeDao;
	
	@Inject
	private UsuarioDao usuarioDao;

	public List<String> carregar(String fileName, InputStream inputstream)throws ArquivoRetornoException, IOException {
		
		int contadorDeNulos = 0;
		int cobrancaNaoCadastrada = 0;
		
		ArquivoRetorno arquivoRetorno = criarArquivoDeRetorno(fileName,
				inputstream);
			
		List<String> mensagens = carregarMensagens(arquivoRetorno);
		
		int totalTitulosPagos = 0;
		List<TransacaoTitulo> transacaoTitulos = arquivoRetorno.getTransacoes();
		for (TransacaoTitulo t: transacaoTitulos){
			
			if(soContemNumeros(t.getNumeroDoBoleto()) == false) {
				contadorDeNulos++;
				mensagens.add(contadorDeNulos + " Cobrança(s) de outro(s) sistema(s)");
			
			}
			if(soContemNumeros(t.getNumeroDoBoleto()) != false) {
				
				CobrancaAnuidade cobrancaAnuidade = this.cobrancaAnuidadeDao.buscaPorId(Long.valueOf(t.getNumeroDoBoleto()));
				
				if(cobrancaAnuidade == null) {
					cobrancaNaoCadastrada++;
					mensagens.add(cobrancaNaoCadastrada + " Cobrança(s) não cadastrada(s) no sistema");
				
				}else{
					
					Usuario usuarioCobranca = this.usuarioDao.buscaPorId(cobrancaAnuidade.getIdUsuarioCobranca());
					
					
					if(t.getValorRecebido() != new BigDecimal("0.00")) {
						cobrancaAnuidade.setDataPagamento(t.getDataDoCredito());
						cobrancaAnuidade.setDesconto(t.getValorRecebido());
						cobrancaAnuidade.setViaDePagamento("Boleto Sicoob");
						cobrancaAnuidade.setStatusContas(StatusContas.PAGO);
						usuarioCobranca.setStatusUsuario(StatusUsuario.ATIVO);
								
						cobrancaAnuidadeDao.atualiza(cobrancaAnuidade);
						usuarioDao.atualiza(usuarioCobranca);
								
						totalTitulosPagos ++;
					}
							
					
					
				}
				
				
			}
			
			
			
		}
		
		mensagens.add("Quantidade de Títulos Pagos: " + totalTitulosPagos);
		return mensagens;
	}

	private List<String> carregarMensagens(ArquivoRetorno arquivoRetorno) {
		Cabecalho cabecalho = arquivoRetorno.getCabecalho();
		Sumario sumario = arquivoRetorno.getSumario();
		List<TransacaoTitulo> transacoes = arquivoRetorno.getTransacoes();
		
		for(TransacaoTitulo transacao: transacoes) {
			
			System.out.println(transacao.getNossoNumero());
			System.out.println(transacao.getValorRecebido());
		}
		List<String> mensagens = new ArrayList<>();
		mensagens.add("Data da Gravação" + DateFormat.DDMMYY_B.format(cabecalho.getDataGravacao()));
		mensagens.add("Quantidade de Títulos: " + (sumario.getQuantidadeDeRegistros()));
		
		
		return mensagens;
	}

	private ArquivoRetorno criarArquivoDeRetorno(String fileName,
			InputStream inputstream) throws IOException {
		ArquivoRetorno arquivoRetorno;
		try{
			File arquivo = File.createTempFile(fileName, "");
			FileUtils.copyInputStreamToFile(inputstream, arquivo);
			arquivoRetorno = new ArquivoRetorno(arquivo);
			
		}catch (IOException e) {
			throw new IOException("erro ao carregar arquivo");
		}
		return arquivoRetorno;
	}
	
	 public static boolean soContemNumeros(String texto) {  
	        if(texto == null)  
	            return false;  
	        for (char letra : texto.toCharArray())  
	            if(letra < '0' || letra > '9')  
	                return false;  
	        return true;  
	          
	 }  

}
