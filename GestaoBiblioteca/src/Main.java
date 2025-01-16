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
        //int idBiblioteca = TratamentoDados.lerInt("Escolha o Id da biblioteca",false,null);
        //Biblioteca biblioteca = new Biblioteca("Biblioteca 1", "Morada 1", idBiblioteca);
        TratamentoDados.inicializador();
        CriarMenu.menuBiblioteca();
    }
}
