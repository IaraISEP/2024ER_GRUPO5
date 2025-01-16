import java.io.IOException;
import java.util.List;

/**
 * A Biblioteca é o orquestrador da aplicação. Ela é responsável por armazenar os dados principais da biblioteca.
 * Tais como clientes, as suas reservas e/ou empréstimos, e os livros, jornais e revistas disponíveis.
 * @since 2014-12-26
 */
public class Biblioteca {
    /** Representa o nome da biblioteca */
    private String nome;
    /** Representa a morada da biblioteca */
    private Constantes.Morada morada;
    /** Representa o código da biblioteca */
    private int codBiblioteca;
    /** Representa a lista de Livros da respetiva biblioteca */
    private List<Livro> livro;
    /** Representa a lista de jornais da respetiva biblioteca */
    private List<JornalRevista> jornal;
    /** Representa a lista de revistas da respetiva biblioteca */
    private List<JornalRevista> revista;
    /** Representa a lista de clientes da respetiva biblioteca */
    private List<Cliente> cliente;
    /** Representa a lista de emprestimos da da biblioteca */
    private List<Emprestimo> emprestimo;
    /** Representa a lista de reservas da biblioteca */
    private List<Reserva> reserva;

    /**
     * Construtor da classe Biblioteca.
     *
     * @param nome          O nome da biblioteca.
     * @param morada        A morada da biblioteca.
     * @param codBiblioteca O código da biblioteca.
     *
     */
    public Biblioteca(String nome, Constantes.Morada morada, int codBiblioteca) {
        this.nome = nome;
        this.morada = morada;
        this.codBiblioteca = codBiblioteca;
    }

    /**
     * Obtém o nome da biblioteca.
     *
     * @return O nome da biblioteca.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da biblioteca.
     *
     * @param nome O nome da biblioteca.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a morada da biblioteca.
     *
     * @return A morada da biblioteca.
     */
    public Constantes.Morada getMorada() {
        return morada;
    }

    /**
     * Define a morada da biblioteca.
     *
     * @param morada A morada da biblioteca.
     */
    public void setMorada(Constantes.Morada morada) {
        this.morada = morada;
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
     * Obtém a lista de clientes da biblioteca.
     *
     * @return A lista de clientes.
     */
    public List<Cliente> getClientes() {
        return cliente;
    }

    /**
     * Define a lista de clientes da biblioteca.
     *
     * @param cliente A lista de clientes.
     */
    public void setClientes(List<Cliente> cliente) {
        this.cliente = cliente;
    }

    public void addCliente(Cliente cliente) throws IOException
    {
        this.cliente.add(cliente);
    }

    /**
     * Obtém a lista de livros da biblioteca.
     *
     * @return A lista de livros.
     */
    public List<Livro> getLivros() {
        return livro;
    }

    /**
     * Define a lista de livros da biblioteca.
     *
     * @param livro A lista de livros.
     */
    public void setLivros(List<Livro> livro) {
        this.livro = livro;
    }

    /**
     * Obtém a lista de jornais da biblioteca.
     *
     * @return A lista de jornais.
     */
    public List<JornalRevista> getJornais() {
        return jornal;
    }

    /**
     * Define a lista de jornais da biblioteca.
     *
     * @param jornal A lista de jornais.
     */
    public void setJornais(List<JornalRevista> jornal) {
        this.jornal = jornal;
    }

    /**
     * Obtém a lista de revistas da biblioteca.
     *
     * @return A lista de revistas.
     */
    public List<JornalRevista> getRevistas() {
        return revista;
    }

    /**
     * Define a lista de revistas da biblioteca.
     *
     * @param revista A lista de revistas.
     */
    public void setRevistas(List<JornalRevista> revista) {
        this.revista = revista;
    }

    /**
     * Obtém a lista de empréstimos da biblioteca.
     *
     * @return A lista de empréstimos.
     */
    public List<Emprestimo> getEmprestimos() {
        return emprestimo;
    }

    /**
     * Define a lista de empréstimos da biblioteca.
     *
     * @param emprestimo A lista de empréstimos.
     */
    public void setEmprestimos(List<Emprestimo> emprestimo) {
        this.emprestimo = emprestimo;
    }

    /**
     * Obtém a lista de reservas da biblioteca.
     *
     * @return A lista de reservas.
     */
    public List<Reserva> getReservas() {
        return reserva;
    }

    /**
     * Define a lista de reservas da biblioteca.
     *
     * @param reserva A lista de reservas.
     */
    public void setReservas(List<Reserva> reserva) {
        this.reserva = reserva;
    }
}
