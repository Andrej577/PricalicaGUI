package Forme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class knjige extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JList<String> knjigeList;
    private DefaultListModel<String> listModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    knjige frame = new knjige();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public knjige() {
        setTitle("Popis knjiga");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 10));

        // Naslov
        JLabel lblNaslov = new JLabel("Popis knjiga:");
        lblNaslov.setFont(new Font("Tahoma", Font.BOLD, 14));
        contentPane.add(lblNaslov, BorderLayout.NORTH);

        // Lista knjiga
        listModel = new DefaultListModel<>();
        knjigeList = new JList<>(listModel);
        knjigeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(knjigeList);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Panel za gumbe
        JPanel panelGumbi = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        contentPane.add(panelGumbi, BorderLayout.SOUTH);

        // Gumb za unos nove knjige
        JButton btnUnosKnjige = new JButton("Unos nove knjige");
        btnUnosKnjige.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UnsoKnjiga dialog = new UnsoKnjiga(knjige.this);
                dialog.setVisible(true);
                // Nakon zatvaranja dijaloga osvježi listu
                ucitajKnjigeIzBaze();
            }
        });
        panelGumbi.add(btnUnosKnjige);

        // Gumb za osvježavanje
        JButton btnOsvjezi = new JButton("Osvježi");
        btnOsvjezi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ucitajKnjigeIzBaze();
            }
        });
        panelGumbi.add(btnOsvjezi);

        // Učitaj knjige prilikom pokretanja
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