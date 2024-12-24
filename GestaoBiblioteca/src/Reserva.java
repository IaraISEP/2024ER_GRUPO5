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

    public Reserva () {
        super();
    }

    public Reserva (int nif){
        this.nif = nif;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }
}
