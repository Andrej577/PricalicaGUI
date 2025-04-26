package Forme;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

	public KorisnikForm(KorisnikDTO kor) {
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

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
					Connection conn = DriverManager.getConnection(
							"jdbc:mysql://ucka.veleri.hr:3306/jbanusic", "jbanusic", "11");

					String ime = textField_2.getText();
					String prezime = textField_3.getText();
					String email = textField_1.getText();
					String lozinka = new String(passwordField.getPassword());
					int tipId = choice.getSelectedIndex() + 1;
					int status = chckbxNewCheckBox.isSelected() ? 1 : 0;
					boolean pretplata = chckbxNewCheckBox_1.isSelected();

					if (kor != null && kor.korisnikId != 0) {
						PreparedStatement stmt = conn.prepareStatement(
								"UPDATE korisnici SET ime=?, prezime=?, email=?, lozinka_hash=?, tip_id=?, status_id=?, ima_pretplatu=? WHERE korisnik_id=?");
						stmt.setString(1, ime);
						stmt.setString(2, prezime);
						stmt.setString(3, email);
						stmt.setString(4, lozinka);
						stmt.setInt(5, tipId);
						stmt.setInt(6, status);
						stmt.setBoolean(7, pretplata);
						stmt.setInt(8, kor.korisnikId);
						stmt.executeUpdate();
					} else {
						PreparedStatement stmt = conn.prepareStatement(
								"INSERT INTO korisnici (ime,prezime,email,lozinka_hash,tip_id,status_id,ima_pretplatu) VALUES(?,?,?,?,?,?,?)");
						stmt.setString(1, ime);
						stmt.setString(2, prezime);
						stmt.setString(3, email);
						stmt.setString(4, lozinka);
						stmt.setInt(5, tipId);
						stmt.setInt(6, status);
						stmt.setBoolean(7, pretplata);
						stmt.executeUpdate();
					}

					conn.close();
					dispose();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.toString());
					ex.printStackTrace();
				}
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		// Popunjavanje forme ako se radi o UPDATE-u
		if (kor != null) {
			textField_2.setText(kor.Ime);
			textField_3.setText(kor.Prezime);
			textField_1.setText(kor.EMail);
			passwordField.setText(kor.Lozinka);
			choice.select(kor.Tip_id - 1);
			chckbxNewCheckBox.setSelected(kor.Status == 1);
			chckbxNewCheckBox_1.setSelected(kor.Pretplata);
		}
	}
}
