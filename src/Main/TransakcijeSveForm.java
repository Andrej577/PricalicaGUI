package Main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.JScrollPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;
import java.awt.Button;

public class TransakcijeSveForm extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TransakcijeSveForm dialog = new TransakcijeSveForm();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TransakcijeSveForm() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		

		
		table = new JTable();
		DefaultTableModel model = new DefaultTableModel(
		    new String[] {"Transakcija ID","Korisnik ID","Korisnik Ime","Iznos","Datum Transakcije","Status ID"},
		    0
		) {
		    @Override public boolean isCellEditable(int r, int c) { return false; }
		};
		table.setModel(model);
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);

		// Ako ostaješ na null layout-u:
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 10, 416, 212);
		contentPanel.add(scrollPane);
		

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			JButton okButton = new JButton("Refresh");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					GetDataAndPopulateTable();
				}
			});
			
			JButton btnNewButton = new JButton("Prikaži profil");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TransakcijeDTO PrikazReda = getSelectedDTO();
					KorisnikDAL korisnikDAL = new KorisnikDAL();
					KorisnikDTO korisnikDTO = new KorisnikDTO();
					korisnikDTO = korisnikDAL.GetKorisnik(PrikazReda.korisnik_id);
					KorisnikForm Forma = new KorisnikForm (korisnikDTO);
					Forma.setVisible(true);
				}
			});
			buttonPane.add(btnNewButton);
			okButton.setActionCommand("Refresh");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			cancelButton.addActionListener(e -> dispose());
			buttonPane.add(cancelButton);
		}
		
		GetDataAndPopulateTable();
	}
	
	
    public void GetDataAndPopulateTable() {
        TransakcijeDAL dal = new TransakcijeDAL();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // očisti stare redove

        try {
            List<TransakcijeDTO> lista = dal.getTransakcijeAll();
            for (TransakcijeDTO dto : lista) {
                model.addRow(new Object[] {
                    dto.transakcija_id,
                    dto.korisnik_id,
                    dto.korisnik_ime,
                    dto.iznos,
                    dto.datumTransakcije,
                    dto.status_id
                });
            } 
            
            table.setRowSelectionInterval(0, 0);

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
    private TransakcijeDTO getSelectedDTO() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) return null; // ništa nije selektirano

        // Ako koristiš sortiranje -> prevedi u model-index
        int modelRow = table.convertRowIndexToModel(viewRow);

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        TransakcijeDTO dto = new TransakcijeDTO();
        dto.transakcija_id   = (int)    model.getValueAt(modelRow, 0);
        dto.korisnik_id      = (int)    model.getValueAt(modelRow, 1);
        dto.korisnik_ime     = (String) model.getValueAt(modelRow, 2);
        dto.iznos            = (BigDecimal) model.getValueAt(modelRow, 3);
        dto.datumTransakcije = (java.sql.Date) model.getValueAt(modelRow, 4);
        dto.status_id        = (String)    model.getValueAt(modelRow, 5);

        return dto;
    }

}
