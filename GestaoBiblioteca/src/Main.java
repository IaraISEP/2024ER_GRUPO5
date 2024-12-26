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
        TratamentoDados.lerFicheiroCsvClientes("Biblioteca_1/Clientes/clientes.csv");
        TratamentoDados.lerFicheiroCsvReservas("Biblioteca_1/Reservas/reservas.csv");
        TratamentoDados.lerFicheiroCsvLivros("Biblioteca_1/Livros/livros.csv");
        CriarMenu.menuPrincipal();
    }
}