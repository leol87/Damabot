package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import ai.ArtificialIntelligence;
import controller.ControlliSchacchiera;
import model.Pedina;
import model.Player;
import model.Scacchiera;
import utility.UtilityParameter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;

public class PlayFrame {

	private JFrame frame;
	private String modalita;
	private String nome;
	private JTable table;
	private JTextField textField;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JButton btnNewButton;
	private Scacchiera scacchiera;
	private ArtificialIntelligence ai;
	private ControlliSchacchiera cS;
	private boolean finePartita = false;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;

	private JLabel lblNewLabel_8;

	private JLabel lblNewLabel_9;
	private String connRobot;
	private int port = 29999;
	private Socket socket;
	private DataOutputStream bufReader;

	/**
	 * Launch the application.
	 */
	public void playFrame(String m, String n, String c) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayFrame window = new PlayFrame(m,n,c);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PlayFrame(String m,String n, String c) {
		this.modalita = m;
		this.nome = n;
		this.connRobot = c;
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		if(connRobot != null) {
			if(connRobot.equalsIgnoreCase("SI")) {
				System.out.println("connetto");
				try(ServerSocket serverSocket = new ServerSocket(port)) {
					socket = serverSocket.accept();
					bufReader = new DataOutputStream(socket.getOutputStream());

				}catch(IOException ex) {
					System.out.println("Server exception: "+ ex.getMessage());
					ex.printStackTrace();
				}
			}
			else {
				System.out.println("non connetto");
			}
		}

		frame = new JFrame();
		frame.setBounds(100, 100, 627, 398);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(frame, 
						"Are you sure you want to close this window?", "Close Window?", 
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					try {
						if(bufReader != null && socket != null) {
							bufReader.close();
							socket.close();
						}
					} catch (IOException e) {
						System.out.println("Server exception: "+ e.getMessage());
						e.printStackTrace();
					}

					System.exit(0);
				}
			}
		});


		Player playerUno = new Player(UtilityParameter.cellaBianca, "Damabot");
		Player playerDue = new Player(UtilityParameter.cellaNera, nome);
		scacchiera = new Scacchiera();
		ai = new ArtificialIntelligence();
		cS = new ControlliSchacchiera();

		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
					{null, UtilityParameter.cellaBianca, null, UtilityParameter.cellaBianca, null, UtilityParameter.cellaBianca, null, UtilityParameter.cellaBianca},
					{UtilityParameter.cellaBianca, null, UtilityParameter.cellaBianca, null, UtilityParameter.cellaBianca, null, UtilityParameter.cellaBianca, null},
					{null, UtilityParameter.cellaBianca, null, UtilityParameter.cellaBianca, null, UtilityParameter.cellaBianca, null, UtilityParameter.cellaBianca},
					{null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null},
					{UtilityParameter.cellaNera, null, UtilityParameter.cellaNera, null, UtilityParameter.cellaNera, null, UtilityParameter.cellaNera, null},
					{null, UtilityParameter.cellaNera, null, UtilityParameter.cellaNera, null, UtilityParameter.cellaNera, null, UtilityParameter.cellaNera},
					{UtilityParameter.cellaNera, null, UtilityParameter.cellaNera, null, UtilityParameter.cellaNera, null, UtilityParameter.cellaNera, null},
				},new String[] {"A", "B", "C", "D", "E", "F", "G", "H"}));
		table.setBounds(71, 36, 503, 128);
		frame.getContentPane().add(table);

		JLabel lblNewLabel = new JLabel("Inserisci Mossa");
		lblNewLabel.setBounds(20, 197, 128, 28);
		frame.getContentPane().add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(158, 201, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setVisible(false);

		lblNewLabel_1 = new JLabel("Player Nero "+playerDue.getNome());
		lblNewLabel_1.setBounds(41, 261, 129, 14);
		frame.getContentPane().add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Modalità "+modalita);
		lblNewLabel_2.setBounds(20, 11, 142, 14);
		frame.getContentPane().add(lblNewLabel_2);


		lblNewLabel_3 = new JLabel("Perse: " +playerUno.getPedinePerse());
		lblNewLabel_3.setBounds(20, 286, 76, 28);
		frame.getContentPane().add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel("Player Bianco "+playerUno.getNome());
		lblNewLabel_4.setBounds(316, 261, 128, 14);
		frame.getContentPane().add(lblNewLabel_4);

		lblNewLabel_5 = new JLabel("In gioco: "+playerUno.getPedineInGioco());
		lblNewLabel_5.setBounds(134, 286, 89, 28);
		frame.getContentPane().add(lblNewLabel_5);

		lblNewLabel_6 = new JLabel("Perse: "+playerDue.getPedinePerse());
		lblNewLabel_6.setBounds(262, 286, 76, 28);
		frame.getContentPane().add(lblNewLabel_6);

		lblNewLabel_7 = new JLabel("In gioco: "+playerDue.getPedineInGioco());
		lblNewLabel_7.setBounds(365, 286, 89, 28);
		frame.getContentPane().add(lblNewLabel_7);


		lblNewLabel_8 = new JLabel("Mossa "+playerUno.getNome());
		lblNewLabel_8.setBounds(294, 204, 201, 21);
		frame.getContentPane().add(lblNewLabel_8);

		lblNewLabel_9 = new JLabel("MOSSA NON VALIDA");
		lblNewLabel_9.setBounds(81, 175, 160, 15);
		lblNewLabel_9.setVisible(false);

		btnNewButton = new JButton("Mossa Bianco");


		JLabel lblNewLabel_10 = new JLabel("VINCE IL PLAYER:");
		lblNewLabel_10.setBounds(219, 176, 178, 14);
		frame.getContentPane().add(lblNewLabel_10);
		lblNewLabel_10.setVisible(false);


		JButton btnNewButton_1 = new JButton("Mossa Nero");
		btnNewButton_1.setVisible(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mossaRobot = "";
				ArrayList<Pedina> cercaMossaRandom = null;
				boolean mossaTrovata = false;

				//legge mossa 
				String mossaInput = textField.getText().toLowerCase();
				int[] mosseInputParse = parseInput(mossaInput);

				//se mossa inserita
				if(mosseInputParse != null) {
					Pedina pedinaSposta = new Pedina(mosseInputParse[0],mosseInputParse[1],UtilityParameter.cellaNera);
					Pedina pedinaSpostata = new Pedina(mosseInputParse[2],mosseInputParse[3],UtilityParameter.cellaNera);	
					//verifica mossa tra quelle disponibili
					HashMap<Pedina,List<Pedina>> mossePossibili = cS.cercaMossa(scacchiera, playerDue, playerUno);

					//						for (Pedina key : mossePossibili.keySet()) {
					//							System.out.println(key + " = " + mossePossibili.get(key));
					//						}

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
					if(mossaPossibile) {
						cS.movePedina(scacchiera,pedinaSposta.getPosI(), pedinaSposta.getPosJ(), pedinaSpostata.getPosI(), pedinaSpostata.getPosJ(), playerDue,playerUno);

						if(pedinaSpostata.isDama()) {
							mossaRobot = "D";	
						}
						else {
							mossaRobot = "N";
						}

						mossaRobot += pedinaSpostata.getPosI()+""+pedinaSpostata.getPosJ();

						if(pedinaSposta.isDama()) {
							mossaRobot += "D";	
						}
						else {
							mossaRobot += "N";
						}		
						mossaRobot += pedinaSposta.getPosI()+""+pedinaSposta.getPosJ();
						mossaTrovata = true;
					}	
					else {
						mossaTrovata = false;
						lblNewLabel_9.setVisible(true);
						textField.setText("");
					}		
				}
				else {
					if(mossaInput.isEmpty()) {
						if(modalita.equalsIgnoreCase(UtilityParameter.facile)) {
							cercaMossaRandom = ai.cercaMossaRandom(scacchiera, playerDue, playerUno);
						}

						if(modalita.equalsIgnoreCase(UtilityParameter.aggressiva)) {
							cercaMossaRandom = ai.cercaMossaAggressiva(scacchiera, playerDue, playerUno);
						}

						if(!cercaMossaRandom.isEmpty()) {
							Pedina pedina = cercaMossaRandom.get(0);
							cS.movePedina(scacchiera, pedina.getPosI(), pedina.getPosJ(), cercaMossaRandom.get(1).getPosI(),cercaMossaRandom.get(1).getPosJ(), playerDue, playerUno);
							lblNewLabel_9.setVisible(false);
							if(cercaMossaRandom.get(1).isDama()) {
								mossaRobot = "D";	
							}
							else {
								mossaRobot = "N";
							}

							mossaRobot += cercaMossaRandom.get(1).getPosI()+""+cercaMossaRandom.get(1).getPosJ();

							if(pedina.isDama()) {
								mossaRobot += "D";	
							}
							else {
								mossaRobot += "N";
							}
							mossaRobot = pedina.getPosI()+""+pedina.getPosJ();
							mossaTrovata = true;
							mossaTrovata = true;
						}
					}
					else {
						mossaTrovata = false;
						lblNewLabel_9.setVisible(true);
						textField.setText("");
					}	
				}


				if(mossaTrovata) {
					//System.out.println(scacchiera);
					lblNewLabel_9.setVisible(false);


					lblNewLabel_3.setText("Perse: " +playerDue.getPedinePerse());
					lblNewLabel_6.setText("Perse: " +playerUno.getPedinePerse());
					lblNewLabel_5.setText("In gioco: "+playerDue.getPedineInGioco());
					lblNewLabel_7.setText("In gioco: "+playerUno.getPedineInGioco());

					Object[][] scacchieraTemporany = new Object[][] {
						{null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null},
					};

					for(int i = 0 ; i< 8; i++) {
						for(int j = 0; j < 8 ; j++) {
							if(!scacchiera.getScacchiera()[i][j].getColore().equals(UtilityParameter.cellaEmpty)) {
								scacchieraTemporany[i][j] = scacchiera.getScacchiera()[i][j].getColore()+"-"+scacchiera.getScacchiera()[i][j].isDama();
							}
						}
					}
					table.setModel(new DefaultTableModel(scacchieraTemporany,new String[] {"A", "B", "C", "D", "E", "F", "G", "H"}));		

					//invia mossa a robot
					if(connRobot.equalsIgnoreCase("SI")) {
						try {
							bufReader.writeUTF(mossaRobot);
						} catch (IOException e1) {
							System.out.println("Server exception: "+ e1.getMessage());
							e1.printStackTrace();
						}
					}
					textField.setVisible(false);
					btnNewButton.setVisible(true);
					btnNewButton_1.setVisible(false);

					//controllo fine partita
					if(playerUno.getPedineInGioco() < 3 || playerDue.getPedineInGioco() < 3) {
						finePartita = true;
						lblNewLabel.setVisible(false);
						textField.setVisible(false);
						lblNewLabel_8.setVisible(false);
						btnNewButton.setVisible(false);

						if(playerUno.getPedineInGioco() < 3) {
							lblNewLabel_10.setText("VINCE IL PLAYER NERO" );
						}
						else {
							lblNewLabel_10.setText("VINCE IL PLAYER BIANCO" );
						}
						lblNewLabel_10.setVisible(true);

					}
				}
			}
		});



		btnNewButton_1.setBounds(20, 232, 127, 23);
		frame.getContentPane().add(btnNewButton_1);


		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mossaRobot = "";
				if(!finePartita) {

					//gioca player Uno
					//	if(giocaPlayerUno) {
					ArrayList<Pedina> cercaMossaRandom = null;

					if(modalita.equalsIgnoreCase(UtilityParameter.facile)) {
						cercaMossaRandom = ai.cercaMossaRandom(scacchiera, playerUno, playerDue);
					}

					if(modalita.equalsIgnoreCase(UtilityParameter.aggressiva)) {
						cercaMossaRandom = ai.cercaMossaAggressiva(scacchiera, playerUno, playerDue);
					}


					if(!cercaMossaRandom.isEmpty()) {
						lblNewLabel_8.setText("Mossa "+playerUno.getNome()+" "+cercaMossaRandom.toString());
						Pedina pedina = cercaMossaRandom.get(0);
						cS.movePedina(scacchiera, pedina.getPosI(), pedina.getPosJ(), cercaMossaRandom.get(1).getPosI(),cercaMossaRandom.get(1).getPosJ(), playerUno, playerDue);

						if(cercaMossaRandom.get(1).isDama()) {
							mossaRobot = "D";	
						}
						else {
							mossaRobot = "N";
						}

						mossaRobot += cercaMossaRandom.get(1).getPosI()+""+cercaMossaRandom.get(1).getPosJ();

						if(pedina.isDama()) {
							mossaRobot += "D";	
						}
						else {
							mossaRobot += "N";
						}
						mossaRobot = pedina.getPosI()+""+pedina.getPosJ();

						lblNewLabel_3.setText("Perse: " +playerDue.getPedinePerse());
						lblNewLabel_6.setText("Perse: " +playerUno.getPedinePerse());
						lblNewLabel_5.setText("In gioco: "+playerDue.getPedineInGioco());
						lblNewLabel_7.setText("In gioco: "+playerUno.getPedineInGioco());

						Object[][] scacchieraTemporany = new Object[][] {
							{null, null, null, null, null, null, null, null},
							{null, null, null, null, null, null, null, null},
							{null, null, null, null, null, null, null, null},
							{null, null, null, null, null, null, null, null},
							{null, null, null, null, null, null, null, null},
							{null, null, null, null, null, null, null, null},
							{null, null, null, null, null, null, null, null},
							{null, null, null, null, null, null, null, null},
						};

						for(int i = 0 ; i< 8; i++) {
							for(int j = 0; j < 8 ; j++) {
								if(!scacchiera.getScacchiera()[i][j].getColore().equals(UtilityParameter.cellaEmpty)) {
									scacchieraTemporany[i][j] = scacchiera.getScacchiera()[i][j].getColore()+"-"+scacchiera.getScacchiera()[i][j].isDama();
								}
							}
						}

						table.setModel(new DefaultTableModel(scacchieraTemporany,new String[] {"A", "B", "C", "D", "E", "F", "G", "H"}));					
					}
					else {
						lblNewLabel_8.setText("Mossa "+playerUno.getNome()+" NESSUNA");
					}

					//invia mossa a robot
					if(connRobot.equalsIgnoreCase("SI")) {
						try {
							bufReader.writeUTF(mossaRobot);
						} catch (IOException e1) {
							System.out.println("Server exception: "+ e1.getMessage());
							e1.printStackTrace();
						}
					}
					textField.setVisible(true);
					textField.setText("");
					btnNewButton_1.setVisible(true);
					btnNewButton.setVisible(false);

					//controllo fine partita
					if(playerUno.getPedineInGioco() < 3 || playerDue.getPedineInGioco() < 3) {
						finePartita = true;
						lblNewLabel.setVisible(false);
						textField.setVisible(false);
						lblNewLabel_8.setVisible(false);	
						btnNewButton.setVisible(false);
						if(playerUno.getPedineInGioco() < 3) {
							lblNewLabel_10.setText("VINCE IL PLAYER NERO" );
						}
						else {
							lblNewLabel_10.setText("VINCE IL PLAYER BIANCO" );
						}
						lblNewLabel_10.setVisible(true);
					}
				}
			}
		});
		btnNewButton.setBounds(187, 232, 151, 23);
		frame.getContentPane().add(btnNewButton);

		frame.getContentPane().add(lblNewLabel_9);



	}

	//parse della mossa in input
	private static boolean isNumeric(String str){
		return str != null && str.matches("[0-7.]+");
	}
	private static int[] parseInput(String mossaInput) {
		int[] result = null;
		String[] mossaInputSplit = mossaInput.split("-");
		if(mossaInputSplit.length==2) {

			boolean verificaNumeroB = isNumeric(String.valueOf(mossaInputSplit[0].charAt(1)));
			boolean verificaNumeroC = isNumeric(String.valueOf(mossaInputSplit[1].charAt(1)));
			if(verificaNumeroB && verificaNumeroC) {
				char a = mossaInputSplit[0].charAt(0);
				char b = mossaInputSplit[0].charAt(1);
				char c = mossaInputSplit[1].charAt(0);
				char d = mossaInputSplit[1].charAt(1);

				switch(a) {
				case 'a': a = '0';break;
				case 'b': a = '1';break;
				case 'c': a = '2';break;
				case 'd': a = '3';break;
				case 'e': a = '4';break;
				case 'f': a = '5';break;
				case 'g': a = '6';break;
				case 'h': a = '7';break;
				default: a='8';
				}

				switch(c) {
				case 'a': c = '0';break;
				case 'b': c = '1';break;
				case 'c': c = '2';break;
				case 'd': c = '3';break;
				case 'e': c = '4';break;
				case 'f': c = '5';break;
				case 'g': c = '6';break;
				case 'h': c = '7';break;
				default: c='8';
				}

				if(a != '8' && c != '8') {
					result = new int[4];
					result[1]=Character.getNumericValue(a);
					result[0]=Character.getNumericValue(b);
					result[3]=Character.getNumericValue(c);
					result[2]=Character.getNumericValue(d);

				}
			}
		}

		return result;
	}
}
