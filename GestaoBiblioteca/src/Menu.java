import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private String titulo;
    private List<OpcaoMenu> opcoes;
    private boolean exit = true, numero = true;
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
