public class ItemBiblioteca {
    private String titulo;
    private String categoria;
    private String editora;
    private int codBiblioteca;

    public ItemBiblioteca(String titulo, String categoria, String editora, int codBiblioteca) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.editora = editora;
        this.codBiblioteca = codBiblioteca;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEditora() {
        return editora;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getCodBiblioteca() {
        return codBiblioteca;
    }
}
