package Forme;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import DTO.KorisnikDTO;

public class LoginForm {

	private JFrame frmPrijava;
	private JPasswordField passwordField;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm window = new LoginForm();
					window.frmPrijava.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginForm() {
		initialize();
	}
	public void Login()
	{
		// ADMIN PRIMJER 
		// maja.peric@example.com
		// Maja*Secure5

		// KORISNIK PRIMJER 
		// sara.juric@example.com
		// SaraLove44
		DAL.Login loginDAL = new DAL.Login();

		String email = textField.getText();
		char[] passwordChars = passwordField.getPassword();
		String password = new String(passwordChars);

		KorisnikDTO kor = loginDAL.CheckUser(email, password);
		if (kor == null || email=="" || password=="")
		{
			JOptionPane.showMessageDialog(null, "Neispravan email ili lozinka.");
			return;
		}
		if (kor.Tip_id == 1)
		{
			frmPrijava.dispose();
			MainFormAdministrator main = new MainFormAdministrator();
			main.show();
		}
		else 
		{
			frmPrijava.dispose();
			MainFormKorisnik main = new MainFormKorisnik(kor);
			main.setVisible(true);
		}
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPrijava = new JFrame();
		frmPrijava.setTitle("Prijava");
		frmPrijava.setBounds(100, 100, 606, 403);
		frmPrijava.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPrijava.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Korisničko ime");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(100, 127, 124, 44);
		frmPrijava.getContentPane().add(lblNewLabel);
		
		JLabel lblLozinka = new JLabel("Lozinka");
		lblLozinka.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLozinka.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLozinka.setBounds(100, 174, 124, 39);
		frmPrijava.getContentPane().add(lblLozinka);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(234, 183, 163, 23);
		frmPrijava.getContentPane().add(passwordField);
		
		textField = new JTextField();
		textField.setBounds(234, 139, 163, 23);
		frmPrijava.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Prijava");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login();
			}
		});
		btnNewButton.setBounds(250, 286, 105, 30);
		frmPrijava.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Prijava u aplikaciju");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(34, 33, 163, 44);
		frmPrijava.getContentPane().add(lblNewLabel_1);
	}
}
