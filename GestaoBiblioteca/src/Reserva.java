import java.time.LocalDateTime;
import java.util.List;

/** Representa as Reservas
 * @author ER_GRUPO_5
 * @since 2024
 */
public class Reserva extends Movimentos {
    private LocalDateTime dataRegisto;
    private int nif;
    private String isbn;

    /**
     * Construtor para criar uma reserva.
     *
     * @param numMovimento O número do movimento.
     * @param codBiblioteca O código da biblioteca.
     * @param dataInicio A data de início do movimento.
     * @param dataFim A data limite da reserva.
     * @param cliente O cliente associado ao movimento.
     * @param livros A lista de livros associados ao movimento.
     * @param jornais A lista de jornais associados ao movimento.
     * @param revistas A lista de revistas associadas ao movimento.
     * @param dataRegisto A data de registo da reserva.
     * @param nif O NIF do cliente.
     * @param isbn O ISBN do livro.
     */
    public Reserva(int numMovimento, int codBiblioteca, LocalDateTime dataInicio, LocalDateTime dataFim,
                   Cliente cliente, List<Livro> livros, List<Jornal> jornais, List<Revista> revistas,
                   LocalDateTime dataRegisto, int nif, String isbn) {
        super(numMovimento, codBiblioteca, dataInicio, dataFim, cliente, livros, jornais, revistas);
        this.dataRegisto = dataRegisto;
        this.nif = nif;
        this.isbn = isbn;
    }

    /**
     * Obtém o NIF do cliente.
     *
     * @return O NIF do cliente.
     */
    public int getNif() {
        return nif;
    }

    /**
     * Define o NIF do cliente.
     *
     * @param nif O NIF do cliente.
     */
    public void setNif(int nif) {
        this.nif = nif;
    }

    /**
     * Obtém o ISBN do livro.
     *
     * @return O ISBN do livro.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Define o ISBN do livro.
     *
     * @param isbn O ISBN do livro.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}