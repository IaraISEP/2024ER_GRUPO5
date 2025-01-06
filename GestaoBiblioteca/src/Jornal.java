/** Representa um Jornal
 * @author ER_GRUPO_5
 * @since 2024
 */

public class Jornal extends JornalRevista {
    /**
     * Construtor para criar um jornal.
     *
     * @param titulo O título do jornal.
     * @param editora A editora do jornal.
     * @param categoria A categoria do jornal.
     * @param issn O ISSN do jornal.
     * @param dataPublicacao A data de publicação do jornal.
     * @param codBiblioteca O código da biblioteca.
     */
    public Jornal(int id, String titulo, String editora, Constantes.Categoria categoria, String issn, int dataPublicacao, int codBiblioteca) {
        super(id, titulo, editora, issn, dataPublicacao, codBiblioteca, Constantes.TipoItem.JORNAL, categoria);
    }
}