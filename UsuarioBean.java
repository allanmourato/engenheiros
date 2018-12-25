package br.com.sistema.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.mail.EmailException;

import br.com.sistema.dao.CategoriaDao;
import br.com.sistema.dao.CobrancaAnuidadeDao;
import br.com.sistema.dao.DesligamentoUsuarioDao;
import br.com.sistema.dao.UsuarioDao;
import br.com.sistema.modelo.Categoria;
import br.com.sistema.modelo.CobrancaAnuidade;
import br.com.sistema.modelo.DesligamentoUsuario;
import br.com.sistema.modelo.Postagem;
import br.com.sistema.modelo.StatusUsuario;
import br.com.sistema.modelo.Usuario;
import br.com.sistema.util.CEP;
import br.com.sistema.util.CepWS;
import br.com.sistema.util.EnviaEmail;
import br.com.sistema.util.FacesUtil;
import br.com.sistema.util.GeradorDeSenha;
import br.com.sistema.util.Validador;

@Named
@SessionScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	CepWS cepWs;
	
	@Inject
	UsuarioDao dao;
	
	@Inject
	Validador validador;
	
	@Inject
	CategoriaDao categoriaDao;
	
	@Inject
	GeradorDeSenha geradorDeSenha;
	
	@Inject
	EnviaEmail enviaEmail;
	
	@Inject
	CEP cep;
	
	@Inject
	CEP cepComercial;
	
	@Inject
	CobrancaAnuidadeDao cobrancaAnuidadeDao;
	
	@Inject
	DesligamentoUsuarioDao desligamentoUsuarioDao;
	
	@Inject
	DesligamentoUsuarioBean desligamentoUsuarioBean;
	
	
	Usuario usuario = new Usuario();
	private long categoriaId;
	private String usuarioStatus;
	private String recebeNome;
	private String recebeNomeParaCobranca;
	private String recebeCpf;
	private String recebeCnpj;
	private int recebeMes;
	private int selecaoStatus;
	private String recebeSenha;
	private String repeteSenha;
	private String habilitaEDesablitaBotao;
	private String cepParaBuscar;
	private String cepParaBuscarComercial;
	private StatusUsuario statusUsuario;
	private Date dataInicialCadastro;
	private Date dataFinalCadastro;
	private List<Usuario> usuarios;
	private List<Usuario> usuariosPorNome;
	private List<Usuario> usuariosPorNomeCobranca;
	private List<Usuario> usuariosCategorias;
	private List<Usuario> usuarioCpf;
	private List<Usuario> usuarioCnpj;
	private List<Usuario> aniversariantesDoMes;
	private List<Usuario> aniversariantesDoMesAtivos;
	private List<Usuario> aniversariantesDoMesInativos;
	private List<Usuario> usuariosCategoriaInativo;
	private List<Usuario> usuariosPorCategoriaSemStatus;
	private List<Usuario> usuariosPorDataDeCadastro;
	private List<CobrancaAnuidade> listaCobrancaAnuidade;
	
	
	
	
	
	public List<Usuario> getUsuariosPorNomeCobranca() {
		return usuariosPorNomeCobranca;
	}
	public void setUsuariosPorNomeCobranca(List<Usuario> usuariosPorNomeCobranca) {
		this.usuariosPorNomeCobranca = usuariosPorNomeCobranca;
	}
	public String getRecebeNomeParaCobranca() {
		return recebeNomeParaCobranca;
	}
	public void setRecebeNomeParaCobranca(String recebeNomeParaCobranca) {
		this.recebeNomeParaCobranca = recebeNomeParaCobranca;
	}
	public int getSelecaoStatus() {
		return selecaoStatus;
	}
	public void setSelecaoStatus(int selecaoStatus) {
		this.selecaoStatus = selecaoStatus;
	}
	public List<CobrancaAnuidade> getListaCobrancaAnuidade() {
		return listaCobrancaAnuidade;
	}
	public void setListaCobrancaAnuidade(List<CobrancaAnuidade> listaCobrancaAnuidade) {
		this.listaCobrancaAnuidade = listaCobrancaAnuidade;
	}
	public List<Usuario> getUsuariosCategoriaInativo() {
		return usuariosCategoriaInativo;
	}
	public void setUsuariosCategoriaInativo(List<Usuario> usuariosCategoriaInativo) {
		this.usuariosCategoriaInativo = usuariosCategoriaInativo;
	}
	public StatusUsuario getStatusUsuario() {
		return statusUsuario;
	}
	public void setStatusUsuario(StatusUsuario statusUsuario) {
		this.statusUsuario = statusUsuario;
	}
	public CEP getCepComercial() {
		return cepComercial;
	}
	public void setCepComercial(CEP cepComercial) {
		this.cepComercial = cepComercial;
	}
	public String getCepParaBuscarComercial() {
		return cepParaBuscarComercial;
	}
	public void setCepParaBuscarComercial(String cepParaBuscarComercial) {
		this.cepParaBuscarComercial = cepParaBuscarComercial;
	}
	public CEP getCep() {
		return cep;
	}
	public void setCep(CEP cep) {
		this.cep = cep;
	}
	public String getCepParaBuscar() {
		return cepParaBuscar;
	}
	public void setCepParaBuscar(String cepParaBuscar) {
		this.cepParaBuscar = cepParaBuscar;
	}
	public long getCategoriaId() {
		
		if(usuario.getId() != 0) {
			categoriaId = usuario.getCategoria().getId();
		}
		
		return categoriaId;
	}
	public void setCategoriaId(long categoriaId) {
		this.categoriaId = categoriaId;
	}
	public String getUsuarioStatus() {
		return usuarioStatus;
	}
	public void setUsuarioStatus(String usuarioStatus) {
		this.usuarioStatus = usuarioStatus;
	}
	public String getHabilitaEDesablitaBotao() {
		return habilitaEDesablitaBotao;
	}
	public void setHabilitaEDesablitaBotao(String habilitaEDesablitaBotao) {
		this.habilitaEDesablitaBotao = habilitaEDesablitaBotao;
	}
	public String getRecebeNome() {
		return recebeNome;
	}
	public void setRecebeNome(String recebeNome) {
		this.recebeNome = recebeNome;
	}
	public String getRecebeCpf() {
		return recebeCpf;
	}
	public void setRecebeCpf(String recebeCpf) {
		this.recebeCpf = recebeCpf;
	}
	
	public String getRecebeCnpj() {
		return recebeCnpj;
	}
	public void setRecebeCnpj(String recebeCnpj) {
		this.recebeCnpj = recebeCnpj;
	}
	public int getRecebeMes() {
		return recebeMes;
	}
	public void setRecebeMes(int recebeMes) {
		this.recebeMes = recebeMes;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public GeradorDeSenha getGeradorDeSenha() {
		return geradorDeSenha;
	}
	public void setGeradorDeSenha(GeradorDeSenha geradorDeSenha) {
		this.geradorDeSenha = geradorDeSenha;
	}
	public EnviaEmail getEnviaEmail() {
		return enviaEmail;
	}
	public void setEnviaEmail(EnviaEmail enviaEmail) {
		this.enviaEmail = enviaEmail;
	}
	public String getRepeteSenha() {
		return repeteSenha;
	}
	public void setRepeteSenha(String repeteSenha) {
		this.repeteSenha = repeteSenha;
	}
	public String getRecebeSenha() {
		return recebeSenha;
	}
	public void setRecebeSenha(String recebeSenha) {
		this.recebeSenha = recebeSenha;
	}
	public List<Usuario> getAniversariantesDoMesAtivos() {
		return aniversariantesDoMesAtivos;
	}
	public void setAniversariantesDoMesAtivos(List<Usuario> aniversariantesDoMesAtivos) {
		this.aniversariantesDoMesAtivos = aniversariantesDoMesAtivos;
	}
	public List<Usuario> getAniversariantesDoMesInativos() {
		return aniversariantesDoMesInativos;
	}
	public void setAniversariantesDoMesInativos(List<Usuario> aniversariantesDoMesInativos) {
		this.aniversariantesDoMesInativos = aniversariantesDoMesInativos;
	}
	public List<Usuario> getUsuariosPorDataDeCadastro() {
		return usuariosPorDataDeCadastro;
	}
	public void setUsuariosPorDataDeCadastro(List<Usuario> usuariosPorDataDeCadastro) {
		this.usuariosPorDataDeCadastro = usuariosPorDataDeCadastro;
	}
	public Date getDataInicialCadastro() {
		return dataInicialCadastro;
	}
	public void setDataInicialCadastro(Date dataInicialCadastro) {
		this.dataInicialCadastro = dataInicialCadastro;
	}
	public Date getDataFinalCadastro() {
		return dataFinalCadastro;
	}
	public void setDataFinalCadastro(Date dataFinalCadastro) {
		this.dataFinalCadastro = dataFinalCadastro;
	}
	
	
	
	
	
	
	
	public void adicionaUsuario() throws EmailException {
				
			if (usuario.getId() == 0) {
				
				Categoria categoriaRelacionada = categoriaDao.busca(categoriaId);
				usuario.setCategoria(categoriaRelacionada);
				usuario.setDataDoCadastro(pegaDataAtual());
				usuario.setSenha(geradorDeSenha.getSenha());
				verificaEndereco();
				//pegaDadosParaEnviarEmail();
				dao.adiciona(usuario);	
				this.usuarios = dao.lista();
				System.out.println("adicionando usuario: " + usuario.getNome());
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sistema Aeasc", "Usuário cadastrado com sucesso"));
				//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sistema Aeasc", "Login e Senha enviado no e-mail cadastrado"));
				
			}else{
				
				verificaEndereco();
				usuario.setCategoria(categoriaDao.busca(categoriaId));
				dao.atualiza(usuario);
				this.usuarios = dao.lista();
				System.out.println("Usuario atualizado com sucesso !");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sistema Aeasc", "Usuário atualizado com sucesso"));
				
			}
			
			limpaFormularioDoJSF();
				
		
	}

	
	public void removeUsuario() {
		dao.remover(usuario);
		this.usuarios = dao.lista();
		System.out.println("Removendo Usuario");
		
		limpaFormularioDoJSF();
	}
	
	
		
	//Lista Usuarios por Categorias
	public List<Usuario> getUsuariosPorCategoria() {
		if(usuariosCategorias == null) {
			this.usuariosCategorias = dao.listaUsuarioPorCategoria(categoriaId,StatusUsuario.ATIVO);
			System.out.println("Lista de usuarios por categoria");
		}
		return usuariosCategorias;
	}
	
	@SuppressWarnings("static-access")
	public List<Usuario> getUsuariosProCategoriaInativos() {
		if(usuariosCategoriaInativo == null) {
			this.usuariosCategoriaInativo = dao.listaUsuarioPorCategoria(categoriaId, statusUsuario.INATIVO);
			System.out.println("Lista de usuarios inativos por categorias");
		}
		
		return usuariosCategoriaInativo;
	}
	
	public List<Usuario> getUsuariosPorCategoriaSemStatus() {
		
		this.usuariosPorCategoriaSemStatus = dao.listaUsuarioPorCategoriaSemStatus(categoriaId);
		System.out.println("Listando usuarios");
		
		return usuariosPorCategoriaSemStatus;
	}
	
	public List<Usuario> getUsuarios() {
		this.usuarios = dao.lista();
		
		return usuarios;
	}
	
	public List<Usuario> usuariosPorPerfil() {
		
		if(selecaoStatus == 1){
			this.usuarios = dao.lista();
			System.out.println("listando usuarios");
		}
		if(selecaoStatus == 2) {
			this.usuarios = dao.listaSelecaoAtivosInativos(statusUsuario.ATIVO);
			System.out.println("listando usuarios ativos");
		}
		if(selecaoStatus == 3) {
			this.usuarios = dao.listaSelecaoAtivosInativos(statusUsuario.INATIVO);
		}
		return usuarios;
		
	}
	
	public List<Usuario> getUsuarioCpf() {
		if(usuarioCpf == null) {
			this.usuarioCpf = dao.listaUsuarioPorCpf(recebeCpf);
			System.out.println("Mostrando cpf do " + usuario.getNome());
		}
		return usuarioCpf;
	}
	
	public List<Usuario> getUsuarioCnpj() {
		if(usuarioCnpj == null) {
			this.usuarioCnpj = dao.listaUsuarioPorCnpj(recebeCnpj);
			System.out.println("Mostrando cnpj do " + usuario.getCnpj());
		}
		return usuarioCnpj;
	}
	
	public List<Usuario> getAniversariantes() {
		if(aniversariantesDoMes == null) {
			this.aniversariantesDoMes = dao.listaUsuarioPorMesDeNascimento(recebeMes);
			System.out.println("Mostrando aniversariantes do mes " + usuario.getMesNascimento());
		}
		return aniversariantesDoMes;
	}
	
	public List<Usuario> aniversariantesAtivos() {
		if(aniversariantesDoMesAtivos == null) {
			this.aniversariantesDoMesAtivos = dao.listaUsuariosPorMesDeNascimentoPeloStatus(recebeMes, statusUsuario.ATIVO);
			System.out.println("Mostrando aniversariantes do mes ativos" + aniversariantesDoMesAtivos);
		}
		
		return aniversariantesDoMesAtivos;
	}
	
	public List<Usuario> aniversariantesInativos() {
		if(aniversariantesDoMesInativos == null) {
			this.aniversariantesDoMesInativos = dao.listaUsuariosPorMesDeNascimentoPeloStatus(recebeMes, statusUsuario.INATIVO);
			System.out.println("Mostrando aniversariantes do mes ativos" + aniversariantesDoMesInativos);
		}
		
		return aniversariantesDoMesInativos;
	}
	
	//Metodo Busca por Nome de Usuario
	public List<Usuario> getUsuarioPorNome() {
		if(usuariosPorNome == null) {
			this.usuariosPorNome = dao.buscaPorNome(recebeNome);
			System.out.println("Buscando Usuario por Nome" + usuario.getNome());
		}
		return usuariosPorNome;
	}
	
	
	
	//Metodo utilizado no botao de busca por categorias
	public void atualizaDados() {
		usuariosCategorias = dao.listaUsuarioPorCategoria(categoriaId, StatusUsuario.ATIVO);
		usuariosCategoriaInativo = dao.listaUsuarioPorCategoria(categoriaId, StatusUsuario.INATIVO);
		System.out.println("Dados Atualizados !");
		
	}
	
	//Metodo utilizado no botao de busca por CPF
	public void atualizaCpf() {
		this.usuarioCpf = dao.listaUsuarioPorCpf(recebeCpf);
		System.out.println("Usuario Buscado!");
	}
	
	//Metodo utilizado no botao de busca por CNPJ
	public void atualizaCnpj() {
		this.usuarioCnpj = dao.listaUsuarioPorCnpj(recebeCnpj);
		System.out.println("Usuario Buscado!");
	}
	
	public void atualizaBuscaPorNome() {
		
		if(recebeNome.length() > 0) {
			this.usuariosPorNome = dao.buscaPorNome(recebeNome);
			System.out.println("Usuario Buscado!");
			System.out.println(recebeNome);
			
		}else {
			usuariosPorNome.clear();
			System.out.println("Campo vazio");
		}
	}
	
	public void atualizaBuscaPorNomeCobranca() {
		
		if(recebeNomeParaCobranca.length() > 0){
			this.usuariosPorNomeCobranca = dao.buscaPorNome(recebeNomeParaCobranca);
			System.out.println("Usuario Buscado!");
			
		}else{
			usuariosPorNomeCobranca.clear();
		}
		
	}
	
	//Metodod utilizado no botao de busca por Mes de Nascimento
	public void atualizaMes() {
		this.aniversariantesDoMes = dao.listaUsuarioPorMesDeNascimento(recebeMes);
		this.aniversariantesDoMesAtivos = dao.listaUsuariosPorMesDeNascimentoPeloStatus(recebeMes, statusUsuario.ATIVO);
		this.aniversariantesDoMesInativos = dao.listaUsuariosPorMesDeNascimentoPeloStatus(recebeMes, statusUsuario.INATIVO);
		System.out.println("Aniversariantes Buscados !");
	}
	
	/**
	 * Esse metodo apenas limpa o formulario da forma com que o JSF espera.
	 * Invoque-o no momento manager que precisar do formulario vazio.
	 */
	public void limpaFormularioDoJSF() {
		this.usuario = new Usuario();
	}
	
	//Metodo para direcionar o endereco para futuras correspondencias
	private void verificaEndereco() {
		
		if(usuario.getPostagem() == 1) {
			
			usuario.setPostagemCorreioRua(usuario.getRua());
			usuario.setPostagemCorreioBairro(usuario.getBairro());
			usuario.setPostagemCorreioNumero(usuario.getNumero());
			usuario.setPostagemCorreioComplemento(usuario.getComplemento());
			usuario.setPostagemCorreioCidade(usuario.getCidade());
			usuario.setPostagemCorreioCep(usuario.getCep());
			usuario.setPostagemCorreioUf(usuario.getUf());
			
		}
		
		if(usuario.getPostagem() == 2) {
			
			usuario.setPostagemCorreioRua(usuario.getCorreioRua());
			usuario.setPostagemCorreioBairro(usuario.getCorreioBairro());
			usuario.setPostagemCorreioComplemento(usuario.getCorreioComplemento());
			usuario.setPostagemCorreioNumero(usuario.getCorreioNumero());
			usuario.setPostagemCorreioCidade(usuario.getCorreioCidade());
			usuario.setPostagemCorreioCep(usuario.getCorreioCep());
			usuario.setPostagemCorreioUf(usuario.getCorreioUf());
			
		}
		
	}
	
		
	public Postagem[] getPostagem() {
		return Postagem.values();
	}
	
	public StatusUsuario[] getStatusUsuarios() {
		return StatusUsuario.values();
	}
	
	public Date pegaDataAtual() {
		Calendar calendar = new GregorianCalendar();
		Date date = new Date();
		calendar.setTime(date);
		return calendar.getTime();
	}
	
	
	//Metodo que verifica se CPF ja esta cadastrado no banco
	public void verificaCPFExite() {
		List<Usuario> pegaResultado = dao.listaUsuarioPorCpf(usuario.getCpf());
		
		
		if(pegaResultado.isEmpty()) {
			System.out.println("cpf ainda nao cadastrado");
			habilitaEDesablitaBotao = "false";
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistema Aeasc", "CPF existente !"));
			habilitaEDesablitaBotao = "true";
		}
	}
	
	//Metodo que verifica se CNPJ ja esta cadastrado no banco
		public void verificaCNPJExite() {
			List<Usuario> pegaResultado = dao.listaUsuarioPorCnpj(usuario.getCnpj());
			
			
			if(pegaResultado.isEmpty()) {
				System.out.println("cnpj ainda nao cadastrado");
				habilitaEDesablitaBotao = "false";
			}else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistema Aeasc", "CPF existente !"));
				habilitaEDesablitaBotao = "true";
			}
		}
		
		
		//Metodo que verifica se CNPJ ja esta cadastrado no banco
			public void verificaEmailExite() {
				
				if(usuario.getId() == 0) {
					
					List<Usuario> pegaResultado = dao.listaUsuarioPorEmail(usuario.getEmail());
					
					if(pegaResultado.isEmpty()) {
						System.out.println("email ainda nao cadastrado");
						habilitaEDesablitaBotao = "false";
					}else {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistema Aeasc", "E-MAIL exitente !"));
						habilitaEDesablitaBotao = "true";
					}
					
				}
				
				
					
			}
			
			//Pega dados do usuário para enviar E-mail com Login e Senha
			public void pegaDadosParaEnviarEmail()  {
				
				enviaEmail.setEnderecoEmail(usuario.getEmail());
				enviaEmail.setMensagemEmail("Seu login de acesso é:"+" "+"  "
						+ this.usuario.getEmail()+ "  Senha: "+" "+ this.usuario.getSenha());
				try {
					enviaEmail.enviaEmail();
				} catch (EmailException e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Sistema Aeasc", "Erro ao enviar E-mail!"));
					e.printStackTrace();
				}
			}
			
			
			//metodo para alterar a senha do usuário
			public void alterarSenha() {
				
				FacesContext context = FacesContext.getCurrentInstance();
				
					if (recebeSenha.equals(repeteSenha)) {
						usuario.setSenha(recebeSenha);
						dao.atualiza(usuario);
						System.out.println("Senha atualizada com sucesso!!");
						context.addMessage(null, new FacesMessage("Sucesso",  "Sistema Aeasc: " + "Senha alterada com sucesso !") );

					}else{
						context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistema Aeasc", "Senhas Divergentes !" ));
						System.out.println("senhas divergentes!!");	
					}
				
				
					
			}
			
			public CEP buscaCepParaPopular() {
				
				CepWS cepWS = new CepWS();
				cep = cepWS.buscaCepWS(usuario.getCep());
				usuario.setRua(cep.getLogradouro());
				usuario.setBairro(cep.getBairro());
				usuario.setCidade(cep.getLocalidade());
				usuario.setUf(cep.getUf());
				
				return cep;
			}
			
			public CEP buscaCepParaPopularComercial() {
				
				CepWS cepWS = new CepWS();
				cepComercial = cepWS.buscaCepWS(usuario.getCorreioCep());
				usuario.setCorreioRua(cepComercial.getLogradouro());
				usuario.setCorreioBairro(cepComercial.getBairro());
				usuario.setCorreioCidade(cepComercial.getLocalidade());
				usuario.setCorreioUf(cepComercial.getUf());
				
				return cepComercial;
			}
			
			public void recuperaEmailEReenvia() {
				try {
					enviaEmail.recuperaEmail("Login e senha de acesso", usuario.getEmail(), "Seu E-mail de acesso é:"+" "+"  "
							+ this.usuario.getEmail()+ "  Senha: "+" "+ this.usuario.getSenha());
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "E-Mail enviado com sucesso !", "Sistema Aeasc"));
					
				} catch (EmailException e) {
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro ao enviar E-mail!", "Sistema Aeasc"));
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public List<CobrancaAnuidade> buscaCobrancaPorUsuario() {
				this.listaCobrancaAnuidade = cobrancaAnuidadeDao.buscaCobrancaAnuidadePorUsuario(usuario.getId());
				
				return listaCobrancaAnuidade;
			}
			
			public void enviaSenhaELoginParaAssociados() {
				
				try{
					
					for(int i = 0; i < dao.lista().size(); i++) {
						
						Usuario usuarioBuscado = dao.buscaPorId(dao.lista().get(i).getId());
						
						enviaEmail.enviaEmailComLogin("Acesso Área do Associado(a) AEASC", "Caro associado seu login é: " + usuarioBuscado.getEmail() + " senha: " + usuarioBuscado.getSenha() , usuarioBuscado.getEmail());
						
					}
					
				}catch (EmailException e) {
					
					FacesUtil.addErrorMessage("Erro ao enviar E-Mails");
					
				}
				
				FacesUtil.addSucessMessage("E-Mails enviados com sucesso !");
				
			}
			
			
			public void passaTodosUsuariosComoInativos() {
				
				for(Usuario usuarioParaInativo : dao.lista()){
					usuarioParaInativo.setStatusUsuario(StatusUsuario.INATIVO);
					dao.atualiza(usuarioParaInativo);
				}
				
				FacesUtil.addSucessMessage("Todos usuarios inativos!");
			}
			
			public void desligaUsuario () {
				
				DesligamentoUsuario desligamentoUsuario = new DesligamentoUsuario();
				
				desligamentoUsuario.setDataDoDesligamento(pegaDataAtual());
				desligamentoUsuario.setPerfil(usuario.getPerfil());
				desligamentoUsuario.setSenha(usuario.getSenha());
				desligamentoUsuario.setDataDoCadastro(usuario.getDataDoCadastro());
				desligamentoUsuario.setNome(usuario.getNome());
				desligamentoUsuario.setEmail(usuario.getEmail());
				desligamentoUsuario.setSexo(usuario.getSexo());
				desligamentoUsuario.setStatusUsuario(usuario.getStatusUsuario());
				desligamentoUsuario.setCategoria(usuario.getCategoria());
				desligamentoUsuario.setCreaCau(usuario.getCreaCau());
				desligamentoUsuario.setCpf(usuario.getCpf());
				desligamentoUsuario.setCnpj(usuario.getCnpj());
				desligamentoUsuario.setRg(usuario.getRg());
				desligamentoUsuario.setTelResidencial(usuario.getTelResidencial());
				desligamentoUsuario.setCelular(usuario.getCelular());
				desligamentoUsuario.setTelComercial(usuario.getTelComercial());
				desligamentoUsuario.setDiaNascimento(usuario.getDiaNascimento());
				desligamentoUsuario.setMesNascimento(usuario.getMesNascimento());
				desligamentoUsuario.setAnoNascimento(usuario.getAnoNascimento());
				desligamentoUsuario.setCep(usuario.getCep());
				desligamentoUsuario.setRua(usuario.getRua());
				desligamentoUsuario.setNumero(usuario.getNumero());
				desligamentoUsuario.setComplemento(usuario.getComplemento());
				desligamentoUsuario.setBairro(usuario.getBairro());
				desligamentoUsuario.setCidade(usuario.getCidade());
				desligamentoUsuario.setUf(usuario.getUf());
				desligamentoUsuario.setCorreioRua(usuario.getCorreioRua());
				desligamentoUsuario.setCorreioCep(usuario.getCorreioCep());
				desligamentoUsuario.setCorreioNumero(usuario.getCorreioNumero());
				desligamentoUsuario.setCorreioComplemento(usuario.getCorreioComplemento());
				desligamentoUsuario.setCorreioBairro(usuario.getCorreioBairro());
				desligamentoUsuario.setCorreioCidade(usuario.getCorreioCidade());
				desligamentoUsuario.setCorreioUf(usuario.getCorreioUf());
				desligamentoUsuario.setPostagemCorreioRua(usuario.getPostagemCorreioRua());
				desligamentoUsuario.setPostagemCorreioBairro(usuario.getPostagemCorreioBairro());
				desligamentoUsuario.setPostagemCorreioCep(usuario.getPostagemCorreioCep());
				desligamentoUsuario.setPostagemCorreioNumero(usuario.getPostagemCorreioNumero());
				desligamentoUsuario.setPostagemCorreioCidade(usuario.getPostagemCorreioCidade());
				desligamentoUsuario.setPostagemCorreioUf(usuario.getPostagemCorreioUf());
				desligamentoUsuario.setPostagemCorreioComplemento(usuario.getPostagemCorreioComplemento());
				desligamentoUsuario.setPostagem(usuario.getPostagem());
				desligamentoUsuario.setNewsletter(usuario.getNewsletter());
				desligamentoUsuario.setAutorizacao(usuario.getAutorizacao());
				
				desligamentoUsuarioDao.AdicionaDesligamento(desligamentoUsuario);
				desligamentoUsuarioBean.lista();
				removeUsuario();
				
				FacesUtil.addSucessMessage("Usuário Desligado");
				
				System.out.println(usuario.getNome());
				System.out.println("Usuário Desligado");
				
			}
			
			public List<Usuario> usuariosPorDataDeCadastro() {
				
				usuariosPorDataDeCadastro = dao.usuariosPorDataDeCadastro(dataInicialCadastro, dataFinalCadastro);
				
				return usuariosPorDataDeCadastro;
			}
	
}
