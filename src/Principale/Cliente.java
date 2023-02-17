package Principale;

public class Cliente {
	
	private String codiceCliente;
	private int numeroTavolo;
	private int numeroCoperti;
	
	
	public Cliente(String codiceCliente, int numeroTavolo, int numeroCoperti) {
		super();
		this.codiceCliente = codiceCliente;
		this.numeroTavolo = numeroTavolo;
		this.numeroCoperti = numeroCoperti;
	}
	
	public String getCodiceCliente() {
		return codiceCliente;
	}
	public void setCodiceCliente(String codiceCliente) {
		this.codiceCliente = codiceCliente;
	}
	public int getNumeroTavolo() {
		return numeroTavolo;
	}
	public void setNumeroTavolo(int numeroTavolo) {
		this.numeroTavolo = numeroTavolo;
	}
	public int getNumeroCoperti() {
		return numeroCoperti;
	}
	public void setNumeroCoperti(int numeroCoperti) {
		this.numeroCoperti = numeroCoperti;
	}
	

}
