package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Pedina;
import model.Player;
import model.Scacchiera;
import utility.UtilityParameter;

public class ControlliSchacchiera {



	//muove la pedina nella scacchiera
	//la mossa deve essere valida
	public String movePedina(Scacchiera scacchiera,int iStart, int jStart, int iFine, int jFine, Player playerMove, Player playerStop) {
		if(iFine >= 8 || jFine >= 8) {
			return UtilityParameter.valoriErrati;
		}
		if(!scacchiera.getScacchiera()[iFine][jFine].getColore().equalsIgnoreCase(UtilityParameter.cellaEmpty)) {
			return UtilityParameter.cellaOccupata;
		}
		scacchiera.getScacchiera()[iFine][jFine].setColore(playerMove.getColor());
		if(iFine == 0 || iFine == 7 || scacchiera.getScacchiera()[iStart][jStart].isDama()) {
			scacchiera.getScacchiera()[iFine][jFine].setDama(true);
		}
		scacchiera.getScacchiera()[iStart][jStart].setColore(UtilityParameter.cellaEmpty);
		if(iStart + 2 == iFine || iStart == iFine + 2) {
			playerMove.setPedineMangiate();
			playerStop.setPedinePerse();
			playerStop.setPedineInGioco();
			cercaPedinaMangiata( scacchiera, iFine,  jFine, iStart, jStart);
		}
		return UtilityParameter.ok;
	}


	//cerca la pedina mangiata
	private void cercaPedinaMangiata(Scacchiera scacchiera, int posI, int posJ, int xStart, int jStart) {
		if(posI < xStart && posJ > jStart) {
			scacchiera.getScacchiera()[xStart-1][jStart+1].setColore(UtilityParameter.cellaEmpty);
		}
		if(posI < xStart && posJ < jStart) {
			scacchiera.getScacchiera()[xStart-1][jStart-1].setColore(UtilityParameter.cellaEmpty);
		}
		if(posI > xStart && posJ > jStart) {
			scacchiera.getScacchiera()[xStart+1][jStart+1].setColore(UtilityParameter.cellaEmpty);
		}
		if(posI > xStart && posJ < jStart) {
			scacchiera.getScacchiera()[xStart+1][jStart-1].setColore(UtilityParameter.cellaEmpty);
		}
	}


	//cerca tutte le mosse possibile per quel giocatore
	public HashMap<Pedina,List<Pedina>> cercaMossa(Scacchiera scacchiera, Player playerMove, Player playerStop){
		HashMap<Pedina,List<Pedina>> resultMap = new HashMap<Pedina,List<Pedina>>();
		for(int i=0; i<scacchiera.getScacchiera().length;i++) {
			for(int j=0; j<scacchiera.getScacchiera()[i].length;j++) {
				if(scacchiera.getScacchiera()[i][j].getColore().equalsIgnoreCase(playerMove.getColor())) {
					List<Pedina> result = new ArrayList<Pedina>();
					result.addAll(cercaMovimento(scacchiera, scacchiera.getScacchiera()[i][j]));
					if(!result.isEmpty()) {
						resultMap.put(scacchiera.getScacchiera()[i][j],result);
					}
				}
			}
		}
		return resultMap;
	}


	//restituisce la lista delle mosse possibili
	//se non trova mosse, restituisce una lista vuota
	private List<Pedina> cercaMovimento(Scacchiera scacchiera, Pedina pedina) {
		ArrayList<Pedina> result = new ArrayList<Pedina>();
		int posIpedina = pedina.getPosI();
		int posJpedina = pedina.getPosJ();
		//se sono damone
		if(pedina.isDama()) {
			//posso spostarmi in alto a destra, in alto a sinistra, in basso a destra e in basso a sinistra
			//mi sposto in basso a destra
			if(posIpedina < 7 && posJpedina > 0) {
				result.addAll(spostaBassoDestra(scacchiera,pedina));
			}
			//mi sposto in basso a sinistra
			if(posIpedina < 7 && posJpedina < 7) {
				result.addAll(spostaBassoSinistra(scacchiera,pedina));
			}
			//mi sposto in alto a destra
			if(posIpedina > 0 && posJpedina < 7) {
				result.addAll(spostaAltoDestra(scacchiera,pedina));
			}
			//mi sposto in alto a sinistra
			if(posIpedina > 0 && posJpedina > 0) {
				result.addAll(spostaAltoSinistra(scacchiera,pedina));
			}
		}
		//se non sono damone
		else {
			//se pedina bianca posso muovermi dall'alto verso il basso
			//se pedina nera posso muovermi dal basso verso l'alto
			if(pedina.getColore().equalsIgnoreCase(UtilityParameter.cellaBianca)) {
				//CASO 1: posso spostarmi a sinistra, nell'ultima riga non posso essere perch� altrimenti sarei dama
				if(/*posIpedina < 7 &&*/ posJpedina > 0) {
					result.addAll(spostaBassoSinistra(scacchiera,pedina));
				}
				//CASO 2: posso spostarmi a destra, nell'ultima riga non posso essere perch� altrimenti sarei dama
				if(/*posIpedina < 7 &&*/ posJpedina < 7) {	
					//CASO 2.1: posso mangiare a destra
					result.addAll(spostaBassoDestra(scacchiera,pedina));
				}
			}
			else {
				//CASO 1: posso spostarmi a sinistra, nell'ultima riga non posso essere perch� altrimenti sarei dama
				if(posJpedina > 0) {	
					result.addAll(spostaAltoSinistra(scacchiera,pedina));
				}
				//CASO 2: posso spostarmi a destra, nell'ultima riga non posso essere perch� altrimenti sarei dama
				if( posJpedina < 7) {	
					result.addAll(spostaAltoDestra(scacchiera,pedina));
				}	
			}
		}
		return result;
	}

	//metodo che calcolo lo spostamento in basso a destra	
	private ArrayList<Pedina> spostaBassoDestra(Scacchiera scacchiera, Pedina pedina) {
		ArrayList<Pedina> result = new ArrayList<Pedina>();
		if(!scacchiera.getScacchiera()[pedina.getPosI()+1][pedina.getPosJ()+1].getColore().equalsIgnoreCase(UtilityParameter.cellaEmpty)) {
			//CASO 2.2 la cella a destra � occupata da una piedina avversaria, provo a mangiare
			if(!scacchiera.getScacchiera()[pedina.getPosI()+1][pedina.getPosJ()+1].getColore().equalsIgnoreCase(pedina.getColore()) && pedina.getPosI() < 6 && pedina.getPosJ() < 6) {
				if(scacchiera.getScacchiera()[pedina.getPosI()+2][pedina.getPosJ()+2].getColore().equalsIgnoreCase(UtilityParameter.cellaEmpty)) {

					if((pedina.isDama() && scacchiera.getScacchiera()[pedina.getPosI()+1][pedina.getPosJ()+1].isDama()) || (pedina.isDama() && !scacchiera.getScacchiera()[pedina.getPosI()+1][pedina.getPosJ()+1].isDama()) || (!pedina.isDama() && !scacchiera.getScacchiera()[pedina.getPosI()+1][pedina.getPosJ()+1].isDama())) {


						Pedina app = new Pedina(pedina.getPosI()+2,pedina.getPosJ()+2,pedina.getColore());
						app.setMangia(true);
						app.setPosImangia(pedina.getPosI()+1);
						app.setPosJmangia(pedina.getPosJ()+1);

						//controllo se dama
						if(!pedina.isDama()) {
							if(app.getPosI() == 7) {
								app.setDama(true);
							}
						}
						result.add(app);
					}
				}
			}
			//CASO 3.3 la cella a destra � occupata da una mia pedina, non faccio niente
			else {
				//non faccio niente
			}
		}
		//CASO 3.4: posso spostarmi perch� la cella � vuota
		else {
			Pedina app = new Pedina(pedina.getPosI()+1,pedina.getPosJ()+1,pedina.getColore());
			if(!pedina.isDama()) {
				if(app.getPosI() == 7) {
					app.setDama(true);
				}
			}
			result.add(app);	
		}
		return result;	
	}
	//metodo che calcolo lo spostamento in basso a sinistra	
	private ArrayList<Pedina> spostaBassoSinistra(Scacchiera scacchiera, Pedina pedina) {
		ArrayList<Pedina> result = new ArrayList<Pedina>();
		//CASO 1.1: posso mangiare a sinistra
		if(!scacchiera.getScacchiera()[pedina.getPosI()+1][pedina.getPosJ()-1].getColore().equalsIgnoreCase(UtilityParameter.cellaEmpty)) {
			//CASO 1.2 la cella a sinistra � occupata da una piedina avversaria, provo a mangiare
			if(!scacchiera.getScacchiera()[pedina.getPosI()+1][pedina.getPosJ()-1].getColore().equalsIgnoreCase(pedina.getColore()) && pedina.getPosI() < 6 && pedina.getPosJ() >1) {
				if(scacchiera.getScacchiera()[pedina.getPosI()+2][pedina.getPosJ()-2].getColore().equalsIgnoreCase(UtilityParameter.cellaEmpty)) {

					if((pedina.isDama() && scacchiera.getScacchiera()[pedina.getPosI()+1][pedina.getPosJ()-1].isDama()) || (pedina.isDama() && !scacchiera.getScacchiera()[pedina.getPosI()+1][pedina.getPosJ()-1].isDama()) || (!pedina.isDama() && !scacchiera.getScacchiera()[pedina.getPosI()+1][pedina.getPosJ()-1].isDama())) {

						Pedina app = new Pedina(pedina.getPosI()+2,pedina.getPosJ()-2,pedina.getColore());
						app.setMangia(true);
						app.setPosImangia(pedina.getPosI()+1);
						app.setPosJmangia(pedina.getPosJ()-1);

						//controllo se dama
						if(!pedina.isDama()) {
							if(app.getPosI() == 7) {
								app.setDama(true);
							}
						}
						result.add(app);
					}
				}  
			}
			//CASO 1.3 la cella a sinistra � occupata da una mia pedina, non faccio niente
			else {
				//non faccio niente
			}
		}
		//CASO 1.4: posso spostarmi perch� la cella � vuota
		else {
			Pedina app = new Pedina(pedina.getPosI()+1,pedina.getPosJ()-1,pedina.getColore());
			if(!pedina.isDama()) {
				if(app.getPosI() == 7) {
					app.setDama(true);
				}
			}
			result.add(app);	
		}	
		return result;
	}

	//metodo che calcolo lo spostamento in alto a sinistra	
	private ArrayList<Pedina> spostaAltoSinistra(Scacchiera scacchiera, Pedina pedina) {
		ArrayList<Pedina> result = new ArrayList<Pedina>();
		//CASO 1.1: posso mangiare a sinistra
		if(!scacchiera.getScacchiera()[pedina.getPosI()-1][pedina.getPosJ()-1].getColore().equalsIgnoreCase(UtilityParameter.cellaEmpty)) {
			//CASO 1.2 la cella a sinistra � occupata da una piedina avversaria, provo a mangiare
			if(!scacchiera.getScacchiera()[pedina.getPosI()-1][pedina.getPosJ()-1].getColore().equalsIgnoreCase(pedina.getColore()) && pedina.getPosI() > 1 && pedina.getPosJ() > 1) {
				if(scacchiera.getScacchiera()[pedina.getPosI()-2][pedina.getPosJ()-2].getColore().equalsIgnoreCase(UtilityParameter.cellaEmpty)) {

					if((pedina.isDama() && scacchiera.getScacchiera()[pedina.getPosI()-1][pedina.getPosJ()-1].isDama()) || (pedina.isDama() && !scacchiera.getScacchiera()[pedina.getPosI()-1][pedina.getPosJ()-1].isDama()) || (!pedina.isDama() && !scacchiera.getScacchiera()[pedina.getPosI()-1][pedina.getPosJ()-1].isDama())) {

						Pedina app = new Pedina(pedina.getPosI()-2,pedina.getPosJ()-2,pedina.getColore());
						app.setMangia(true);
						app.setPosImangia(pedina.getPosI()-1);
						app.setPosJmangia(pedina.getPosJ()-1);

						//controllo se dama
						if(!pedina.isDama()) {
							if(app.getPosI() == 0) {
								app.setDama(true);
							}
						}
						result.add(app);
					}
				}
			}
			//CASO 1.3 la cella a sinistra � occupata da una mia pedina, non faccio niente
			else {
				//non faccio niente
			}
		}
		//CASO 1.4: posso spostarmi perch� la cella � vuota
		else {
			Pedina app = new Pedina(pedina.getPosI()-1,pedina.getPosJ()-1,pedina.getColore());
			if(!pedina.isDama()) {
				if(app.getPosI() == 0) {
					app.setDama(true);
				}
			}
			result.add(app);	
		}
		return result;
	}

	//metodo che calcolo lo spostamento in alto a destra
	private ArrayList<Pedina> spostaAltoDestra(Scacchiera scacchiera, Pedina pedina) {
		ArrayList<Pedina> result = new ArrayList<Pedina>();
		if(!scacchiera.getScacchiera()[pedina.getPosI()-1][pedina.getPosJ()+1].getColore().equalsIgnoreCase(UtilityParameter.cellaEmpty)) {
			//CASO 2.2 la cella a destra � occupata da una piedina avversaria, provo a mangiare
			if(!scacchiera.getScacchiera()[pedina.getPosI()-1][pedina.getPosJ()+1].getColore().equalsIgnoreCase(pedina.getColore()) && pedina.getPosI() > 1 && pedina.getPosJ() < 6) {
				if(scacchiera.getScacchiera()[pedina.getPosI()-2][pedina.getPosJ()+2].getColore().equalsIgnoreCase(UtilityParameter.cellaEmpty)) {

					if((pedina.isDama() && scacchiera.getScacchiera()[pedina.getPosI()-1][pedina.getPosJ()+1].isDama()) || (pedina.isDama() && !scacchiera.getScacchiera()[pedina.getPosI()-1][pedina.getPosJ()+1].isDama()) || (!pedina.isDama() && !scacchiera.getScacchiera()[pedina.getPosI()-1][pedina.getPosJ()+1].isDama())) {

						Pedina app = new Pedina(pedina.getPosI()-2,pedina.getPosJ()+2,pedina.getColore());
						app.setMangia(true);
						app.setPosImangia(pedina.getPosI()-1);
						app.setPosJmangia(pedina.getPosJ()+1);

						//controllo se dama
						if(!pedina.isDama()) {
							if(app.getPosI() == 0) {
								app.setDama(true);
							}
						}

						result.add(app);
					}
				}
			}
			//CASO 3.3 la cella a destra � occupata da una mia pedina, non faccio niente
			else {
				//non faccio niente
			}
		}
		//CASO 3.4: posso spostarmi perch� la cella � vuota
		else {
			Pedina app = new Pedina(pedina.getPosI()-1,pedina.getPosJ()+1,pedina.getColore());
			if(!pedina.isDama()) {
				if(app.getPosI() == 0) {
					app.setDama(true);
				}
			}
			result.add(app);	
		}
		return result;
	}


	//elimina la pedina dalla scacchiera
	public String eliminaPedina(Scacchiera scacchiera, int posI, int posJ, String pedina) {
		if(posI >= 8 || posJ >= 8) {
			return UtilityParameter.valoriErrati;
		}

		if(scacchiera.getScacchiera()[posI][posJ].getColore().equalsIgnoreCase(UtilityParameter.cellaEmpty)) {
			return UtilityParameter.cellaLibera;
		}

		if(!pedina.equalsIgnoreCase(scacchiera.getScacchiera()[posI][posJ].getColore())) {
			return UtilityParameter.cellaOccupataAltra;
		}

		scacchiera.getScacchiera()[posI][posJ].setColore(UtilityParameter.cellaEmpty);
		return UtilityParameter.ok;
	}
}
