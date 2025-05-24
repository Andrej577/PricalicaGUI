package Forme;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DTO.KorisnikDTO;

import javax.swing.JLabel;
import java.awt.Font;

public class KorisniciForm extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;

	public static void main(String[] args) {
		try {
			KorisniciForm dialog = new KorisniciForm();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public KorisniciForm() {
		setTitle("Popis korisnika");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				GetKorisnici();
			}
		});
		
		setBounds(100, 100, 860, 532);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// Dugme za dodavanje korisnika
		JButton btnNewButton = new JButton("Dodaj korisnika");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				KorisnikDTO kor = null;
				
				KorisnikForm korDialog = new KorisnikForm(kor);
				korDialog.setModal(true);
				korDialog.setVisible(true);

				// Nakon zatvaranja "Korisnik" dijaloga, osveži 
				GetKorisnici();
			}
		});
		btnNewButton.setBounds(702, 11, 132, 23);
		contentPanel.add(btnNewButton);

		// Tablica unutar ScrollPane-a
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(20, 61, 814, 388);
		contentPanel.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("Korisnici");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(20, 15, 132, 19);
		contentPanel.add(lblNewLabel);

		// Donji panel sa OK/Cancel
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> dispose());
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	public void GetKorisnici() {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://ucka.veleri.hr:3306/jbanusic", "jbanusic", "11");

			String query = "SELECT * FROM korisnici";
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			// Pravimo needitabilan model
			DefaultTableModel model = new DefaultTableModel() {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false; // sve ćelije su needitabilne
				}
			};
			table.setModel(model);

			// Postavljanje kolona
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			for (int column = 1; column <= columnCount; column++) {
				model.addColumn(metaData.getColumnName(column));
			}

			// Dodavanje redova
			while (rs.next()) {
				Object[] rowData = new Object[columnCount];
				for (int i = 0; i < columnCount; i++) {
					rowData[i] = rs.getObject(i + 1);
				}
				model.addRow(rowData);
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
