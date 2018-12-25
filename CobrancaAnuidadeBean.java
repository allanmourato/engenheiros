package br.com.sistema.bean;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

import br.com.sistema.dao.AnuidadeDao;
import br.com.sistema.dao.CobrancaAnuidadeDao;
import br.com.sistema.dao.UsuarioDao;
import br.com.sistema.modelo.Anuidade;
import br.com.sistema.modelo.CobrancaAnuidade;
import br.com.sistema.modelo.Lancamento;
import br.com.sistema.modelo.StatusContas;
import br.com.sistema.modelo.StatusUsuario;
import br.com.sistema.modelo.Usuario;
import br.com.sistema.pagseguro.CheckoutPagSeguro;
import br.com.sistema.util.DataAtual;
import br.com.sistema.util.EnviaEmail;
import br.com.sistema.util.FacesUtil;
import br.com.sistema.util.GeradorDigitoVerificador;
import br.com.sistema.util.Menssagens;

@Named
@SessionScoped
public class CobrancaAnuidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private CobrancaAnuidade cobranca = new CobrancaAnuidade();
	
	@Inject
	private CobrancaAnuidadeDao dao;
	
	@Inject
	private AnuidadeDao anuidadeDao;
	
	@Inject
	private UsuarioDao usuarioDao;

	@Inject
	private Menssagens menssagens;
	
	@Inject
	private DataAtual dataAtual;
	
	@Inject
	EnviaEmail enviaEmail;
	
	@Inject
	private GeradorDigitoVerificador geradorDigitoVerificador;
	
	
	private long idAnuidade;
	
	private String recebeCpf;
	
	private String recebeCnpj;
	
	private BigDecimal desconto;
	
	private BigDecimal valorCalculado;
	
	private List<CobrancaAnuidade> cobrancas;
	
	private List<CobrancaAnuidade> cobrancasAnuidadePorUsuario;
	
	private List<CobrancaAnuidade> cobrancasParaRemessa;
	
	private List<CobrancaAnuidade> cobrancasParaBoletos;
	
	private List<CobrancaAnuidade> cobrancaParaEnviarEmail;
	
	private List<CobrancaAnuidade> cobrancasPendentes;
	
	private List<CobrancaAnuidade> cobrancasPagas;
	
	private List<CobrancaAnuidade> cobrancasPagasPorPeriodo;
	
	private List<Usuario> usuariosQuePagaram;
	
	private Usuario usuarioSelecionado;
	
	private Usuario usuarioBuscado;
	
	private Usuario usuarioCobrancaPagSeguro;
	
	private List<Usuario> usuariosBuscadosParaSet;
	
	private String nossoNumero;
	
	private String DV;
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	private String instrucao1;
	private String instrucao2;
	private String instrucao3;
	private String instrucao4;
	
	private String msgEmail;
	private int opcaoEmail;
	
	


	
	
	public List<Usuario> getUsuariosQuePagaram() {
		return usuariosQuePagaram;
	}

	public void setUsuariosQuePagaram(List<Usuario> usuariosQuePagaram) {
		this.usuariosQuePagaram = usuariosQuePagaram;
	}

	public List<CobrancaAnuidade> getCobrancasPendentes() {
		return cobrancasPendentes;
	}

	public void setCobrancasPendentes(List<CobrancaAnuidade> cobrancasPendentes) {
		this.cobrancasPendentes = cobrancasPendentes;
	}

	public List<CobrancaAnuidade> getCobrancasPagas() {
		return cobrancasPagas;
	}

	public void setCobrancasPagas(List<CobrancaAnuidade> cobrancasPagas) {
		this.cobrancasPagas = cobrancasPagas;
	}

	public List<CobrancaAnuidade> getCobrancaParaEnviarEmail() {
		return cobrancaParaEnviarEmail;
	}

	public void setCobrancaParaEnviarEmail(List<CobrancaAnuidade> cobrancaParaEnviarEmail) {
		this.cobrancaParaEnviarEmail = cobrancaParaEnviarEmail;
	}

	public String getMsgEmail() {
		return msgEmail;
	}

	public void setMsgEmail(String msgEmail) {
		this.msgEmail = msgEmail;
	}

	public int getOpcaoEmail() {
		return opcaoEmail;
	}

	public void setOpcaoEmail(int opcaoEmail) {
		this.opcaoEmail = opcaoEmail;
	}

	public List<CobrancaAnuidade> getCobrancasParaBoletos() {
		return cobrancasParaBoletos;
	}

	public void setCobrancasParaBoletos(List<CobrancaAnuidade> cobrancasParaBoletos) {
		this.cobrancasParaBoletos = cobrancasParaBoletos;
	}

	public List<CobrancaAnuidade> getCobrancasParaRemessa() {
		return cobrancasParaRemessa;
	}

	public List<CobrancaAnuidade> getCobrancasPagasPorPeriodo() {
		return cobrancasPagasPorPeriodo;
	}

	public void setCobrancasPagasPorPeriodo(List<CobrancaAnuidade> cobrancasPagasPorPeriodo) {
		this.cobrancasPagasPorPeriodo = cobrancasPagasPorPeriodo;
	}

	public void setCobrancasParaRemessa(List<CobrancaAnuidade> cobrancasParaRemessa) {
		this.cobrancasParaRemessa = cobrancasParaRemessa;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public String getDV() {
		return DV;
	}

	public void setDV(String dV) {
		DV = dV;
	}

	public Usuario getUsuarioCobrancaPagSeguro() {
		return usuarioCobrancaPagSeguro;
	}

	public void setUsuarioCobrancaPagSeguro(Usuario usuarioCobrancaPagSeguro) {
		this.usuarioCobrancaPagSeguro = usuarioCobrancaPagSeguro;
	}

	public List<Usuario> getUsuariosBuscadosParaSet() {
		return usuariosBuscadosParaSet;
	}

	public void setUsuariosBuscadosParaSet(List<Usuario> usuariosBuscadosParaSet) {
		this.usuariosBuscadosParaSet = usuariosBuscadosParaSet;
	}

	public Usuario getUsuarioBuscado() {
		return usuarioBuscado;
	}

	public void setUsuarioBuscado(Usuario usuarioBuscado) {
		this.usuarioBuscado = usuarioBuscado;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public List<CobrancaAnuidade> getCobrancasAnuidadePorUsuario() {
		return cobrancasAnuidadePorUsuario;
	}

	public void setCobrancasAnuidadePorUsuario(
			List<CobrancaAnuidade> cobrancasAnuidadePorUsuario) {
		this.cobrancasAnuidadePorUsuario = cobrancasAnuidadePorUsuario;
	}

	public String getRecebeCpf() {
		return recebeCpf;
	}

	public void setRecebeCpf(String recebeCpf) {
		this.recebeCpf = recebeCpf;
	}

	public BigDecimal getValorCalculado() {
		return valorCalculado;
	}

	public void setValorCalculado(BigDecimal valorCalculado) {
		this.valorCalculado = valorCalculado;
	}

	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public long getIdAnuidade() {
		return idAnuidade;
	}

	public void setIdAnuidade(long idAnuidade) {
		this.idAnuidade = idAnuidade;
	}

	public CobrancaAnuidade getCobranca() {
		return cobranca;
	}

	public void setCobranca(CobrancaAnuidade cobranca) {
		this.cobranca = cobranca;
	}
	public String getInstrucao1() {
		return instrucao1;
	}

	public void setInstrucao1(String instrucao1) {
		this.instrucao1 = instrucao1;
	}

	public String getInstrucao2() {
		return instrucao2;
	}

	public void setInstrucao2(String instrucao2) {
		this.instrucao2 = instrucao2;
	}

	public String getInstrucao3() {
		return instrucao3;
	}

	public void setInstrucao3(String instrucao3) {
		this.instrucao3 = instrucao3;
	}

	public String getInstrucao4() {
		return instrucao4;
	}

	public void setInstrucao4(String instrucao4) {
		this.instrucao4 = instrucao4;
	}

	public String getRecebeCnpj() {
		return recebeCnpj;
	}

	public void setRecebeCnpj(String recebeCnpj) {
		this.recebeCnpj = recebeCnpj;
	}

	public void adicionaCobranca() {
		
		try{
			
			if(cobranca.getId() == 0) {
				cobranca.setIdUsuarioCobranca(usuarioSelecionado.getId());
				cobranca.setNomeUsuario(usuarioSelecionado.getNome());
				cobranca.setStatusContas(StatusContas.PENDENTE);
				cobranca.setLancamento(Lancamento.NAOLANCADAS);
				cobranca.setDataEmissao(dataAtual.pegaDataAtual());
				cobranca.setAnuidade(anuidadeDao.anuidadePorId(idAnuidade));
				cobranca.setValor(anuidadeDao.anuidadePorId(idAnuidade).getValor());
				menssagens.info("Cobrança lançada");
				menssagens.warn("Lembre-se de gerar o arquivo de remessa !");
				
				dao.adiciona(cobranca);
				
				this.cobrancas = dao.lista();
				menssagens.info("Cobrança lançada");
				
				//criaBoletoSicoobUnicoUsuario();
				limpaFormularioJsf();
				
				System.out.println("Comprando!");
				
			
			}else{
				
				cobranca.setAnuidade(anuidadeDao.anuidadePorId(idAnuidade));
				menssagens.info("Cobrança atualizada");
				dao.atualiza(cobranca);
				this.cobrancas = dao.lista();
				limpaFormularioJsf();
				System.out.println("Cobrança Atualizada");
			}
			
		}catch (NullPointerException e) {
			menssagens.error("Falha na geração de cobrança, dados incompletos ! ");
		}
		
		
	}
	
	public void removeCobranca() {
		dao.remove(cobranca);
		this.cobrancas = dao.lista();
		limpaFormularioJsf();
		System.out.println("Cobrança Removida");
	}



	public List<CobrancaAnuidade> getCobrancas() {
		this.cobrancas = dao.lista();
		System.out.println("Listando Cobranças");
		return cobrancas;
		
	}
	
	
	public void limpaFormularioJsf() {
		this.cobranca = new CobrancaAnuidade();
	}
	
	public StatusContas[] getStatusContas() {
		return StatusContas.values();
	}
	
	public Anuidade buscaAnuidadePorId() {
		Anuidade anuidadeBuscada = this.anuidadeDao.anuidadePorId(idAnuidade);
		return anuidadeBuscada;
	}
	
	public BigDecimal calculaValorDeDesconto() {
		
		try{
			
			valorCalculado = buscaAnuidadePorId().getValor().subtract(desconto);
			
		}catch (NullPointerException e) {
			
			menssagens.error("Falha na geração de cobrança ");
		}
		
		return valorCalculado;
	}
	
	public void exibeValorComDesconto() {
		FacesUtil.addSucessMessage("Valor com desconto R$" + calculaValorDeDesconto());
	}
	

	public void buscaUsuarioPorCPF(){
		
		this.usuarioBuscado = usuarioDao.usuarioPorCpf(recebeCpf);
		this.cobrancasAnuidadePorUsuario = dao.buscaCobrancaAnuidadePorUsuario(usuarioBuscado.getId());
	}	
	
	public void buscaCobrancasPorCnpj() {
		this.usuarioBuscado = usuarioDao.usuarioPorCnpj(recebeCnpj);
		this.cobrancasAnuidadePorUsuario = dao.buscaCobrancaAnuidadePorUsuario(usuarioBuscado.getId());
	}
	
	public void enviaDadosParaPagSeguro() throws IOException {
		
		System.out.println("chamou o metodo envia para o PagSeguro");
		
		this.usuarioCobrancaPagSeguro = usuarioDao.buscaPorId(cobranca.getIdUsuarioCobranca());
		
		System.out.println("Buscou o Usuário "+ usuarioCobrancaPagSeguro.getNome());
		
		CheckoutPagSeguro checkoutPagSeguro = new CheckoutPagSeguro();
		checkoutPagSeguro.setNumeroItem(String.valueOf(cobranca.getId()));
		checkoutPagSeguro.setDescricao(cobranca.getTitulo());
		checkoutPagSeguro.setQuantidade(1);
		checkoutPagSeguro.setValorItem(cobranca.getValor());
		
		checkoutPagSeguro.setNomeComprador(usuarioCobrancaPagSeguro.getNome());
		
		System.out.println(usuarioCobrancaPagSeguro.getNome());
		checkoutPagSeguro.setEmailComprador(usuarioCobrancaPagSeguro.getEmail());
		
		System.out.println(usuarioCobrancaPagSeguro.getEmail());
		checkoutPagSeguro.setCpfComprador(usuarioCobrancaPagSeguro.getCpf());
		
		checkoutPagSeguro.setCodigoReferencaCobranca(String.valueOf(cobranca.getId()));
		
		System.out.println("Setou todas as Informações");
		
		checkoutPagSeguro.lancaNoCheckoutPagSeguro();
		
		System.out.println("chamando o metodo de lançamento");
		
		String url = checkoutPagSeguro.getResposta();
		
		System.out.println(url);
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	    externalContext.redirect(url);
		
		System.out.println("encaminhando para o site PagSeguro.com.br");
		
	}
	
		
		
		
	
	public void lancaCobrancaParaTodosUsuarios(){
		
		try{
			
			//cria lista de boletos
			//List<Boleto> boletos = new ArrayList<Boleto>();
			
			Date data = cobranca.getDataVencimento();
			BigDecimal valor = anuidadeDao.anuidadePorId(idAnuidade).getValor();
			int contador = 0;
			
			for(int i = 0; i<usuariosBuscadosParaSet.size();i++) {
				CobrancaAnuidade cobrancaParaSet = new CobrancaAnuidade();
				
				cobrancaParaSet.setIdUsuarioCobranca(usuariosBuscadosParaSet.get(i).getId());
				cobrancaParaSet.setNomeUsuario(usuariosBuscadosParaSet.get(i).getNome());
				cobrancaParaSet.setStatusContas(StatusContas.PENDENTE);
				cobrancaParaSet.setDataEmissao(dataAtual.pegaDataAtual());
				cobrancaParaSet.setDataVencimento(data);
				cobrancaParaSet.setAnuidade(anuidadeDao.anuidadePorId(idAnuidade));
				cobrancaParaSet.setValor(valor);
				cobrancaParaSet.setTitulo("Anuidade");
				cobrancaParaSet.setLancamento(Lancamento.NAOLANCADAS);
				
				dao.adiciona(cobrancaParaSet);
				
				contador++;
				
				//gera boletos com cobranças
				//lancaBoletoParaTodos(boletos, i, cobrancaParaSet);
							
				System.out.println(usuariosBuscadosParaSet);
				
				
			}

			menssagens.info("Cobrança de Anuidade Lançada para "+ contador + " Associado(s)");
			menssagens.warn("Lembre-se de gerar o arquivo de remessa !");
			 // cria um array com todos os boletos
			// byte[] pdf = BoletoViewer.groupInOnePDF(boletos);
			// enviarBoleto(pdf);
			
		}catch (IllegalArgumentException e) {
			menssagens.error("Falha na geração de cobrança, dados incompletos ! ");
		}
		
		
	}

	public void geraBoletos() {
		
		if(cobrancasParaBoletos.size() > 250){
			menssagens.error("Selecione no máximo 250 cobranças");
		
		}else{
			
			List<Boleto> boletos = new ArrayList<Boleto>();
			
			
			for(int i = 0; i<cobrancasParaBoletos.size();i++) {
				
				CobrancaAnuidade cobrancaParaSet = dao.buscaPorId(cobrancasParaBoletos.get(i).getId());
				Usuario usuariosBuscadosParaSet = usuarioDao.buscaPorId(cobrancaParaSet.getIdUsuarioCobranca());
				
				
				Cedente cedente = new Cedente("ASSOCIACAO ENGENHEIROS ARQUITETOS AGRONOMOS DE S", "48.020.424/0001-44");
				
				// Sacado
				Sacado sacado = new Sacado(usuariosBuscadosParaSet.getNome());
				
				// Endereço do sacado
				Endereco endereco = new Endereco();
				endereco.setUF(UnidadeFederativa.SP);
				endereco.setLocalidade(usuariosBuscadosParaSet.getPostagemCorreioCidade());
				endereco.setCep(new CEP(usuariosBuscadosParaSet.getPostagemCorreioCep()));
				endereco.setBairro(usuariosBuscadosParaSet.getPostagemCorreioBairro());
				endereco.setLogradouro(usuariosBuscadosParaSet.getPostagemCorreioRua() + " - " + usuariosBuscadosParaSet.getPostagemCorreioComplemento());
				endereco.setNumero(String.valueOf(usuariosBuscadosParaSet.getPostagemCorreioNumero()));
				
				
				sacado.addEndereco(endereco);
				
				// Criando o título
				ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCOOB.create());
				contaBancaria.setAgencia(new Agencia(4400));
				contaBancaria.setNumeroDaConta(new NumeroDaConta(20672));
				contaBancaria.setCarteira(new Carteira(1));
				
			
				
				nossoNumero = geradorDigitoVerificador.completaComZero(String.valueOf((cobrancaParaSet.getId()*1000)));
				DV = geradorDigitoVerificador.geraDigitoVerificador(nossoNumero);
				
				Titulo titulo = new Titulo(contaBancaria, sacado, cedente);
				titulo.setNumeroDoDocumento(String.valueOf(cobrancaParaSet.getId()));
				titulo.setNossoNumero(nossoNumero+DV);
				
				System.out.println("O ID da Cobrança é  "+cobrancaParaSet.getId());
				System.out.println("O ID do Usuário é  "+usuariosBuscadosParaSet.getId());
				System.out.println("O Somatorio dos dois é "+ (cobrancaParaSet.getId()+usuariosBuscadosParaSet.getId()));
				
				
				titulo.setValor(cobrancaParaSet.getValor());
				titulo.setDataDoDocumento(new Date());
				
				titulo.setDataDoVencimento(cobrancaParaSet.getDataVencimento());
				
				titulo.setTipoDeDocumento(TipoDeTitulo.OUTROS);
				
				titulo.setAceite(Aceite.N);
				
				Boleto boleto = new Boleto(titulo);
				boleto.setLocalPagamento("Pagável em qualquer banco até o vencimento");
				boleto.setInstrucao1("Não receber após o vencimento");
				boleto.setInstrucao1(instrucao1);
				boleto.setInstrucao2(instrucao2);
				boleto.setInstrucao3(instrucao3);
				boleto.setInstrucao4(instrucao4);
				
				boletos.add(boleto);
				
			}
			
			byte[] pdf = BoletoViewer.groupInOnePDF(boletos);
			enviarBoleto(pdf);
			
		}
		
		
		
	}
	
	public void criaBoletoSicoobUnicoUsuario() {
		
		usuarioSelecionado = usuarioDao.buscaPorId(cobranca.getIdUsuarioCobranca());
		
		
		
		Cedente cedente = new Cedente("ASSOCIACAO ENGENHEIROS ARQUITETOS AGRONOMOS DE S", "48.020.424/0001-44");
		
		// Sacado
		Sacado sacado = new Sacado(usuarioSelecionado.getNome());
		
		// Endereço do sacado
		Endereco endereco = new Endereco();
		endereco.setUF(UnidadeFederativa.SP);
		endereco.setLocalidade(usuarioSelecionado.getPostagemCorreioCidade());
		endereco.setCep(new CEP(usuarioSelecionado.getPostagemCorreioCep()));
		endereco.setBairro(usuarioSelecionado.getPostagemCorreioBairro());
		endereco.setLogradouro(usuarioSelecionado.getPostagemCorreioRua() + " - " + usuarioSelecionado.getPostagemCorreioComplemento() );
		endereco.setNumero(String.valueOf(usuarioSelecionado.getPostagemCorreioNumero()));
		
		sacado.addEndereco(endereco);
		
		
		// Criando o título
		
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCOOB.create());
		contaBancaria.setAgencia(new Agencia(4400));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(20672));
		contaBancaria.setCarteira(new Carteira(1));
		
		nossoNumero = geradorDigitoVerificador.completaComZero(String.valueOf((cobranca.getId()*1000)));
		DV = geradorDigitoVerificador.geraDigitoVerificador(nossoNumero);
		
		Titulo titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNumeroDoDocumento(String.valueOf(cobranca.getId()));
		titulo.setNossoNumero(nossoNumero+DV);
		//titulo.setDigitoDoNossoNumero(DV);
		
		System.out.println("O ID da Cobrança é  "+cobranca.getId());
		System.out.println("O ID do Usuário é  "+usuarioSelecionado.getId());
		System.out.println("O Somatorio dos dois é "+ (cobranca.getId()+usuarioSelecionado.getId()));
		System.out.println("Endereço complemento " + usuarioSelecionado.getPostagemCorreioComplemento());
		
		
		titulo.setValor(cobranca.getValor());
		titulo.setDataDoDocumento(new Date());
		
		titulo.setDataDoVencimento(cobranca.getDataVencimento());
		
		titulo.setTipoDeDocumento(TipoDeTitulo.OUTROS);
		
		titulo.setAceite(Aceite.N);
		
		Boleto boleto = new Boleto(titulo);
		boleto.setLocalPagamento("Pagável em qualquer banco até o vencimento");
		boleto.setInstrucao1("Não receber após o vencimento");
		boleto.setInstrucao1(instrucao1);
		boleto.setInstrucao2(instrucao2);
		boleto.setInstrucao3(instrucao3);
		boleto.setInstrucao4(instrucao4);
		

		
		BoletoViewer boletoViewer = new BoletoViewer(boleto);
		
		
		byte[] pdf = boletoViewer.getPdfAsByteArray();
		enviarBoleto(pdf);
	}
	
	private void enviarBoleto(byte[] pdf) {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=boleto" + cobranca.getTitulo() + ".pdf");
		
		try {
			OutputStream output = response.getOutputStream();
			output.write(pdf);
			response.flushBuffer();
		} catch (Exception e) {
			throw new RuntimeException("Erro gerando boleto", e);
		}
		
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	public List<CobrancaAnuidade> buscaCobrancaAnuidadesPorStatus() {
		
		this.cobrancasParaRemessa = dao.buscaCobrancaAnuidadePorStatus(StatusContas.PENDENTE, dataInicial, dataFinal);
		
		return cobrancasParaRemessa;
	}
	
	public List<CobrancaAnuidade> buscaCobrancaAnuidadesPorStatusNaoLancadas() {
		
		this.cobrancasParaRemessa = dao.buscaCobrancaAnuidadePorStatusNaoLancadas(StatusContas.PENDENTE, dataInicial, dataFinal, Lancamento.NAOLANCADAS);
		
		return cobrancasParaRemessa;
	}
	
	public List<CobrancaAnuidade> buscaCobrancasPendentes() {
		this.cobrancasPendentes = dao.buscaCobrancaAnuidadePorStatusSemData(StatusContas.PENDENTE);
		
		return cobrancasPendentes;
	}
	
	
	public List<CobrancaAnuidade> buscaCobrancasPagas() {
		this.cobrancasPagas = dao.buscaCobrancaAnuidadePorStatusSemData(StatusContas.PAGO);
		
		return cobrancasPagas;
	}
	
	
	
	//Lança Cobrança e envia por E-Mail
	public void lancaCobrancaEnviaPorEmail() {
		
		if(cobrancaParaEnviarEmail.isEmpty()){
			menssagens.error("Selecione Cobranças");
			
		}
		if(cobrancaParaEnviarEmail.size() > 100) {
				
			menssagens.error("Selecione no máximo 100 cobranças");
		
		}
		if(cobrancaParaEnviarEmail.size() > 0) {
			
			for (int i = 0; i<cobrancaParaEnviarEmail.size();i++){
							
				CobrancaAnuidade cobrancaAnuidadeSet = dao.buscaPorId(cobrancaParaEnviarEmail.get(i).getId());
				Usuario usuarioBuscado = usuarioDao.buscaPorId(cobrancaAnuidadeSet.getIdUsuarioCobranca());
							
							
							
				Cedente cedente = new Cedente("ASSOCIACAO ENGENHEIROS ARQUITETOS AGRONOMOS DE S", "48.020.424/0001-44");
							
				// Sacado
				Sacado sacado = new Sacado(usuarioBuscado.getNome());
							
				// Endereço do sacado
				Endereco endereco = new Endereco();
				endereco.setUF(UnidadeFederativa.SP);
				endereco.setLocalidade(usuarioBuscado.getPostagemCorreioCidade());
				endereco.setCep(new CEP(usuarioBuscado.getPostagemCorreioCep()));
				endereco.setBairro(usuarioBuscado.getPostagemCorreioBairro());
				endereco.setLogradouro(usuarioBuscado.getPostagemCorreioRua() + " - " + usuarioBuscado.getPostagemCorreioComplemento());
				endereco.setNumero(String.valueOf(usuarioBuscado.getPostagemCorreioNumero()));
							
				sacado.addEndereco(endereco);
							
				// Criando o título
				ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCOOB.create());
				contaBancaria.setAgencia(new Agencia(4400));
				contaBancaria.setNumeroDaConta(new NumeroDaConta(20672));
				contaBancaria.setCarteira(new Carteira(1));
							
						
							
				nossoNumero = geradorDigitoVerificador.completaComZero(String.valueOf((cobrancaAnuidadeSet.getId()*1000)));
				DV = geradorDigitoVerificador.geraDigitoVerificador(nossoNumero);
							
				Titulo titulo = new Titulo(contaBancaria, sacado, cedente);
				titulo.setNumeroDoDocumento(String.valueOf(cobrancaAnuidadeSet.getId()));
				titulo.setNossoNumero(nossoNumero+DV);
							
				System.out.println("O ID da Cobrança é  "+cobrancaAnuidadeSet.getId());
				System.out.println("O ID do Usuário é  "+usuarioBuscado.getId());
				System.out.println("O Somatorio dos dois é "+ (cobrancaAnuidadeSet.getId()+usuarioBuscado.getId()));
							
							
				titulo.setValor(cobrancaAnuidadeSet.getValor());
				titulo.setDataDoDocumento(new Date());
							
				titulo.setDataDoVencimento(cobrancaAnuidadeSet.getDataVencimento());
							
				titulo.setTipoDeDocumento(TipoDeTitulo.OUTROS);
							
				titulo.setAceite(Aceite.N);
							
				Boleto boleto = new Boleto(titulo);
				boleto.setLocalPagamento("Pagável em qualquer banco até o vencimento");
				boleto.setInstrucao1(instrucao1);
				boleto.setInstrucao2(instrucao2);
				boleto.setInstrucao3(instrucao3);
				boleto.setInstrucao4(instrucao4);
							
				BoletoViewer boletoViewer = new BoletoViewer(boleto);
				File pdfParaEnviar = boletoViewer.getPdfAsFile("Anuidade_AEASC.pdf");
				
							
				enviaEmail.enviaEmailComAnexo(pdfParaEnviar, usuarioBuscado.getEmail(), "Anuidade AEASC - (mensagem automática)", usuarioBuscado.getNome() + "\n" + msgEmail);
							
				System.out.println("E-Mail enviado" + usuarioBuscado.getNome() + "E-Mail" + usuarioBuscado.getEmail());
				
							
			}
			
			menssagens.info("E-Mail(s) enviado(s)");
			
		}
		
	}
	
	
	
	public void verificaSeAnuidadeExpirou() {
		
		int contador = 0;
		
		for(int i = 0; i < dao.lista().size(); i ++) {
			
			CobrancaAnuidade cobrancaBuscada = dao.buscaPorId(dao.lista().get(i).getId());
			
			Anuidade anuidadeEncontada = anuidadeDao.buscaAnuidade(cobrancaBuscada.getAnuidade());
			
			int data = anuidadeEncontada.getDataExpiracao().compareTo(dataAtual.pegaDataAtual());
			
			
			
				if (data < 0) {
					
					Usuario usuarioQueVaiSerAlterado = usuarioDao.buscaPorId(cobrancaBuscada.getIdUsuarioCobranca());
					usuarioQueVaiSerAlterado.setStatusUsuario(StatusUsuario.INATIVO);
					cobrancaBuscada.setStatusContas(StatusContas.EXPIRADA);
					usuarioDao.atualiza(usuarioQueVaiSerAlterado);
					dao.atualiza(cobrancaBuscada);
					
					System.out.println(usuarioQueVaiSerAlterado.getNome());
					
					contador++;
					
					
				}
				
				System.out.println("saida do metodo " + data);
			
		}
		
		
		menssagens.info(contador + " Cobrança(s) Expirada(s)");
	}
	
	public BigDecimal somatorioAnuidadePaga() {
		BigDecimal valor = dao.calculaTotalAnuidade(StatusContas.PAGO);
		
		return valor;
	}
	
	public BigDecimal somatorioAnuidadeAReceber() {
		BigDecimal valor = dao.calculaAReceberAnuidade(StatusContas.PENDENTE);
		
		return valor;
	}
	
	public List<CobrancaAnuidade> buscaCobrancasPagasPorPeriodo () {
		this.cobrancasPagasPorPeriodo = dao.buscaCobrancaAnuidadePagasPorPeriodo(StatusContas.PAGO, dataInicial, dataFinal);
		
		return cobrancasPagasPorPeriodo;
	}
	

}
