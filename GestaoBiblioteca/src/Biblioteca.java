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
}
