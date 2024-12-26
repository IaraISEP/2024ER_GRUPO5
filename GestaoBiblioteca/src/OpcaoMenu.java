/** Representa adicão de opcões aos Menus
 * @author ER_GRUPO_5
 * @since 2024
 */
public class OpcaoMenu {
    private String descricao;
    private Runnable acao;

    /**
     * Construtor para criar uma opção de menu.
     *
     * @param descricao A descrição da opção.
     * @param acao A ação a ser executada quando a opção for selecionada.
     */
    public OpcaoMenu(String descricao, Runnable acao) {
        this.descricao = descricao;
        this.acao = acao;
    }

    /**
     * Obtém a descrição da opção.
     *
     * @return A descrição da opção.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Executa a ação associada à opção.
     */
    public void executarAcao() {
        acao.run();
    }
}

