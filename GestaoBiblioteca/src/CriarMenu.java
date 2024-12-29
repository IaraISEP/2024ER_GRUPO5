import java.io.IOException;
import java.util.Scanner;

/** Representa a criação de um menu
 * @author ER_GRUPO_5
 * @since 2024
 */
public class CriarMenu {
    private static Scanner input = new Scanner(System.in);

    /**
     * Exibe o menu principal da gestão da biblioteca.
     */
    public static void menuPrincipal(){
        Menu menuPrincipal = new Menu("Gestão Biblioteca");
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Cliente", CriarMenu::menuCliente));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Livro", CriarMenu::menuLivro));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Jornal", CriarMenu::menuJornal));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Revista", CriarMenu::menuRevista));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Reservas", CriarMenu::menuReservas));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Emprestimos", CriarMenu::menuEmprestimos));
        menuPrincipal.exibir();
    }

    /**
     * Aguarda que se pressione a tecla Enter.
     */
    private static void keyPress() {
        System.out.println("\nPressione Enter para continuar...");
        input.nextLine();
    }

    /**
     * Exibe o menu de gestão de clientes.
     */
    private static void menuCliente(){
        Menu menuCliente = new Menu("Gestão Clientes");

        menuCliente.adicionarOpcao(new OpcaoMenu("Criar Clientes", () -> {
            TratamentoDados.criarCliente();
            keyPress();
            try {
                TratamentoDados.gravarArrayClientes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        menuCliente.adicionarOpcao(new OpcaoMenu("Listar Clientes", CriarMenu::menuListarClientes));
        menuCliente.adicionarOpcao(new OpcaoMenu("Editar Cliente", () -> {
            try {
                TratamentoDados.editarCliente();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));
        menuCliente.adicionarOpcao(new OpcaoMenu("Apagar Cliente", () -> {
            try {
                TratamentoDados.apagarClientePeloId();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));
        menuCliente.exibir();
    }

    /**
     * Exibe o menu de listagem de clientes.
     */
    private static void menuListarClientes() {
        Menu menuListarClientes = new Menu("Listar Clientes");

        menuListarClientes.adicionarOpcao(new OpcaoMenu("Todos Clientes",  () -> {
            TratamentoDados.listaTodosClientes();
            keyPress();
        }));
        menuListarClientes.adicionarOpcao(new OpcaoMenu("Listar Clientes por NIF", () -> {
            TratamentoDados.listaClientePorNif();
            keyPress();
        }));

        menuListarClientes.exibir();
    }

    /**
     * Exibe o menu de gestão de reservas.
     */
    private static void menuReservas() {
        Menu menuReservas = new Menu("Gestão de Reservas");

        menuReservas.adicionarOpcao(new OpcaoMenu("Criar Reserva", () -> {
            TratamentoDados.criarReserva();
            keyPress();
            try {
                TratamentoDados.gravarArrayReservas();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        menuReservas.adicionarOpcao(new OpcaoMenu("Editar Reserva", () -> {
            System.out.println("Reserva Editada...");
            keyPress();
        }));
        menuReservas.adicionarOpcao(new OpcaoMenu("Concluir Reserva", () -> {
            System.out.println("Reserva Concluída...");
            keyPress();
        }));

        menuReservas.exibir();
    }

    /**
     * Exibe o menu de gestão de empréstimos.
     */
    private static void menuEmprestimos() {
        Menu menuEmprestimos = new Menu("Gestão de Empréstimos");

        menuEmprestimos.adicionarOpcao(new OpcaoMenu("Criar Empréstimo", () -> {
            System.out.println("Empréstimo Criado...");
            keyPress();
        }));
        menuEmprestimos.adicionarOpcao(new OpcaoMenu("Editar Empréstimo", () -> {
            System.out.println("Empréstimo Editado...");
            keyPress();
        }));
        menuEmprestimos.adicionarOpcao(new OpcaoMenu("Concluir Empréstimo", () -> {
            System.out.println("Empréstimo Concluído...");
            keyPress();
        }));

        menuEmprestimos.exibir();
    }

    /**
     * Exibe o menu de gestão de livros.
     */
    private static void menuLivro() {
        Menu menuLivro = new Menu("Gestão de Livros");

        menuLivro.adicionarOpcao(new OpcaoMenu("Criar Livro", () -> {
            TratamentoDados.criarLivro();
            keyPress();
            try {
                TratamentoDados.gravarArrayLivros();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        menuLivro.adicionarOpcao(new OpcaoMenu("Listar Livro", CriarMenu::menuListarLivros));
        menuLivro.adicionarOpcao(new OpcaoMenu("Editar Livro", () -> {
            TratamentoDados.editarLivro();
            keyPress();
        }));
        menuLivro.adicionarOpcao(new OpcaoMenu("Apagar Livro", () -> {
            TratamentoDados.apagarLivroPeloId();
            keyPress();
        }));
        menuLivro.adicionarOpcao(new OpcaoMenu("Reservar Livro", () -> {
            System.out.println("Reservar Livro...");
            keyPress();
        }));
        menuLivro.adicionarOpcao(new OpcaoMenu("Empréstimo Livro", () -> {
            System.out.println("Empréstimo Livro...");
            keyPress();
        }));

        menuLivro.exibir();
    }

    /**
     * Exibe o menu de listagem de livros.
     */
    private static void menuListarLivros() {
        Menu menuListarClientes = new Menu("Listar Livros");

        menuListarClientes.adicionarOpcao(new OpcaoMenu("Todos Livros",  () -> {
            TratamentoDados.listaTodosLivros();
            keyPress();
        }));
        menuListarClientes.adicionarOpcao(new OpcaoMenu("Listar Livros por ISBN", () -> {
            TratamentoDados.listaLivroPorIsbn();
            keyPress();
        }));

        menuListarClientes.exibir();
    }

    /**
     * Exibe o menu de gestão de jornais.
     */
    private static void menuJornal() {
        Menu menuJornal = new Menu("Gestão de Jornais");

        menuJornal.adicionarOpcao(new OpcaoMenu("Criar Jornal", () -> {
            System.out.println("Criar Jornal...");
            keyPress();
        }));
        menuJornal.adicionarOpcao(new OpcaoMenu("Editar Jornal", () -> {
            System.out.println("Editar Jornal...");
            keyPress();
        }));
        menuJornal.adicionarOpcao(new OpcaoMenu("Apagar Jornal", () -> {
            System.out.println("Apagar Jornal...");
            keyPress();
        }));
        menuJornal.adicionarOpcao(new OpcaoMenu("Reservar Jornal", () -> {
            System.out.println("Reservar Jornal...");
            keyPress();
        }));
        menuJornal.adicionarOpcao(new OpcaoMenu("Empréstimo Jornal", () -> {
            System.out.println("Empréstimo Jornal...");
            keyPress();
        }));

        menuJornal.exibir();
    }

    /**
     * Exibe o menu de gestão de revistas.
     */
    private static void menuRevista() {
        Menu menuRevista = new Menu("Gestão de Revistas");

        menuRevista.adicionarOpcao(new OpcaoMenu("Criar Revista", () -> {
            System.out.println("Criar Revista...");
            keyPress();
        }));
        menuRevista.adicionarOpcao(new OpcaoMenu("Editar Revista", () -> {
            System.out.println("Editar Revista...");
            keyPress();
        }));
        menuRevista.adicionarOpcao(new OpcaoMenu("Apagar Revista", () -> {
            System.out.println("Apagar Revista...");
            keyPress();
        }));
        menuRevista.adicionarOpcao(new OpcaoMenu("Reservar Revista", () -> {
            System.out.println("Reservar Revista...");
            keyPress();
        }));
        menuRevista.adicionarOpcao(new OpcaoMenu("Empréstimo Revista", () -> {
            System.out.println("Empréstimo Revista...");
            keyPress();
        }));

        menuRevista.exibir();
    }
}