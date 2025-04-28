package Forme;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JList;

public class Knjigetemp extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JList<String> knjigeList;
    private DefaultListModel<String> listModel;

    public static void main(String[] args) {
        try {
            Knjigetemp dialog = new Knjigetemp();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Knjigetemp() {
        setBounds(100, 100, 731, 450);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        listModel = new DefaultListModel<>();
        knjigeList = new JList<>(listModel);
        knjigeList.setBounds(10, 11, 695, 356);
        contentPanel.add(knjigeList);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton UnosKnjige = new JButton("Unesi Knjigu");
        UnosKnjige.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KnjigaForm dialog = new KnjigaForm();
                dialog.setVisible(true);
                ucitajKnjigeIzBaze(); // osvježi listu
            }
        });
        buttonPane.add(UnosKnjige);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // zatvori dijalog
            }
        });
        buttonPane.add(cancelButton);

        // Učitaj knjige odmah na otvaranju
        ucitajKnjigeIzBaze();
    }

    private void ucitajKnjigeIzBaze() {
        listModel.clear();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://ucka.veleri.hr:3306/jbanusic", "jbanusic", "11")) {

            String sql = "SELECT naslov FROM knjige ORDER BY naslov";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    listModel.addElement(rs.getString("naslov"));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Greška pri učitavanju knjiga: " + ex.getMessage(),
                    "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
