package it.unibs.ing.progetto.ristorante.Prenotazioni;

public class Cliente {
	
	private String codiceCliente;
	private int numeroCoperti;
	
	
	public Cliente(String codiceCliente, int numeroCoperti) {
		super();
		this.codiceCliente = codiceCliente;
		this.numeroCoperti = numeroCoperti;
	}
	
	public String getCodiceCliente() {
		return codiceCliente;
	}
	public void setCodiceCliente(String codiceCliente) {
		this.codiceCliente = codiceCliente;
	}
	public int getNumeroCoperti() {
		return numeroCoperti;
	}
	public void setNumeroCoperti(int numeroCoperti) {
		this.numeroCoperti = numeroCoperti;
	}
	
	@Override
	public String toString() {
		return "Cliente [codiceCliente=" + codiceCliente + ", numeroCoperti=" + numeroCoperti + "]";
	}

}
