package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import DTO.KorisnikDTO;

public class Login {
    public KorisnikDTO CheckUser(String emial, String password)
	{
		KorisnikDTO kor = new KorisnikDTO();
		try
		{
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://ucka.veleri.hr:3306/jbanusic", "jbanusic", "11");

			String query = "SELECT ime, prezime, email, lozinka_hash, tip_id, status_id, ima_pretplatu " +
			"FROM korisnici WHERE email = ? AND lozinka_hash = ?";

			PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, emial);
            stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) { // ako postoji rezultat
				kor.Ime = rs.getString("ime");
				kor.Prezime = rs.getString("prezime");
				kor.EMail = rs.getString("email");
				kor.Lozinka = rs.getString("lozinka_hash");
				kor.Tip_id = rs.getInt("tip_id");
				kor.Status = rs.getInt("status_id");
				kor.Pretplata = rs.getBoolean("ima_pretplatu");
			}
			
			return kor;
		}
		catch(Exception ex) 
		{
			JOptionPane.showMessageDialog(null, ex.toString());
			ex.printStackTrace();
			return null;
		}
    }
}
