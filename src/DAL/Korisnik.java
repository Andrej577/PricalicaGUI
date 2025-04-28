package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DTO.KorisnikDTO;

public class Korisnik {
	public KorisnikDTO GetKorisnik()
	{
		try
		{
			KorisnikDTO kor = new KorisnikDTO();
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://ucka.veleri.hr:3306/jbanusic", "jbanusic", "11");

			String query = "SELECT * FROM korisnici WHERE korisnik_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, 13);
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
			ex.printStackTrace();
			return null;
		}
	}
}
