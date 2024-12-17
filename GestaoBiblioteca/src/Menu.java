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
        	case "Criar":
                submenu.adicionarOpcao(new OpcaoMenu("Criar Clientes", () -> {
                    Client cliente = new Client();
                    cliente.createClient();
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Criar Livros", () -> {
                	System.out.println("Livro criado...");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Criar Jornal", () -> {
                	System.out.println("Jornal criado...");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Criar Revista", () -> {
                	System.out.println("Revista criada...");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Criar Emprestimo", () -> exibirSubmenu("Criar Emprestimo")));
                submenu.adicionarOpcao(new OpcaoMenu("Criar Reserva", () -> exibirSubmenu("Criar Reserva")));
                break;
        	case "Criar Emprestimo":
                submenu.adicionarOpcao(new OpcaoMenu("Emprestimo de Livro", () -> {
                	System.out.println("Emprestimo de Livro Efectuado");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Emprestimo de Jornal", () -> {
                	System.out.println("Emprestimo de Jornal Efectuado");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Emprestimo de Revista", () -> {
                	System.out.println("Emprestimo de Revista Efectuada");
                	keyPress();
                }));
                break;
        	case "Criar Reserva":
                submenu.adicionarOpcao(new OpcaoMenu("Reserva de Livro", () -> {
                	System.out.println("Reserva de Livro Efectuado");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Reserva de Jornal", () -> {
                	System.out.println("Reserva de Jornal Efectuado");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Reserva de Revista", () -> {
                	System.out.println("Reserva de Revista Efectuado");
                	keyPress();
                }));
                break;
                
            case "Listar":
                submenu.adicionarOpcao(new OpcaoMenu("Listar Clientes", () -> exibirSubmenu("Listar Clientes")));
                submenu.adicionarOpcao(new OpcaoMenu("Listar Livros", () -> exibirSubmenu("Listar Livros")));
                submenu.adicionarOpcao(new OpcaoMenu("Listar Jornais", () -> exibirSubmenu("Listar Jornais")));
                submenu.adicionarOpcao(new OpcaoMenu("Listar Revistas", () -> exibirSubmenu("Listar Revistas")));
                submenu.adicionarOpcao(new OpcaoMenu("Listar Emprestimos", () -> {
                	System.out.println("Listagem de emprestimos");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar Reservas", () -> {
                	System.out.println("Listagem de reservas");
                	keyPress();
                }));
                break;
        	case "Listar Clientes":
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Nome", () -> {
                	System.out.println("Listando Clientes por Nome");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Nif", () -> {
                	System.out.println("Listando Clientes por Nif");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Contacto", () -> {
                	System.out.println("Listando Clientes por Contacto");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Emprestimo", () -> {
                	System.out.println("Listando Clientes por Emprestimo");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Reserva", () -> {
                	System.out.println("Listando Clientes por Reserva");
                	keyPress();
                }));
                break;
        	case "Listar Livros":
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Titulo", () -> {
                	System.out.println("Listando Livros por Titulo");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Editora", () -> {
                	System.out.println("Listando Livros por Editora");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Categoria", () -> {
                	System.out.println("Listando Livros por Categoria");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por ISBN", () -> {
                	System.out.println("Listando Livros por ISBN");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Emprestimo", () -> {
                	System.out.println("Listando Livros por Emprestimo");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Reserva", () -> {
                	System.out.println("Listando Livros por Reserva");
                	keyPress();
                }));
                break;
        	case "Listar Jornais":
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Titulo", () -> {
                	System.out.println("Listando Jornais por Titulo");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Editora", () -> {
                	System.out.println("Listando Jornais por Editora");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Categoria", () -> {
                	System.out.println("Listando Jornais por Categoria");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por ISSN", () -> {
                	System.out.println("Listando Jornais por ISSN");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Emprestimo", () -> {
                	System.out.println("Listando Jornais por Emprestimo");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Reserva", () -> {
                	System.out.println("Listando Jornais por Reserva");
                	keyPress();
                }));
                break;
        	case "Listar Revistas":
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Titulo", () -> {
                	System.out.println("Listando Revistas por Titulo");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Editora", () -> {
                	System.out.println("Listando Revistas por Editora");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Categoria", () -> {
                	System.out.println("Listando Revistas por Categoria");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por ISSN", () -> {
                	System.out.println("Listando Revistas por ISSN");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Emprestimo", () -> {
                	System.out.println("Listando Revistas por Emprestimo");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Listar por Reserva", () -> {
                	System.out.println("Listando Revistas por Reserva");
                	keyPress();
                }));
                break;
            case "Editar":
                submenu.adicionarOpcao(new OpcaoMenu("Editar Clientes", () -> {
                	System.out.println("Editar Clientes");
                	keyPress();
        		}));
                submenu.adicionarOpcao(new OpcaoMenu("Editar Livros", () -> {
                	System.out.println("Editar Livros");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Editar Jornal", () -> {
                	System.out.println("Editar Jornal");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Editar Revista", () -> {
                	System.out.println("Editar Revista");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Editar Emprestimo", () -> {
                	System.out.println("Editar Emprestimo");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Editar Reserva", () -> {
                	System.out.println("Editar Reserva");
                	keyPress();
                }));
                break;
            case "Apagar":
                submenu.adicionarOpcao(new OpcaoMenu("Apagar Clientes", () -> {
                	System.out.println("Cliente Apagado");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Apagar Livros", () -> {
                	System.out.println("Livro Apagado");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Apagar Jornal", () -> {
                	System.out.println("Jornal Apagado");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Apagar Revista", () -> {
                	System.out.println("Revista Apagada");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Apagar Emprestimo", () -> {
                	System.out.println("Emprestimo Apagado");
                	keyPress();
                }));
                submenu.adicionarOpcao(new OpcaoMenu("Apagar Reserva", () -> {
                	System.out.println("Reserva Apagada");
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
