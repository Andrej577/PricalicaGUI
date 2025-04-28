package Forme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.time.LocalDateTime;

public class KnjigaForm extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField tfNaslov;
    private JTextField tfAutorId;
    private JTextField tfZanrId;
    private JTextField tfTrajanje;
    private JTextArea taOpis;
    private JTextField tfPoveznica;

    // Glavni konstruktor koji prima roditeljski JFrame
    public KnjigaForm(Knjigetemp knjigetemp) {
        super(knjigetemp, "Unos nove knjige", true);
        initialize();
    }

    // Privatni konstruktor za testiranje (ako je potrebno)
    KnjigaForm() {
        initialize();
    }

    private void initialize() {
        setTitle("Unos nove knjige");
        setBounds(100, 100, 600, 500);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new GridLayout(0, 2, 10, 10));

        // Dodavanje komponenti
        contentPanel.add(new JLabel("Naslov knjige:"));
        tfNaslov = new JTextField();
        contentPanel.add(tfNaslov);

        contentPanel.add(new JLabel("ID autora:"));
        tfAutorId = new JTextField();
        contentPanel.add(tfAutorId);

        contentPanel.add(new JLabel("ID žanra:"));
        tfZanrId = new JTextField();
        contentPanel.add(tfZanrId);

        contentPanel.add(new JLabel("Trajanje (min):"));
        tfTrajanje = new JTextField();
        contentPanel.add(tfTrajanje);

        contentPanel.add(new JLabel("Opis:"));
        taOpis = new JTextArea(3, 20);
        JScrollPane scrollOpis = new JScrollPane(taOpis);
        contentPanel.add(scrollOpis);

        contentPanel.add(new JLabel("Poveznica:"));
        tfPoveznica = new JTextField();
        contentPanel.add(tfPoveznica);

        // Panel za dugmad
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("Unesi");
        okButton.addActionListener(this::unesiKnjigu);
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Odustani");
        cancelButton.addActionListener(e -> dispose());
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }

    private void unesiKnjigu(ActionEvent e) {
        try {
            String naslov = tfNaslov.getText().trim();
            int autorId = Integer.parseInt(tfAutorId.getText().trim());
            int zanrId = Integer.parseInt(tfZanrId.getText().trim());
            int trajanje = Integer.parseInt(tfTrajanje.getText().trim());
            String opis = taOpis.getText().trim();
            String poveznica = tfPoveznica.getText().trim();

            if (naslov.isEmpty() || opis.isEmpty() || poveznica.isEmpty()) {
                showError("Molimo popunite sva obavezna polja (naslov, opis, poveznica)!");
                return;
            }

            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://ucka.veleri.hr:3306/jbanusic", "jbanusic", "11");
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO knjige (naslov, autor_id, zanr_id, trajanje_min, opis, datum_dodavanja, poveznica, status_id) VALUES (?, ?, ?, ?, ?, ?, ?, 1)")) {
                
                stmt.setString(1, naslov);
                stmt.setInt(2, autorId);
                stmt.setInt(3, zanrId);
                stmt.setInt(4, trajanje);
                stmt.setString(5, opis);
                stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                stmt.setString(7, poveznica);
                
                if (stmt.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Knjiga uspješno unesena!", "Uspjeh", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        } catch (NumberFormatException ex) {
            showError("ID autora, ID žanra i trajanje moraju biti brojevi!");
        } catch (SQLException ex) {
            showError("Greška pri unosu u bazu: " + ex.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Greška", JOptionPane.ERROR_MESSAGE);
    }

    // Metoda main ostaje ista za testiranje
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                KnjigaForm dialog = new KnjigaForm();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}