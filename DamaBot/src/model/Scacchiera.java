package model;

/*
 * ASSUNZIONI
 * I bianchi in alto (quindi vanno da 0 a salire) e i neri in basso (quindi vanno da 8 a scendere)
 * 
 * 
 */

import utility.UtilityParameter;

public class Scacchiera {

	private Pedina[][] scacchiera;

	public Scacchiera() {
		//creo la scaicchiera
		scacchiera = new Pedina[8][8];
		for(int i =0 ; i< 8; i++) {
			for(int j =0 ; j< 8; j++) {
				Pedina vuota = new Pedina(i, j,  UtilityParameter.cellaEmpty);
				scacchiera[i][j] = vuota;
				
				
				if(i==0 && (j==1 || j==3 || j==5 || j==7)) {
					scacchiera[i][j]=new Pedina(i,j, UtilityParameter.cellaBianca);
				}
				if(i==1 && (j==0 || j==2 || j==4 || j==6)) {
					scacchiera[i][j]=new Pedina(i,j, UtilityParameter.cellaBianca);
				}
				if(i==2 && (j==1 || j==3 || j==5 || j==7)) {
					scacchiera[i][j]=new Pedina(i,j, UtilityParameter.cellaBianca);
				}
				
				
				if(i==5 && (j==0 || j==2 || j==4 || j==6)) {
					scacchiera[i][j]=new Pedina(i,j, UtilityParameter.cellaNera);
				}
				if(i==6 && (j==1 || j==3 || j==5 || j==7)) {
					scacchiera[i][j]=new Pedina(i,j, UtilityParameter.cellaNera);
				}
				if(i==7 && (j==0 || j==2 || j==4 || j==6)) {
					scacchiera[i][j]=new Pedina(i,j, UtilityParameter.cellaNera);
				}
				
				
			}
		}
	}
	
	
	public Pedina[][] getScacchiera() {
		return scacchiera;
	}


	public void setScacchiera(Pedina[][] scacchiera) {
		this.scacchiera = scacchiera;
	}

	@Override
	public String toString() {
		
		String result = "--------------------------------------------------------------------------\n";
				for(int i =0 ; i< 8; i++) {
					for(int j =0 ; j< 8; j++) {
						result = result + scacchiera[i][j] +" | ";
					}
					result = result + "\n";
					result = result + "--------------------------------------------------------------\n";
				}
				
		return result;
	}
	
	
}
