package Forme;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.Font;

public class KnjigeZanrForm extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JList<String> list;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    KnjigeZanrForm frame = new KnjigeZanrForm();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public KnjigeZanrForm() {
    	setTitle("Žanrovi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 540, 527);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Žanrovi");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel.setBounds(25, 11, 478, 26);
        contentPane.add(lblNewLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(45, 98, 429, 338);
        contentPane.add(scrollPane);

        list = new JList<>();
        list.setBounds(45, 99, 429, 336);
        contentPane.add(list);

        ucitajZanrove();
    }

    private void ucitajZanrove() {
        Vector<String> zanrovi = new Vector<>();

        String url = "jdbc:mysql://ucka.veleri.hr:3306/jbanusic"; // provjeri ime baze!
        String user = "jbanusic";
        String password = "11";

        try (
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT naziv FROM zanrovi");
        ) {
            while (rs.next()) {
                zanrovi.add(rs.getString("naziv"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        list.setListData(zanrovi);
    }
}


