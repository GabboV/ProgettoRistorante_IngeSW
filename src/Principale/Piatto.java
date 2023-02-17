package Principale;

public class Piatto {
	
	private String nomePiatto;
	
	private boolean valido;
	
	private Ricetta recipe;
	
	private int caricoLavoro;

	public Piatto(String nomePiatto, boolean valido, Ricetta recipe, int caricoLavoro) {
		super();
		this.nomePiatto = nomePiatto;
		this.valido = valido;
		this.recipe = recipe;
		this.caricoLavoro = caricoLavoro;
	}

	public String getNomePiatto() {
		return nomePiatto;
	}

	public void setNomePiatto(String nomePiatto) {
		this.nomePiatto = nomePiatto;
	}

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

	public Ricetta getRecipe() {
		return recipe;
	}

	public void setRecipe(Ricetta recipe) {
		this.recipe = recipe;
	}

	public int getCaricoLavoro() {
		return caricoLavoro;
	}

	public void setCaricoLavoro(int caricoLavoro) {
		this.caricoLavoro = caricoLavoro;
	}
	
	

}
