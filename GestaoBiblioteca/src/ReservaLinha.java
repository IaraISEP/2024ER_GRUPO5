/** Representa o detalhe da Reserva
 * @author ER_GRUPO_5
 * @since 2024
 */
public class ReservaLinha {
    private int idReserva;
    private int idReservaLinha;
    private Constantes.TipoItem tipoItem;
    private Constantes.Estado estado;
    private int idItem;

    /**
     * Construtor para criar a linha da reserva.
     *
     * @param idReserva         O Id da Reserva
     * @param idReservaLinha    O Id da Reserva Linha
     * @param tipoItem          O Tipo de item associado à reserva
     * @param idItem            O Id do item associado à reserva
     */
    public ReservaLinha(int idReserva, int idReservaLinha, Constantes.TipoItem tipoItem, int idItem, Constantes.Estado estado) {
        this.idReserva = idReserva;
        this.idReservaLinha = idReservaLinha;
        this.tipoItem = tipoItem;
        this.idItem = idItem;
        this.estado = estado;
    }

    /**
     * Obtém o Id da reserva.
     *
     * @return O Id da reserva.
     */
    public int getIdReserva() {
        return idReserva;
    }

    /**
     * Define o Id da reserva.
     *
     * @param idReserva O Id da reserva.
     */
    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    /**
     * Obtém o Id da reserva linha.
     *
     * @return O Id da reserva linha.
     */
    public int getIdReservaLinha() { return idReservaLinha; }

    /**
     * Define o Id da reserva linha.
     *
     * @param idReservaLinha O Id da reserva linha.
     */
    public void setIdReservaLinha(int idReservaLinha) {
        this.idReservaLinha = idReservaLinha;
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

    public void setEstado(Constantes.Estado estado) {
        this.estado = estado;
    }

    public Constantes.Estado getEstado() {
        return estado;
    }
}
