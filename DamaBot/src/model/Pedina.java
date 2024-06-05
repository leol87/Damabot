package model;

public class Pedina {
	private int posI;
	private int posJ;
	private boolean dama;
	private String colore;
	private boolean mangia;
	private int posImangia;
	private int posJmangia;	
	
	
	public Pedina(int posI, int posJ, String colore) {
		this.posI = posI;
		this.posJ = posJ;
		this.dama = false;
		this.mangia = false;
		this.colore = colore;
	}

	public int getPosI() {
		return posI;
	}

	public void setPosI(int posI) {
		this.posI = posI;
	}

	public int getPosJ() {
		return posJ;
	}

	public void setPosJ(int posJ) {
		this.posJ = posJ;
	}

	public boolean isDama() {
		return dama;
	}

	public void setDama(boolean dama) {
		this.dama = dama;
	}

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}
	
	public boolean isMangia() {
		return mangia;
	}

	public void setMangia(boolean mangia) {
		this.mangia = mangia;
	}
	
	public int getPosImangia() {
		return posImangia;
	}

	public void setPosImangia(int posImangia) {
		this.posImangia = posImangia;
	}

	public int getPosJmangia() {
		return posJmangia;
	}

	public void setPosJmangia(int posJmangia) {
		this.posJmangia = posJmangia;
	}

	@Override
	public String toString() {
	//	return "Piedina [posX=" + posI + ", posY=" + posJ + ", dama=" + dama + ", colore=" + colore + "]";
	//	return colore+" - "+dama;
	//return posI+""+posJ+"-"+colore+" - "+dama;
	return posI+""+posJ+"-"+colore;
//	 if(colore.equalsIgnoreCase("Vuota")) return "       ";
//	 else return colore;
	
	}
	
	
	
	
}
