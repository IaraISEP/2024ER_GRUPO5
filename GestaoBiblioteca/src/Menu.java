import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private String titulo;
    private List<OpcaoMenu> opcoes;
    private boolean exit = true;
    private Scanner input;

    public Menu(String titulo) {
        this.titulo = titulo;
        this.opcoes = new ArrayList<>();
        this.input = new Scanner(System.in);
    }

    public void adicionarOpcao(OpcaoMenu opcao) {
        opcoes.add(opcao);
    }

    public void exibir() {
        while (exit) {
            System.out.println("\n####################################");
            System.out.println("########## " + titulo.toUpperCase() + " ##########");
            System.out.println("####################################");
            System.out.println("Por favor, escolha uma opção:");

            for (int i = 0; i < opcoes.size(); i++) {
                System.out.println((i + 1) + " - " + opcoes.get(i).getDescricao());
            }
            System.out.println("0 - Sair");

            int escolha = input.nextInt();
            input.nextLine();

            if (escolha == 0) {
                exit = false;
            } else if (escolha > 0 && escolha <= opcoes.size()) {
                opcoes.get(escolha - 1).executarAcao();
            } else {
                System.out.println("Opção inválida. Tente novamente.");
                keyPress();
            }
        }
    }

    public void setExit(boolean exit) {

        this.exit = exit;
    }
    
    public void keyPress() {
        System.out.println("\nPressione Enter para continuar...");
        input.nextLine();
    }
    
    public void exibirSubmenu(String tipo) {
        Menu submenu = new Menu(tipo);

        switch (tipo) {
        	case "Cliente":
                submenu.adicionarOpcao(new OpcaoMenu("Criar Clientes", () -> {
                    Cliente cliente = new Cliente();
                    cliente.createCliente();
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar Clientes", () -> {
                	System.out.println("Listar Clientes...");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Editar Cliente", () -> {
                	System.out.println("Editar Cliente...");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Apagar Cliente", () -> {
                	System.out.println("Apagar Cliente...");
                	keyPress();
                }));
/*                submenu.adicionarOpcao(new OpcaoMenu("Criar Emprestimo", () -> exibirSubmenu("Criar Emprestimo")));
                submenu.adicionarOpcao(new OpcaoMenu("Criar Reserva", () -> exibirSubmenu("Criar Reserva")));*/
                break;
        	case "itens":
                submenu.adicionarOpcao(new OpcaoMenu("Livro", () -> {
                    exibirSubmenu("Livro");
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Jornal", () -> {
                    exibirSubmenu("Jornal");
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Revista", () -> {
                    exibirSubmenu("Revista");
                }));
                break;
        	case "Livro":
                submenu.adicionarOpcao(new OpcaoMenu("Criar Livro", () -> {
                	System.out.println("Criar Livro ... ");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Editar Livro", () -> {
                	System.out.println("Editar Livro ... ");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Apagar Livro", () -> {
                	System.out.println("Apagar Livro ... ");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Reservar Livro", () -> {
                    System.out.println("Reservar Livro ... ");
                    keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Emprestimo Livro", () -> {
                    System.out.println("Emprestimo Livro ... ");
                    keyPress();
                }));
                break;
            case "Jornal":
                submenu.adicionarOpcao(new OpcaoMenu("Criar Jornal", () -> {
                    System.out.println("Criar Jornal ... ");
                    keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Editar Jornal", () -> {
                    System.out.println("Editar Jornal ... ");
                    keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Apagar Jornal", () -> {
                    System.out.println("Apagar Jornal ... ");
                    keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Reservar Jornal", () -> {
                    System.out.println("Reservar Jornal ... ");
                    keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Emprestimo Jornal", () -> {
                    System.out.println("Emprestimo Jornal ... ");
                    keyPress();
                }));
                break;
            case "Revista":
                submenu.adicionarOpcao(new OpcaoMenu("Criar Revista", () -> {
                    System.out.println("Criar Revista ... ");
                    keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Editar Revista", () -> {
                    System.out.println("Editar Revista ... ");
                    keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Apagar Revista", () -> {
                    System.out.println("Apagar Revista ... ");
                    keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Reservar Revista", () -> {
                    System.out.println("Reservar Revista ... ");
                    keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Emprestimo Revista", () -> {
                    System.out.println("Emprestimo Revista ... ");
                    keyPress();
                }));
                break;
            default:
                System.out.println("Submenu desconhecido!");
                keyPress();
                return;
        }

        submenu.exibir();
    }

}
