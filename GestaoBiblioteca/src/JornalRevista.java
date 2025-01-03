/** Representa um Jornal/Revista na biblioteca.
 * @author ER_GRUPO_5
 * @since 2024
 */
public class JornalRevista extends ItemBiblioteca {
    private String issn;
    private int dataPublicacao;

    /**
     * Construtor para criar um Jornal/Revista.
     *
     * @param id O ID do jornal/revista.
     * @param titulo O título do jornal/revista.
     * @param editora A editora do jornal/revista.
     * @param issn O ISSN do jornal/revista.
     * @param dataPublicacao A data de publicação do jornal/revista.
     * @param codBiblioteca O código da biblioteca.
     */
    public JornalRevista(int id, String titulo, String editora, String issn, int dataPublicacao, int codBiblioteca) {
        super(id, titulo, editora, codBiblioteca);
        this.issn = issn;
        this.dataPublicacao = dataPublicacao;
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