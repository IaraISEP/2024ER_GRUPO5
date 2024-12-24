public class ItemBiblioteca {
    private String titulo;
    private String categoria;
    private String editora;

    public ItemBiblioteca(String titulo, String categoria, String editora) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.editora = editora;
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
}
