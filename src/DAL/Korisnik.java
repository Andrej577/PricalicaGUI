package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
				kor.korisnikId = rs.getInt("korisnik_id");
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
	
	public void insertOrUpdateKorisnik(KorisnikDTO kor) throws SQLException {
	    // First check for existence
	    boolean exists;
	    String checkSql = "SELECT 1 FROM korisnici WHERE korisnik_id = ?";
	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr:3306/jbanusic", "jbanusic", "11");
	         PreparedStatement check = conn.prepareStatement(checkSql)) {
	        check.setInt(1, kor.korisnikId);
	        try (ResultSet rs = check.executeQuery()) {
	            exists = rs.next();
	        }
	    }
	    
	    if (exists)
	    {
	    	String sql = "UPDATE korisnici SET ime=?, prezime=?, lozinka_hash=?, tip_id=?, status_id=?, ima_pretplatu=? "
		  	          + "WHERE korisnik_id=?";
			    try (Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr:3306/jbanusic", "jbanusic", "11");
				         PreparedStatement stmt = conn.prepareStatement(sql)) {
				        stmt.setString(1, kor.Ime);
				        stmt.setString(2, kor.Prezime);
				        stmt.setString(3, kor.Lozinka);
				        stmt.setInt   (4, kor.Tip_id);
				        stmt.setInt   (5, kor.Status);
				        stmt.setBoolean(6, kor.Pretplata);
				        if (exists) {
				            stmt.setInt(7, kor.korisnikId);
				        }
				        stmt.executeUpdate();
				    }
	    }
	    else
	    {
	    	String sql = "INSERT INTO korisnici(ime, prezime, email, lozinka_hash, tip_id, status_id, ima_pretplatu) "
	  	          + "VALUES(?,?,?,?,?,?,?)";
		    try (Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr:3306/jbanusic", "jbanusic", "11");
			         PreparedStatement stmt = conn.prepareStatement(sql)) {
			        stmt.setString(1, kor.Ime);
			        stmt.setString(2, kor.Prezime);
			        stmt.setString(3, kor.EMail);
			        stmt.setString(4, kor.Lozinka);
			        stmt.setInt   (5, kor.Tip_id);
			        stmt.setInt   (6, kor.Status);
			        stmt.setBoolean(7, kor.Pretplata);
			        if (exists) {
			            stmt.setInt(8, kor.korisnikId);
			        }
			        stmt.executeUpdate();
			    }
	    }
	}

}
