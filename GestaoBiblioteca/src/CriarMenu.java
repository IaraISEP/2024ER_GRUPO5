import java.io.IOException;
import java.util.Scanner;

/**
 * Representa a criação de menus para a gestão da biblioteca.
 * Esta classe contém métodos para criar e exibir diferentes menus relacionados à biblioteca,
 * como menus para gestão de clientes, livros, jornais, revistas, reservas e empréstimos.
 * Também inclui métodos para listar e exibir detalhes desses itens.
 *
 * @since 2024
 */
public class CriarMenu {
    private static Scanner input = new Scanner(System.in);

    /**
     * Cria e exibe o menu principal da gestão da biblioteca.
     * Este menu principal contém opções para acessar submenus de gestão de bibliotecas, clientes,
     * livros, jornais, revistas, reservas, empréstimos e listagens.
     */
    public static void menuPrincipal(){
        Menu menuPrincipal = new Menu("Gestão Biblioteca");
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Bilioteca", CriarMenu::menuBiblioteca));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Cliente", CriarMenu::menuCliente));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Livro", CriarMenu::menuLivro));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Jornal", CriarMenu::menuJornal));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Revista", CriarMenu::menuRevista));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Reservas", CriarMenu::menuReservas));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Emprestimos", CriarMenu::menuEmprestimos));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Listagens", CriarMenu::menuListagens));
        menuPrincipal.exibir();
    }

    /**
     * Aguarda que o utilizador pressione a tecla Enter.
     * Este método é utilizado para pausar a execução do programa até que o utilizador pressione Enter,
     * permitindo que o utilizador leia mensagens ou resultados exibidos no console.
     */
    private static void keyPress() {
        System.out.println("\nPressione Enter para continuar...");
        input.nextLine();
    }

    /**
     * Cria e exibe o menu de gestão de bibliotecas.
     * Este menu contém opções para iniciar sessão, criar, listar, editar e apagar bibliotecas.
     */
    public static void menuBiblioteca(){
        Menu menuBiblioteca = new Menu("Gestão Bibliotecas");

        menuBiblioteca.adicionarOpcao(new OpcaoMenu("Iniciar Sessão", () -> {
            try {
                TratamentoDados.inicioSessao();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));

        menuBiblioteca.adicionarOpcao(new OpcaoMenu("Criar Biblioteca", () -> {
            try {
                TratamentoDados.criarBiblioteca();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));

        menuBiblioteca.adicionarOpcao(new OpcaoMenu("Listar Biblioteca", () -> {
            TratamentoDados.listaTodasBibliotecas();
            keyPress();
        }));

        menuBiblioteca.adicionarOpcao(new OpcaoMenu("Editar Biblitoeca", () -> {
            try {
                TratamentoDados.editarBiblioteca();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));

        menuBiblioteca.adicionarOpcao(new OpcaoMenu("Apagar Biblitoeca", () -> {
            try {
                TratamentoDados.apagarBiblioteca();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));

        menuBiblioteca.exibir();
    }

    /**
     * Cria e exibe o menu de gestão de clientes.
     * Este menu contém opções para criar, listar, editar e apagar clientes.
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
     * Cria e exibe o menu de listagem de clientes.
     * Este menu contém opções para listar todos os clientes ou listar clientes por parâmetro.
     */
    private static void menuListarClientes() {
        Menu menuListarClientes = new Menu("Listar Clientes");

        menuListarClientes.adicionarOpcao(new OpcaoMenu("Todos Clientes",  () -> {
            TratamentoDados.listaTodosClientes();
            keyPress();
        }));
        menuListarClientes.adicionarOpcao(new OpcaoMenu("Listar Clientes por parâmetro.", () -> {
            TratamentoDados.listaClientePorParametro();
            keyPress();
        }));

        menuListarClientes.exibir();
    }

    /**
     * Cria e exibe o menu de gestão de livros.
     * Este menu contém opções para criar, listar, editar e apagar livros.
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

        menuLivro.exibir();
    }

    /**
     * Cria e exibe o menu de listagem de livros.
     * Este menu contém opções para listar todos os livros ou listar livros por ISBN.
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
     * Cria e exibe o menu de gestão de jornais.
     * Este menu contém opções para criar, listar, editar e apagar jornais.
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

        menuJornal.exibir();
    }

    /**
     * Cria e exibe o menu de listagem de jornais.
     * Este menu contém opções para listar todos os jornais ou listar jornais por ISSN.
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
     * Cria e exibe o menu de gestão de revistas.
     * Este menu contém opções para criar, listar, editar e apagar revistas.
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
        menuRevista.exibir();
    }

    /**
     * Cria e exibe o menu de listagem de revistas.
     * Este menu contém opções para listar todas as revistas ou listar revistas por ISSN.
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
     * Cria e exibe o menu de gestão de reservas.
     * Este menu contém opções para criar, listar, editar, concluir e cancelar reservas.
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
            try {
                TratamentoDados.concluirReserva();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));
        menuReservas.adicionarOpcao(new OpcaoMenu("Cancelar Reserva", () -> {
                boolean flag = TratamentoDados.listaTodasReservas(Constantes.Etapa.CANCELAR);
                if (flag) {
                    try {
                        TratamentoDados.cancelarReserva( Constantes.Estado.CANCELADO);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            keyPress();
        }));

        menuReservas.exibir();
    }

    /**
     * Cria e exibe o menu de gestão de empréstimos.
     * Este menu contém opções para criar, listar, editar, cancelar e concluir empréstimos.
     */
    private static void menuEmprestimos() {
        Menu menuEmprestimos = new Menu("Gestão de Empréstimos");

        menuEmprestimos.adicionarOpcao(new OpcaoMenu("Criar Empréstimo", () -> {
            try {
                TratamentoDados.criarEmprestimo();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));
        menuEmprestimos.adicionarOpcao(new OpcaoMenu("Listar Empréstimo", CriarMenu::menuListarEmprestimos));
        menuEmprestimos.adicionarOpcao(new OpcaoMenu("Editar Empréstimo", () -> {
            try {
                TratamentoDados.editarEmprestimo(Constantes.Etapa.EDITAR);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));
        menuEmprestimos.adicionarOpcao(new OpcaoMenu("Cancelar Empréstimo", () -> {
            try {
                TratamentoDados.concluirCancelarEmprestimo(Constantes.Etapa.CANCELAR);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));
        menuEmprestimos.adicionarOpcao(new OpcaoMenu("Concluir Empréstimo", () -> {
            try {
                TratamentoDados.concluirCancelarEmprestimo(Constantes.Etapa.CONCLUIR);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            keyPress();
        }));

        menuEmprestimos.exibir();
    }

    /**
     * Cria e exibe o menu de listagens de reservas e empréstimos.
     * Este menu contém opções para listar reservas e empréstimos por cliente, por data,
     * calcular o tempo médio de empréstimos e identificar o tipo de item mais requisitado.
     */
    private static void menuListagens(){
        Menu menuListagens = new Menu("Listagens de Reservas / Emprestimos");

        menuListagens.adicionarOpcao(new OpcaoMenu("Listar Reservas / Emprestimos do Cliente", () -> {
            TratamentoDados.listarTodasReservasEmprestimoCliente();
            keyPress();
        }));
        menuListagens.adicionarOpcao(new OpcaoMenu("Listar Reservas / Emprestimos do Cliente por data", () -> {
            TratamentoDados.listarTodasReservasEmprestimoClienteData();
            keyPress();
        }));
        menuListagens.adicionarOpcao(new OpcaoMenu("Total de Reservas / Emprestimos por data", () -> {
            TratamentoDados.listarTodasReservasEmprestimoData();
            keyPress();
        }));
        menuListagens.adicionarOpcao(new OpcaoMenu("Tempo medio Reservas / Emprestimos por data", () -> {
            TratamentoDados.emprestimoMedioData();
            keyPress();
        }));
        menuListagens.adicionarOpcao(new OpcaoMenu("Tipo Item mais Reservado / Emprestado ( Data )", () -> {
            TratamentoDados.itemMaisRequisitadoData();
            keyPress();
        }));
        menuListagens.exibir();
    }

    /**
     * Cria e exibe o menu de listagem de reservas.
     * Este menu contém opções para listar reservas de forma simplificada ou detalhada.
     */
    private static void menuListarReservas() {
        Menu menuListarReservas = new Menu("Listar Reservas");

        menuListarReservas.adicionarOpcao(new OpcaoMenu("Reserva Simplificada",  () -> {
            TratamentoDados.listaTodasReservas(null);
            keyPress();
        }));
        menuListarReservas.adicionarOpcao(new OpcaoMenu("Reserva Detalhada", () -> {

                boolean flag = TratamentoDados.listaTodasReservas(null);
                if (flag) {
                    try {
                        TratamentoDados.listarDetalhesReserva(0, Constantes.Etapa.LISTAR);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            keyPress();
        }));

        menuListarReservas.exibir();
    }

    /**
     * Cria e exibe o menu de listagem de empréstimos.
     * Este menu contém opções para listar empréstimos de forma simplificada ou detalhada.
     */
    private static void menuListarEmprestimos() {
        Menu menuListarEmprestimos = new Menu("Listar Empréstimos");

        menuListarEmprestimos.adicionarOpcao(new OpcaoMenu("Empréstimos Simplificados",  () -> {
            TratamentoDados.listaTodosEmprestimos(Constantes.Etapa.LISTAR);
            keyPress();
        }));
        menuListarEmprestimos.adicionarOpcao(new OpcaoMenu("Empréstimos Detalhados", () -> {
            TratamentoDados.listarDetalhesEmprestimo();
            keyPress();
        }));

        menuListarEmprestimos.exibir();
    }
}