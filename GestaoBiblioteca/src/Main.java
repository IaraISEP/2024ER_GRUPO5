import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        tratamentoDados.criarSistemaFicheiros();

        tratamentoDadosClientes.lerFicheiroCsvClientes("Biblioteca_1/Clientes/clientes.csv");
        criarMenu.menuPrincipal();
    }

}