import java.util.Date;
/** Representa um Jornal/Revista
 * @author ER_GRUPO_5
 * @since 2024
 */
public class JornalRevista extends ItemBiblioteca{
    private String issn;
    private Date dataPublicacao;

    public JornalRevista(String titulo, String editora, String categoria, String issn, Date dataPublicacao, int codBiblioteca) {
        super(titulo, editora, categoria, codBiblioteca);
        this.issn = issn;
        this.dataPublicacao = dataPublicacao;
    }

    public String getIssn() {
        return issn;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }
}
