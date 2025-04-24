package Forme;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;




public class LoginForm {

	private JFrame frame;
	private JPasswordField passwordField;
	private JTextField textField;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm window = new LoginForm();
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
	public LoginForm() {
		initialize();
	}
	public void Login()
	{
		String username = textField.getText();
		
		char[] passwordChars = passwordField.getPassword();
		String password = new String(passwordChars);
		//String test1= User + " " + password;
		
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 606, 476);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("USERNAME");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setBounds(28, 83, 124, 54);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblLozinka = new JLabel("LOZINKA");
		lblLozinka.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblLozinka.setBounds(28, 170, 124, 54);
		frame.getContentPane().add(lblLozinka);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(174, 180, 163, 39);
		frame.getContentPane().add(passwordField);
		
		textField = new JTextField();
		textField.setBounds(174, 83, 163, 39);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
				frame.dispose();
		        MainForm main = new MainForm();
		        main.show();
=======
				KorisnikForm dialog = new KorisnikForm();
				dialog.setVisible(true);
				dialog.dispose();
				//Login();
>>>>>>> 269831ccd8cb950b7fe1ab7d1e3c8bea04a61d22
			}
		});
		btnNewButton.setBounds(248, 364, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		lblNewLabel_1 = new JLabel("Test");
		lblNewLabel_1.setBounds(394, 277, 132, 48);
		frame.getContentPane().add(lblNewLabel_1);
	}
}
