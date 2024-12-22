import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class tratamentoDados {

    private static Scanner input = new Scanner(System.in);
    public static void criarCliente() {
        int nif, id, contacto, opcao;
        String nome, genero="";
        boolean flag;
        System.out.print("\nPor favor, insira o Id do Cliente: ");
        id = validarInteiro();
        do {
            System.out.print("\nPor favor, insira o Contribuinte do Cliente: ");
            nif = validarInteiro();
            flag=validarTamanho(String.valueOf(nif),9);
            if(!flag)
                System.out.print("Contribuinte Inválido! ex: 123456789: ");
        }while (!flag);
        do {
            System.out.print("\nPor favor, insira o Contacto do Cliente: ");
            contacto = validarInteiro();
            flag=validarTamanho(String.valueOf(contacto),9);
            if(!flag)
                System.out.print("Contribuinte Inválido! ex: 123456789: ");
        }while (!flag);
        System.out.print("\nPor favor, insira o nome do Cliente: ");
        nome=input.next();
        do{
            System.out.print("\nPor favor, insira o Genero do Cliente (1-M | 2-F): ");
            opcao = validarInteiro();
            switch (opcao){
                case 1:
                    genero="Masculino";
                    break;
                case 2:
                    genero="Feminino";
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }while(opcao<1||opcao>2);
        try {
            printFileCsv("clientes.csv", id, nome, genero, nif, contacto);
        } catch (IOException e) {
            System.err.println("Erro ao criar Cliente" + e.getMessage());
        }
    }
    public static int validarInteiro(){
        boolean isInt = false;
        int valor = 0;
        while(!isInt){
            try {
                valor = input.nextInt();
                isInt = true;
            } catch (Exception e) {
                System.out.print("Por favor, insira um número inteiro:");
                input.nextLine();
            }
        }
        return valor;
    }
    public static boolean validarTamanho(String valor, int tamanho){
        return valor.length()==tamanho;
    }
    public static void printFileCsv(String ficheiro, int Id, String nome, String genero, int nif, int contacto) throws IOException {
        FileWriter fw = new FileWriter(ficheiro, true);
        fw.append(Integer.toString(Id));
        fw.append(";");
        fw.append(nome);
        fw.append(";");
        fw.append(genero);
        fw.append(";");
        fw.append(Long.toString(nif));
        fw.append(";");
        fw.append(Long.toString(contacto));
        fw.append("\n");
        fw.flush();
        fw.close();
    }

    public static void lerFicehiro(String ficheiro){
        String arquivo = ficheiro;
        BufferedReader readFile = null;
        String linha = null;
        String csvDivisor = ";";
        ArrayList<String> dados= new ArrayList<String>();

        try{
            readFile = new BufferedReader(new FileReader(arquivo));
            while ((linha = readFile.readLine()) != null) {
                dados.add(linha);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        for (String dado : dados) {
            System.out.println(dado);
        }
    }
}
