import java.time.LocalDate;

/**
 * Classe base que representa um movimento na biblioteca (empréstimo ou reserva).
 * Fornece propriedades e métodos comuns para as classes derivadas.
 *
 * @author ER_GRUPO_5
 * @since 2024
 */
public class Movimentos {
    private int numMovimento;
    private int codBiblioteca;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Cliente cliente;
    /**
     * Instancia um novo Movimentos.
     *
     * @param numMovimento  o número do movimento
     * @param codBiblioteca o código da biblioteca
     * @param dataInicio    a data de início
     * @param dataFim       a data de fim
     * @param cliente       o cliente afeto ao movimento
     */
    public Movimentos(int numMovimento, int codBiblioteca, LocalDate dataInicio, LocalDate dataFim, Cliente cliente) {
        this.numMovimento = numMovimento;
        this.codBiblioteca = codBiblioteca;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.cliente = cliente;
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
    public LocalDate getDataInicio() {
        return dataInicio;
    }

    /**
     * Define a data de início do movimento.
     *
     * @param dataInicio A data de início do movimento.
     */
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Obtém a data de início do movimento.
     *
     * @return A data de início do movimento.
     */
    public LocalDate getDataFim() {
        return dataFim;
    }

    /**
     * Define a data de início do movimento.
     *
     * @param dataFim A data de início do movimento.
     */
    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Obtém o cliente associado à reserva.
     *
     * @return O cliente associado à reserva.
     */
    public Cliente getCliente() {
        return cliente;
    }

    public int getClienteId() {
        return cliente.getId();
    }

    public String getClienteNome() {
        return cliente.getNome();
    }
    /**
     * Define o cliente associado à reserva.
     *
     * @param cliente O cliente associado à reserva.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
