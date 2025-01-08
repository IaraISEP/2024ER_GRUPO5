import java.time.LocalDate;

/** Representa um Empréstimo
 * @author ER_GRUPO_5
 * @since 2024
 */
    public class Emprestimo extends Movimentos{
    private LocalDate dataPrevFim;

    /**
     * Construtor para criar um empréstimo.
     *
     * @param numMovimento O número do movimento.
     * @param codBiblioteca O código da biblioteca.
     * @param dataInicio A data de início do empréstimo.
     * @param dataPrevFim A data prevista da devolução do item bibliotecário.
     * @param cliente o cliente associado ao empréstimo.
     */
    public Emprestimo(int codBiblioteca, int numMovimento, LocalDate dataInicio, LocalDate dataPrevFim, LocalDate dataFim, Cliente cliente, Constantes.Estado estado) {
        super(codBiblioteca, numMovimento, dataInicio, dataFim, cliente, estado);
        this.dataPrevFim = dataPrevFim;
    }

    /**
     * Obtém a data prevista da devolução.
     *
     * @return A data prevista da devolução.
     */
    public LocalDate getDataPrevFim() {
        return dataPrevFim;
    }

    /**
     * Define a data prevista de devolução do empréstimo.
     *
     * @param dataPrevFim A data de devolução.
     */
    public void setDataPrevFim(LocalDate dataPrevFim) {
        this.dataPrevFim = dataPrevFim;
    }
}
