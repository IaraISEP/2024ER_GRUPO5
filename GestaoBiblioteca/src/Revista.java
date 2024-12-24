import java.util.Date;

public class Revista extends JornalRevista{
    public Revista(String titulo, String editora, String categoria, String issn, Date dataPublicacao, int codBiblioteca) {
        super(titulo, editora, categoria, issn, dataPublicacao, codBiblioteca);
    }
}
