/** Representa o detalhe da Emprestimo
 * @author ER_GRUPO_5
 * @since 2024
 */
public class EmprestimoLinha {
    private int idEmprestimo;
    private int idEmprestimoLinha;
    private Constantes.TipoItem tipoItem;
    private int idItem;
    private Constantes.Estado estado;

    /**
     * Construtor para criar a linha da reserva.
     *
     * @param idEmprestimo          O Id do empréstimo
     * @param idEmprestimoLinha     O Id do empréstimo Linha
     * @param tipoItem              O Tipo de item associado ao empréstimo
     * @param idItem                O Id do item associado ao empréstimo
     */
    public EmprestimoLinha(int idEmprestimo, int idEmprestimoLinha, Constantes.TipoItem tipoItem, int idItem, Constantes.Estado estado) {
        this.idEmprestimo = idEmprestimo;
        this.idEmprestimoLinha = idEmprestimoLinha;
        this.tipoItem = tipoItem;
        this.idItem = idItem;
        this.estado = estado;
    }

    public Constantes.Estado getEstado() {
        return estado;
    }

    public void setEstado(Constantes.Estado estado) {
        this.estado = estado;
    }

    /**
     * Obtém o Id da emprestimo.
     *
     * @return O Id da emprestimo.
     */
    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    /**
     * Define o Id da emprestimo.
     *
     * @param idEmprestimo O Id da emprestimo.
     */
    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    /**
     * Obtém o Id do empréstimo linha.
     *
     * @return O Id do empréstimo linha.
     */
    public int getIdEmprestimoLinha() { return idEmprestimoLinha; }

    /**
     * Define o Id do empréstimo.
     *
     * @param idEmprestimoLinha O Id do emprestimo linha.
     */
    public void setIdEmprestimoLinha(int idEmprestimoLinha) {
        this.idEmprestimoLinha = idEmprestimoLinha;
    }

    /**
     * Obtém o Tipo de Item.
     *
     * @return O Tipo de Item.
     */
    public Constantes.TipoItem getTipoItem() {
        return tipoItem;
    }

    /**
     * Define o Tipo de Item.
     *
     * @param tipoItem O Tipo de Item.
     */
    public void setTipoItem(Constantes.TipoItem tipoItem) {
        this.tipoItem = tipoItem;
    }

    /**
     * Obtém o Id do Item.
     *
     * @return O Id do item.
     */
    public int getIdItem() {
        return idItem;
    }

    /**
     * Define o Id do Item.
     *
     * @param idItem O Id do Item.
     */
    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }
}
