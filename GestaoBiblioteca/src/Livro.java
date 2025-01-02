/** Representa um Livro
 * @author ER_GRUPO_5
 * @since 2024
 */
public class Livro extends ItemBiblioteca {
    private int id;
    private int anoEdicao;
    private String isbn;
    private String autor;

    /**
     * Construtor para criar um livro.
     *
     * @param id            O ID do livro.
     * @param titulo        O título do livro.
     * @param editora       A editora do livro.
     * @param categoria     A categoria do livro.
     * @param anoEdicao     O ano de edição do livro.
     * @param isbn          O ISBN do livro.
     * @param autor         O autor do livro.
     * @param codBiblioteca O código da biblioteca.
     */
    public Livro(int id, String titulo, String editora, String categoria, int anoEdicao, String isbn, String autor, int codBiblioteca) {
        super(titulo, editora, categoria, codBiblioteca);
        this.id = id;
        this.anoEdicao = anoEdicao;
        this.isbn = isbn;
        this.autor = autor;
    }

    /**
     * Obtém o ID do livro.
     *
     * @return O ID do livro.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtém o ano de edição do livro.
     *
     * @return O ano de edição do livro.
     */
    public int getAnoEdicao() {
        return anoEdicao;
    }

    /**
     * Obtém o ISBN do livro.
     *
     * @return O ISBN do livro.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Obtém o autor do livro.
     *
     * @return O autor do livro.
     */
    public String getAutor() {
        return autor;
    }
}