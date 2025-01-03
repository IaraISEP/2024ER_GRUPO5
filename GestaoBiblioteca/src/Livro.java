/**
 * Representa um Livro da Biblioteca.
 * @author ER_GRUPO_5
 * @since 2024
 */
public class Livro extends ItemBiblioteca {
    private int anoEdicao;
    private String isbn;
    private String autor;
    private Constantes.CategoriaLivro categoria;

    /**
     * Constrói uma nova instância de Livro.
     *
     * @param id            o ID do livro
     * @param codBiblioteca o código da biblioteca
     * @param titulo        o título do livro
     * @param editora       a editora do livro
     * @param categoria     a categoria do livro
     * @param anoEdicao     o ano da edição
     * @param isbn          o ISBN do livro
     * @param autor         o autor do livro
     */
    public Livro(int id, int codBiblioteca, String titulo, String editora, Constantes.CategoriaLivro categoria, int anoEdicao, String isbn, String autor) {
        super(id, titulo, editora, codBiblioteca);
        this.anoEdicao = anoEdicao;
        this.isbn = isbn;
        this.autor = autor;
        this.categoria = categoria;
    }

    /**
     * Obtém o ano da edição.
     *
     * @return o ano da edição
     */
    public int getAnoEdicao() {
        return anoEdicao;
    }

    /**
     * Define o ano da edição.
     *
     * @param anoEdicao o ano da edição
     */
    public void setAnoEdicao(int anoEdicao) {
        this.anoEdicao = anoEdicao;
    }

    /**
     * Obtém o ISBN do livro.
     *
     * @return o ISBN do livro
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Define o ISBN do livro.
     *
     * @param isbn o ISBN do livro
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Obtém o autor do livro.
     *
     * @return o autor do livro
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Define o autor do livro.
     *
     * @param autor o autor do livro
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Obtém a categoria do livro.
     *
     * @return a categoria do livro
     */
    public Constantes.CategoriaLivro getCategoria() {
        return categoria;
    }

    /**
     * Obtém o identificador da categoria do livro.
     *
     * @return identificador da categoria do livro
     */
    public int getCategoriaInt()
    {
        return categoria.getCategoriaLivro();
    }

    /**
     * Define a categoria do livro.
     *
     * @param categoria a categoria do livro
     */
    public void setCategoria(Constantes.CategoriaLivro categoria) {
        this.categoria = categoria;
    }
}