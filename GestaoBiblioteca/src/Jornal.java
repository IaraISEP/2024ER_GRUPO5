import java.time.LocalDateTime;

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
    public Jornal(String titulo, String editora, String categoria, String issn, LocalDateTime dataPublicacao, int codBiblioteca) {
        super(titulo, editora, categoria, issn, dataPublicacao, codBiblioteca);
    }
}