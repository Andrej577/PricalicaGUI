package Forme;


import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.sql.*;

public class Korisnik extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Korisnik dialog = new Korisnik();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Korisnik() {
		setBounds(100, 100, 348, 444);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("E-Mail");
		lblNewLabel_1.setBounds(10, 153, 46, 14);
		contentPanel.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 167, 313, 20);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Lozinka");
		lblNewLabel_2.setBounds(10, 209, 46, 14);
		contentPanel.add(lblNewLabel_2);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Aktivan");
		chckbxNewCheckBox.setBounds(10, 301, 97, 23);
		contentPanel.add(chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Ima pretplatu");
		chckbxNewCheckBox_1.setEnabled(false);
		chckbxNewCheckBox_1.setBounds(10, 327, 97, 23);
		contentPanel.add(chckbxNewCheckBox_1);
		
		JLabel lblNewLabel_3 = new JLabel("Status");
		lblNewLabel_3.setBounds(10, 255, 46, 14);
		contentPanel.add(lblNewLabel_3);
		
		Choice choice = new Choice();
		choice.add("Administrator");
		choice.add("Autor");
		choice.add("Korisnik");
		choice.setBounds(10, 275, 313, 20);
		contentPanel.add(choice);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 224, 313, 20);
		contentPanel.add(passwordField);
		
		JLabel lblNewLabel_4 = new JLabel("Ime");
		lblNewLabel_4.setBounds(10, 11, 46, 14);
		contentPanel.add(lblNewLabel_4);
		
		textField_2 = new JTextField();
		textField_2.setBounds(10, 29, 313, 20);
		contentPanel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Prezime");
		lblNewLabel_5.setBounds(10, 61, 46, 14);
		contentPanel.add(lblNewLabel_5);
		
		textField_3 = new JTextField();
		textField_3.setBounds(10, 76, 313, 20);
		contentPanel.add(textField_3);
		textField_3.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						DTO.KorisnikDTO kor = new DTO.KorisnikDTO();
						kor.Ime = textField_2.getText();
						kor.Prezime = textField_3.getText();
						//kor.KorisnickoIme = textField.getText();
						kor.EMail = textField_1.getText();
						
						char[] passwordChars = passwordField.getPassword();
						kor.Lozinka = new String(passwordChars);
						
						kor.Tip_id = choice.getSelectedIndex() + 1;
						kor.Status = chckbxNewCheckBox.isSelected() ? 1 : 0;
						
						kor.Pretplata = chckbxNewCheckBox_1.isSelected() ? true : false;
						
						try {
							Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
								Connection conn = DriverManager.
								getConnection("jdbc:mysql://ucka.veleri.hr:3306/jbanusic", "jbanusic", "11");
								PreparedStatement stmt = conn.
									prepareStatement("INSERT INTO korisnici (ime,prezime,email,"
									+ "lozinka_hash,tip_id,status_id,ima_pretplatu) VALUES(?,?,?,?,?,?,?)");
								stmt.setString(1, kor.Ime);
								stmt.setString(2, kor.Prezime);
								stmt.setString(3, kor.EMail);
								stmt.setString(4, kor.Lozinka);
								stmt.setInt(5, kor.Tip_id);
								stmt.setInt(6, kor.Status);
								stmt.setBoolean(7, kor.Pretplata);
								stmt.execute();
																
								conn.close();
								dispose();
						} catch (Exception ex) 
						{
							JOptionPane.showMessageDialog(null, ex.toString());
							System.out.println(ex.toString());
						}

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
