/** Representa um Item da Biblioteca
 * @author ER_GRUPO_5
 * @since 2024
 */
public class ItemBiblioteca {
    private int id;
    private String titulo;
    private String editora;
    private int codBiblioteca;
    private Constantes.Categoria categoria;

    /**
     * Construtor para criar um item da biblioteca.
     *
     * @param id            O id do item.
     * @param titulo        O título do item.
     * @param editora       A editora do item.
     * @param codBiblioteca O código da biblioteca.
     */
    public ItemBiblioteca(int id, String titulo, String editora, Constantes.Categoria categoria, int codBiblioteca) {
        this.id = id;
        this.titulo = titulo;
        this.editora = editora;
        this.codBiblioteca = codBiblioteca;
        this.categoria = categoria;
    }

    public Constantes.Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Constantes.Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtém o id do item.
     *
     * @return O id do item.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtém o título do item.
     *
     * @return O título do item.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Obtém a editora do item.
     *
     * @return A editora do item.
     */
    public String getEditora() {
        return editora;
    }

    /**
     * Obtém o código da biblioteca.
     *
     * @return O código da biblioteca.
     */
    public int getCodBiblioteca() {
        return codBiblioteca;
    }
}