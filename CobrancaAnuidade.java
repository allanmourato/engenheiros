package br.com.sistema.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.XStream;

@Entity
@Cacheable
@XmlRootElement
public class CobrancaAnuidade implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String titulo;
	
	@Temporal(TemporalType.DATE)
	private Date dataPagamento;
	
	@Temporal(TemporalType.DATE)
	private Date dataVencimento;
	
	@Temporal(TemporalType.DATE)
	private Date dataEmissao;
	
	@Enumerated(EnumType.STRING)
	private StatusContas statusContas;
	
	@Enumerated(EnumType.STRING)
	private Lancamento lancamento;
	
	@NotNull
	private BigDecimal valor;
	
	@Column(name = "valor_com_desconto")
	private BigDecimal desconto;
	
	@OneToOne
	@JoinColumn(name = "pk_anuidade")
	private Anuidade anuidade;
	
	@OneToOne
	private Usuario usuarioCobranca;
	

	private long idUsuarioCobranca;
	
	private String nomeUsuario;
	
	private String viaDePagamento;

	
	
	
	
	public long getIdUsuarioCobranca() {
		return idUsuarioCobranca;
	}

	public void setIdUsuarioCobranca(long idUsuarioCobranca) {
		this.idUsuarioCobranca = idUsuarioCobranca;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public StatusContas getStatusContas() {
		return statusContas;
	}

	public void setStatusContas(StatusContas statusContas) {
		this.statusContas = statusContas;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public Anuidade getAnuidade() {
		return anuidade;
	}

	public void setAnuidade(Anuidade anuidade) {
		this.anuidade = anuidade;
	}
	public Usuario getUsuarioCobranca() {
		return usuarioCobranca;
	}

	public void setUsuarioCobranca(Usuario usuarioCobranca) {
		this.usuarioCobranca = usuarioCobranca;
	}
	
	public String getViaDePagamento() {
		return viaDePagamento;
	}

	public void setViaDePagamento(String viaDePagamento) {
		this.viaDePagamento = viaDePagamento;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		CobrancaAnuidade other = (CobrancaAnuidade) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public String toXML() {
	    return new XStream().toXML(this);
	}

	
	
	
	
	
	
}
