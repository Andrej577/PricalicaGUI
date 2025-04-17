package Forme;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainForm {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
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
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnKorisnici = new JButton("Korisnici");
		btnKorisnici.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KorisniciForm korisniciForma = new KorisniciForm();
				korisniciForma.setVisible(true);
			}
		});
		btnKorisnici.setBounds(172, 70, 85, 21);
		frame.getContentPane().add(btnKorisnici);
		
		JButton btnKnjige = new JButton("Knjige");
		btnKnjige.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KnjigeForm knjigeForma = new KnjigeForm();
				knjigeForma.setVisible(true);
			}
		});
		btnKnjige.setBounds(172, 151, 85, 21);
		frame.getContentPane().add(btnKnjige);
	}
	
	public void show() 
	{
	    frame.setVisible(true);
	}
}
