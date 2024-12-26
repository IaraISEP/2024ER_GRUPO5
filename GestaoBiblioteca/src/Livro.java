/** Representa um Livro
 * @author ER_GRUPO_5
 * @since 2024
 */
public class Livro extends ItemBiblioteca{
    private int id, anoEdicao;
    private String isbn;
    private String autor;


    public Livro(int id, String titulo, String editora, String categoria, int anoEdicao, String isbn, String autor, int codBiblioteca) {
        super(titulo, editora, categoria, codBiblioteca);
        this.id = id;
        this.anoEdicao = anoEdicao;
        this.isbn = isbn;
        this.autor = autor;
    }

    public int getId() { return id; }

    public int getAnoEdicao() {
        return anoEdicao;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAutor() { return autor; }
}
