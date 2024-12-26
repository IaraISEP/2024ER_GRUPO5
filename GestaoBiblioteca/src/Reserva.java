import java.util.Date;
/** Representa as Reservas
 * @author ER_GRUPO_5
 * @since 2024
 */
public class Reserva extends Movimentos{

    private Date dataRegisto;
    private Date dataInicio;
    private Date dataFim;
    private int nif;
    private String isbn;

    public Reserva () {
        super();
    }

    public Reserva (int nif, String isbn){
        this.nif = nif;
        this.isbn = isbn;
    }

    public int getNif() {
        return nif;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }
}
