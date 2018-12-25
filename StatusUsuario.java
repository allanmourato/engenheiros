package br.com.sistema.modelo;

public enum StatusUsuario {
	ATIVO("Ativo"), 
	INATIVO("Inativo");

	private String label;

    private StatusUsuario(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
