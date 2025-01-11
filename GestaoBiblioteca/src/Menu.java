import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Representa um Menu.
 * Esta classe permite criar e exibir um menu com várias opções.
 * O utilizador pode selecionar uma opção que o levará a uma área de gestão da aplicação.
 * O menu continua a ser exibido, entre menus, até que o utilizador escolha a opção de sair.
 *
 * @autor ER_GRUPO_5
 * @since 2024
 */
public class Menu {
    private String titulo;
    private List<OpcaoMenu> opcoes;
    private boolean exit = true;
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
     * Exibe o menu e processa a opção do utilizador.
     * O menu é exibido repetidamente até que o utilizador escolha a opção de sair.
     */
    public void exibir() {
        while (exit) {
            // Comprimento total da linha do menu
            int qtdTotal = Constantes.QtdTotalCharMenu;

            // Ajusta o comprimento total se o comprimento do título for ímpar
            if((qtdTotal - titulo.length()) % 2 != 0)
                qtdTotal++;

            // Calcula o comprimento do padding
            int padLen = ((qtdTotal - titulo.length()) / 2) - Constantes.qtdPad;
            String padding = " ".repeat(Math.max(0, padLen));

            // Imprime o cabeçalho do menu
            System.out.println("\n" + Constantes.MenuChar.repeat(qtdTotal));
            System.out.println(Constantes.MenuChar.repeat(Constantes.qtdPad) + padding + titulo.toUpperCase() + padding + Constantes.MenuChar.repeat(Constantes.qtdPad));
            System.out.println(Constantes.MenuChar.repeat(qtdTotal));
            System.out.println("Por favor, escolha uma opção:");

            // Imprime as opções do menu
            for (int i = 0; i < opcoes.size(); i++) {
                System.out.println((i + 1) + " - " + opcoes.get(i).getDescricao());
            }
            
            System.out.println("0 - Sair");

            int escolha;
            try {
                escolha = input.nextInt();
            } catch (Exception e) {
                escolha = -1;
            }
            
            input.nextLine(); // Limpar buffer da leitura do Inteiro

            // Processa a seleção do utilizador
            if (escolha == 0) {
                exit = false; // Sai do loop do menu
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
