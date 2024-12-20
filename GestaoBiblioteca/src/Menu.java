import java.util.Scanner;

public class Menu {


    private int opcao;
    private Scanner input = new Scanner(System.in);
    private boolean exit = false;

    public Menu() {

    }
    public void mainMenu() {

        while(!exit) {
            subMenu subMenu = new subMenu();
            System.out.println("Escolha uma Opção: ");
            System.out.println();
            System.out.println("1 - Cliente");
            System.out.println("2 - Livros");
            System.out.println("3 - Jornais");
            System.out.println("4 - Revistas");
            System.out.println("5 - Reservas");
            System.out.println("6 - Empréstimos");
            System.out.println("0 - Sair");
            opcao = input.nextInt();
            input.nextLine();
            switch(opcao) {
                case 1:
                    subMenu.subMenuSel("Cliente");
                    break;
                case 2:
                    subMenu.subMenuSel("Livro");
                    break;
                case 3:
                    subMenu.subMenuSel("Jornal");
                    break;
                case 4:
                    subMenu.subMenuSel("Revista");
                    break;
                case 5:
                    subMenu.subMenuSel("Reservas");
                    break;
                case 6:
                    subMenu.subMenuSel("Emprestimos");
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Opção Inválida!");
                    break;
            }
        }
    }

}
