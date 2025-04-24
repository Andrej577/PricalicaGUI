package Forme;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JScrollPane;

public class RecenzijeForm extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RecenzijeForm dialog = new RecenzijeForm();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Create the dialog.
	 */
	public RecenzijeForm() {
		setBounds(100, 100, 820, 455);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Moje recenzije");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 11, 110, 27);
		contentPanel.add(lblNewLabel);
		
		table = new JTable();
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 65, 784, 340);
		contentPanel.add(scrollPane);
		

		scrollPane.setViewportView(table);
		
		GetRecenzije();
	}
	
	public void GetRecenzije() {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://ucka.veleri.hr:3306/jbanusic", "jbanusic", "11");

			String query = "SELECT * FROM interakcije WHERE korisnik_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, 13);
			ResultSet rs = stmt.executeQuery();

			DefaultTableModel model = new DefaultTableModel() {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			for (int column = 1; column <= columnCount; column++) {
				model.addColumn(metaData.getColumnName(column));
			}

			while (rs.next()) {
				Object[] rowData = new Object[columnCount];
				for (int i = 0; i < columnCount; i++) {
					rowData[i] = rs.getObject(i + 1);
				}
				model.addRow(rowData);
			}

			table.setModel(model);

			conn.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this,
				"Greška pri učitavanju recenzija: " + ex.getMessage(),
				"Greška", JOptionPane.ERROR_MESSAGE);
		}
	}
}
