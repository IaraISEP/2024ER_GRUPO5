import java.io.*;

import java.time.LocalDateTime;
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
        int idApagar, index=0, nif=0, nifReserva=0;
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
                if(!reservas.isEmpty()){
                    nifReserva = reservas.getFirst().getNif();
                }
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

    public static Livro inserirDadosLivro(int id){
        int anoEdicao = 0;
        String titulo = "", editora = "", categoria = "", isbn = "", autor = "";
        boolean flag;
        do {
            System.out.print("\nPor favor, insira o ISBN do Livro: ");
            isbn = input.nextLine();
            isbn = pesquisarIsbnArrayLivro(isbn);
            flag=validarTamanho((isbn),9);
            if(!flag)
                System.out.print("ISBN Inválido! ex: 1234-5678: ");
        }while (!flag);
        System.out.print("\nPor favor, insira o Titulo do Livro: ");
        titulo = input.nextLine();
        System.out.print("\nPor favor, insira o Editora do Livro: ");
        editora = input.nextLine();
        System.out.print("\nPor favor, insira o Categoria do Livro: ");
        categoria = input.nextLine();
        System.out.print("\nPor favor, insira o Autor do Livro: ");
        autor = input.nextLine();
        System.out.print("\nPor favor, insira o Ano edição do Livro: ");
        anoEdicao = input.nextInt();

        return new Livro(id, titulo, editora, categoria, anoEdicao, isbn, autor,1);
    }

    /**
     * Metodo para criar novo Livro
     * */
    public static void criarLivro() {
        livros.add(inserirDadosLivro(pesquisarIdArrayLivro()));
    }

    /**
     * Metodo para editar o Livro
     * */
    public static Livro editarLivro(int id) {
        return inserirDadosLivro(id);
    }

    public static void lerArrayLivros(){
        if(!livros.isEmpty()){
            for (Livro livro : livros) {
                System.out.println(
                        "ID: " + livro.getId() + " " + livro.getTitulo() +
                                " " + livro.getEditora() + " " + livro.getCategoria() +
                                " " + livro.getAnoEdicao() + " " + livro.getIsbn() +
                                " " + livro.getAutor()
                );
            }
        }else {
            System.out.println("Array vazio");
        }
    }

    public static void pesquisarLivrosPeloIsbn(){
        int index=0;
        String isbn = "";
        boolean idFound = false;
        System.out.println("Digite o ISBN do livro que deseja encontrar: ");
        isbn = input.nextLine();
        if(!livros.isEmpty()){
            for(int i = 0; i < livros.size(); i++){
                String idActual =  livros.get(i).getIsbn();
                if(idActual.equals(isbn)){
                    idFound = true;
                    index = i;
                }
            }
            if(idFound){
                System.out.println(
                        "ID: " + livros.get(index).getId() +" "+ livros.get(index).getTitulo() +
                                " " + livros.get(index).getEditora() + " " + livros.get(index).getCategoria() +
                                " " + livros.get(index).getAnoEdicao() +
                                " " + livros.get(index).getIsbn() +
                                " " + livros.get(index).getAutor() +
                                " " + livros.get(index).getCodBiblioteca()
                );
            }
            else{
                System.out.println("ID não encontrado!");
            }
        }else {
            System.out.println("Array vazio");
        }
    }

    public static void apagarLivroPeloIsbn() throws IOException {
        int idApagar, index=0;
        String isbn="", isbnReserva="";
        boolean idFound = false;
        lerArrayLivros();
        System.out.println("Escolha o ID do livro que deseja apagar: ");
        idApagar = input.nextInt();
        input.nextLine(); //Limpar buffer
        if(!livros.isEmpty()){
            for(int i = 0; i < livros.size(); i++){
                int idActual =  livros.get(i).getId();
                if(idActual == idApagar){
                    index = i;
                    idFound = true;
                    isbn = livros.get(index).getIsbn();
                }
            }
            if(idFound){
                if (!reservas.isEmpty()) {
                    isbnReserva = reservas.getFirst().getIsbn();
                }
                if (!isbn.equals(isbnReserva)){
                    livros.remove(index);
                    System.out.println("Livro apagado com sucesso!");
                }else{
                    System.out.println("Reserva activa não pode apagar");
                }
            }
            else{
                System.out.println("ID não encontrado!");
            }
        }else {
            File file = new File("Biblioteca_1/Livros/livros.csv");
            file.delete();
            System.out.println("Array vazio");
        }
        gravarArraylivros();
    }

    public static void criarFicheiroCsvlivro(String ficheiro, Livro livro, Boolean firstLine) throws IOException {

        FileWriter fw = new FileWriter(ficheiro, firstLine);

        fw.append(Integer.toString(livro.getId()));
        fw.append(";");
        fw.append(livro.getTitulo());
        fw.append(";");
        fw.append(livro.getEditora());
        fw.append(";");
        fw.append(livro.getCategoria());
        fw.append(";");
        fw.append(Integer.toString(livro.getAnoEdicao()));
        fw.append(";");
        fw.append(livro.getAutor());
        fw.append(";");
        fw.append(livro.getIsbn());
        fw.append(";");
        fw.append(Integer.toString(1));
        fw.append(";");
        fw.append("\n");
        fw.flush();
        fw.close();
    }

    public static void gravarArraylivros() throws IOException {

        if(!livros.isEmpty()){
            for(int i = 0; i < livros.size(); i++){
                Livro livro = livros.get(i);
                criarFicheiroCsvlivro("Biblioteca_1/Livros/livros.csv", livro, i != 0);
            }
        }else {
            File file = new File("Biblioteca_1/Livros/livros.csv");
            file.delete();
            System.out.println("Array vazio");
        }
    }

    public static int pesquisarIdArrayLivro(){
        int valor = 0;
        if(!livros.isEmpty()){
            for (Livro livro : livros) {
                if (livro.getId() >= valor) {
                    valor = livro.getId();
                    valor++;
                }
            }
        }else {
            valor = 1;
        }
        return valor;
    }

    public static void editarLivroPeloId() throws IOException {
        int idEditar;
        boolean idFound = false;
        lerArrayLivros();
        System.out.println("Escolha o ID do Livro que deseja editar: ");
        idEditar = input.nextInt();
        input.nextLine(); //Limpar buffer
        if(!livros.isEmpty()){
            for(int i = 0; i < livros.size(); i++){
                int idActual =  livros.get(i).getId();
                if(idActual == idEditar){
                    livros.set(i,editarLivro(i+1));
                    idFound = true;
                }
            }
            if(idFound){
                System.out.println("Livro editado com sucesso!");
            }
            else{
                System.out.println("ID não encontrado!");
            }
        }else {
            System.out.println("Array vazio");
        }
        gravarArraylivros();
    }

    public static String pesquisarIsbnArrayLivro(String isbn){
        if(!livros.isEmpty()){
            for (Livro livro : livros) {
                if (livro.getIsbn().equals(isbn)) {
                    System.out.println("ISBN já existe!");
                    isbn = "";
                }
            }
        }
        return isbn;
    }

    public static void lerFicheiroCsvLivros(String ficheiro){

        BufferedReader readFile;
        String linha;
        String csvDivisor = ";";
        //ArrayList<String> dados= new ArrayList<String>();

        try{
            readFile = new BufferedReader(new FileReader(ficheiro));
            while ((linha = readFile.readLine()) != null) {
                int     id = Integer.parseInt(linha.split(csvDivisor)[0]);
                String  titulo =linha.split(csvDivisor)[1],
                        editora = linha.split(csvDivisor)[2],
                        categoria = linha.split(csvDivisor)[3];

                int        anoEdicao = Integer.parseInt(linha.split(csvDivisor)[4]);
                String  isbn = linha.split(csvDivisor)[5],
                        autor = linha.split(csvDivisor)[6];

                Livro livro = new Livro(id, titulo, editora, categoria, anoEdicao, isbn, autor,1);
                livros.add(livro);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            //e.printStackTrace(); //remover o erro do ecra
        }
        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }

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
                String isbn = linha.split(csvDivisor)[1];

                //TODO : Alterar a maneira como constroi o objeto para adicionar à lista de reservas assim que o resto da lógica estiver completa
                LocalDateTime dataInicio = LocalDateTime.now();
                LocalDateTime dataFim = dataInicio.plusDays(7); // Example duration
                LocalDateTime dataRegisto = LocalDateTime.now();
                Cliente cliente = null;
                List<Livro> livros = new ArrayList<>();
                List<Jornal> jornais = new ArrayList<>();
                List<Revista> revistas = new ArrayList<>();

                Reserva reserva = new Reserva(1, 1, dataInicio, dataFim, cliente, livros, jornais, revistas, dataRegisto, nif, isbn);

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
