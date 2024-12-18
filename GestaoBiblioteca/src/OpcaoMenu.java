
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

