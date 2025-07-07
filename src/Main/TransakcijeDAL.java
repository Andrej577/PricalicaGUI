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
                    dto.korisnik_id = rs.getInt("korisnik_id");
                    dto.iznos = rs.getBigDecimal("iznos");
                    dto.datumTransakcije = rs.getDate("datum_transakcije");
                    dto.status_id = rs.getInt("status_id");
                    transakcije.add(dto);
                }
            }
        }

        return transakcije;
    }
}
