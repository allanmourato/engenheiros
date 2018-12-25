package br.com.sistema.modelo;

public enum StatusContas {
	PAGO("PAGO"),
	PENDENTE("PENDENTE"),
	EXPIRADA("EXPIRADA");
	
	
	private String label;

    private StatusContas(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
