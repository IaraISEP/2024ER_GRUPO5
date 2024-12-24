/** Representa um Item da Biblioteca
 * @author ER_GRUPO_5
 * @since 2024
 */
public class ItemBiblioteca {
    private String titulo;
    private String categoria;
    private String editora;

    public ItemBiblioteca() {
    }

    public ItemBiblioteca(String titulo, String categoria, String editora) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.editora = editora;
    }
}
