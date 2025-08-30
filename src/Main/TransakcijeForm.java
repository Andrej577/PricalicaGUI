package Main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;


import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;

public class TransakcijeForm extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, int korisnik_id) {
		try {
			TransakcijeForm dialog = new TransakcijeForm(korisnik_id);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TransakcijeForm(int korisnik_id) {
        setTitle("Transakcije korisnika " + korisnik_id);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 450);
        getContentPane().setLayout(new BorderLayout(5,5));

        // Panel za tablicu
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5,5,5,5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Kreiraj tablicu i model
        table = new JTable();
        DefaultTableModel model = new DefaultTableModel(
            new String[] {"Transakcija ID","Korisnik ID","Iznos","Datum Transakcije","Status ID"},
            0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table.setModel(model);

        // Umotaj u JScrollPane da se prikažu headeri i scroll
        JScrollPane scroll = new JScrollPane(table);
        contentPanel.add(scroll, BorderLayout.CENTER);

        // Napuni podatke
        GetDataAndPopulateTable(korisnik_id);

        // Prilagodi veličinu i centriraj
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

	
    public void GetDataAndPopulateTable(int odabraniKorisnikId) {
        TransakcijeDAL dal = new TransakcijeDAL();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // očisti stare redove

        try {
            List<TransakcijeDTO> lista = dal.getTransakcijeByKorisnik(odabraniKorisnikId);
            for (TransakcijeDTO dto : lista) {
                model.addRow(new Object[] {
                    dto.transakcija_id,
                    dto.korisnik_ime,
                    dto.iznos,
                    dto.datumTransakcije,
                    dto.status_id
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Greška pri učitavanju transakcija:\n" + ex.getMessage(),
                "Greška",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
