import java.util.Date;

public class JornalRevista extends ItemBiblioteca{
    private String issn;
    private Date dataPublicacao;

    public JornalRevista(String titulo, String editora, String categoria, String issn, Date dataPublicacao) {
        super(titulo, editora, categoria);
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
