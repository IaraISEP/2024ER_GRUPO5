/** Representa um Item da Biblioteca
 * @author ER_GRUPO_5
 * @since 2024
 */
public class ItemBiblioteca {
    private String titulo;
    private String categoria;
    private String editora;
    private int codBiblioteca;

    /**
     * Construtor para criar um item da biblioteca.
     *
     * @param titulo        O título do item.
     * @param editora       A editora do item.
     * @param categoria     A categoria do item.
     * @param codBiblioteca O código da biblioteca.
     */
    public ItemBiblioteca(String titulo, String editora, String categoria, int codBiblioteca) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.editora = editora;
        this.codBiblioteca = codBiblioteca;
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
     * Obtém a categoria do item.
     *
     * @return A categoria do item.
     */
    public String getCategoria() {
        return categoria;
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