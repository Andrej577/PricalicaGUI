package Forme;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import DTO.KorisnikDTO;
import java.sql.*;
import java.awt.Font;

public class KorisnikForm extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JTextField textField_2;
	private JTextField textField_3;
	private KorisnikDTO kor;

	public KorisnikForm(KorisnikDTO korNovi) {
		if (korNovi == null)
		{
			kor = new KorisnikDTO();
		}
		else
		{
			kor = korNovi;
		}
			
		
		setTitle("Unos novog korisnika");
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
		lblNewLabel_4.setBounds(10, 35, 46, 14);
		contentPanel.add(lblNewLabel_4);

		textField_2 = new JTextField();
		textField_2.setBounds(10, 60, 313, 20);
		contentPanel.add(textField_2);
		textField_2.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Prezime");
		lblNewLabel_5.setBounds(10, 91, 64, 14);
		contentPanel.add(lblNewLabel_5);

		textField_3 = new JTextField();
		textField_3.setBounds(10, 116, 313, 20);
		contentPanel.add(textField_3);
		textField_3.setColumns(10);

		JLabel lblNewLabel = new JLabel("Korisnik");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 10, 313, 20);
		contentPanel.add(lblNewLabel);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JButton btnNewButton = new JButton("Unesi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			    kor.Ime     = textField_2.getText();
			    kor.Prezime = textField_3.getText();
			    kor.EMail   = textField_1.getText();
			    kor.Lozinka = new String(passwordField.getPassword()); 

			    kor.Tip_id  = choice.getSelectedIndex() + 1;

			    kor.Status    = chckbxNewCheckBox.isSelected()   ? 1 : 0;
			    kor.Pretplata = chckbxNewCheckBox_1.isSelected();
			    
			    
				DAL.Korisnik korDAL = new DAL.Korisnik();
				try {
					korDAL.insertOrUpdateKorisnik(kor);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Pogreška prilikom unosta" + e1.getMessage());
					e1.printStackTrace();
				}
				
				dispose();
			}
		});
		buttonPane.add(btnNewButton);
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		// Popunjavanje forme ako se radi o UPDATE-u
		if (korNovi != null) {
			textField_2.setText(kor.Ime);
			textField_3.setText(kor.Prezime);
			textField_1.setText(kor.EMail);
			passwordField.setText(kor.Lozinka);
			choice.select(kor.Tip_id - 1);
			chckbxNewCheckBox.setSelected(kor.Status == 1);
			chckbxNewCheckBox_1.setSelected(kor.Pretplata);
			choice.setEnabled(false);
			chckbxNewCheckBox.setEnabled(false);
		}
	}
}
