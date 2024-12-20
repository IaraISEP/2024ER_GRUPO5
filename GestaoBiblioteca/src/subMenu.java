import java.util.Scanner;

public class subMenu {

    private boolean exit;
    private int opcao;
    private Scanner input = new Scanner(System.in);

    public void subMenuSel(String ident) {
        while(!exit) {
            System.out.println("Escolha uma Opção: ");
            System.out.println();
            System.out.println("1 - Criar " + ident);
            System.out.println("2 - Listar " + ident);
            System.out.println("3 - Editar " + ident);
            System.out.println("4 - Apagar " + ident);
            System.out.println("0 - Sair");
            opcao = input.nextInt();
            input.nextLine();
            switch (opcao) {
                case 1:
                    if (ident.equals("Cliente")) {
                        Cliente cliente = new Cliente();
                        cliente.createCliente(); }
                    if (ident.equals("Livro")) { System.out.println("Criar " + ident); }
                    if (ident.equals("Jornal")) { System.out.println("Editar " + ident); }
                    if (ident.equals("Revista")) { System.out.println("Editar " + ident); }
                    if (ident.equals("Reservas")) { System.out.println("Editar " + ident); }
                    if (ident.equals("Emprestimos")) { System.out.println("Editar " + ident); }
                    break;
                case 2:
                    if (ident.equals("Cliente")) {
                        tratamentoDados dados = new tratamentoDados();
                        dados.lerFicehiro("clientes.csv"); }
                    if (ident.equals("Livro")) { System.out.println("Listar " + ident); }
                    if (ident.equals("Jornal")){ System.out.println("Listar " + ident); }
                    if (ident.equals("Revista")) { System.out.println("Listar " + ident); }
                    if (ident.equals("Reservas")) { System.out.println("Listar " + ident); }
                    if (ident.equals("Emprestimos")) { System.out.println("Listar " + ident); }
                    break;
                case 3:
                    if (ident.equals("Cliente")) { System.out.println("Editar " + ident); }
                    if (ident.equals("Livro")) { System.out.println("Editar " + ident); }
                    if (ident.equals("Jornal")) { System.out.println("Editar " + ident); }
                    if (ident.equals("Revista")) { System.out.println("Editar " + ident); }
                    if (ident.equals("Reservas")) { System.out.println("Editar " + ident); }
                    if (ident.equals("Emprestimos")) { System.out.println("Editar " + ident); }
                    break;
                case 4:
                    if (ident.equals("Cliente")) { System.out.println("Apagar " + ident);}
                    if (ident.equals("Livro")) { System.out.println("Apagar " + ident);}
                    if (ident.equals("Jornal")) { System.out.println("Apagar " + ident); }
                    if (ident.equals("Revista")) { System.out.println("Apagar " + ident); }
                    if (ident.equals("Reservas")) { System.out.println("Apagar " + ident); }
                    if (ident.equals("Emprestimos")) { System.out.println("Apagar " + ident); }
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
