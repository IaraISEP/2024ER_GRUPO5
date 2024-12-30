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
    private static List<Emprestimo> emprestimos = new ArrayList<>();
    private static List<Reserva> reservas = new ArrayList<>();
    private static List<Jornal> jornais = new ArrayList<>();
    private static List<Revista> revistas = new ArrayList<>();

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

    /*
     * ########################### TRATAMENTO DE DADOS CLIENTE - INICIO #################################################
     * */

    /**
     * Metodo para apresentar ao utilizador os dados
     * que deve introduzir para criar ou editar um Cliente
     * */
    public static Cliente inserirDadosCliente(int id, Constantes.Etapa etapa)
    {
        int nif, contacto;
        boolean flag;
        Constantes.Genero genero = null;

        do {
            nif = lerInt("\nPor favor, insira o Contribuinte do Cliente: ", false);
            nif = pesquisarNifArrayCliente(nif, etapa, id);
            flag = validarTamanho(String.valueOf(nif),9);

            if(!flag)
                System.out.print("Contribuinte Inválido! ex: 123456789");
        } while (!flag);

        String nome = lerString("\nPor favor, insira o nome do Cliente: ");

        do {
            char gen = lerChar("\nPor favor, insira o Genero do Cliente (M/F): ");
            flag = (gen == 'M' || gen == 'F');
            if (flag)
                genero = Constantes.Genero.fromGenero(gen);
            else
                System.out.print("O gênero introduzido não é válido!");

        } while (!flag);

        do {
            contacto = lerInt("\nPor favor, insira o Contacto do Cliente: ", false);
            flag=validarTamanho(String.valueOf(contacto),9);

            if(!flag)
                System.out.print("Número de contacto com formato inválido! ex: 912345678");
        } while (!flag);

        return new Cliente(id, nome, genero, nif, contacto,1);
    }

    /**
     * Metodo para criar novo Cliente
     * */
    public static void criarCliente() {
        clientes.add(inserirDadosCliente(pesquisarIdArray(Constantes.TipoItem.CLIENTE), Constantes.Etapa.CRIAR));
    }

    /**
     * Edita um cliente pelo ID fornecido.
     *
     * @throws IOException Se ocorrer um erro de I/O durante a gravação dos dados.
     */
    public static void editarCliente() throws IOException {
        // Verifica se a lista de clientes está vazia
        if(clientes.isEmpty()) {
            System.out.println("Não há clientes nesta biblioteca.");
            return;
        }

        // Lista todos os clientes
        listaTodosClientes();

        // Lê o ID do cliente a ser apagado
        int idEditar = lerInt("Escolha o ID do cliente que deseja editar: ", false);

        // Procura o cliente pelo ID e, caso encontre, edita o cliente
        for(int index = 0; index < clientes.size(); index++) {
            if (clientes.get(index).getId() == idEditar) {
                Cliente cliente = inserirDadosCliente(idEditar, Constantes.Etapa.EDITAR);
                // Grava as alterações na Array dos clientes
                clientes.set(index, cliente);
                System.out.println("Cliente editado com sucesso!");
                gravarArrayClientes();
                return;
            }
        }

        System.out.println("ID não encontrado!");
    }

    /**
     * Metódo para criar o ficehiro clientes.csv
     * e adicionar conteúdo ao mesmo.
     * */
    public static void criarFicheiroCsvCliente(String ficheiro, Cliente cliente, Boolean firstLine) throws IOException {
        try (FileWriter fw = new FileWriter(ficheiro, firstLine)) {
            fw.write(cliente.getId() + ";" + cliente.getNome() + ";" + cliente.getGenero() + ";" + cliente.getNif() + ";" + cliente.getContacto() + "\n");
        }
    }

    /**
     * Metódo que lista todos os Clientes existentes na biblioteca
     * */
    public static void listaTodosClientes(){
        if (clientes.isEmpty())
            System.out.println("Não existem clientes para mostrar.");
        else
            mostraTabelaClientes(clientes);
    }

    /**
     * Lista um cliente pelo NIF fornecido.
     * Se o cliente for encontrado, exibe os detalhes do cliente.
     * Caso contrário, exibe uma mensagem a informar que o NIF não existe.
     */
    public static void listaClientePorNif(){
        // Lê o NIF do cliente a ser encontrado
        int idNif = lerInt("Digite o NIF do cliente que deseja encontrar: ", false);

        // Verifica se a lista de clientes está vazia
        if(clientes.isEmpty()){
            System.out.println("Array vazio");
            return;
        }

        // Procura o cliente pelo NIF
        for(Cliente cliente : clientes) {
            if (cliente.getNif() == idNif) {
                List<Cliente> clienteComNif = new ArrayList<>();
                clienteComNif.add(cliente);
                mostraTabelaClientes(clienteComNif);
                return;
            }
        }

        System.out.println("O NIF que inseriu não existe.");
    }

    /**
     * Apaga um cliente pelo ID fornecido.
     *
     * @throws IOException Se ocorrer um erro de I/O durante a gravação dos dados.
     */
    public static void apagarClientePeloId() throws IOException {
        // Verifica se a lista de clientes está vazia
        if(clientes.isEmpty()) {
            System.out.println("Não há clientes nesta biblioteca.");
            return;
        }

        // Lista todos os clientes
        listaTodosClientes();

        // Lê o ID do cliente a ser apagado
        int idApagar = lerInt("Escolha o ID do cliente que deseja apagar: ", false);

        Cliente clienteApagar = null;

        // Procura o cliente pelo ID e obtém o objeto a apagar
        for(Cliente cliente : clientes) {
            if (cliente.getId() == idApagar) {
                clienteApagar = cliente;
                break;
            }
        }

        // Verifica se o cliente foi encontrado
        if(clienteApagar == null) {
            System.out.println("ID não encontrado!");
            return;
        }

        // Verifica se o cliente possui reservas ativas, caso tenha, o programa salta fora e não apaga cliente
        for (Reserva reserva : reservas) {
            if (reserva.getNif() == clienteApagar.getNif()) {
                System.out.println("Não pode apagar um cliente com reservas ativas.");
                return;
            }
        }

        // Remove o cliente da lista e grava no ficheiro
        clientes.remove(clienteApagar);
        System.out.println("Cliente apagado com sucesso!");
        gravarArrayClientes();
    }

    /**
     * Grava a lista de clientes em ficheiro.
     * Se a lista estiver vazia, apaga o ficheiro e mostra uma mensagem a informar.
     *
     * @throws IOException Se ocorrer um erro de I/O durante as operações.
     */
    public static void gravarArrayClientes() throws IOException {
        // Verifica se a lista de clientes está vazia
        if(clientes.isEmpty()){
/*            File file = new File("Biblioteca_1/Clientes/clientes.csv");
            file.delete();*/
            System.out.println("Array vazio");
        }

        // Itera pela lista de clientes e grava cada um no ficheiro
        for(int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            criarFicheiroCsvCliente("Biblioteca_1/Clientes/clientes.csv", cliente, i != 0);
        }
    }

    /**
     * Lê os dados dos clientes a partir de um ficheiro e popula a lista de clientes.
     *
     * @param ficheiro O caminho para o ficheiro que contém os dados dos clientes.
     */
    public static void lerFicheiroCsvClientes(String ficheiro){
        try(BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            String linha;
            String csvDivisor = ";";
            // Lê cada linha do ficheiro
            while ((linha = readFile.readLine()) != null) {
                // Separa a linha num array para que sejam individualmente preenchidos e criados no objeto
                String[] dados = linha.split(csvDivisor);
                int id = Integer.parseInt(dados[0]);
                String nome = dados[1];
                Constantes.Genero genero = Constantes.Genero.fromGenero(dados[2].charAt(0));
                int nif = Integer.parseInt(dados[3]);
                int contacto = Integer.parseInt(dados[4]);

                // Cria um novo objeto Cliente e adiciona à lista
                Cliente cliente = new Cliente(id, nome, genero, nif, contacto,1); //TODO : codBiblioteca a ser desenvolvido posteriormente
                clientes.add(cliente);
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        // Imprime todos os clientes
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    /**
     * Metodo para verificar se o NIF já se encontra
     * atribuido a algum Cliente
     * @param nif Recebe o valor introduzido pelo utilizador
     * @param etapa Recebe o enum da etapa em questão (criação ou edição de utilizador)
     * @param idCliente Recebe o id do cliente, para validar se ao editar está a repetir o ID dele mesmo ou a colocar um já existente mas de outro cliente
     * */
    public static int pesquisarNifArrayCliente(int nif, Constantes.Etapa etapa, int idCliente) {
        if (!clientes.isEmpty()) {
            for (Cliente cliente : clientes) {

                if (cliente.getNif() == nif) {
                    //Se o NIF existir, valida se a etapa em que estamos é a de criação de utilizador. Caso seja, não permite inserir esse NIF
                    //Caso a etapa seja Editar e o NIF existir, valida se o user em questão é o mesmo que estamos a editar, e permite o NIF, caso contrário retorna o erro
                    if (etapa == Constantes.Etapa.CRIAR || (etapa == Constantes.Etapa.EDITAR && cliente.getId() != idCliente)) {
                        System.out.println("Nif existente!");
                        return 0;
                    }
                }
            }
        }else{
            System.out.println("Não existem Clientes nesta Biblioteca.");
            // TODO caso o NIF não exista dar erro ao utilizador ao criar uma Reserva/Empréstimo

        }

        return nif;
    }

    /*
     * ########################### TRATAMENTO DE DADOS CLIENTE - FIM #################################################
     * */
    /*
     * ########################### TRATAMENTO DE DADOS LIVROS - INICIO #################################################
     */

    /**
     * Adiciona um novo livro ao sistema.
     */
    public static void criarLivro() {
        Livro novoLivro = inserirDadosLivro(pesquisarProximoId());
        livros.add(novoLivro);
        System.out.println("Livro criado com sucesso!");
    }

    /**
     * Lista todos os livros cadastrados no sistema.
     */
    public static void listaTodosLivros() {
        if (livros.isEmpty()) {
            System.out.println("Não existem livros para mostrar.");
        } else {
            mostraTabelaLivros(livros);
        }
    }

    /**
     * Lista um livro pelo ISBN fornecido.
     */
    public static void listaLivroPorIsbn() {
        String isbn = lerString("Digite o ISBN do livro que deseja encontrar: ");
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
            return;
        }
        for (Livro livro : livros) {
            if (livro.getIsbn().equals(isbn)) {
                List<Livro> livroComIsbn = new ArrayList<>();
                livroComIsbn.add(livro);
                mostraTabelaLivros(livroComIsbn);
                return;
            }
        }
        System.out.println("O ISBN que inseriu não existe.");
    }

    /**
     * Edita os dados de um livro existente.
     */
    public static void editarLivro() {
        listaTodosLivros();
        int idEditar = lerInt("Escolha o ID do livro que deseja editar: ", false);
        for (int i = 0; i < livros.size(); i++) {
            if (livros.get(i).getId() == idEditar) {
                livros.set(i, inserirDadosLivro(idEditar));
                System.out.println("Livro editado com sucesso!");
                return;
            }
        }
        System.out.println("ID do livro não encontrado.");
    }

    /**
     * Apaga um livro pelo ID.
     */
    public static void apagarLivroPeloId() {
        listaTodosLivros();
        int idApagar = lerInt("Escolha o ID do livro que deseja apagar: ", false);
        Livro livroRemover = null;
        for (Livro livro : livros) {
            if (livro.getId() == idApagar) {
                livroRemover = livro;
                break;
            }
        }
        if (livroRemover == null) {
            System.out.println("ID do livro não encontrado.");
            return;
        }
        livros.remove(livroRemover);
        System.out.println("Livro apagado com sucesso!");
    }

    /**
     * Grava a lista de livros em um arquivo CSV.
     */
    public static void gravarArrayLivros() throws IOException {
        if (livros.isEmpty()) {
            File file = new File("Biblioteca_1/Livros/livros.csv");
            file.delete();
            System.out.println("Lista de livros vazia. Arquivo excluído.");
            return;
        }
        for (int i = 0; i < livros.size(); i++) {
            Livro livro = livros.get(i);
            criarFicheiroCsvLivro("Biblioteca_1/Livros/livros.csv", livro, i != 0);
        }
    }

    /**
     * Cria um arquivo CSV para armazenar os dados dos livros.
     */
    public static void criarFicheiroCsvLivro(String ficheiro, Livro livro, boolean append) throws IOException {
        try (FileWriter fw = new FileWriter(ficheiro, append)) {
            fw.write(String.join(";",
                    Integer.toString(livro.getCodBiblioteca()),
                    Integer.toString(livro.getId()),
                    livro.getTitulo(),
                    livro.getEditora(),
                    livro.getCategoria(),
                    Integer.toString(livro.getAnoEdicao()),
                    livro.getAutor(),
                    livro.getIsbn()) + "\n");
        }
    }

    /**
     * Lê os livros do arquivo CSV.
     */
    public static void lerFicheiroCsvLivros(String ficheiro) {
        try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            String linha;
            String csvDivisor = ";";
            while ((linha = readFile.readLine()) != null) {
                String[] dados = linha.split(csvDivisor);
                int id = Integer.parseInt(dados[0]);
                String titulo = dados[1];
                String editora = dados[2];
                String categoria = dados[3];
                int anoEdicao = Integer.parseInt(dados[4]);
                String autor = dados[5];
                String isbn = dados[6];
                int codBiblioteca = Integer.parseInt(dados[7]);
                Livro livro = new Livro(id, titulo, editora, categoria, anoEdicao, isbn, autor, codBiblioteca);
                livros.add(livro);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Solicita os dados do usuário para criar ou editar um livro.
     */
    private static Livro inserirDadosLivro(int id) {
        String titulo = lerString("Insira o título do livro: ");
        String editora = lerString("Insira a editora do livro: ");
        String categoria = lerString("Insira a categoria do livro: ");
        int anoEdicao = lerInt("Insira o ano de edição do livro: ", false);
        String autor = lerString("Insira o autor do livro: ");
        String isbn;
        while (true) {
            isbn = lerString("Insira o ISBN do livro: ");
            if (pesquisarIsbn(isbn) == null) {
                break;
            } else {
                System.out.println("ISBN já existe! Tente novamente.");
            }
        }
        return new Livro(id, titulo, editora, categoria, anoEdicao, isbn, autor, 1);
    }

    /**
     * Pesquisa um ISBN na lista de livros.
     */
    private static Livro pesquisarIsbn(String isbn) {
        for (Livro livro : livros) {
            if (livro.getIsbn().equals(isbn)) {
                return livro;
            }
        }
        return null;
    }

    /**
     * Pesquisa o próximo ID disponível.
     */
    private static int pesquisarProximoId() {
        int maiorId = 0;
        for (Livro livro : livros) {
            if (livro.getId() > maiorId) {
                maiorId = livro.getId();
            }
        }
        return maiorId + 1;
    }

    /*
     * ########################### TRATAMENTO DE DADOS LIVROS - FIM #################################################
     */

    /*
     * ########################### TRATAMENTO DE DADOS RESERVAS - INICIO #################################################
     * */

    public static Reserva inserirDadosReserva(int id){
        int anoEdicao = 0,numMovimento=0,codBiblioteca=1, nif=0;
        LocalDateTime dataInicio = null, dataFim = null, dataRegisto=null;
        String titulo = "", editora = "", categoria = "", isbn = "", autor = "";
        boolean flag;
        numMovimento = id;
        do {
            nif = lerInt("\nPor favor, insira o Contribuinte do Cliente: ", false);
            int idCliente;
            nif = pesquisarNifArrayCliente(nif, null, -1);
            flag = validarTamanho(String.valueOf(nif),9);
            if(!flag)
                System.out.print("Contribuinte Inválido! ex: 123456789");
        }while (!flag);


        return new Reserva(numMovimento, codBiblioteca, dataInicio, dataFim, clientes,  livros, jornais, revistas, dataRegisto, nif, isbn);
    }

    /**
     * Metodo para criar nova Reserva
     * */
    public static void criarReserva() {
        reservas.add(inserirDadosReserva(pesquisarIdArray(Constantes.TipoItem.RESERVA)));
    }

    public static void editarReserva() throws IOException {
        // Verifica se a lista de clientes está vazia
        if(reservas.isEmpty()) {
            System.out.println("Não há reservas nesta biblioteca.");
            return;
        }

        // Lista todos os clientes
        listaTodasReservas();

        // Lê o ID do cliente a ser apagado
        int idEditar = lerInt("Escolha o ID da reserva que deseja editar: ", false);

        // Procura a reserva pelo ID e, caso encontre, edita o cliente
        for(Reserva reserva : reservas) {
            if (reserva.getNumMovimento() == idEditar) {
                reservas.set(1, inserirDadosReserva(idEditar/*, Constantes.Etapa.EDITAR*/));
                //TODO : Verificar o que está errado com este metodo. Não está a gravar as alterações
                System.out.println("Reserva editada com sucesso!");
                gravarArrayReservas();
                return;
            }
        }

        System.out.println("ID não encontrado!");
    }

    public static void criarFicheiroCsvReservas(String ficheiro, Reserva reserva, Boolean firstLine) throws IOException {
        try (FileWriter fw = new FileWriter(ficheiro, firstLine)) {
            fw.write(String.join(";",
                    Integer.toString(reserva.getCodBiblioteca()),
                    Integer.toString(reserva.getNumMovimento()),
                    Integer.toString(reserva.getNif())) + "\n");
        }
    }

    public static void lerFicheiroCsvReservas(String ficheiro){

        BufferedReader readFile;
        String linha;
        String csvDivisor = ";", isbn="";
        //ArrayList<String> dados= new ArrayList<String>();

        try{
            readFile = new BufferedReader(new FileReader(ficheiro));
            while ((linha = readFile.readLine()) != null) {
                int codBiblioteca = Integer.parseInt(linha.split(csvDivisor)[0]),
                    codMovimento = Integer.parseInt(linha.split(csvDivisor)[1]),
                    nif = Integer.parseInt(linha.split(csvDivisor)[2]);

                //TODO : Alterar a maneira como constroi o objeto para adicionar à lista de reservas assim que o resto da lógica estiver completa
                LocalDateTime dataInicio = LocalDateTime.now();
                LocalDateTime dataFim = dataInicio.plusDays(7); // Example duration
                LocalDateTime dataRegisto = LocalDateTime.now();
                /*
                Cliente cliente = null;
                List<Livro> livros = new ArrayList<>();
                List<Jornal> jornais = new ArrayList<>();
                List<Revista> revistas = new ArrayList<>();
                */

                Reserva reserva = new Reserva(codBiblioteca, codMovimento, dataInicio, dataFim, clientes, livros, jornais, revistas, dataRegisto, nif, isbn);

                reservas.add(reserva);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        for (Reserva reserva : reservas) {
            System.out.println(reserva);
        }
    }



    public static void listaTodasReservas() {
        if (reservas.isEmpty()) {
            System.out.println("Não existem livros para mostrar.");
        } else {
            mostraTabelaReservas(reservas);
        }
    }

    public static void gravarArrayReservas() throws IOException {
        if(!reservas.isEmpty()){
            for(int i = 0; i < reservas.size(); i++){
                Reserva reserva = reservas.get(i);
                criarFicheiroCsvReservas("Biblioteca_1/Reservas/reservas.csv", reserva, i != 0);
            }
        }else {
            File file = new File("Biblioteca_1/Reservas/reservas.csv");
            file.delete();
            System.out.println("Array vazio");
        }
    }

    /*
     * ############################### TRATAMENTO DE DADOS RESERVAS - FIM ##############################################
     * */

    /*
     * ######################################## HELPERS - INICIO #######################################################
     * */

    /**
     * Método para atribuir automaticamente um ID com base no tipo de função.
     *
     * @param tipoItem O tipo de item para o qual o ID está sendo pesquisado.
     * @return O próximo ID disponível para o tipo de item especificado.
     */
    public static int pesquisarIdArray(Constantes.TipoItem tipoItem)
    {
        int valor = 1;

        switch (tipoItem) {
            case CLIENTE:
                for (Cliente cliente : clientes) {
                    if (cliente.getId() >= valor)
                        valor = cliente.getId() + 1;
                }
                break;
            case LIVRO:
                for (Livro livro : livros) {
                    if (livro.getId() >= valor)
                        valor = livro.getId() + 1;
                }
                break;
            case JORNAL:
                for (Jornal jornal : jornais) {
                    if (jornal.getId() >= valor)
                        valor = jornal.getId() + 1;
                }
                break;
            case REVISTA:
                for (Revista revista : revistas) {
                    if (revista.getId() >= valor)
                        valor = revista.getId() + 1;
                }
                break;
            case EMPRESTIMO:
                for (Emprestimo emprestimo : emprestimos) {
                    if (emprestimo.getNumMovimento() >= valor)
                        valor = emprestimo.getNumMovimento() + 1;
                }
                break;
            case RESERVA:
                for (Reserva reserva : reservas) {
                    if (reserva.getNumMovimento() >= valor)
                        valor = reserva.getNumMovimento() + 1;
                }
                break;
            default:
                // TODO: Mensagem de erro/evitar que a app crashe quando se passa algo errado para aqui
                break;
        }

        return valor;
    }

    /**
     * Mostra uma mensagem antes de ler uma string.
     *
     * @param mensagem A mensagem a ser mostrada antes de ler a entrada.
     * @return A string introduzida pelo utilizador.
     */
    public static String lerString(String mensagem) {
        System.out.print(mensagem);
        return input.nextLine();
    }

    /**
     * Lê e valida um número inteiro a partir da entrada do usuário.
     *
     * @param mensagem A mensagem a ser exibida ao usuário antes de ler a entrada.
     * @return O número inteiro digitado pelo usuário.
     */
    public static int lerInt(String mensagem, Boolean isDate)
    {
        if (isDate == null)
            isDate = false;

        int valor = 0;
        boolean isInt = false;
        while (!isInt) {
            System.out.print(mensagem);
            try {
                valor = input.nextInt();
                input.nextLine(); // necessário para limpar buffer
                if (isDate) {
                    //TODO :
                    //Podemos criar um easter egg aqui, com base no tipo de item (livro, revista, jornal)
                    //Primeiro livro impresso, assim como os conhecemos, em 1455 em Mainz, foi a A Bíblia de Gutenberg
                    //Primeiro jornal impresso, assim como os conhecemos, foi o Relation aller Fürnemmen und gedenckwürdigen Historien, impresso em 1605 em Strasbourg.
                    //Primeira revista impressa, assim como as conhecemos, foi The Gentleman’s Magazine impressa, impressa em 1731 em Londres.
                    if (valor >= 1455 && valor <= LocalDateTime.now().getYear()) {
                        isInt = true;
                    } else {
                        System.out.print("Por favor, insira um ano válido (yyyy): ");
                    }
                } else {
                    isInt = true;
                }
            } catch (Exception e) {
                System.out.print("Número inserido não é válido.");
                input.nextLine(); // necessário para limpar buffer
            }
        }

        return valor;
    }

    /**
     * Lê e valida um número inteiro a partir da entrada do usuário.
     *
     * @param mensagem A mensagem a ser exibida ao usuário antes de ler a entrada.
     * @return O char introduzido pelo utilizador, colocando de seguida em maiúsculo.
     */
    public static char lerChar(String mensagem) {
        System.out.print(mensagem);
        return Character.toUpperCase(input.next().charAt(0));
    }

    /**
     * Função para validar tamanho
     * @param valor valor introduzido pelo utilizador
     * @param tamanho Tamanho pre definido
     * */
    public static boolean validarTamanho(String valor, int tamanho){
        return valor.length()==tamanho;
    }

    /**
     * Função para mostrar a lista de clientes da biblioteca
     * @param listaClientes Recebe a lista de clientes, que pode ser inteira, ou apenas uma parte dela
     * */
    public static void mostraTabelaClientes(List<Cliente> listaClientes)
    {
        int idMaxLen = "Id".length();
        int nifMaxLen = "NIF".length();
        int nomeMaxLen = "Nome".length();
        int generoMaxLen = "Genero".length();
        int contactoMaxLen = "Contacto".length();

        //percorre a lista, e retorna o tamanho máximo de cada item, caso seja diferente do cabeçalho
        for (Cliente cliente : listaClientes) {
            idMaxLen = Math.max(idMaxLen, String.valueOf(cliente.getId()).length());
            nifMaxLen = Math.max(nifMaxLen, String.valueOf(cliente.getNif()).length());
            nomeMaxLen = Math.max(nomeMaxLen, cliente.getNome().length());
            generoMaxLen = Math.max(generoMaxLen, String.valueOf(Constantes.Genero.fromGenero(cliente.getGenero())).length());
            contactoMaxLen = Math.max(contactoMaxLen, String.valueOf(cliente.getContacto()).length());
        }

        //Esta string cria as linhas baseado no tamanho máximo de cada coluna
        String formato = "| %-" + idMaxLen + "s | %-" + nifMaxLen  + "s | %-" + nomeMaxLen  + "s | %-" + generoMaxLen + "s | %-" + contactoMaxLen + "s |\n";
        //Esta string cria a linha de separação
        String separador = "+-" + "-".repeat(idMaxLen) + "-+-" + "-".repeat(nifMaxLen) + "-+-" + "-".repeat(nomeMaxLen) + "-+-" + "-".repeat(generoMaxLen) + "-+-" + "-".repeat(contactoMaxLen) + "-+";

        //Imprime a linha de separação (+---+---+ ...)
        System.out.println(separador);
        //Imprime o cabeçalho da tabela
        System.out.printf(formato, "Id", "NIF", "Nome", "Genero", "Contacto");
        //Imprime a linha de separação
        System.out.println(separador);

        //Imprime os dados dos clientes
        for (Cliente cliente : listaClientes) {
            System.out.printf(formato, cliente.getId(), cliente.getNif(), cliente.getNome(), Constantes.Genero.fromGenero(cliente.getGenero()), cliente.getContacto());
        }

        System.out.println(separador);
    }

    /**
     * Exibe a lista de livros em formato de tabela.
     */
    private static void mostraTabelaLivros(List<Livro> listaLivros)
    {
        int idMaxLen = "Id".length();
        int editoraMaxLen = "Editora".length();
        int categoriaMaxLen = "Categoria".length();
        int anoEdicaoMaxLen = "Ano Edicao".length();
        int isbnMaxLen = "ISBN".length();
        int autorMaxLen = "Autor".length();

        //percorre a lista, e retorna o tamanho máximo de cada item, caso seja diferente do cabeçalho
        for (Livro livro : listaLivros) {
            idMaxLen = Math.max(idMaxLen, String.valueOf(livro.getId()).length());
            editoraMaxLen = Math.max(editoraMaxLen, String.valueOf(livro.getEditora()).length());
            categoriaMaxLen = Math.max(categoriaMaxLen, livro.getCategoria().length());
            anoEdicaoMaxLen = Math.max(anoEdicaoMaxLen, String.valueOf(livro.getAnoEdicao()).length());
            isbnMaxLen = Math.max(isbnMaxLen, String.valueOf(livro.getIsbn()).length());
            autorMaxLen = Math.max(autorMaxLen, String.valueOf(livro.getAutor()).length());
        }

        //Esta string cria as linhas baseado no tamanho máximo de cada coluna
        String formato = "| %-" + idMaxLen + "s | %-" + editoraMaxLen  + "s | %-" + categoriaMaxLen  + "s | %-" + anoEdicaoMaxLen + "s | %-" + autorMaxLen + "s | %-" + isbnMaxLen + "s |\n";
        //Esta string cria a linha de separação
        String separador = "+-" + "-".repeat(idMaxLen) + "-+-" + "-".repeat(editoraMaxLen) + "-+-" + "-".repeat(categoriaMaxLen) + "-+-" + "-".repeat(anoEdicaoMaxLen) + "-+-" + "-".repeat(autorMaxLen) + "-+-" + "-".repeat(isbnMaxLen) + "-+";

        //Imprime a linha de separação (+---+---+ ...)
        System.out.println(separador);
        //Imprime o cabeçalho da tabela
        System.out.printf(formato, "Id", "Editora", "Categoria", "Ano Edição", "Autor", "ISBN");
        //Imprime a linha de separação
        System.out.println(separador);

        //Imprime os dados dos livros
        for (Livro livro : listaLivros) {
            System.out.printf(formato, livro.getId(), livro.getEditora(), livro.getCategoria(), livro.getAnoEdicao(), livro.getAutor(), livro.getIsbn());
        }

        System.out.println(separador);
    }

    public static void mostraTabelaReservas(List<Reserva> listaReservas)
    {
        int bibliotecaMaxLen = "Biblioteca".length();
        int idMaxLen = "Id".length();
        int nifMaxLen = "NIF".length();

        //percorre a lista, e retorna o tamanho máximo de cada item, caso seja diferente do cabeçalho
        for (Reserva reservas : listaReservas) {
            bibliotecaMaxLen = Math.max(bibliotecaMaxLen, String.valueOf(reservas.getCodBiblioteca()).length());
            idMaxLen = Math.max(idMaxLen, String.valueOf(reservas.getNumMovimento()).length());
            nifMaxLen = Math.max(nifMaxLen, String.valueOf(reservas.getNif()).length());
        }

        //Esta string cria as linhas baseado no tamanho máximo de cada coluna
        String formato = "| %-" + bibliotecaMaxLen + "s | %-" + idMaxLen  + "s | %-" + nifMaxLen + "s |\n";
        //Esta string cria a linha de separação
        String separador = "+-" + "-".repeat(bibliotecaMaxLen) + "-+-" + "-".repeat(idMaxLen) + "-+-" + "-".repeat(nifMaxLen) + "-+";

        //Imprime a linha de separação (+---+---+ ...)
        System.out.println(separador);
        //Imprime o cabeçalho da tabela
        System.out.printf(formato, "Biblioteca", "Id", "NIF");
        //Imprime a linha de separação
        System.out.println(separador);

        //Imprime os dados dos clientes
        for (Reserva reserva : listaReservas) {
            System.out.printf(formato, reserva.getCodBiblioteca(), reserva.getNumMovimento(), reserva.getNif());
        }

        System.out.println(separador);
    }

    /*
     * ########################################## HELPERS - FIM ########################################################
     * */
}
