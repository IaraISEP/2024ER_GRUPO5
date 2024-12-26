import java.time.LocalDateTime;

/** Representa uma Revista
 * @author ER_GRUPO_5
 * @since 2024
 */
public class Revista extends JornalRevista{
    public Revista(String titulo, String editora, String categoria, String issn, LocalDateTime dataPublicacao, int codBiblioteca) {
        super(titulo, editora, categoria, issn, dataPublicacao, codBiblioteca);
    }
}
