import java.time.LocalDateTime;

/** Representa um Jornal/Revista
 * @author ER_GRUPO_5
 * @since 2024
 */
public class JornalRevista extends ItemBiblioteca {
    private int id;
    private String issn;
    private int dataPublicacao;

    /**
     * Construtor para criar um Jornal/Revista.
     *
     * @param titulo O título do jornal/revista.
     * @param editora A editora do jornal/revista.
     * @param categoria A categoria do jornal/revista.
     * @param issn O ISSN do jornal/revista.
     * @param dataPublicacao A data de publicação do jornal/revista.
     * @param codBiblioteca O código da biblioteca.
     */
    public JornalRevista(String titulo, String editora, String categoria, String issn, int dataPublicacao, int codBiblioteca) {
        super(titulo, editora, categoria, codBiblioteca);
        this.issn = issn;
        this.dataPublicacao = dataPublicacao;
    }

    /**
     * Obtém o ID do jornal/revista.
     *
     * @return O ID do jornal/revista.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtém o ISSN do jornal/revista.
     *
     * @return O ISSN do jornal/revista.
     */
    public String getIssn() {
        return issn;
    }

    /**
     * Obtém a data de publicação do jornal/revista.
     *
     * @return A data de publicação do jornal/revista.
     */
    public int getDataPublicacao() {
        return dataPublicacao;
    }
}