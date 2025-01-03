/** Representa uma Revista
 * @author ER_GRUPO_5
 * @since 2024
 */
public class Revista extends JornalRevista{
    private Constantes.CategoriaRevista categoria;

    /**
     * Construtor para criar uma revista.
     *
     * @param id O ID da revista.
     * @param titulo O título da revista.
     * @param editora A editora da revista.
     * @param categoria A categoria da revista.
     * @param issn O ISSN da revista.
     * @param dataPublicacao A data de publicação da revista.
     * @param codBiblioteca O código da biblioteca.
     */
    public Revista(int id, String titulo, String editora, Constantes.CategoriaRevista categoria, String issn, int dataPublicacao, int codBiblioteca) {
        super(id, titulo, editora, issn, dataPublicacao, codBiblioteca);
        this.categoria = categoria;
    }

    /**
     * Obtém a categoria da Revista.
     *
     * @return a categoria da Revista
     */
    public Constantes.CategoriaRevista getCategoria() {
        return categoria;
    }

    /**
     * Obtém o identificador da categoria da Revista.
     *
     * @return identificador da categoria da Revista
     */
    public int getCategoriaInt()
    {
        return categoria.getCategoriaRevista();
    }

    /**
     * Define a categoria da Revista.
     *
     * @param categoria a categoria da Revista
     */
    public void setCategoria(Constantes.CategoriaRevista categoria) {
        this.categoria = categoria;
    }
}
