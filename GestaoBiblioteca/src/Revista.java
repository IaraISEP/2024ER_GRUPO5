import java.time.LocalDateTime;

/** Representa uma Revista
 * @author ER_GRUPO_5
 * @since 2024
 */
public class Revista extends JornalRevista{
    /**
     * Construtor para criar uma revista.
     *
     * @param titulo O título da revista.
     * @param editora A editora da revista.
     * @param categoria A categoria da revista.
     * @param issn O ISSN da revista.
     * @param dataPublicacao A data de publicação da revista.
     * @param codBiblioteca O código da biblioteca.
     */
    public Revista(String titulo, String editora, String categoria, String issn, int dataPublicacao, int codBiblioteca) {
        super(titulo, editora, categoria, issn, dataPublicacao, codBiblioteca);
    }
}
