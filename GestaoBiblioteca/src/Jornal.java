import java.util.Date;

/** Representa um Jornal
 * @author ER_GRUPO_5
 * @since 2024
 */

public class Jornal extends JornalRevista {
    public Jornal(String titulo, String editora, String categoria, String issn, Date dataPublicacao, int codBiblioteca) {
        super(titulo, editora, categoria, issn, dataPublicacao, codBiblioteca);
    }
}