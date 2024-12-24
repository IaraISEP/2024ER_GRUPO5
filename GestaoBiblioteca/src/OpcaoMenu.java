/** Representa adicão de opcões aos Menus
 * @author ER_GRUPO_5
 * @since 2024
 */
public class OpcaoMenu {
    private String descricao;
    private Runnable acao;

    
    public OpcaoMenu(String descricao, Runnable acao) {
        this.descricao = descricao;
        this.acao = acao;
    }

    public String getDescricao() {
        return descricao;
    }

    
    public void executarAcao() {
        acao.run();
    }
}

