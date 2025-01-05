import java.time.LocalDate;
import java.util.List;

/**
 * Representa as Reservas
 *
 * @author ER_GRUPO_5
 * @since 2024
 */
public class Reserva extends Movimentos {
    private List<ReservaLinha> reservaLinha;

    /**
     * Construtor para criar uma Reserva.
     *
     * @param codBiblioteca o código da biblioteca
     * @param numMovimento  o número do movimento
     * @param dataInicio    a data de inicio da reserva
     * @param dataFim       a data de fim da reserva
     * @param cliente       o cliente associado à reserva
     * @param reservaLinha  a lista que contém todos os items associados à reserva
     */
    public Reserva(int codBiblioteca, int numMovimento, LocalDate dataInicio, LocalDate dataFim, Cliente cliente, List<ReservaLinha> reservaLinha) {
        super(codBiblioteca, numMovimento, dataInicio, dataFim, cliente);
        this.reservaLinha = reservaLinha;
    }

    /**
     * Obtém a lista dos items da reserva
     *
     * @return A lista dos items da reserva
     */
    public List<ReservaLinha> getReservaLinha() {
        return reservaLinha;
    }

    /**
     * Define a lista com os items da reserva
     *
     * @param reservaLinha A lista com os items da reserva
     */
    public void setReservaLinha(List<ReservaLinha> reservaLinha) {
        this.reservaLinha = reservaLinha;
    }
}