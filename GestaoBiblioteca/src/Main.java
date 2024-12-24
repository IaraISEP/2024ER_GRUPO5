import java.io.IOException;
/** Representa Class principal
 * @author ER_GRUPO_5
 * @since 2024
 */
public class Main {

    public static void main(String[] args) throws IOException {
        TratamentoDados.criarSistemaFicheiros();
        TratamentoDados.lerFicheiroCsvClientes("Biblioteca_1/Clientes/clientes.csv");
        CriarMenu.menuPrincipal();
    }

}