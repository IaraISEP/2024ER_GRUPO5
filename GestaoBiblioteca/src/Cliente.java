/** Representa um Cliente
 * @author ER_GRUPO_5
 * @since 2024
 */
public class Cliente {

    private int id;
    private String nome;
    private Constantes.Genero genero;
    private int nif;
    private int contacto;
    private int codBiblioteca;
    private int opcao;
    private boolean status = true;

    /**
     * Construtor para criar um cliente.
     *
     * @param id ID único do cliente (atribuído de forma automática).
     * @param nome O nome do cliente.
     * @param genero O genero do cliente.
     * @param nif O NIF do cliente.
     * @param contacto O contacto do cliente.
     * @param codBiblioteca O código da biblioteca associada ao cliente.
     */
    public Cliente(int id, String nome, Constantes.Genero genero, int nif, int contacto, int codBiblioteca) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.nif = nif;
        this.contacto = contacto;
        this.codBiblioteca = codBiblioteca;
    }

    public int getCodBiblioteca() {
        return codBiblioteca;
    }

    /**
     * Obtém o ID do cliente.
     *
     * @return O ID do cliente.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do cliente.
     *
     * @param id O ID do cliente.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém o nome do cliente.
     *
     * @return O nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do cliente.
     *
     * @param nome O nome do cliente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o gênero do cliente.
     *
     * @return O gênero do cliente.
     */
    public char getGenero() {
        return genero.getGenero();
    }

    /**
     * Define o gênero do cliente.
     *
     * @param genero O gênero do cliente.
     */
    public void setGenero(Constantes.Genero genero) {
        this.genero = genero;
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
     * Obtém o contacto do cliente.
     *
     * @return O contacto do cliente.
     */
    public int getContacto() {
        return contacto;
    }

    /**
     * Define o contacto do cliente.
     *
     * @param contacto O contacto do cliente.
     */
    public void setContacto(int contacto) {
        this.contacto = contacto;
    }
}