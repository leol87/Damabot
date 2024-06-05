package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import ai.ArtificialIntelligence;
import model.Pedina;
import model.Player;
import model.Scacchiera;
import utility.UtilityParameter;


//player uno sempre bianco, inizia a giocare lui, player uno � il PC
public class ControllerGame {
	private Player playerUno;
	private Player playerDue;
	private String modalita;
	private Scacchiera scacchiera;
	private ArtificialIntelligence ai;
	private ControlliSchacchiera cS;

	public ControllerGame(Player playerUno, Player playerDue, String modalita) {
		super();
		this.playerUno = playerUno;
		this.playerDue = playerDue;
		this.modalita = modalita;
		scacchiera = new Scacchiera();
		ai = new ArtificialIntelligence();
		cS = new ControlliSchacchiera();



	}

	public boolean partita() {
		boolean finePartita = false;

		//apri connessione con client


		while(!finePartita) {


			if(modalita.equalsIgnoreCase(UtilityParameter.facile)) {
				//gioca player Uno
				ArrayList<Pedina> cercaMossaRandom = ai.cercaMossaRandom(scacchiera, playerUno, playerDue);
				System.out.println("mossa automatica: " +cercaMossaRandom.toString());
				Pedina pedina = cercaMossaRandom.get(0);
				cS.movePedina(scacchiera, cercaMossaRandom.get(1), pedina, playerUno, playerDue);
				System.out.println(scacchiera);
				//TODO invia mossa a robot


				//gioca player Due
				boolean inserisciMossaPossibile = false;
				while(!inserisciMossaPossibile) {
					//legge mossa 
					Scanner in = new Scanner( System.in );
					System.out.println("Scrivi 4 numeri interi");
					int a = in.nextInt();
					int b = in.nextInt();
					int c = in.nextInt();
					int d = in.nextInt();

					Pedina pedinaSposta = new Pedina(a,b,UtilityParameter.cellaNera);
					Pedina pedinaSpostata = new Pedina(c,d,UtilityParameter.cellaNera);
					//verifica mossa tra quelle disponibili
					HashMap<Pedina,List<Pedina>> mossePossibili = cS.cercaMossa(scacchiera, playerDue, playerUno);
					for (Pedina key : mossePossibili.keySet()) {

						System.out.println(key + " = " + mossePossibili.get(key));
					}
					boolean mossaPossibile = false;
					for (Pedina key : mossePossibili.keySet()) {
						if(pedinaSposta.getPosI() == key.getPosI() && pedinaSposta.getPosJ() == key.getPosJ()) {
							List<Pedina> resultMossePossibiliPedina = mossePossibili.get(key);
							for(Pedina pedineMosse : resultMossePossibiliPedina) {
								if(pedineMosse.getPosI() == pedinaSpostata.getPosI() && pedineMosse.getPosJ() == pedinaSpostata.getPosJ()) {
									mossaPossibile = true;
									pedinaSpostata = pedineMosse;
									break;
								}
							}
						}
					}
					//fa la mossa
					if(mossaPossibile) {
						cS.movePedina(scacchiera, pedinaSpostata, pedinaSposta, playerDue,playerUno);
						//System.out.println(scacchiera);
						inserisciMossaPossibile = true;
						//TODO invia mossa a robot
					}
					else {
						System.out.println("mossa non ammessa");
					}
				}
			}



			//controllo fine partita
			if(playerUno.getPedineInGioco() < 3 || playerDue.getPedineInGioco() < 3) {
				finePartita = true;
			}
			else {
				System.out.println(playerUno.getNome()+" "+playerUno.getPedineInGioco());
				System.out.println(playerDue.getNome()+" "+playerDue.getPedineInGioco());
			}
		}
		return finePartita;
	}
}
