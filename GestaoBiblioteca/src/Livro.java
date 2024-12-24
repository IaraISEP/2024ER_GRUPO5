public class Livro extends ItemBiblioteca{
    private int anoEdicao;
    private String isbn;
    private String autor;

    public Livro(String titulo, String editora, String categoria, int anoEdicao, String isbn, String autor, int codBiblioteca) {
        super(titulo, editora, categoria, codBiblioteca);
        this.anoEdicao = anoEdicao;
        this.isbn = isbn;
        this.autor = autor;
    }

    public int getAnoEdicao() {
        return anoEdicao;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAutor() { return autor; }
}
