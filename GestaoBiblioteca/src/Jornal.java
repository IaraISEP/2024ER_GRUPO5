/** Representa um Jornal
 * @author ER_GRUPO_5
 * @since 2024
 */

public class Jornal extends JornalRevista {
    private Constantes.CategoriaJornal categoria;

    /**
     * Construtor para criar um jornal.
     *
     * @param id O ID do jornal.
     * @param titulo O título do jornal.
     * @param editora A editora do jornal.
     * @param categoria A categoria do jornal.
     * @param issn O ISSN do jornal.
     * @param dataPublicacao A data de publicação do jornal.
     * @param codBiblioteca O código da biblioteca.
     */
    public Jornal(int id, String titulo, String editora, Constantes.CategoriaJornal categoria, String issn, int dataPublicacao, int codBiblioteca) {
        super(id, titulo, editora, issn, dataPublicacao, codBiblioteca);
        this.categoria = categoria;
    }

    /**
     * Obtém a categoria do jornal.
     *
     * @return A categoria do jornal.
     */
    public Constantes.CategoriaJornal getCategoria() {
        return categoria;
    }

    /**
     * Define a categoria do jornal.
     *
     * @param categoria A categoria do jornal.
     */
    public void setCategoria(Constantes.CategoriaJornal categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtém o identificador da categoria do Jornal.
     *
     * @return identificador da categoria do Jornal
     */
    public int getCategoriaInt()
    {
        return categoria.getCategoriaJornal();
    }
}