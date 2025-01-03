/** Representa um Jornal/Revista
 * @author ER_GRUPO_5
 * @since 2024
 */
public class JornalRevista extends ItemBiblioteca {
    private String issn;
    private int dataPublicacao;
    private Constantes.TipoItem tipo;

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
    public JornalRevista(int id, String titulo, String editora, String categoria, String issn, int dataPublicacao, int codBiblioteca, Constantes.TipoItem tipo) {
        super(id, titulo, categoria, editora, codBiblioteca);
        this.issn = issn;
        this.dataPublicacao = dataPublicacao;
        this.tipo = tipo;
    }

    /**
     * Obtém o ISSN do jornal/revista.
     *
     * @return O ISSN do jornal/revista.
     */
    public String getIssn() { return issn; }

    /**
     * Obtém um tipo jornal ou revista.
     *
     * @return O tipo do jornal/revista.
     */
    public Constantes.TipoItem getTipo() { return tipo; }

    /**
     * Obtém a data de publicação do jornal/revista.
     *
     * @return A data de publicação do jornal/revista.
     */
    public int getDataPublicacao() {
        return dataPublicacao;
    }
}