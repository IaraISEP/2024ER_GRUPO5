import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Representa Classe responsavel pelo tratamento de dados
 * @author ER_GRUPO_5
 * @since 2024
 * Criação de novos objectos das classes
 * Criação de toda a estrutura de ficheiros
 * Edição e Leitura de ficheiros
 * */
public class TratamentoDados {

    private static Scanner input = new Scanner(System.in);
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Livro> livros = new ArrayList<>();
    private static List<Reserva> reservas = new ArrayList<>();


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
                new File("Biblioteca_1/Reservas"),
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
                new File("Biblioteca_1/Reservas/reservas.csv"),
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
     * @param valor valor introduzido pelo utilizador
     * @param tamanho Tamanho pre definido
     * */
    public static boolean validarTamanho(String valor, int tamanho){
        return valor.length()==tamanho;
    }

    /*
     * ########################### TRATAMENTO DE DADOS CLIENTE - INICIO #################################################
     * */

    /**
     * Metodo para apresentar ao utilizador os dados
     * que deve introduzir para criar ou editar um Cliente
     * */
    public static Cliente inserirDadosCliente(int id){
        int nif, contacto, opcao;
        String nome, genero="";
        boolean flag;
        do {
            System.out.print("\nPor favor, insira o Contribuinte do Cliente: ");
            nif = validarInteiro();
            nif = pesquisarNifArrayCliente(nif);
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
        System.out.println("\nPor favor, insira o Genero do Cliente: ");
        for (int i = 0; i < Constantes.Genero.values().length; i++) {
            System.out.println((i+1)+"- "+Constantes.Genero.values()[i]);
        }
        do{

            System.out.print("\nPor favor, insira a sua resposta: ");
            opcao = validarInteiro();
        }while(opcao<1||opcao>Constantes.Genero.values().length);
        return new Cliente(id, nome,Constantes.Genero.values()[opcao-1].toString(),nif,contacto,1);
    }

    /**
     * Metodo para criar novo Cliente
     * */
    public static void criarCliente() {
        clientes.add(inserirDadosCliente(pesquisarIdArrayCliente()));
    }

    /**
     * Metodo para editar o Cliente
     * */
    public static Cliente editarCliente(int id) {
        return inserirDadosCliente(id);
    }

    /**
     * Metódo para criar o ficehiro clientes.csv
     * e adicionar conteúdo ao mesmo.
     * */
    public static void criarFicheiroCsvCliente(String ficheiro, Cliente cliente, Boolean firstLine) throws IOException {

        FileWriter fw = new FileWriter(ficheiro, firstLine);
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

    public static void lerArrayClientes(){
        if(!clientes.isEmpty()){
            for (Cliente cliente : clientes) {
                System.out.println(
                        "ID: " + cliente.getId() + " " + cliente.getNome() +
                                " " + cliente.getGenero() + " " + cliente.getNif() +
                                " " + cliente.getContacto()
                );
            }
        }else {
            System.out.println("Array vazio");
        }
    }

    public static void pesquisarClientesPeloNif(){
        int idNif, index=0;
        boolean idFound = false;
        System.out.println("Digite o NIF do cliente que deseja encontrar: ");
        idNif = input.nextInt();
        if(!clientes.isEmpty()){
            for(int i = 0; i < clientes.size(); i++){
                int idActual =  clientes.get(i).getNif();
                if(idActual == idNif){
                    idFound = true;
                    index = i;
                }
            }
            if(idFound){
                System.out.println(
                        "ID: " + clientes.get(index).getId() +" "+ clientes.get(index).getNome() +
                                " " + clientes.get(index).getGenero() + " " + clientes.get(index).getNif() +
                                " " + clientes.get(index).getContacto()
                );
            }
            else{
                System.out.println("ID não encontrado!");
            }
        }else {
            System.out.println("Array vazio");
        }
    }

    public static void apagarClientePeloId() throws IOException {
        int idApagar, index=0, nif=0;
        boolean idFound = false;
        lerArrayClientes();
        System.out.println("Escolha o ID do cliente que deseja apagar: ");
        idApagar = input.nextInt();
        input.nextLine(); //Limpar buffer
        if(!clientes.isEmpty()){
            for(int i = 0; i < clientes.size(); i++){
                int idActual =  clientes.get(i).getId();
                if(idActual == idApagar){
                    index = i;
                    idFound = true;
                    nif = clientes.get(index).getNif();
                }
            }
            if(idFound){
                int nifReserva = clientes.getFirst().getNif();
                if (nif != nifReserva){
                    clientes.remove(index);
                    System.out.println("Cliente apagado com sucesso!");
                }else{
                    System.out.println("Reserva activa não pode apagar");
                }
            }
            else{
                System.out.println("ID não encontrado!");
            }
        }else {
            System.out.println("Array vazio");
        }
        gravarArrayClientes();
    }

    public static void editarClientePeloId() throws IOException {
        int idEditar;
        boolean idFound = false;
        lerArrayClientes();
        System.out.println("Escolha o ID do cliente que deseja editar: ");
        idEditar = input.nextInt();
        input.nextLine(); //Limpar buffer
        if(!clientes.isEmpty()){
            for(int i = 0; i < clientes.size(); i++){
                int idActual =  clientes.get(i).getId();
                if(idActual == idEditar){
                    clientes.set(i,editarCliente(i+1));
                    idFound = true;
                }
            }
            if(idFound){
                System.out.println("Cliente editado com sucesso!");
            }
            else{
                System.out.println("ID não encontrado!");
            }
        }else {
            System.out.println("Array vazio");
        }
        gravarArrayClientes();
    }

    public static void gravarArrayClientes() throws IOException {

        if(!clientes.isEmpty()){
            for(int i = 0; i < clientes.size(); i++){
                Cliente cliente = clientes.get(i);
                criarFicheiroCsvCliente("Biblioteca_1/Clientes/clientes.csv", cliente, i != 0);
            }
        }else {
            File file = new File("Biblioteca_1/Clientes/clientes.csv");
            file.delete();
            System.out.println("Array vazio");
        }
    }

    public static void lerFicheiroCsvClientes(String ficheiro){

        BufferedReader readFile;
        String linha;
        String csvDivisor = ";";
        try{
            readFile = new BufferedReader(new FileReader(ficheiro));
            while ((linha = readFile.readLine()) != null) {
                int id = Integer.parseInt(linha.split(csvDivisor)[0]);
                String  nome =linha.split(csvDivisor)[1],
                        genero = linha.split(csvDivisor)[2];
                int nif = Integer.parseInt(linha.split(csvDivisor)[3]),
                        contacto = Integer.parseInt(linha.split(csvDivisor)[4]);

                Cliente cliente = new Cliente(id, nome,genero,nif,contacto,1); //TODO : codBiblioteca a ser desenvolvido posteriormente
                clientes.add(cliente);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }
    /**
     * Metodo para atribuir ID automaticamente ao Cliente
     * */
    public static int pesquisarIdArrayCliente(){
        int valor = 0;
        if(!clientes.isEmpty()){
            for (Cliente cliente : clientes) {
                if (cliente.getId() >= valor) {
                    valor = cliente.getId();
                    valor++;
                }
            }
        }else {
            valor = 1;
        }
        return valor;
    }
    /**
     * Metodo para verificar se o NIF já se encontra
     * atribuido a algum Cliente
     * @param valor Recebe o valor introduzido pelo utilizador
     * */
    public static int pesquisarNifArrayCliente(int valor){
        if(!clientes.isEmpty()){
            for (Cliente cliente : clientes) {
                if (cliente.getNif() == valor) {
                    System.out.println("Nif existente!");
                    valor = 0;
                }
            }
        }
        return valor;
    }


    /*
     * ########################### TRATAMENTO DE DADOS CLIENTE - FIM #################################################
     * */

    /*
    * ########################### TRATAMENTO DE DADOS LIVROS - INICIO #################################################
    * */
    //  todo
    /*
     * ########################### TRATAMENTO DE DADOS LIVROS - FIM #################################################
     * */

    /*
     * ########################### TRATAMENTO DE DADOS RESERVAS - INICIO #################################################
     * */

    public static void lerFicheiroCsvReservas(String ficheiro){

        BufferedReader readFile;
        String linha;
        String csvDivisor = ";";
        //ArrayList<String> dados= new ArrayList<String>();

        try{
            readFile = new BufferedReader(new FileReader(ficheiro));
            while ((linha = readFile.readLine()) != null) {
                int nif = Integer.parseInt(linha.split(csvDivisor)[0]);
                Reserva reserva = new Reserva(nif);
                reservas.add(reserva);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            //e.printStackTrace(); //remover o erro do ecra
        }
        for (Reserva reserva : reservas) {
            System.out.println(reserva);
        }
    }

    /*
     * ########################### TRATAMENTO DE DADOS RESERVAS - FIM #################################################
     * */
}
