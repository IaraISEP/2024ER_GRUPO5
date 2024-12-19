public class Main {

    public static void main(String[] args) {

        Menu menuPrincipal = new Menu("Gestão Biblioteca");


        menuPrincipal.adicionarOpcao(new OpcaoMenu("Cliente", () -> menuPrincipal.exibirSubmenu("Cliente")));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Itens Biblioteca", () -> menuPrincipal.exibirSubmenu("itens")));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Reservas", () -> menuPrincipal.exibirSubmenu("Reservas")));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Emprestimos", () -> menuPrincipal.exibirSubmenu("Emprestimos")));

        menuPrincipal.exibir();
    }

}