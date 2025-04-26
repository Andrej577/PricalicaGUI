package Forme;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DTO.KorisnikDTO;

import java.awt.Font;

public class MainFormAdministrator {

	private JFrame frmAdministrator;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFormAdministrator window = new MainFormAdministrator();
					window.frmAdministrator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFormAdministrator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAdministrator = new JFrame();
		frmAdministrator.setTitle("Glavni izbornik - Administrator");
		frmAdministrator.setBounds(100, 100, 595, 414);
		frmAdministrator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAdministrator.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Glavni izbornik");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(20, 11, 115, 22);
		frmAdministrator.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 54, 253, 236);
		frmAdministrator.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnKorisnici = new JButton("Unos novog korisnika");
		btnKorisnici.setBounds(10, 58, 198, 38);
		panel.add(btnKorisnici);
		
		JLabel lblNewLabel_1 = new JLabel("Korisnici");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 11, 89, 25);
		panel.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Pregled korisnika");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KorisniciForm korisniciForma = new KorisniciForm();
				korisniciForma.setVisible(true);
			}
		});
		btnNewButton.setBounds(10, 123, 198, 38);
		panel.add(btnNewButton);
		btnKorisnici.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KorisnikDTO korisnik = null;
				KorisnikForm korisnikForma = new KorisnikForm(korisnik);
				korisnikForma.setVisible(true);
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(302, 54, 238, 236);
		frmAdministrator.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnKnjige = new JButton("Unos nove knjige");
		btnKnjige.setBounds(10, 58, 218, 36);
		panel_1.add(btnKnjige);
		
		JLabel lblNewLabel_2 = new JLabel("Knjige");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(10, 11, 92, 24);
		panel_1.add(lblNewLabel_2);
		
		JButton btnNewButton_1 = new JButton("Pregled knjiga");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KnjigeForm knjigeForma = new KnjigeForm();
				knjigeForma.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(10, 123, 218, 36);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Izlaz");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_2.setBounds(454, 345, 115, 30);
		frmAdministrator.getContentPane().add(btnNewButton_2);
		btnKnjige.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KnjigaForm knjigaForma = new KnjigaForm();
				knjigaForma.setVisible(true);
			}
		});
	}
	
	public void show() 
	{
	    frmAdministrator.setVisible(true);
	}
}
