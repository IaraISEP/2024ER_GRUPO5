import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/** Representa um Menu
 * @author ER_GRUPO_5
 * @since 2024
 */
public class Menu {
    private String titulo;
    private List<OpcaoMenu> opcoes;
    private boolean exit = true, numero = true;
    private Scanner input;

    /**
     * Construtor para criar um menu.
     *
     * @param titulo O título do menu.
     */
    public Menu(String titulo) {
        this.titulo = titulo;
        this.opcoes = new ArrayList<>();
        this.input = new Scanner(System.in);
    }

    /**
     * Adiciona uma opção ao menu.
     *
     * @param opcao A opção a ser adicionada.
     */
    public void adicionarOpcao(OpcaoMenu opcao) {
        opcoes.add(opcao);
    }

    /**
     * Exibe o menu e processa a seleção do usuário.
     */
    public void exibir() {
        int escolha=0;
        while (exit) {
            System.out.println("\n####################################");
            System.out.println("########## " + titulo.toUpperCase() + " ##########");
            System.out.println("####################################");
            System.out.println("Por favor, escolha uma opção:");

            for (int i = 0; i < opcoes.size(); i++) {
                System.out.println((i + 1) + " - " + opcoes.get(i).getDescricao());
            }
            System.out.println("0 - Sair");
            try {
                escolha = input.nextInt();
            }catch (Exception e) {
                escolha = -1;
            }
            input.nextLine(); // Limpar buffer da leitura do Inteiro
            if (escolha == 0) {
                exit = false;
            } else if (escolha > 0 && escolha <= opcoes.size()) {
                opcoes.get(escolha - 1).executarAcao();
            } else {
                System.out.println("Opção inválida. Tente novamente.");
                System.out.println("\nPressione Enter para continuar...");
                input.nextLine();
            }
        }
    }
}
