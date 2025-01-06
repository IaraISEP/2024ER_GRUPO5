/** Representa o detalhe da Emprestimo
 * @author ER_GRUPO_5
 * @since 2024
 */
public class EmprestimoLinha {
    private int idEmprestimo;
    private Constantes.TipoItem tipoItem;
    private int idItem;
    private Constantes.Estado estado;

    /**
     * Construtor para criar a linha da reserva.
     *
     * @param idEmprestimo O Id da Emprestimo
     * @param tipoItem  O Tipo de item associado à reserva
     * @param idItem    O Id do item associado à reserva
     */
    public EmprestimoLinha(int idEmprestimo, Constantes.TipoItem tipoItem, int idItem, Constantes.Estado estado) {
        this.idEmprestimo = idEmprestimo;
        this.tipoItem = tipoItem;
        this.idItem = idItem;
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
