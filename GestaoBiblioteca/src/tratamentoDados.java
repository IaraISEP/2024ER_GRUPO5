import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Classe responsavel pelo tratamento de dados
 * Criação de novos objectos das classes
 * Criação de toda a estrutura de ficheiros
 * Edição e Leitura de ficheiros
 * */
public class tratamentoDados {

    private static Scanner input = new Scanner(System.in);
    private static List<Cliente> clientes = new ArrayList<Cliente>();

    /**
     * Metodo para criar a estrutura de ficheiros para
     * guardar os dados permanentemente
     * Cria 7 Directorios com um ficheiro cada
     * */
    public static void criarSistemaFicheiros() throws IOException {
        File[] dirs = new File[]{
                new File("Biblioteca_1/Clientes"),
                new File("Biblioteca_1/Livros"),
                new File("Biblioteca_1/Jornais"),
                new File("Biblioteca_1/Revistas"),
                new File("Biblioteca_1/Emprestimos"),
                new File("Biblioteca_1/Reseervas"),
                new File("Biblioteca_1/Historico"),
        };
        for (File dir : dirs) {
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        File[] files = new File[]{
                new File("Biblioteca_1/Clientes/clientes.csv"),
                new File("Biblioteca_1/Livros/livros.csv"),
                new File("Biblioteca_1/Jornais/jornais.csv"),
                new File("Biblioteca_1/Revistas/revistas.csv"),
                new File("Biblioteca_1/Emprestimos/emprestimos.csv"),
                new File("Biblioteca_1/Reseervas/reseervas.csv"),
                new File("Biblioteca_1/Historico/historico.csv")
        };
        for (File file : files) {
            if (!file.exists()) {
                file.createNewFile();
            }
        }
    }

    /**
     * Metódo para validar se o User introduziu um
     * valor inteiro
     * */
    public static int validarInteiro(){
        boolean isInt = false;
        int valor = 0;
        while(!isInt){
            try {
                valor = input.nextInt();
                input.nextLine(); // necessário para limpar buffer
                isInt = true;
            } catch (Exception e) {
                System.out.print("Por favor, insira um número inteiro:");
                input.nextLine();
            }
        }
        return valor;
    }

    /**
     * Função para validar tamanho
     * */
    public static boolean validarTamanho(String valor, int tamanho){
        return valor.length()==tamanho;
    }

    /**
     * Metodo para criar novo Cliente com validação de dados
     * introduzidos como NIF e Contacto
     * */

}
