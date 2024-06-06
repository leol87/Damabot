package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import controller.ControlliSchacchiera;
import model.Pedina;
import model.Player;
import model.Scacchiera;

public class ArtificialIntelligence {


	//estrae una mossa casuale tra quelle possibili
	public ArrayList<Pedina> cercaMossaRandom(Scacchiera scacchiera, Player playerMove, Player playerStop) {
		ControlliSchacchiera c = new ControlliSchacchiera();
		Map<Pedina,List<Pedina>> map = c.cercaMossa(scacchiera, playerMove,playerStop);	
		ArrayList<Pedina> result = new ArrayList<Pedina>();	
		if(!map.isEmpty()) {
			int key = map.entrySet().size();
			Random random = new Random();
			int estraiCont = random.nextInt(key) + 1;

			//trova pedine
			for (Pedina pedina : map.keySet()) {
				estraiCont --;
				if(estraiCont == 0) {
					List<Pedina> listaPossibiliMosse = map.get(pedina);
					int maxPossibiliMosse = listaPossibiliMosse.size();
					int estraiMossePoss = random.nextInt(maxPossibiliMosse);
					Pedina p = listaPossibiliMosse.get(estraiMossePoss);
					result.add(pedina);
					result.add(p);
					break;
				}
			}
		}
		return result;
	}


	//prediligie il mangiare tra le mosse possibili
	public ArrayList<Pedina> cercaMossaAggressiva(Scacchiera scacchiera, Player playerMove, Player playerStop) {
		ControlliSchacchiera c = new ControlliSchacchiera();
		Map<Pedina,List<Pedina>> map = c.cercaMossa(scacchiera, playerMove,playerStop);	
		ArrayList<Pedina> result = new ArrayList<Pedina>();	


		Map<Pedina,List<Pedina>> mapPossibile = new HashMap<Pedina, List<Pedina>>();

		if(!map.isEmpty()) {
			for (Map.Entry<Pedina,List<Pedina>> entry : map.entrySet()) {
				List<Pedina> listaPossibiliMosse = map.get(entry.getKey());
				List<Pedina> pedineTrovate = new ArrayList<Pedina>();
				for(Pedina pedinaAnalis: listaPossibiliMosse) {					
					if(pedinaAnalis.isMangia()) {
						pedineTrovate.add(pedinaAnalis);
					}
				}
				if(!pedineTrovate.isEmpty()) {
					mapPossibile.put(entry.getKey(),pedineTrovate);
				}
			}


			if(mapPossibile.isEmpty()) {

				int key = map.entrySet().size();
				Random random = new Random();
				int estraiCont = random.nextInt(key) + 1;

				//trova pedine
				for (Pedina pedina : map.keySet()) {
					estraiCont --;
					if(estraiCont == 0) {
						List<Pedina> listaPossibiliMosse = map.get(pedina);
						int maxPossibiliMosse = listaPossibiliMosse.size();
						int estraiMossePoss = random.nextInt(maxPossibiliMosse);
						Pedina p = listaPossibiliMosse.get(estraiMossePoss);
						result.add(pedina);
						result.add(p);
						break;
					}
				}
			}
			else {
				int key = mapPossibile.entrySet().size();
				Random random = new Random();
				int estraiCont = random.nextInt(key) + 1;

				//trova pedine
				for (Pedina pedina : mapPossibile.keySet()) {
					estraiCont --;
					if(estraiCont == 0) {
						List<Pedina> listaPossibiliMosse = mapPossibile.get(pedina);
						int maxPossibiliMosse = listaPossibiliMosse.size();
						int estraiMossePoss = random.nextInt(maxPossibiliMosse);
						Pedina p = listaPossibiliMosse.get(estraiMossePoss);
						result.add(pedina);
						result.add(p);
						break;
					}
				}
			}		
		}
		return result;
	}



}
