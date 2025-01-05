import java.io.IOException;
/** Representa Class principal
 * @author ER_GRUPO_5
 * @since 2024
 */

public class Main {
    /**
     * Método principal que inicia a aplicação de gestão da biblioteca.
     *
     * @param args Argumentos da linha de comando.
     * @throws IOException Se ocorrer um erro de I/O durante a leitura dos ficheiros.
     */
    public static void main(String[] args) throws IOException {
        TratamentoDados.criarSistemaFicheiros();
        TratamentoDados.lerFicheiroCsvClientes(Constantes.Path.CLIENTE.getValue());
        TratamentoDados.lerFicheiroCsvLivros(Constantes.Path.LIVRO.getValue());
        TratamentoDados.lerFicheiroCsvJornaisRevistas(Constantes.Path.JORNAL.getValue(), Constantes.TipoItem.JORNAL);
        TratamentoDados.lerFicheiroCsvJornaisRevistas(Constantes.Path.REVISTA.getValue(), Constantes.TipoItem.REVISTA);
        TratamentoDados.lerFicheiroCsvReservas(Constantes.Path.RESERVA.getValue());
        TratamentoDados.lerFicheiroCsvReservasDtl(Constantes.Path.RESERVADTL.getValue());
        CriarMenu.menuPrincipal();
    }
}
