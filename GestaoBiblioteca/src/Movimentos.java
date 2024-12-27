import java.time.LocalDateTime;
import java.util.List;

/** Classe base que representa um movimento na biblioteca (empréstimo ou reserva).
 * Fornece propriedades e métodos comuns para as classes derivadas.
 * @author ER_GRUPO_5
 * @since 2024
 */
public class Movimentos {
    private int numMovimento;
    private int codBiblioteca;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private List<Cliente> clientes;
    private List<Livro> livros;
    private List<Jornal> jornais;
    private List<Revista> revistas;

    /**
     * Construtor para criar um movimento.
     *
     * @param numMovimento O número do movimento.
     * @param codBiblioteca O código da biblioteca.
     * @param dataInicio A data de início do movimento.
     * @param dataFim A data de fim do movimento.
     * @param clientes O cliente associado ao movimento.
     * @param livros A lista de livros associados ao movimento.
     * @param jornais A lista de jornais associados ao movimento.
     * @param revistas A lista de revistas associadas ao movimento.
     */
    public Movimentos(int numMovimento, int codBiblioteca, LocalDateTime dataInicio, LocalDateTime dataFim,List<Cliente> clientes, List<Livro> livros, List<Jornal> jornais, List<Revista> revistas) {
        this.numMovimento = numMovimento;
        this.codBiblioteca = codBiblioteca;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.clientes = clientes;
        this.livros = livros;
        this.jornais = jornais;
        this.revistas = revistas;
    }

    /**
     * Obtém o número do movimento.
     *
     * @return O número do movimento.
     */
    public int getNumMovimento() {
        return numMovimento;
    }

    /**
     * Define o número do movimento.
     *
     * @param numMovimento O número do movimento.
     */
    public void setNumMovimento(int numMovimento) {
        this.numMovimento = numMovimento;
    }

    /**
     * Obtém o código da biblioteca.
     *
     * @return O código da biblioteca.
     */
    public int getCodBiblioteca() {
        return codBiblioteca;
    }

    /**
     * Define o código da biblioteca.
     *
     * @param codBiblioteca O código da biblioteca.
     */
    public void setCodBiblioteca(int codBiblioteca) {
        this.codBiblioteca = codBiblioteca;
    }

    /**
     * Obtém a data de início do movimento.
     *
     * @return A data de início do movimento.
     */
    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    /**
     * Define a data de início do movimento.
     *
     * @param dataInicio A data de início do movimento.
     */
    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Obtém a data de início do movimento.
     *
     * @return A data de início do movimento.
     */
    public LocalDateTime getDataFim() {
        return dataFim;
    }

    /**
     * Define a data de início do movimento.
     *
     * @param dataFim A data de início do movimento.
     */
    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Obtém o cliente associado ao movimento.
     *
     * @return O cliente associado ao movimento.
     */
    public List<Cliente> getCliente() {
        return clientes;
    }

    /**
     * Define o cliente associado ao movimento.
     *
     * @param clientes O cliente associado ao movimento.
     */
    public void setCliente(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    /**
     * Obtém a lista de livros associados ao movimento.
     *
     * @return A lista de livros associados ao movimento.
     */
    public List<Livro> getLivros() {
        return livros;
    }

    /**
     * Define a lista de livros associados ao movimento.
     *
     * @param livros A lista de livros associados ao movimento.
     */
    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    /**
     * Obtém a lista de jornais associados ao movimento.
     *
     * @return A lista de jornais associados ao movimento.
     */
    public List<Jornal> getJornais() {
        return jornais;
    }

    /**
     * Define a lista de jornais associados ao movimento.
     *
     * @param jornais A lista de jornais associados ao movimento.
     */
    public void setJornais(List<Jornal> jornais) {
        this.jornais = jornais;
    }

    /**
     * Obtém a lista de revistas associadas ao movimento.
     *
     * @return A lista de revistas associadas ao movimento.
     */
    public List<Revista> getRevistas() {
        return revistas;
    }

    /**
     * Define a lista de revistas associadas ao movimento.
     *
     * @param revistas A lista de revistas associadas ao movimento.
     */
    public void setRevistas(List<Revista> revistas) {
        this.revistas = revistas;
    }
}
