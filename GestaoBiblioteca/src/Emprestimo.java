import java.time.LocalDateTime;
import java.util.List;

/** Representa um Emprestimo
 * @author ER_GRUPO_5
 * @since 2024
 */
public class Emprestimo extends Movimentos{
    private LocalDateTime dataPrevFim;

    /**
     * Construtor para criar um empréstimo.
     *
     * @param numMovimento O número do movimento.
     * @param codBiblioteca O código da biblioteca.
     * @param dataInicio A data de início do empréstimo.
     * @param clientes O cliente associado ao empréstimo.
     * @param livros A lista de livros associados ao empréstimo (pode ser null).
     * @param jornais A lista de jornais associados ao empréstimo (pode ser null).
     * @param revistas A lista de revistas associadas ao empréstimo (pode ser null).
     * @param dataPrevFim A data prevista da devolução do item bibliotecário.
     * @param dataFim A data de devolução do item bibliotecário.
     */
    public Emprestimo(int numMovimento, int codBiblioteca, LocalDateTime dataInicio, LocalDateTime dataFim,
                      List<Cliente> clientes, List<Livro> livros, List<Jornal> jornais, List<Revista> revistas,
                      LocalDateTime dataPrevFim) {
        super(codBiblioteca, numMovimento, dataInicio, dataFim, clientes, livros, jornais, revistas);
        this.dataPrevFim = dataPrevFim;
    }

    /**
     * Obtém a data prevista da devolução.
     *
     * @return A data prevista da devolução.
     */
    public LocalDateTime getDataPrevFim() {
        return dataPrevFim;
    }

    /**
     * Define a data de devolução do empréstimo.
     *
     * @param dataPrevFim A data de devolução.
     */
    public void setDataPrevFim(LocalDateTime dataPrevFim) {
        this.dataPrevFim = dataPrevFim;
    }
}
