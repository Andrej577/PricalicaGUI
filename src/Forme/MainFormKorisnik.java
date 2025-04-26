package Forme;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DTO.KorisnikDTO;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class MainFormKorisnik extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, KorisnikDTO kor) {
		try {
			MainFormKorisnik dialog = new MainFormKorisnik(kor);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MainFormKorisnik(KorisnikDTO kor) {
		setTitle("Glavni izbornik - korisnik");
		setBounds(100, 100, 550, 494);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Glavni izbornik");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblNewLabel.setBounds(20, 11, 138, 30);
			contentPanel.add(lblNewLabel);
		}
		{
			JButton btnNewButton = new JButton("Pregled knjiga");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					KnjigeForm knjigeForma = new KnjigeForm();
					knjigeForma.setVisible(true);
				}
			});
			btnNewButton.setBounds(20, 80, 169, 30);
			contentPanel.add(btnNewButton);
		}
		{
			JLabel lblNewLabel_1 = new JLabel(kor.Ime + " " + kor.Prezime);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
			lblNewLabel_1.setBounds(312, 21, 212, 14);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JButton btnNewButton_1 = new JButton("Pregled recenzija");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					RecenzijeForm recenzijeForma = new RecenzijeForm();
					recenzijeForma.setVisible(true);
				}
			});
			btnNewButton_1.setBounds(20, 134, 169, 30);
			contentPanel.add(btnNewButton_1);
		}
		{
			JButton btnNewButton_2 = new JButton("Pregled transakcija");
			btnNewButton_2.setBounds(20, 190, 169, 30);
			contentPanel.add(btnNewButton_2);
		}
		{
			JButton btnNewButton_3 = new JButton("Moj profil");
			btnNewButton_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DAL.Korisnik korisnikDAL = new DAL.Korisnik();
					KorisnikDTO kor = korisnikDAL.GetKorisnik();
					
					KorisnikForm korisnikForma = new KorisnikForm(kor);
					korisnikForma.setVisible(true);
				}
			});
			btnNewButton_3.setBounds(20, 414, 169, 30);
			contentPanel.add(btnNewButton_3);
		}
		{
			JButton btnNewButton_4 = new JButton("Izlaz");
			btnNewButton_4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			btnNewButton_4.setBounds(415, 418, 109, 23);
			contentPanel.add(btnNewButton_4);
		}
	}

}
