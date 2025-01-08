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
            try {
                TratamentoDados.criarCliente();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
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
     * Exibe o menu de gestão de livros.
     */
    private static void menuLivro() {
        Menu menuLivro = new Menu("Gestão de Livros");

        menuLivro.adicionarOpcao(new OpcaoMenu("Criar Livro", () -> {
            try {
                TratamentoDados.criarLivro();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));
        menuLivro.adicionarOpcao(new OpcaoMenu("Listar Livro", CriarMenu::menuListarLivros));
        menuLivro.adicionarOpcao(new OpcaoMenu("Editar Livro", () -> {
            try {
                TratamentoDados.editarLivro();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));
        menuLivro.adicionarOpcao(new OpcaoMenu("Apagar Livro", () -> {
            try {
                TratamentoDados.apagarLivroPeloId();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));
        /*
        TODO:
            Penso que seja possivel retirar estes menus daqui
            e apenas usar o menu principal das Reservas / Emprestimos
        menuLivro.adicionarOpcao(new OpcaoMenu("Reservar Livro", () -> {
            System.out.println("Reservar Livro...");
            keyPress();
        }));
        menuLivro.adicionarOpcao(new OpcaoMenu("Empréstimo Livro", () -> {
            System.out.println("Empréstimo Livro...");
            keyPress();
        }));*/

        menuLivro.exibir();
    }

    /**
     * Exibe o menu de listagem de livros.
     */
    private static void menuListarLivros() {
        Menu menuListarLivros = new Menu("Listar Livros");

        menuListarLivros.adicionarOpcao(new OpcaoMenu("Todos Livros",  () -> {
            TratamentoDados.listaTodosLivros();
            keyPress();
        }));
        menuListarLivros.adicionarOpcao(new OpcaoMenu("Listar Livros por ISBN", () -> {
            TratamentoDados.listaLivroPorIsbn();
            keyPress();
        }));

        menuListarLivros.exibir();
    }

    /**
     * Exibe o menu de gestão de jornais.
     */
    private static void menuJornal() {
        Menu menuJornal = new Menu("Gestão de Jornais");

        menuJornal.adicionarOpcao(new OpcaoMenu("Criar Jornal", () -> {
            try {
                TratamentoDados.criarJornal();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));
        menuJornal.adicionarOpcao(new OpcaoMenu("Listar Jornais", CriarMenu::menuListarJornais));
        menuJornal.adicionarOpcao(new OpcaoMenu("Editar Jornal", () -> {
            try {
                TratamentoDados.editarJornalRevista(Constantes.TipoItem.JORNAL);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));
        menuJornal.adicionarOpcao(new OpcaoMenu("Apagar Jornal", () -> {
            try {
                TratamentoDados.apagarJornalRevista(Constantes.TipoItem.JORNAL);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));
        /*
        TODO:
            Penso que seja possivel retirar estes menus daqui
            e apenas usar o menu principal das Reservas / Emprestimos
        menuJornal.adicionarOpcao(new OpcaoMenu("Reservar Jornal", () -> {
            System.out.println("Reservar Jornal...");
            keyPress();
        }));
        menuJornal.adicionarOpcao(new OpcaoMenu("Empréstimo Jornal", () -> {
            System.out.println("Empréstimo Jornal...");
            keyPress();
        }));*/

        menuJornal.exibir();
    }

    /**
     * Exibe o menu de listagem de jornais.
     */
    private static void menuListarJornais() {
        Menu menuListarJornais = new Menu("Listar Jornais");

        menuListarJornais.adicionarOpcao(new OpcaoMenu("Todos jornais",  () -> {
            TratamentoDados.listaTodosJornalRevista(Constantes.TipoItem.JORNAL);
            keyPress();
        }));
        menuListarJornais.adicionarOpcao(new OpcaoMenu("Listar jornais por ISSN", () -> {
            TratamentoDados.listaJornalRevistaPorIssn(Constantes.TipoItem.JORNAL);
            keyPress();
        }));

        menuListarJornais.exibir();
    }

    /**
     * Exibe o menu de gestão de revistas.
     */
    private static void menuRevista() {
        Menu menuRevista = new Menu("Gestão de Revistas");

        menuRevista.adicionarOpcao(new OpcaoMenu("Criar Revista", () -> {
            try {
                TratamentoDados.criarRevista();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));
        menuRevista.adicionarOpcao(new OpcaoMenu("Listar Revistas", CriarMenu::menuListarRevistas));
        menuRevista.adicionarOpcao(new OpcaoMenu("Editar Revistas", () -> {
            try {
                TratamentoDados.editarJornalRevista(Constantes.TipoItem.REVISTA);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));
        menuRevista.adicionarOpcao(new OpcaoMenu("Apagar Revista", () -> {
            try {
                TratamentoDados.apagarJornalRevista(Constantes.TipoItem.REVISTA);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));
        /*
        TODO:
            Penso que seja possivel retirar estes menus daqui
            e apenas usar o menu principal das Reservas / Emprestimos
        menuRevista.adicionarOpcao(new OpcaoMenu("Reservar Revista", () -> {
            System.out.println("Reservar Revista...");
            keyPress();
        }));
        menuRevista.adicionarOpcao(new OpcaoMenu("Empréstimo Revista", () -> {
            System.out.println("Empréstimo Revista...");
            keyPress();
        }));
        */
        menuRevista.exibir();
    }

    /**
     * Exibe o menu de listagem de revistas.
     */
    private static void menuListarRevistas() {
        Menu menuListarRevistas = new Menu("Listar Revistas");

        menuListarRevistas.adicionarOpcao(new OpcaoMenu("Todas revistas",  () -> {
            TratamentoDados.listaTodosJornalRevista(Constantes.TipoItem.REVISTA);
            keyPress();
        }));
        menuListarRevistas.adicionarOpcao(new OpcaoMenu("Listar revistas por ISSN", () -> {
            TratamentoDados.listaJornalRevistaPorIssn(Constantes.TipoItem.REVISTA);
            keyPress();
        }));

        menuListarRevistas.exibir();
    }

    /**
     * Exibe o menu de gestão de reservas.
     */
    private static void menuReservas() {
        Menu menuReservas = new Menu("Gestão de Reservas");

        menuReservas.adicionarOpcao(new OpcaoMenu("Criar Reserva", () -> {
            try {
                TratamentoDados.criarReserva();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));

        menuReservas.adicionarOpcao(new OpcaoMenu("Listar Reservas", CriarMenu::menuListarReservas));

        menuReservas.adicionarOpcao(new OpcaoMenu("Editar Reserva", () -> {
            try {
                TratamentoDados.editarReserva();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));
        menuReservas.adicionarOpcao(new OpcaoMenu("Concluir Reserva", () -> {
            //TODO : A desenvolver
            TratamentoDados.terminarReserva();
            keyPress();
        }));
        menuReservas.adicionarOpcao(new OpcaoMenu("Cancelar Reserva", () -> {
            try {
                TratamentoDados.cancelarReserva();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
            //TODO : A desenvolver
            System.out.println("Empréstimo Criado...");
            keyPress();
        }));
        menuEmprestimos.adicionarOpcao(new OpcaoMenu("Editar Empréstimo", () -> {
            //TODO : A desenvolver
            System.out.println("Empréstimo Editado...");
            keyPress();
        }));
        menuEmprestimos.adicionarOpcao(new OpcaoMenu("Concluir Empréstimo", () -> {
            //TODO : A desenvolver
            System.out.println("Empréstimo Concluído...");
            keyPress();
        }));

        menuEmprestimos.exibir();
    }

    /**
     * Exibe o menu de listagem de reservas.
     */
    private static void menuListarReservas() {
        Menu menuListarReservas = new Menu("Listar Reservas");

        menuListarReservas.adicionarOpcao(new OpcaoMenu("Reserva Simplificada",  () -> {
            TratamentoDados.listaTodasReservas();
            keyPress();
        }));
        menuListarReservas.adicionarOpcao(new OpcaoMenu("Reserva Detalhada", () -> {
            try {
                TratamentoDados.listaTodasReservas();
                TratamentoDados.listarDetalhesReserva(TratamentoDados.lerInt("Insira o Id da reserva: ",false,null));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));

        menuListarReservas.exibir();
    }
}