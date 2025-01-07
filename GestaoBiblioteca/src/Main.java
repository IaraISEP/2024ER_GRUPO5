import java.io.IOException;
/**
 * Classe principal da aplicação de gestão da biblioteca.
 * @author ER_GRUPO_5
 * @since 2024
 */

public class Main {
    /**
     * Método principal que inicia a aplicação de gestão da biblioteca.
     *
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        try {
            TratamentoDados.criarSistemaFicheiros();
            TratamentoDados.lerFicheiroCsvClientes(Constantes.Path.CLIENTE.getValue());
            TratamentoDados.lerFicheiroCsvLivros(Constantes.Path.LIVRO.getValue());
            TratamentoDados.lerFicheiroCsvJornaisRevistas(Constantes.Path.JORNAL.getValue(), Constantes.TipoItem.JORNAL);
            TratamentoDados.lerFicheiroCsvJornaisRevistas(Constantes.Path.REVISTA.getValue(), Constantes.TipoItem.REVISTA);
            TratamentoDados.lerFicheiroCsvReservas(Constantes.Path.RESERVA.getValue());
            TratamentoDados.lerFicheiroCsvReservasLinha(Constantes.Path.RESERVALINHA.getValue());
            CriarMenu.menuPrincipal();
        } catch (IOException e) {
            System.err.println("Erro durante a execução: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
