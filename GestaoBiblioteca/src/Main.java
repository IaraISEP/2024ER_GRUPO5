public class Main {

    public static void main(String[] args) {

        Menu menuPrincipal = new Menu("Gestão Biblioteca");

        menuPrincipal.adicionarOpcao(new OpcaoMenu("Criar", () -> menuPrincipal.exibirSubmenu("Criar")));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Listar", () -> menuPrincipal.exibirSubmenu("Listar")));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Editar", () -> menuPrincipal.exibirSubmenu("Editar")));
        menuPrincipal.adicionarOpcao(new OpcaoMenu("Apagar", () -> menuPrincipal.exibirSubmenu("Apagar")));

        menuPrincipal.exibir();
    }

}