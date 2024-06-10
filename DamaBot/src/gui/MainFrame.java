package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import utility.UtilityParameter;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup2 = new ButtonGroup();
	private JTextField textField;
	private String nomePlayer;
	private String modalita;
	private String connRobot;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JRadioButton rdbtnNewRadioButton = new JRadioButton(UtilityParameter.facile);
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(280, 62, 109, 23);
		contentPane.add(rdbtnNewRadioButton);

		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton(UtilityParameter.aggressiva);
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBounds(280, 88, 109, 23);
		contentPane.add(rdbtnNewRadioButton_1);

		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton(UtilityParameter.esperta);
		buttonGroup.add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton_2.setBounds(280, 114, 109, 23);
		contentPane.add(rdbtnNewRadioButton_2);

		JLabel lblNewLabel = new JLabel("Seleziona la difficoltà");
		lblNewLabel.setBounds(251, 41, 120, 14);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(10, 89, 144, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Inserisci il tuo Nickname");
		lblNewLabel_1.setBounds(20, 66, 132, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("ROBOT");
		lblNewLabel_2.setBounds(42, 118, 46, 14);
		contentPane.add(lblNewLabel_2);

		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("No");
		rdbtnNewRadioButton_3.setSelected(true);
		buttonGroup2.add(rdbtnNewRadioButton_3);
		rdbtnNewRadioButton_3.setBounds(6, 139, 58, 23);
		contentPane.add(rdbtnNewRadioButton_3);

		JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("Si");
		buttonGroup2.add(rdbtnNewRadioButton_4);
		rdbtnNewRadioButton_4.setBounds(90, 139, 46, 23);
		contentPane.add(rdbtnNewRadioButton_4);


		JButton btnNewButton = new JButton("AVANTI");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnNewRadioButton.setActionCommand(UtilityParameter.facile);
				rdbtnNewRadioButton_1.setActionCommand(UtilityParameter.aggressiva);
				rdbtnNewRadioButton_2.setActionCommand(UtilityParameter.esperta);
				rdbtnNewRadioButton_3.setActionCommand("No");
				rdbtnNewRadioButton_4.setActionCommand("Si");


				nomePlayer = textField.getText();
				if(buttonGroup.getSelection() != null) {
					modalita = buttonGroup.getSelection().getActionCommand();

					if(buttonGroup2.getSelection() != null) {
						connRobot = buttonGroup2.getSelection().getActionCommand();

						if(!modalita.isEmpty() && modalita != null && !nomePlayer.isEmpty() && nomePlayer!= null && !connRobot.isEmpty() && connRobot != null) {
							setVisible(false);
							PlayFrame p = new PlayFrame(null,null, null);
							p.playFrame(modalita,nomePlayer,connRobot);
						}
						else {

						}
					}
				}
			}
		});
		btnNewButton.setBounds(169, 191, 89, 23);
		contentPane.add(btnNewButton);
	}
}
