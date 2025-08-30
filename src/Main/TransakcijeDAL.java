package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransakcijeDAL {

    private static final String URL = "jdbc:mysql://ucka.veleri.hr:3306/jbanusic";
    private static final String USER = "jbanusic";
    private static final String PASSWORD = "11";

    public List<TransakcijeDTO> getTransakcijeByKorisnik(int korisnikId) throws SQLException {
    	
        String query = "SELECT transakcija_id, korisnik_id, iznos, datum_transakcije, status_id " +
                       "FROM transakcije WHERE korisnik_id = ?";
        
        List<TransakcijeDTO> transakcije = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, korisnikId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TransakcijeDTO dto = new TransakcijeDTO();
                    dto.transakcija_id = rs.getInt("transakcija_id");
                    dto.korisnik_ime = rs.getString("ime");
                    dto.iznos = rs.getBigDecimal("iznos");
                    dto.datumTransakcije = rs.getDate("datum_transakcije");
                    if (rs.getInt("status_id") == 2)
                    {
                    	dto.status_id = "Platio";
                    }
                    else if (rs.getInt("status_id") == 2)
                    {
                    	dto.status_id = "Nije platio";
                    };
                 
                    transakcije.add(dto);
                }
            }
        }

        return transakcije;
    }
    
    public List <TransakcijeDTO> getTransakcijeAll () throws SQLException{
    	String query = "SELECT \n"
    			+ "	t.transakcija_id,\n"
    			+ "	t.korisnik_id,\n"
    			+ "	kor.ime as ime,\n"
    			+ "	t.iznos,\n"
    			+ "	t.datum_transakcije,\n"
    			+ "	t.status_id \n"
    			+ "FROM transakcije t\n"
    			+ "JOIN korisnici kor ON t.korisnik_id = kor.korisnik_id ";
    	
    	List<TransakcijeDTO> transakcije = new ArrayList<>();
    	
    	try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {

               
               try (ResultSet rs = stmt.executeQuery()) {
                   while (rs.next()) {
                       TransakcijeDTO dto = new TransakcijeDTO();
                       dto.transakcija_id = rs.getInt("transakcija_id");
                       dto.korisnik_id = rs.getInt("korisnik_id");
                       dto.korisnik_ime = rs.getString("ime");
                       dto.iznos = rs.getBigDecimal("iznos");
                       dto.datumTransakcije = rs.getDate("datum_transakcije");
                       if (rs.getInt("status_id") == 2)
                       {
                       		dto.status_id = "Platio";
                       }
                       else if (rs.getInt("status_id") == 1)
                       {
                       		dto.status_id = "Nije platio";
                       };
                    
                       transakcije.add(dto);
                   }
               }
           }

           return transakcije;    
    }
}
