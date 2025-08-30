package Main;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

public class TransakcijeDTO {
	public int transakcija_id;
	public int korisnik_id;
	public String korisnik_ime;
    public BigDecimal iznos;
    public Date datumTransakcije;
	public String status_id;
}
