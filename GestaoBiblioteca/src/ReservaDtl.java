import java.time.LocalDateTime;
import java.util.List;

public class ReservaDtl extends Reserva {
    private int idDetalhe;
    /**
     * Construtor para criar uma reserva.
     *
     * @param numMovimento  O número do movimento.
     * @param codBiblioteca O código da biblioteca.
     * @param dataInicio    A data de início do movimento.
     * @param dataFim       A data limite da reserva.
     * @param clientes      O cliente associado ao movimento.
     * @param livros        A lista de livros associados ao movimento.
     * @param jornais       A lista de jornais associados ao movimento.
     * @param revistas      A lista de revistas associadas ao movimento.
     * @param dataRegisto   A data de registo da reserva.
     * @param nif           O NIF do cliente.
     * @param isbn          O ISBN do livro.
     */
    public ReservaDtl(int idDetalhe, int numMovimento, int codBiblioteca, LocalDateTime dataInicio, LocalDateTime dataFim, List<Cliente> clientes, List<Livro> livros, List<Jornal> jornais, List<Revista> revistas, LocalDateTime dataRegisto, int nif, String isbn) {
        super(codBiblioteca, numMovimento, dataInicio, dataFim, clientes, livros, jornais, revistas, dataRegisto, nif, isbn);

        this.idDetalhe = idDetalhe;
    }

    public int getIdDetalhe() {
        return idDetalhe;
    }

    public void setIdDetalhe(int idDetalhe) {
        this.idDetalhe = idDetalhe;
    }
}
