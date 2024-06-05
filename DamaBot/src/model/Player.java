package model;


public class Player {
	private String color;
	private int pedineMangiate;
	private int pedinePerse;
	private int pedineInGioco;
	private String nome;
	public Player(String color, String nome) {
		this.color = color;
		this.nome = nome;
		this.pedineMangiate = 0;
		this.pedinePerse = 0;
		this.pedineInGioco = 12;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getPedineMangiate() {
		return pedineMangiate;
	}
	public void setPedineMangiate( ) {
		this.pedineMangiate ++ ;
	}
	public int getPedinePerse() {
		return pedinePerse;
	}
	public void setPedinePerse( ) {
		this.pedinePerse ++;
	}
	public int getPedineInGioco() {
		return pedineInGioco;
	}
	public void setPedineInGioco() {
		this.pedineInGioco --;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@Override
	public String toString() {
		return "Player [color=" + color + ", pedineMangiate=" + pedineMangiate + ", pedinePerse=" + pedinePerse
				+ ", pedineInGioco=" + pedineInGioco + ", nome=" + nome + "]";
	}
	
	
}
