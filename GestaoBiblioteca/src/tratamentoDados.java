import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Classe responsavel pelo tratamento de dados
 * Criação de novos objectos das classes
 * Criação de toda a estrutura de ficheiros
 * Edição e Leitura de ficheiros
 * */
public class tratamentoDados {

    private static Scanner input = new Scanner(System.in);

    /**
     * Metodo para criar novo Cliente com validação de dados
     * introduzidos como NIF e Contacto
     * */
    public static void criarCliente() {
        int nif, id, contacto, opcao;
        String nome, genero="";
        boolean flag;
        //System.out.print("\nPor favor, insira o Id do Cliente: ");
        id = (lerIdFicheiro("clientes.csv")+1);
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
        nome=input.nextLine(); //Tem que ser next line para ler a String se tiver espaços
        for (int i = 0; i < Constantes.Genero.values().length; i++) {
            System.out.println((i+1)+"- "+Constantes.Genero.values()[i]);
        }
        do{
            System.out.print("\nPor favor, insira a sua resposta: ");
            opcao = validarInteiro();
        }while(opcao<1||opcao>Constantes.Genero.values().length);
        Cliente cliente = new Cliente(id, nome,genero,nif,contacto);
        try {
            createClientFileCsv("clientes.csv", cliente);
        } catch (IOException e) {
            System.err.println("Erro ao criar Cliente" + e.getMessage());
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
     * Metódo para criar o ficehiro clientes.csv
     * e adicionar conteúdo ao mesmo.
     * */
    public static void createClientFileCsv(String ficheiro, Cliente cliente) throws IOException {
        FileWriter fw = new FileWriter(ficheiro, true);
        fw.append(Integer.toString(cliente.getId()));
        fw.append(";");
        fw.append(cliente.getNome());
        fw.append(";");
        fw.append(cliente.getGenero());
        fw.append(";");
        fw.append(Integer.toString(cliente.getNif()));
        fw.append(";");
        fw.append(Integer.toString(cliente.getContacto()));
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
            System.out.println(e.getMessage());
            //e.printStackTrace(); //remover o erro do ecra
        }
        for (String dado : dados) {
            System.out.println(dado);
        }
    }

    public static int lerIdFicheiro(String ficheiro){

        String arquivo = ficheiro;
        BufferedReader readFile = null;
        String linha = null;
        String csvDivisor = ";";
        ArrayList<String> dados= new ArrayList<String>();
        int valor = 0;

        try{
            readFile = new BufferedReader(new FileReader(arquivo));
            while ((linha = readFile.readLine()) != null) {
                dados.add(linha.split(csvDivisor)[0]);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        for (String dado : dados) {
            if (Integer.parseInt(dado) > valor){
                valor = Integer.parseInt(dado);
            }
        }
        return valor;
    }
}
