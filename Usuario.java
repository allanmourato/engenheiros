package br.com.sistema.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Cacheable
@XmlRootElement
public class Usuario implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Temporal(TemporalType.DATE)
	private Date dataDoCadastro;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusUsuario statusUsuario;
	
	@NotNull(message="Digite seu nome")
	private String nome;
	
	@Email(message="Digite um E-Mail válido")
	@NotNull(message="Digite seu e-mail")
	@Column(unique = true)
	private String email;
	
	private int diaNascimento;
	
	private int mesNascimento;
	
	private int anoNascimento;
	
	private String senha;
	
	
	private String sexo;
	
	
	private int perfil;
	
	@ManyToOne
	@JoinColumn(name="categoria_pk")
	private Categoria categoria;
	
	private String creaCau;
	
	@Column(unique = true)
	@CPF(message = "Digite um CPF válido")
	private String cpf;
	
	@Column(unique = true)
	private String cnpj;
	private String rg;
	private String telResidencial;
	private String celular;
	private String telComercial;
	private String rua;
	private int numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String cep;
	private String uf;
	
	//endereço de correio
	
	private String correioRua;
	private int correioNumero;
	private String correioComplemento;
	private String correioBairro;
	private String correioCidade;
	private String correioCep;
	private String correioUf;
	
	
	private int postagem;
	
	private String postagemCorreioRua;
	private int postagemCorreioNumero;
	private String postagemCorreioComplemento;
	private String postagemCorreioBairro;
	private String postagemCorreioCidade;
	private String postagemCorreioCep;
	private String postagemCorreioUf;
	
	private String newsletter;
	private String autorizacao;
	
	
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDataDoCadastro() {
		return dataDoCadastro;
	}
	public void setDataDoCadastro(Date dataDoCadastro) {
		this.dataDoCadastro = dataDoCadastro;
	}
	public StatusUsuario getStatusUsuario() {
		return statusUsuario;
	}
	public void setStatusUsuario(StatusUsuario statusUsuario) {
		this.statusUsuario = statusUsuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getDiaNascimento() {
		return diaNascimento;
	}
	public void setDiaNascimento(int diaNascimento) {
		this.diaNascimento = diaNascimento;
	}
	public int getMesNascimento() {
		return mesNascimento;
	}
	public void setMesNascimento(int mesNascimento) {
		this.mesNascimento = mesNascimento;
	}
	public int getAnoNascimento() {
		return anoNascimento;
	}
	public void setAnoNascimento(int anoNascimento) {
		this.anoNascimento = anoNascimento;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public String getCreaCau() {
		return creaCau;
	}
	public void setCreaCau(String creaCau) {
		this.creaCau = creaCau;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getTelResidencial() {
		return telResidencial;
	}
	public void setTelResidencial(String telResidencial) {
		this.telResidencial = telResidencial;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getTelComercial() {
		return telComercial;
	}
	public void setTelComercial(String telComercial) {
		this.telComercial = telComercial;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getCorreioRua() {
		return correioRua;
	}
	public void setCorreioRua(String correioRua) {
		this.correioRua = correioRua;
	}
	public int getCorreioNumero() {
		return correioNumero;
	}
	public void setCorreioNumero(int correioNumero) {
		this.correioNumero = correioNumero;
	}
	public String getCorreioBairro() {
		return correioBairro;
	}
	public void setCorreioBairro(String correioBairro) {
		this.correioBairro = correioBairro;
	}
	public String getCorreioCidade() {
		return correioCidade;
	}
	public void setCorreioCidade(String correioCidade) {
		this.correioCidade = correioCidade;
	}
	public String getCorreioCep() {
		return correioCep;
	}
	public void setCorreioCep(String correioCep) {
		this.correioCep = correioCep;
	}
	public String getCorreioUf() {
		return correioUf;
	}
	public void setCorreioUf(String correioUf) {
		this.correioUf = correioUf;
	}
	
	public int getPerfil() {
		return perfil;
	}
	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public int getPostagem() {
		return postagem;
	}
	public void setPostagem(int postagem) {
		this.postagem = postagem;
	}
	public String getPostagemCorreioRua() {
		return postagemCorreioRua;
	}
	public void setPostagemCorreioRua(String postagemCorreioRua) {
		this.postagemCorreioRua = postagemCorreioRua;
	}
	public int getPostagemCorreioNumero() {
		return postagemCorreioNumero;
	}
	public void setPostagemCorreioNumero(int postagemCorreioNumero) {
		this.postagemCorreioNumero = postagemCorreioNumero;
	}
	public String getPostagemCorreioBairro() {
		return postagemCorreioBairro;
	}
	public void setPostagemCorreioBairro(String postagemCorreioBairro) {
		this.postagemCorreioBairro = postagemCorreioBairro;
	}
	public String getPostagemCorreioCidade() {
		return postagemCorreioCidade;
	}
	public void setPostagemCorreioCidade(String postagemCorreioCidade) {
		this.postagemCorreioCidade = postagemCorreioCidade;
	}
	public String getPostagemCorreioCep() {
		return postagemCorreioCep;
	}
	public void setPostagemCorreioCep(String postagemCorreioCep) {
		this.postagemCorreioCep = postagemCorreioCep;
	}
	public String getPostagemCorreioUf() {
		return postagemCorreioUf;
	}
	public void setPostagemCorreioUf(String postagemCorreioUf) {
		this.postagemCorreioUf = postagemCorreioUf;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getCorreioComplemento() {
		return correioComplemento;
	}
	public void setCorreioComplemento(String correioComplemento) {
		this.correioComplemento = correioComplemento;
	}
	public String getPostagemCorreioComplemento() {
		return postagemCorreioComplemento;
	}
	public void setPostagemCorreioComplemento(String postagemCorreioComplemento) {
		this.postagemCorreioComplemento = postagemCorreioComplemento;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNewsletter() {
		return newsletter;
	}
	public void setNewsletter(String newsletter) {
		this.newsletter = newsletter;
	}
	public String getAutorizacao() {
		return autorizacao;
	}
	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		return true;
	}
	
	

	
	
	
	
	
	

}
