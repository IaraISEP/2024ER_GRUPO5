import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

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

        return new Cliente(id, nome, genero, nif,contacto,1);
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
        for(Cliente cliente : clientes) {
            if (cliente.getId() == idEditar) {
                inserirDadosCliente(idEditar, Constantes.Etapa.EDITAR);
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
            File file = new File("Biblioteca_1/Clientes/clientes.csv");
            file.delete();
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
        for (Cliente cliente : clientes) {
            if (cliente.getNif() == nif) {
                //Se o NIF existir, valida se a etapa em que estamos é a de criação de utilizador. Caso seja, não permite inserir esse NIF
                //Caso a etapa seja Editar e o NIF existir, valida se o user em questão é o mesmo que estamos a editar, e permite o NIF, caso contrário retorna o erro
                if(etapa == Constantes.Etapa.CRIAR || (etapa == Constantes.Etapa.EDITAR && cliente.getId() != idCliente)) {
                    System.out.println("Nif existente!");
                    return 0;
                }
            }
        }

        return nif;
    }

    /*
     * ########################### TRATAMENTO DE DADOS CLIENTE - FIM #################################################
     * */

    /*
    * ########################### TRATAMENTO DE DADOS LIVROS - INICIO #################################################
    * */

    public static Livro inserirDadosLivro(int id)
    {
        String isbn = "";
        boolean flag;

        do {
            isbn = lerString("\nPor favor, insira o ISBN do Livro: ");
            isbn = pesquisarIsbnArrayLivro(isbn);
            flag=validarTamanho((isbn),9);
            if(!flag)
                System.out.print("ISBN Inválido! ex: 1234-5678: ");
        }while (!flag);

        String titulo = lerString("\nPor favor, insira o Titulo do Livro: ");
        String editora = lerString("\nPor favor, insira o Editora do Livro: ");
        String categoria = lerString("\nPor favor, insira o Categoria do Livro: ");
        String autor = lerString("\nPor favor, insira o Autor do Livro: ");
        int anoEdicao = lerInt("\nPor favor, insira o Ano edição do Livro: ", false);

        return new Livro(id, titulo, editora, categoria, anoEdicao, isbn, autor,1);
    }

    /**
     * Metodo para criar novo Livro
     * */
    public static void criarLivro() {
        livros.add(inserirDadosLivro(pesquisarIdArray(Constantes.TipoItem.LIVRO)));
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
        boolean idFound;
        lerArrayLivros();
        System.out.println("Escolha o ID do livro que deseja apagar: ");
        idApagar = input.nextInt();
        input.nextLine(); //Limpar buffer
        if(!livros.isEmpty()){
            idFound = false;
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
        try (FileWriter fw = new FileWriter(ficheiro, firstLine)) {
            fw.write(String.join(";",
                    Integer.toString(livro.getId()),
                    livro.getTitulo(),
                    livro.getEditora(),
                    livro.getCategoria(),
                    Integer.toString(livro.getAnoEdicao()),
                    livro.getAutor(),
                    livro.getIsbn(),
                    Integer.toString(1)) + "\n");
        }
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

    public static Reserva inserirDadosReserva(int id){
        int anoEdicao = 0,numMovimento=0,codBiblioteca=1, nif=0;
        LocalDateTime dataInicio = null, dataFim = null, dataRegisto=null;
        String titulo = "", editora = "", categoria = "", isbn = "", autor = "";
        boolean flag;
        numMovimento = id;
        do {
            nif = lerInt("\nPor favor, insira o Contribuinte do Cliente: ", false);
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

    public static void criarFicheiroCsvReservas(String ficheiro, Reserva reserva, Boolean firstLine) throws IOException {
        try (FileWriter fw = new FileWriter(ficheiro, firstLine)) {
            fw.write(String.join(";",
                    Integer.toString(reserva.getNumMovimento()),
                    Integer.toString(reserva.getCodBiblioteca()),
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
                int codMovimento = Integer.parseInt(linha.split(csvDivisor)[0]),
                    codBiblioteca = Integer.parseInt(linha.split(csvDivisor)[1]),
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

                Reserva reserva = new Reserva(codMovimento, codBiblioteca, dataInicio, dataFim, clientes, livros, jornais, revistas, dataRegisto, nif, isbn);

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
                List<Integer> ids = new ArrayList<>();

                for (Cliente cliente : clientes) {
                    ids.add(cliente.getId());
                }

                Collections.sort(ids);
                for (int id : ids) {
                    if (id == valor)
                        valor++;
                    else if (id > valor)
                        break;
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

    /*
     * ########################################## HELPERS - FIM ########################################################
     * */
}
