import java.util.Scanner;

public class criarMenu {
    private static Scanner input = new Scanner(System.in);
    public static void menuPrincipal(){
        Menu menuPrincipal = new Menu("Gestão Biblioteca");
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Cliente", () -> menuCliente()));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Livro", () -> menuLivro()));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Jornal", () -> menuJornal()));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Revista", () -> menuRevista()));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Reservas", () -> menuReservas()));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Emprestimos", () -> menuEmprestimos()));
        menuPrincipal.exibir();
    }

    private static void keyPress() {
        System.out.println("\nPressione Enter para continuar...");
        input.nextLine();
    }
    private static void menuCliente(){
        Menu menuCliente = new Menu("Gestão Clientes");
        Cliente cliente = new Cliente();
        tratamentoDados dados = new tratamentoDados();

        menuCliente.adicionarOpcao(new OpcaoMenu("Criar Clientes", () -> {
            cliente.createCliente();
            keyPress();
        }));
        menuCliente.adicionarOpcao(new OpcaoMenu("Listar Clientes", () -> {
            dados.lerFicehiro("clientes.csv");
            keyPress();
        }));
        menuCliente.adicionarOpcao(new OpcaoMenu("Editar Cliente", () -> {
            System.out.println("Editar Cliente...");
            keyPress();
        }));
        menuCliente.adicionarOpcao(new OpcaoMenu("Apagar Cliente", () -> {
            System.out.println("Apagar Cliente...");
            keyPress();
        }));
        menuCliente.exibir();
    }

    private static void menuReservas() {
        Menu menuReservas = new Menu("Gestão de Reservas");

        menuReservas.adicionarOpcao(new OpcaoMenu("Criar Reserva", () -> {
            System.out.println("Reserva Criada...");
            keyPress();
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

    private static void menuLivro() {
        Menu menuLivro = new Menu("Gestão de Livros");

        menuLivro.adicionarOpcao(new OpcaoMenu("Criar Livro", () -> {
            System.out.println("Criar Livro...");
            keyPress();
        }));
        menuLivro.adicionarOpcao(new OpcaoMenu("Editar Livro", () -> {
            System.out.println("Editar Livro...");
            keyPress();
        }));
        menuLivro.adicionarOpcao(new OpcaoMenu("Apagar Livro", () -> {
            System.out.println("Apagar Livro...");
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
