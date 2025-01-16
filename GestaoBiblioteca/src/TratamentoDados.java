import java.io.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Classe responsável pelo tratamento de dados no sistema de gestão de bibliotecas.
 * Esta classe gere a criação e manipulação de ficheiros e dados relacionados com bibliotecas, clientes, livros, jornais, revistas, empréstimos e reservas.
 *
 * As suas principais funcionalidades incluem:
 * - Criação de novos objetos das classes relacionadas.
 * - Criação e manutenção da estrutura de ficheiros para armazenamento de dados persistentes.
 * - Edição e leitura de ficheiros para manter os dados atualizados.
 *
 * @author ER_GRUPO_5
 * @since 2024
 */
public class TratamentoDados {

    private static Scanner input = new Scanner(System.in);
    private static List<Biblioteca> bibliotecas = new ArrayList<>();
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Livro> livros = new ArrayList<>();
    private static List<Emprestimo> emprestimos = new ArrayList<>();
    private static List<EmprestimoLinha> emprestimosLinha = new ArrayList<>();
    private static List<JornalRevista> jornais = new ArrayList<>();
    private static List<JornalRevista> revistas = new ArrayList<>();
    private static List<Reserva> reservas = new ArrayList<>();
    private static List<ReservaLinha> reservasLinha = new ArrayList<>();
    private static int codBibliotecaSessao = -1;

    public static void inicializador(){
        try {
            criarSistemaFicheiros();
            lerFicheiroCsvBiblioteca(Constantes.Path.BIBLIOTECA.getValue());
            lerFicheiroCsvClientes(Constantes.Path.CLIENTE.getValue());
            lerFicheiroCsvLivros(Constantes.Path.LIVRO.getValue());
            lerFicheiroCsvJornaisRevistas(Constantes.Path.JORNAL.getValue(), Constantes.TipoItem.JORNAL);
            lerFicheiroCsvJornaisRevistas(Constantes.Path.REVISTA.getValue(), Constantes.TipoItem.REVISTA);
            lerFicheiroCsvReservas(Constantes.Path.RESERVA.getValue());
            lerFicheiroCsvReservasLinha(Constantes.Path.RESERVALINHA.getValue());
            lerFicheiroCsvEmprestimos(Constantes.Path.EMPRESTIMO.getValue());
            lerFicheiroCsvEmprestimosLinha(Constantes.Path.EMPRESTIMOLINHA.getValue());
            AtualizarAtrasoEmprestimo();
        } catch (IOException e) {
            System.err.println("Erro durante a execução: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método responsável por criar a estrutura de ficheiros necessária para o armazenamento de dados de forma persistente.
     *
     * O método cria uma série de diretórios e ficheiros para armazenar informações sobre bibliotecas, clientes, livros, jornais, revistas,
     * empréstimos e reservas. Caso os diretórios ou ficheiros já existam, não são recriados.
     *
     * Diretórios criados:
     * - Dados/Bibliotecas
     * - Dados/Clientes
     * - Dados/Livros
     * - Dados/Jornais
     * - Dados/Revistas
     * - Dados/Emprestimos
     * - Dados/Reservas
     * - Dados/Reservas/Details
     * - Dados/Historico
     *
     * Ficheiros criados:
     * - Definidos pelos caminhos armazenados nas constantes em `Constantes.Path`
     *
     * @throws IOException Se ocorrer um erro ao criar os ficheiros ou diretórios.
     */
    public static void criarSistemaFicheiros() throws IOException {
        File[] dirs = new File[]{
                new File("Dados/Bibliotecas"),
                new File("Dados/Clientes"),
                new File("Dados/Livros"),
                new File("Dados/Jornais"),
                new File("Dados/Revistas"),
                new File("Dados/Emprestimos"),
                new File("Dados/Reservas"),
                new File("Dados/Reservas/Details"),
                new File("Dados/Historico"),
        };
        for (File dir : dirs) {
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        File[] files = new File[]{
                new File(Constantes.Path.BIBLIOTECA.getValue()),
                new File(Constantes.Path.CLIENTE.getValue()),
                new File(Constantes.Path.LIVRO.getValue()),
                new File(Constantes.Path.JORNAL.getValue()),
                new File(Constantes.Path.REVISTA.getValue()),
                new File(Constantes.Path.EMPRESTIMO.getValue()),
                new File(Constantes.Path.RESERVA.getValue()),
                new File(Constantes.Path.RESERVALINHA.getValue())
        };
        for (File file : files) {
            if (!file.exists()) {
                file.createNewFile();
            }
        }
    }
    /*
     * ########################### TRATAMENTO DE DADOS LOGIN - INICIO #################################################
     * */

    /**
     * Método responsável pelo início de sessão.
     *
     * O método valida se existe bibliotecas. 
     * Caso exista, lista todas as bibliotecas e solicita ao utilizador que escolha uma.
     * Caso contrário, cria uma nova biblioteca e inicia sessão automaticamente.
     *
     * @throws IOException Se ocorrer um erro ao criar os ficheiros ou diretórios.
     */
    public static void inicioSessao() throws IOException {
        //Se não houver bibliotecas configuradas, cria uma nova e inicia sessão automaticamente.
        if(bibliotecas.isEmpty()) {
            System.out.println("Não existem bibliotecas configuradas.\nA iniciar a configuração...");
            criarBiblioteca();
            codBibliotecaSessao = bibliotecas.getFirst().getCodBiblioteca();
            System.out.println("Início de sessão automático...");
            CriarMenu.menuPrincipal();
            return;
        }

        //Lista todas as bibliotecas
        listaTodasBibliotecas();
        
        //Escolhe a biblioteca a iniciar sessão
        int opcao = lerInt("Escolha a Biblioteca: ", false, null);
        for (Biblioteca biblioteca : bibliotecas) {
            if (biblioteca.getCodBiblioteca() == opcao){
                biblioteca.setCodBiblioteca(opcao);
                codBibliotecaSessao = biblioteca.getCodBiblioteca();
                CriarMenu.menuPrincipal();
            }
        }
    }

    /*
     * ########################### TRATAMENTO DE DADOS LOGIN - FIM #################################################
     * */

    /*
     * ########################### TRATAMENTO DE DADOS BIBLIOTECA - INICIO #################################################
     */

    /**
     * Método responsável por inserir os dados de uma nova biblioteca.
     * Solicita ao utilizador o nome e a morada da biblioteca, e gera automaticamente o ID da mesma.
     *
     * @return Um objeto do tipo Biblioteca contendo as informações inseridas.
     * @throws IOException Se ocorrer um erro ao ler a entrada do utilizador.
     */
    public static Biblioteca inserirDadosBiblioteca(int id) throws IOException
    {
        String nome = lerString("Insira o Nome da Biblioteca: ");
        Constantes.Morada morada = selecionaMorada("Insira a Morada da biblioteca: ");
        return new Biblioteca(nome, morada, id);
    }

    /**
     * Método que cria uma nova biblioteca e adiciona-a à lista de bibliotecas.
     * Após a criação, os dados são guardados no ficheiro CSV correspondente.
     *
     * @throws IOException Se ocorrer um erro ao gravar os dados.
     */
    public static void criarBiblioteca() throws IOException
    {
        bibliotecas.add(inserirDadosBiblioteca(getIdAutomatico(Constantes.TipoItem.BIBLIOTECA, -1)));
        gravarArrayBibliotecas();
    }

    /**
     * Método que edita biblioteca consoante o input do utilizador.
     * Após a edição, os dados são guardados no ficheiro CSV correspondente.
     *
     * @throws IOException Se ocorrer um erro ao gravar os dados.
     */
    public static void editarBiblioteca() throws IOException
    {
        if(bibliotecas.isEmpty()) {
            System.out.println("Não existem bibliotecas configuradas.");
            return;
        }
        
        listaTodasBibliotecas();

        int idEditar = lerInt("Escolha o ID da biblioteca que deseja apagar: ", false, null);

        for (Biblioteca biblioteca : bibliotecas) {
            if (biblioteca.getCodBiblioteca() == idEditar) {
                Biblioteca bibAdd = inserirDadosBiblioteca(idEditar);
                biblioteca.setNome(bibAdd.getNome());
                biblioteca.setMorada(bibAdd.getMorada());
                System.out.println("Biblioteca editada com sucesso!");
                gravarArrayBibliotecas();
                return;
            }
        }
        
        System.out.println("ID não encontrado!");
    }

    /**
     * Método que apaga biblioteca consoante o input do utilizador.
     * Após a remoção, os dados são guardados no ficheiro CSV correspondente.
     *
     * @throws IOException Se ocorrer um erro ao gravar os dados.
     */
    public static void apagarBiblioteca() throws IOException {
        if (bibliotecas.isEmpty()) {
            System.out.println("Não existem bibliotecas configuradas.");
            return;
        }
        
        //Mostra as bibliotecas existentes
        listaTodasBibliotecas();
        int idApagar = lerInt("Escolha o ID da biblioteca que deseja apagar: ", false, null);

        //Procura o objeto Biblioteca pelo ID fornecido
        Biblioteca bibliotecaApagar = null;
        for (Biblioteca biblioteca : bibliotecas) {
            if (biblioteca.getCodBiblioteca() == idApagar) {
                bibliotecaApagar = biblioteca;
                break;
            }
        }
        
        //Verifica se a biblioteca foi encontrada e remove-a 
        if (bibliotecaApagar != null) {
            bibliotecas.remove(bibliotecaApagar);
            System.out.println("Biblioteca apagada com sucesso!");
            gravarArrayBibliotecas();
        } else {
            System.out.println("Não foi possível apagar a biblioteca. Id não válido.");
        }
    }
    
    /**
     * Método responsável por criar um ficheiro CSV e gravar os dados de uma biblioteca.
     *
     * @param ficheiro Nome do ficheiro a ser criado/atualizado.
     * @param biblioteca Objeto Biblioteca cujos dados serão gravados.
     * @param firstLine Define se a gravação deve sobrescrever ou adicionar ao ficheiro.
     * @throws IOException Se ocorrer um erro ao gravar os dados no ficheiro.
     */
    public static void criarFicheiroCsvBiblioteca(String ficheiro, Biblioteca biblioteca, Boolean firstLine) throws IOException
    {
        try (FileWriter fw = new FileWriter(ficheiro, firstLine)) {
            fw.write(biblioteca.getNome() + ";" + biblioteca.getMorada() + ";" + biblioteca.getCodBiblioteca() + "\n");
        }
    }

    /**
     * Método responsável por ler os dados de bibliotecas a partir de um ficheiro CSV.
     *
     * @param ficheiro Caminho do ficheiro CSV a ser lido.
     * Lê cada linha e cria objetos Biblioteca que são adicionados à lista.
     */
    public static void lerFicheiroCsvBiblioteca(String ficheiro)
    {
        try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            String linha = readFile.readLine();
            if (linha == null) {
                return;
            }
            do {
                // Divide a linha em diferentes atributos e cria o objeto Biblioteca
                String[] dados = linha.split(Constantes.SplitChar);
                String nome = dados[0];
                Constantes.Morada morada = Constantes.Morada.valueOf(dados[1]);
                int id = Integer.parseInt(dados[2]);

                // Cria um novo objeto Biblioteca e adiciona-o à lista
                Biblioteca biblioteca = new Biblioteca(nome, morada, id);
                bibliotecas.add(biblioteca);
            } while ((linha = readFile.readLine()) != null);
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro: " + e.getMessage());
        }
    }

    /**
     * Método que lista todas as bibliotecas existentes.
     * Se não existirem bibliotecas, uma mensagem informativa é apresentada.
     */
    public static void listaTodasBibliotecas()
    {
        if (bibliotecas.isEmpty())
            System.out.println("Não existem Bibliotecas para mostrar.");
        else
            mostraTabelaBibliotecas(bibliotecas);
    }

    /**
     * Método responsável por gravar a lista de bibliotecas num ficheiro CSV.
     * Itera por todas as bibliotecas e grava cada uma individualmente no ficheiro.
     *
     * @throws IOException Se ocorrer um erro ao gravar os dados no ficheiro.
     */
    public static void gravarArrayBibliotecas() throws IOException
    {
        // Percorre todas as bibliotecas e grava cada uma no ficheiro CSV
        for (int i = 0; i < bibliotecas.size(); i++) 
            criarFicheiroCsvBiblioteca(Constantes.Path.BIBLIOTECA.getValue(), bibliotecas.get(i), i != 0);
    }

    /*
     * ########################### TRATAMENTO DE DADOS BIBLIOTECA - FIM #################################################
     */
    /*
     * ########################### TRATAMENTO DE DADOS CLIENTE - INICIO #################################################
     * */

    /**
     * Método para requisitar ao utilizador os dados do Cliente.
     * Este método solicita ao utilizador que insira o contribuinte (NIF), nome, género e contacto do cliente.
     * Valida o NIF para garantir que tem 9 dígitos e que não está duplicado.
     * Valida o género para garantir que é 'M' ou 'F'.
     * Valida o contacto para garantir que tem 9 dígitos e começa com 2 ou 9.
     *
     * @param id O ID do cliente a ser inserido.
     * @return Um objeto Cliente com os dados inseridos.
     */
    public static Cliente inserirDadosCliente(int id)
    {
        int contacto;
        boolean flag;
        Constantes.Genero genero = null;
        String nome, nif;

        do {
            flag = true;
            nif = lerString("\nPor favor, insira o Contribuinte do Cliente:");
            if ( !nif.matches("^\\d{9}$")) {
                System.out.println("Contribuinte Inválido! ( Ex: 252252252 )");
                flag = false;
            }
            if(!clientes.isEmpty() && flag){
                for (Cliente cliente : clientes) {
                    if (cliente.getNif() == Integer.parseInt(nif) && cliente.getId() != id) {
                        System.out.println("Contribuinte já existente! Tente novamente.");
                        flag = false;
                    }
                }
            }
        } while (!flag);

        nome = lerString("\nPor favor, insira o nome do Cliente: ");

        do {
            char gen = lerChar("\nPor favor, insira o Gênero do Cliente (M/F): ");
            flag = (gen == 'M' || gen == 'F');
            if (flag)
                genero = Constantes.Genero.fromGenero(gen);
            else
                System.out.print("O gênero introduzido não é válido!");
        } while (!flag);

        do {
            contacto = lerInt("\nPor favor, insira o Contacto do Cliente: ", false, null);
            if (!String.valueOf(contacto).matches("^[29]\\d{8}$")) {
                System.out.print("Número de contacto com formato inválido! ex: 912345678");
                continue;
            }
            break;
        } while (true);

        return new Cliente(id, nome, genero, Integer.parseInt(nif), contacto, codBibliotecaSessao);
    }

    /**
     * Método para adicionar um novo Cliente à lista.
     * Este método gera automaticamente um ID para o novo cliente, solicita os dados do cliente ao utilizador,
     * adiciona o cliente à lista de clientes e grava a lista atualizada no ficheiro CSV.
     *
     * @throws IOException Se ocorrer um erro durante a gravação dos dados no ficheiro.
     */
    public static void criarCliente() throws IOException
    {
        clientes.add(inserirDadosCliente(getIdAutomatico(Constantes.TipoItem.CLIENTE, -1)));
        gravarArrayClientes();
    }

    /**
     * Edita um cliente pelo ID fornecido.
     * Este método lista todos os clientes, solicita ao utilizador que escolha o ID do cliente a ser editado,
     * solicita os novos dados do cliente, atualiza a lista de clientes e grava a lista atualizada no ficheiro CSV.
     *
     * @throws IOException Se ocorrer um erro de I/O durante a gravação dos dados.
     */
    public static void editarCliente() throws IOException
    {
        if(clientes.isEmpty()) {
            System.out.println("Não há clientes nesta biblioteca.");
            return;
        }
        listaTodosClientes();

        int idEditar = lerInt("Escolha o ID do cliente que deseja editar: ", false, null);

        for(int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == idEditar) {
                Cliente cliente = inserirDadosCliente(idEditar);
                clientes.set(i, cliente);
                System.out.println("Cliente editado com sucesso!");
                gravarArrayClientes();
                return;
            }
        }
        System.out.println("ID não encontrado!");
    }

    /**
     * Método para criar o ficheiro clientes.csv e adicionar conteúdo ao mesmo.
     * Este método grava os dados de um cliente no ficheiro CSV.
     *
     * @param ficheiro O caminho do ficheiro CSV.
     * @param cliente O objeto Cliente cujos dados serão gravados.
     * @param firstLine Define se a gravação deve sobrescrever (true) ou adicionar (false) ao ficheiro.
     * @throws IOException Se ocorrer um erro ao gravar os dados no ficheiro.
     */
    public static void criarFicheiroCsvCliente(String ficheiro, Cliente cliente, Boolean firstLine) throws IOException
    {
        try (FileWriter fw = new FileWriter(ficheiro, firstLine)) {
            fw.write(cliente.getId() + ";" + cliente.getNome() + ";" + cliente.getGenero() + ";" + cliente.getNif() + ";" + cliente.getContacto() + ";" + cliente.getCodBiblioteca() + "\n");
        }
    }

    /**
     * Método que lista todos os Clientes existentes na biblioteca.
     * Este método verifica se a lista de clientes está vazia e, se não estiver, exibe os dados de todos os clientes.
     */
    public static void listaTodosClientes()
    {
        if (clientes.isEmpty())
            System.out.println("Não existem Clientes para mostrar.");
        else
            mostraTabelaClientes(clientes);
    }

    /**
     * Lista um cliente pelo Nome, NIF ou Contacto fornecido.
     * Este método solicita ao utilizador que escolha o parâmetro de pesquisa (Nome, NIF ou Contacto),
     * realiza a pesquisa e exibe os detalhes do cliente encontrado.
     * Se não encontrar o cliente, exibe uma mensagem informativa.
     */
    public static void listaClientePorParametro()
    {
        if(clientes.isEmpty()){
            System.out.println("Esta biblioteca não contém clientes.");
            return;
        }

        do{
            boolean hasClient = false;
            System.out.println("Escolha o parâmetro que deseja pesquisar: ");
            System.out.println("1 - Nome\n2 - Contribuinte\n3 - Contacto\n0 - Sair");
            int escolha = lerInt("Escolha uma opção: ", false, null);
            switch (escolha){
                case 1:
                    String nome = lerString("\nDigite o Nome do Cliente que deseja encontrar: ");

                    List<Cliente> clienteComNome = new ArrayList<>();
                    for (Cliente cliente : clientes) {
                        if (cliente.getNome().toLowerCase().contains(nome.toLowerCase())) {
                            clienteComNome.add(cliente);
                        }
                    }

                    if(clienteComNome.isEmpty()) {
                        System.out.println("Cliente não encontrado.");
                        keyPress();
                    }
                    else {
                        mostraTabelaClientes(clienteComNome);
                        keyPress();
                    }
                    break;
                case 2:
                    String nif;

                    do {
                        nif = lerString("\nPor favor, insira o Contribuinte do Cliente:");
                        if ( !nif.matches("^\\d{9}$"))
                            System.out.println("Contribuinte Inválido! ( Ex: 252252252 )");
                        else
                            break;
                    } while (true);

                    for (Cliente cliente : clientes) {
                        if (cliente.getNif() == Integer.parseInt(nif)) {
                            mostraTabelaClientes(Collections.singletonList(cliente));
                            keyPress();
                            hasClient = true;
                            break;
                        }
                    }

                    if(!hasClient)
                    {
                        System.out.println("Cliente não encontrado.");
                        keyPress();
                    }
                    break;
                case 3:
                    String contacto;
                    do {
                        contacto = lerString("\nPor favor, insira o Contacto do Cliente:");
                        if ( !contacto.matches("^\\d{9}$"))
                            System.out.println("Contacto Inválido! ( Ex: 252252252 )");
                        else
                            break;
                    } while (true);

                    List<Cliente> clienteComContacto = new ArrayList<>();

                    for (Cliente cliente : clientes) {
                        if (cliente.getContacto() == Integer.parseInt(contacto)) {
                            clienteComContacto.add(cliente);
                        }
                    }

                    if(clienteComContacto.isEmpty()){
                        System.out.println("Cliente(s) não encontrado(s).");
                        keyPress();
                    }
                    else {
                        mostraTabelaClientes(clienteComContacto);
                        keyPress();
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    keyPress();
                    break;
            }
        }while (true);
    }

    /**
     * Apaga um cliente pelo ID fornecido.
     * Este método lista todos os clientes, solicita ao utilizador que escolha o ID do cliente a ser apagado,
     * verifica se o cliente possui reservas ou empréstimos ativos, e se não possuir, remove o cliente da lista e grava a lista atualizada no ficheiro CSV.
     *
     * @throws IOException Se ocorrer um erro de I/O durante a gravação dos dados.
     */
    public static void apagarClientePeloId() throws IOException
    {
        if(clientes.isEmpty()) {
            System.out.println("Não há clientes nesta biblioteca.");
            return;
        }

        // Lista todos os clientes
        listaTodosClientes();

        // Lê o ID do cliente a ser apagado
        int idApagar = lerInt("Escolha o ID do cliente que deseja apagar: ", false, null);

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
            if (reserva.getCliente().equals(clienteApagar)) {
                System.out.println("Não pode apagar um cliente com reservas ativas.");
                return;
            }
        }

        // Verifica se o cliente possui empréstimos ativos, caso tenha, o programa salta fora e não apaga cliente
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getCliente().equals(clienteApagar)) {
                System.out.println("Não pode apagar um cliente com empréstimos ativos.");
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
     * Este método verifica se a lista de clientes está vazia e, se não estiver, grava os dados de cada cliente no ficheiro CSV.
     * Se a lista estiver vazia, exibe uma mensagem informativa.
     *
     * @throws IOException Se ocorrer um erro de I/O durante as operações.
     */
    public static void gravarArrayClientes() throws IOException {
        // Itera pela lista de clientes e grava cada um no ficheiro
        for(int i = 0; i < clientes.size(); i++) 
            criarFicheiroCsvCliente(Constantes.Path.CLIENTE.getValue(), clientes.get(i), i != 0);
    }

    /**
     * Lê os dados dos clientes a partir de um ficheiro e popula a lista de clientes.
     * Este método lê cada linha do ficheiro CSV, cria um objeto Cliente com os dados lidos e adiciona-o à lista de clientes.
     *
     * @param ficheiro O caminho para o ficheiro que contém os dados dos clientes.
     */
    public static void lerFicheiroCsvClientes(String ficheiro){
        try(BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            String linha = readFile.readLine();
            if (linha == null) {
                return;
            }
            String csvDivisor = ";";
            do {
                // Separa a linha num array para que sejam individualmente preenchidos e criados no objeto
                String[] dados = linha.split(csvDivisor);
                int id = Integer.parseInt(dados[0]);
                String nome = dados[1];
                Constantes.Genero genero = Constantes.Genero.fromGenero(dados[2].charAt(0));
                int nif = Integer.parseInt(dados[3]);
                int contacto = Integer.parseInt(dados[4]);
                int codBibliotecaLogin = Integer.parseInt(dados[5]);

                // Cria um novo objeto Cliente e adiciona à lista
                Cliente cliente = new Cliente(id, nome, genero, nif, contacto, codBibliotecaLogin); //TODO : codBiblioteca a ser desenvolvido posteriormente
                clientes.add(cliente);
            }while ((linha = readFile.readLine()) != null);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /*
     * ########################### TRATAMENTO DE DADOS CLIENTE - FIM #################################################
     * */
    /*
     * ########################### TRATAMENTO DE DADOS LIVROS - INICIO #################################################
     */

    /**
     * Adiciona um novo livro ao sistema.
     * Este método gera automaticamente um ID para o novo livro, solicita os dados do livro ao utilizador,
     * adiciona o livro à lista de livros e grava a lista atualizada no ficheiro CSV.
     *
     * @throws IOException Se ocorrer um erro durante a gravação dos dados no ficheiro.
     */
    public static void criarLivro() throws IOException {
        livros.add(inserirDadosLivro(getIdAutomatico(Constantes.TipoItem.LIVRO, -1)));
        System.out.println("Livro criado com sucesso!");
        gravarArrayLivros();
    }

    /**
     * Lista todos os livros cadastrados no sistema.
     * Este método verifica se a lista de livros está vazia e, se não estiver, exibe os dados de todos os livros.
     */
    public static void listaTodosLivros() {
        if (livros.isEmpty()) {
            System.out.println("Não existem livros para mostrar.");
            return;
        }

        mostraTabelaLivros(livros);
    }

    /**
     * Lista um livro pelo ISBN fornecido.
     * Este método solicita ao utilizador que insira o ISBN do livro, realiza a pesquisa e exibe os detalhes do livro encontrado.
     * Se não encontrar o livro, exibe uma mensagem informativa.
     */
    public static void listaLivroPorIsbn() {
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
            return;
        }

        String isbn = lerString("Digite o ISBN do livro que deseja encontrar: ");

        for (Livro livro : livros) {
            if (livro.getIsbn().equals(isbn)) {
                mostraTabelaLivros(Collections.singletonList(livro));
                return;
            }
        }

        System.out.println("O ISBN que inseriu não existe.");
    }

    /**
     * Edita os dados de um livro existente.
     * Este método lista todos os livros, solicita ao utilizador que escolha o ID do livro a ser editado,
     * solicita os novos dados do livro, atualiza a lista de livros e grava a lista atualizada no ficheiro CSV.
     *
     * @throws IOException Se ocorrer um erro de I/O durante a gravação dos dados.
     */
    public static void editarLivro() throws IOException {
        if (livros.isEmpty()) {
            System.out.println("Não existem livros nesta Biblioteca.");
            return;
        }
        listaTodosLivros();
        int idEditar = lerInt("Escolha o ID do livro que deseja editar: ", false, null);

        for (Livro livro : livros) {
            if (livro.getId() == idEditar) {
                livros.set(livros.indexOf(livro), inserirDadosLivro(idEditar));
                System.out.println("Livro editado com sucesso!");
                gravarArrayLivros();
                return;
            }
        }

        System.out.println("ID do livro não encontrado.");
    }

    /**
     * Apaga um livro pelo ID.
     * Este método lista todos os livros, solicita ao utilizador que escolha o ID do livro a ser apagado,
     * remove o livro da lista e grava a lista atualizada no ficheiro CSV.
     *
     * @throws IOException Se ocorrer um erro de I/O durante a gravação dos dados.
     */
    public static void apagarLivroPeloId() throws IOException {
        if (livros.isEmpty()) {
            System.out.println("Não existem livros nesta Biblioteca.");
            return;
        }

        listaTodosLivros();
        int idApagar = lerInt("Escolha o ID do livro que deseja apagar: ", false, null);
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
        gravarArrayLivros();
    }

    /**
     * Grava a lista de livros em um arquivo CSV.
     * Este método itera pela lista de livros e grava os dados de cada livro no ficheiro CSV.
     *
     * @throws IOException Se ocorrer um erro de I/O durante as operações.
     */
    public static void gravarArrayLivros() throws IOException {
        for (int i = 0; i < livros.size(); i++)
            criarFicheiroCsvLivro(Constantes.Path.LIVRO.getValue(), livros.get(i), i != 0);
    }

    /**
     * Cria um arquivo CSV para armazenar os dados dos livros.
     * Este método grava os dados de um livro no ficheiro CSV.
     *
     * @param ficheiro O caminho do ficheiro CSV.
     * @param livro O objeto Livro cujos dados serão gravados.
     * @param append Define se a gravação deve sobrescrever (false) ou adicionar (true) ao ficheiro.
     * @throws IOException Se ocorrer um erro ao gravar os dados no ficheiro.
     */
    public static void criarFicheiroCsvLivro(String ficheiro, Livro livro, boolean append) throws IOException {
        try (FileWriter fw = new FileWriter(ficheiro, append)) {
            fw.write(String.join(";",
                    String.valueOf(livro.getId()),
                    livro.getTitulo(),
                    livro.getEditora(),
                    String.valueOf(livro.getCategoria()),
                    String.valueOf(livro.getAnoEdicao()),
                    livro.getIsbn(),
                    livro.getAutor(),
                    String.valueOf(livro.getCodBiblioteca()),
                    "\n"));
        }
    }

    /**
     * Lê os livros do arquivo CSV.
     * Este método lê cada linha do ficheiro CSV, cria um objeto Livro com os dados lidos e adiciona-o à lista de livros.
     *
     * @param ficheiro O caminho para o ficheiro que contém os dados dos livros.
     */
    public static void lerFicheiroCsvLivros(String ficheiro)
    {
        try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            String linha = readFile.readLine();
            if (linha == null) {
                return;
            }
            String csvDivisor = ";";
            do {
                String[] dados = linha.split(csvDivisor);
                int id = Integer.parseInt(dados[0]);
                String titulo = dados[1];
                String editora = dados[2];
                Constantes.Categoria categoria = Constantes.Categoria.valueOf(dados[3]);
                int anoEdicao = Integer.parseInt(dados[4]);
                String isbn = dados[5];
                String autor = dados[6];
                int codBiblioteca = Integer.parseInt(dados[7]);
                Livro livro = new Livro(id, codBiblioteca, titulo, editora, categoria, anoEdicao, isbn, autor);//TODO : codBiblioteca a ser desenvolvido posteriormente
                livros.add(livro);
            }while ((linha = readFile.readLine()) != null);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Solicita os dados do usuário para criar ou editar um livro.
     * Este método solicita ao utilizador que insira o título, editora, categoria, ano de edição, ISBN e autor do livro.
     * Valida o ISBN para garantir que tem 10 ou 13 dígitos e que não está duplicado.
     *
     * @param id O ID do livro a ser inserido.
     * @return Um objeto Livro com os dados inseridos.
     */
    private static Livro inserirDadosLivro(int id) {
        String titulo = lerString("Insira o Título do livro: ");
        String editora = lerString("Insira a Editora do livro: ");
        Constantes.Categoria categoria = selecionaCategoria("Insira a Categoria do livro: ");
        int anoEdicao = lerInt("Insira o ano de Edição do livro: ", true, Constantes.TipoItem.LIVRO);
        String isbn;
        boolean flag;
        do {
            isbn = lerString("Insira o ISBN do livro: ");
            flag = isbn.matches("\\d{9}[\\dX]") || isbn.matches("\\d{13}");
            if (!flag) {
                System.out.println("ISBN Invalido! ( Ex: 0306406152 ou 9780306406157 )");
                continue;
            }
            if (isbn.equals(pesquisarIsbn(isbn, id))) {
                System.out.println("ISBN já existe! Tente novamente.");
                flag = false;
            }
        }while(!flag);
        String autor = lerString("Insira o Autor do livro: ");

        return new Livro(id, 1, titulo, editora, categoria, anoEdicao, isbn, autor);//TODO : codBiblioteca a ser desenvolvido posteriormente
    }

    /**
     * Pesquisa um ISBN na lista de livros.
     * Este método verifica se o ISBN fornecido já existe na lista de livros, excluindo o livro com o ID fornecido.
     *
     * @param isbn O ISBN a ser pesquisado.
     * @param id O ID do livro a ser excluído da pesquisa.
     * @return O ISBN se encontrado, ou null se não encontrado.
     */
    private static String pesquisarIsbn(String isbn, int id) {
        for (Livro livro : livros) {
            if (livro.getIsbn().equals(isbn) && livro.getId() != id) {
                return isbn;
            }
        }
        return null;
    }

    /*
     * ########################### TRATAMENTO DE DADOS LIVROS - FIM #################################################
     */
        /*
     * ########################### TRATAMENTO DE DADOS JORNAIS/REVISTAS - INICIO #################################################
     */

    /**
     * Lê os jornais ou revistas do arquivo CSV.
     * Este método lê cada linha do ficheiro CSV, cria um objeto JornalRevista com os dados lidos e adiciona-o à lista correspondente (jornais ou revistas).
     * Se o ficheiro estiver vazio, exibe uma mensagem informativa.
     *
     * @param ficheiro O caminho do ficheiro CSV.
     * @param tipoItem O tipo de item a ser lido (JORNAL ou REVISTA).
     */
    public static void lerFicheiroCsvJornaisRevistas(String ficheiro, Constantes.TipoItem tipoItem) {
        try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            String linha = readFile.readLine();
            if (linha == null) {
                return;
            }
            do {
                String[] dados = linha.split(Constantes.SplitChar);
                int id = Integer.parseInt(dados[0]);
                String titulo = dados[1];
                String editora = dados[2];
                Constantes.Categoria categoria = Constantes.Categoria.valueOf(dados[3]);
                int anoPub = Integer.parseInt(dados[4]);
                String issn = dados[5];
                Constantes.TipoItem tipo = Constantes.TipoItem.valueOf(dados[6]);
                int codBiblioteca = Integer.parseInt(dados[7]);
                JornalRevista jornalRevista = new JornalRevista(id, titulo, editora, issn, anoPub, codBiblioteca, tipo, categoria);//TODO : codBiblioteca a ser desenvolvido posteriormente
                if (tipoItem == Constantes.TipoItem.JORNAL)
                    jornais.add(jornalRevista);
                else
                    revistas.add(jornalRevista);
            } while ((linha = readFile.readLine()) != null);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adiciona um novo jornal ao sistema.
     * Este método gera automaticamente um ID para o novo jornal, solicita os dados do jornal ao utilizador,
     * adiciona o jornal à lista de jornais e grava a lista atualizada no ficheiro CSV.
     * Exibe uma mensagem de sucesso após a criação do jornal.
     *
     * @throws IOException Se ocorrer um erro durante a gravação dos dados no ficheiro.
     */
    public static void criarJornal() throws IOException {
        jornais.add(inserirDadosJornalRevista(getIdAutomatico(Constantes.TipoItem.JORNAL, -1), Constantes.TipoItem.JORNAL, Constantes.Etapa.CRIAR));
        System.out.println("Jornal criado com sucesso!");
        gravarArrayJornal();
    }

    /**
     * Adiciona uma nova revista ao sistema.
     * Este método gera automaticamente um ID para a nova revista, solicita os dados da revista ao utilizador,
     * adiciona a revista à lista de revistas e grava a lista atualizada no ficheiro CSV.
     * Exibe uma mensagem de sucesso após a criação da revista.
     *
     * @throws IOException Se ocorrer um erro durante a gravação dos dados no ficheiro.
     */
    public static void criarRevista() throws IOException {
        revistas.add(inserirDadosJornalRevista(getIdAutomatico(Constantes.TipoItem.REVISTA, -1), Constantes.TipoItem.REVISTA, Constantes.Etapa.CRIAR));
        System.out.println("Revista criada com sucesso!");
        gravarArrayRevista();
    }

    /**
     * Solicita os dados do usuário para criar ou editar um jornal/revista.
     * Este método solicita ao utilizador que insira o título, editora, categoria, ano de publicação e ISSN do jornal/revista.
     * Valida o ISSN para garantir que tem o formato correto e que não está duplicado.
     * Se o ISSN já existir, solicita ao utilizador que insira um novo ISSN.
     *
     * @param id O ID do jornal/revista a ser inserido.
     * @param tipoItem O tipo de item a ser inserido (JORNAL ou REVISTA).
     * @param etapa A etapa do processo (CRIAR ou EDITAR).
     * @return Um objeto JornalRevista com os dados inseridos.
     */
    private static JornalRevista inserirDadosJornalRevista(int id, Constantes.TipoItem tipoItem, Constantes.Etapa etapa) {
        String titulo = lerString("Insira o Título do " + tipoItem.toString().toLowerCase() + ": ");
        String editora = lerString("Insira a Editora do " + tipoItem.toString().toLowerCase() + ": ");
        String issn;
        boolean flag;
        Constantes.Categoria categoria = selecionaCategoria("Insira a Categoria do " + tipoItem.toString().toLowerCase() + ": ");
        int anoPub = lerInt("Insira o ano de publicação do " + tipoItem.toString().toLowerCase() + ": ", true, tipoItem);
        do {
            flag=true;
            issn = lerString("Insira o ISSN do " + tipoItem.toString().toLowerCase() + ": ");
            if (!issn.matches("^\\d{4}-\\d{3}[X0-9]$")) {
                System.out.println("ISSN Invalido! ( Ex: 1111-111X )");
                flag=false;
                continue;
            }
            for(JornalRevista jornal : jornais)
            {
                if(jornal.getIssn().equals(issn) && etapa == Constantes.Etapa.CRIAR)
                {
                    flag=false;
                }
                else if(jornal.getIssn().equals(issn) && etapa == Constantes.Etapa.EDITAR && jornal.getId() != id)
                {
                    flag=false;
                }
            }
            for(JornalRevista revista : revistas )
            {
                if(revista.getIssn().equals(issn) && etapa == Constantes.Etapa.CRIAR)
                {
                    flag=false;
                }
                else if(revista.getIssn().equals(issn) && etapa == Constantes.Etapa.EDITAR && revista.getId() != id)
                {
                    flag=false;
                }
            }
            if(!flag)
                System.out.println("ISSN já existe! Tente novamente.");
        }while(!flag);
        return new JornalRevista(id, titulo, editora, issn, anoPub, 1, tipoItem, categoria);//TODO : codBiblioteca a ser desenvolvido posteriormente
    }

    /**
     * Lista todos os jornais ou revistas cadastrados no sistema.
     * Este método verifica se a lista de jornais ou revistas está vazia e, se não estiver, exibe os dados de todos os itens.
     * Se a lista estiver vazia, exibe uma mensagem informativa.
     *
     * @param tipoItem O tipo de item a ser listado (JORNAL ou REVISTA).
     */
    public static void listaTodosJornalRevista(Constantes.TipoItem tipoItem) {
        if (tipoItem == Constantes.TipoItem.JORNAL && jornais.isEmpty()) {
            System.out.println("Não existem jornais para mostrar.");
            return;
        }
        else if (tipoItem == Constantes.TipoItem.REVISTA && revistas.isEmpty()) {
            System.out.println("Não existem revistas para mostrar.");
            return;
        }

        if (tipoItem == Constantes.TipoItem.JORNAL)
            mostraTabelaJornalRevista(jornais);
        else
            mostraTabelaJornalRevista(revistas);
    }

    /**
     * Lista um jornal ou revista pelo ISSN fornecido.
     * Este método solicita ao utilizador que insira o ISSN do jornal/revista, realiza a pesquisa e exibe os detalhes do item encontrado.
     * Se não encontrar o item, exibe uma mensagem informativa.
     * Permite ao utilizador voltar ao menu anterior digitando 0.
     *
     * @param tipoItem O tipo de item a ser listado (JORNAL ou REVISTA).
     */
    public static void listaJornalRevistaPorIssn(Constantes.TipoItem tipoItem) {
        String issn;
        boolean exists = false;

        if (tipoItem == Constantes.TipoItem.JORNAL && jornais.isEmpty()) {
            System.out.println("Não existem jornais para mostrar.");
        }
        else if (tipoItem == Constantes.TipoItem.REVISTA && revistas.isEmpty()) {
            System.out.println("Não existem revistas para mostrar.");
        }
        else{
            do{
                issn = lerString("Digite o ISSN do " + tipoItem.toString().toLowerCase() + " que deseja encontrar (0 para voltar ao menu anterior) :");

                if(!issn.equals("0")) {
                    if (tipoItem == Constantes.TipoItem.JORNAL) {
                        for (JornalRevista jornal : jornais) {
                            if (jornal.getIssn().equals(issn)) {
                                mostraTabelaJornalRevista(Collections.singletonList(jornal));
                                exists = true;
                            }
                        }
                    } else {
                        for (JornalRevista revista : revistas) {
                            if (revista.getIssn().equals(issn)) {
                                mostraTabelaJornalRevista(Collections.singletonList(revista));
                                exists = true;
                            }
                        }
                    }
                    if (!exists)
                        System.out.println("O item " + tipoItem.toString().toLowerCase() + " não foi encontrado.");
                }
            }while (!issn.equals("0"));
        }
    }

    /**
     * Grava a lista de jornais em um arquivo CSV.
     * Este método itera pela lista de jornais e grava os dados de cada jornal no ficheiro CSV.
     * Utiliza o método criarFicheiroCsvJornalRevista para gravar os dados.
     *
     * @throws IOException Se ocorrer um erro de I/O durante as operações.
     */
    public static void gravarArrayJornal() throws IOException {
        for (int i = 0; i < jornais.size(); i++)
            criarFicheiroCsvJornalRevista(Constantes.Path.JORNAL.getValue(), jornais.get(i), i != 0);
    }

    /**
     * Grava a lista de revistas em um arquivo CSV.
     * Este método itera pela lista de revistas e grava os dados de cada revista no ficheiro CSV.
     * Utiliza o método criarFicheiroCsvJornalRevista para gravar os dados.
     *
     * @throws IOException Se ocorrer um erro de I/O durante as operações.
     */
    public static void gravarArrayRevista() throws IOException {
        for (int i = 0; i < revistas.size(); i++)
            criarFicheiroCsvJornalRevista(Constantes.Path.REVISTA.getValue(), revistas.get(i), i != 0);
    }

    /**
     * Cria um arquivo CSV para armazenar os dados dos jornais/revistas.
     * Este método grava os dados de um jornal/revista no ficheiro CSV.
     * Utiliza o FileWriter para escrever os dados no ficheiro.
     *
     * @param ficheiro O caminho do ficheiro CSV.
     * @param jornalRevista O objeto JornalRevista cujos dados serão gravados.
     * @param append Define se a gravação deve sobrescrever (false) ou adicionar (true) ao ficheiro.
     * @throws IOException Se ocorrer um erro ao gravar os dados no ficheiro.
     */
    public static void criarFicheiroCsvJornalRevista(String ficheiro, JornalRevista jornalRevista, boolean append) throws IOException {
        try (FileWriter fw = new FileWriter(ficheiro, append)) {
            fw.write(String.join(Constantes.SplitChar,
                    String.valueOf(jornalRevista.getId()),
                    jornalRevista.getTitulo(),
                    jornalRevista.getEditora(),
                    String.valueOf(jornalRevista.getCategoria()),
                    String.valueOf(jornalRevista.getDataPublicacao()),
                    jornalRevista.getIssn(),
                    String.valueOf(jornalRevista.getTipo()),
                    String.valueOf(jornalRevista.getCodBiblioteca()),
                    "\n"));
        }
    }

    /**
     * Edita os dados de uma revista ou jornal existente.
     * Este método lista todos os jornais/revistas, solicita ao utilizador que escolha o ID do item a ser editado,
     * solicita os novos dados do item, atualiza a lista de jornais/revistas e grava a lista atualizada no ficheiro CSV.
     * Exibe uma mensagem de sucesso após a edição.
     *
     * @param tipoItem O tipo de item a ser editado (JORNAL ou REVISTA).
     * @throws IOException Se ocorrer um erro de I/O durante a gravação dos dados.
     */
    public static void editarJornalRevista(Constantes.TipoItem tipoItem) throws IOException {
        int idEditar;
        boolean flag;
        if(tipoItem== Constantes.TipoItem.JORNAL && jornais.isEmpty()) {
            System.out.println("Não existem jornais nesta Biblioteca.");
            return;
        }
        if(tipoItem == Constantes.TipoItem.REVISTA && revistas.isEmpty()){
            System.out.println("Não existem revistas nesta Biblioteca.");
            return;
        }
        listaTodosJornalRevista(tipoItem);
        do {
            flag=true;
            idEditar = lerInt("Escolha o ID do " + tipoItem.toString().toLowerCase() + " que deseja editar (0 para voltar ao menu anterior) :", false, null);
            if(idEditar != 0) {
                for(ReservaLinha reservaLinha : reservasLinha){
                   if(reservaLinha.getIdItem()==idEditar && reservaLinha.getEstado() == Constantes.Estado.RESERVADO){
                       System.out.println("Não pode editar o item " + tipoItem.toString().toLowerCase() + " com reservas ativas.");
                       flag=false;
                       break;
                   }
                }
                if(flag)
                {
                    for(EmprestimoLinha emprestimoLinha : emprestimosLinha){
                        if(emprestimoLinha.getIdItem()==idEditar && emprestimoLinha.getEstado() == Constantes.Estado.EMPRESTADO){
                            System.out.println("Não pode editar o item " + tipoItem.toString().toLowerCase() + " com empréstimos ativos.");
                            flag=false;
                            break;
                        }
                    }
                }
                if (tipoItem == Constantes.TipoItem.JORNAL && flag) {
                    for (JornalRevista jornal : jornais) {
                        if (jornal.getId() == idEditar) {
                            jornais.set(jornais.indexOf(jornal), inserirDadosJornalRevista(idEditar, tipoItem, Constantes.Etapa.EDITAR));
                            gravarArrayJornal();
                            idEditar = 0;
                            System.out.println("O item " + tipoItem.toString().toLowerCase() + " foi editado com sucesso.");
                            break;
                        }
                        else
                            System.out.println("O item " + tipoItem.toString().toLowerCase() + " não existe.");
                    }
                } else if (tipoItem == Constantes.TipoItem.REVISTA && flag){
                    for (JornalRevista revista : revistas) {
                        if (revista.getId() == idEditar) {
                            revistas.set(revistas.indexOf(revista), inserirDadosJornalRevista(idEditar, tipoItem, Constantes.Etapa.EDITAR));
                            gravarArrayRevista();
                            idEditar = 0;
                            System.out.println("O item " + tipoItem.toString().toLowerCase() + " foi editado com sucesso.");
                            break;
                        }
                        else
                            System.out.println("O item " + tipoItem.toString().toLowerCase() + " não existe.");
                    }
                }
            }
        }while(idEditar != 0);
    }

    /**
     * Apaga uma revista ou jornal pelo ID.
     * Este método lista todos os jornais/revistas, solicita ao utilizador que escolha o ID do item a ser apagado,
     * verifica se o item possui empréstimos ou reservas ativas, e se não possuir, remove o item da lista e grava a lista atualizada no ficheiro CSV.
     * Exibe uma mensagem de sucesso após a remoção.
     *
     * @param tipoItem O tipo de item a ser apagado (JORNAL ou REVISTA).
     * @throws IOException Se ocorrer um erro de I/O durante a gravação dos dados.
     */
    public static void apagarJornalRevista(Constantes.TipoItem tipoItem) throws IOException {
        boolean flag;
        int idApagar;
        if(tipoItem== Constantes.TipoItem.JORNAL && jornais.isEmpty()) {
            System.out.println("Não existem jornais nesta Biblioteca.");
            return;
        }
        if(tipoItem == Constantes.TipoItem.REVISTA && revistas.isEmpty()){
            System.out.println("Não existem revistas nesta Biblioteca.");
            return;
        }
        listaTodosJornalRevista(tipoItem);
        do {
            flag=true;
            idApagar = lerInt("Escolha o ID do(a) " + tipoItem.toString().toLowerCase() + " que deseja apagar (0 para voltar ao menu anterior) :", false, null);
            if(idApagar != 0) {
                if (tipoItem == Constantes.TipoItem.JORNAL) {
                    for (JornalRevista jornal : jornais) {
                        if (jornal.getId() == idApagar) {
                            for (ReservaLinha reservaLinha : reservasLinha)
                                if (reservaLinha.getIdItem() == idApagar && reservaLinha.getTipoItem() == tipoItem && reservaLinha.getEstado() == Constantes.Estado.RESERVADO) {
                                    flag = false;
                                    break;
                                }

                            for (EmprestimoLinha emprestimoLinha : emprestimosLinha)
                                if (emprestimoLinha.getIdItem() == idApagar && emprestimoLinha.getTipoItem() == tipoItem && emprestimoLinha.getEstado() == Constantes.Estado.EMPRESTADO) {
                                    flag = false;
                                    break;
                                }
                            if (flag) {
                                idApagar=0;
                                jornais.remove(jornal);
                                gravarArrayJornal();
                                System.out.println("O item " + tipoItem.toString().toLowerCase() + " foi apagado com sucesso!");
                                break;
                            }
                        }
                    }
                } else {
                    for (JornalRevista revista : revistas) {
                        if (revista.getId() == idApagar) {
                            for (ReservaLinha reservaLinha : reservasLinha)
                                if (reservaLinha.getIdItem() == idApagar && reservaLinha.getTipoItem() == tipoItem && reservaLinha.getEstado() == Constantes.Estado.RESERVADO) {
                                    flag = false;
                                    break;
                                }
                            for (EmprestimoLinha emprestimoLinha : emprestimosLinha)
                                if (emprestimoLinha.getIdItem() == idApagar && emprestimoLinha.getTipoItem() == tipoItem && emprestimoLinha.getEstado() == Constantes.Estado.EMPRESTADO) {
                                    flag = false;
                                    break;
                                }
                            if (flag) {
                                idApagar=0;
                                revistas.remove(revista);
                                gravarArrayRevista();
                                System.out.println("O item " + tipoItem.toString().toLowerCase() + " foi apagado com sucesso!");
                                break;
                            }
                        }
                    }
                }
                if (!flag)
                    System.out.println("Não pode apagar o item " + tipoItem.toString().toLowerCase() + " com empréstimos ou reservas ativas.");
            }
        }while(!flag || idApagar != 0);
    }

    /*
     * ########################### TRATAMENTO DE DADOS JORNAIS/REVISTAS - FIM #################################################
     */
    /*
     * ########################### TRATAMENTO DE DADOS RESERVAS - INICIO #################################################
     */

    /**
     * Metodo para inserir os dados de uma reserva.
     * Este metodo solicita ao utilizador que selecione o cliente e insira as datas da reserva.
     * Garante que a data de início não é anterior a hoje e que a data de fim não é anterior à data de início.
     * Valida o cliente com base em ID, NIF ou contacto.
     *
     * @param id O ID da reserva.
     * @return Um novo objeto Reserva com os detalhes fornecidos.
     */
    public static Reserva inserirDadosReserva(int id) {
        Cliente cliente = null;
        LocalDate dataInicio;
        LocalDate dataFim;
        Constantes.Estado estado;

        // Verifica qual é a maneira como queremos procurar pelo cliente, para ser mais flexível
        do {
            int opcao = lerInt("Escolha a opção de validação do cliente (1 - ID, 2 - Contribuinte, 3 - Contacto): ", false, null);
            Constantes.ValidacaoCliente validacaoCliente;
            switch (opcao) {
                case 1:
                    validacaoCliente = Constantes.ValidacaoCliente.ID;
                    break;
                case 2:
                    validacaoCliente = Constantes.ValidacaoCliente.NIF;
                    break;
                case 3:
                    validacaoCliente = Constantes.ValidacaoCliente.CONTACTO;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    continue;
            }
            int valor = lerInt("Insira o " + validacaoCliente.toString().toLowerCase() + " : ", false, null);
            cliente = validarCliente(validacaoCliente, valor);
            if (cliente == null) {
                System.out.println("Cliente não encontrado. Tente novamente.");
            }
        } while (cliente == null);

        // Introdução das datas.
        // É validado se a data início introduzida é inferior a hoje.
        // É validado se a data fim introduzida é inferior à início e superior ao limite estipulado.
        do {
            dataInicio = lerData("Insira a data de início da reserva (dd/MM/yyyy): ");
            if (dataInicio.isBefore(Constantes.getDatahoje()) || dataInicio.isAfter(Constantes.getDatahoje().plusDays(30))) {
                System.out.println("A data de início não pode ser anterior ao dia de hoje. (Com Max de 30 dias após)");
            }
        } while (dataInicio.isBefore(Constantes.getDatahoje()) || dataInicio.isAfter(Constantes.getDatahoje().plusDays(30)));

        do {
            dataFim = lerData("Insira a data de fim da reserva (dd/MM/yyyy): ");
            if (dataFim.isBefore(dataInicio)) {
                System.out.println("A data de fim não pode ser anterior à data de início.");
            } else if (dataFim.isAfter(dataInicio.plusDays(Constantes.TempoMaxReservaDias))) {
                System.out.println("A reserva não pode ser superior a " + Constantes.TempoMaxReservaDias + " dias.");
            }
        } while (dataFim.isBefore(dataInicio) || dataFim.isAfter(dataInicio.plusDays(Constantes.TempoMaxReservaDias)));

        estado = Constantes.Estado.RESERVADO;
        return new Reserva(codBibliotecaSessao, id, dataInicio, dataFim, cliente, null, estado);//TODO : codBiblioteca a ser desenvolvido posteriormente
    }

    /**
     * Metodo para criar uma nova reserva.
     * Verifica se existem clientes e itens (livros, jornais, revistas) na biblioteca.
     * Solicita ao utilizador que insira os dados da reserva e adiciona itens à reserva.
     * Grava a reserva e seus detalhes em arquivos CSV.
     *
     * @throws IOException Se ocorrer um erro durante a gravação dos dados no ficheiro.
     */
    public static void criarReserva() throws IOException {
        int opcao = 1;
        // Mostra mensagem a informar que a Biblioteca não tem nada que seja possível reserva, e sai fora.
        if (livros.isEmpty() && jornais.isEmpty() && revistas.isEmpty()) {
            System.out.println("Não existem Items nesta Biblioteca");
            return;
        }
        // Mostra mensagem a informar que não tem cliente.
        if (clientes.isEmpty()) {
            System.out.println("Não existem clientes nesta Biblioteca");
            return;
        }
        mostraTabelaClientes(clientes);

        // Atribui automaticamente o Id com base no último Id existente.
        int idReserva = getIdAutomatico(Constantes.TipoItem.RESERVA, -1);

        // Cria a reserva
        reservas.add(inserirDadosReserva(idReserva));
        Reserva reserva = reservas.getLast();

        boolean firstEntry = true;
        do {
            if (opcao < 1 || opcao > 2) {
                System.out.println("Opção inválida! Tente novamente.");
            } else {
                if (!criarDetalheEmprestimoReserva(reserva.getNumMovimento(), Constantes.TipoItem.RESERVA) && firstEntry) {
                    reservas.remove(reservas.getLast());
                    return;
                }
                firstEntry = false;
            }
            opcao = lerInt("Deseja adicionar mais Items à Reserva? (1 - Sim, 2 - Não)", false, null);
        } while (opcao != 2);

        System.out.println("Reserva criada com sucesso!");

        gravarArrayReservas();
        gravarArrayReservaLinha();
    }

    /**
     * Metodo para editar uma reserva existente.
     * Lista todas as reservas e permite ao utilizador adicionar ou remover itens da reserva.
     * Grava as alterações nos arquivos CSV.
     *
     * @throws IOException Se ocorrer um erro durante a gravação dos dados.
     */
    public static void editarReserva() throws IOException {

        // Verifica se a lista de reservas está vazia
        if (reservas.isEmpty()) {
            System.out.println("Não há reservas nesta biblioteca.");
            return;
        }

        // Lista todas as reservas
        listaTodasReservas(Constantes.Etapa.EDITAR);
        boolean flag = false;
        do{
            // Lê o ID da reserva a ser editada
            int idEditar = lerInt("Escolha o id da reserva (0 - para voltar): ", false, null);
            if (idEditar == 0)
                return;
            for (Reserva reserva : reservas) {
                if (reserva.getNumMovimento() == idEditar ) {
                    flag=true;
                    break;
                }
            }
            if (!flag){
                System.out.println("Id Inválido!");
            }else {
                listarDetalhesReserva(idEditar, Constantes.Etapa.EDITAR);
            }
            if (flag){
                int opcao = lerInt("Escolha uma opção :\n1 - Adicionar Item\n2 - Remover Item\n0 - Voltar\n", false, null);
                switch (opcao) {
                    case 0:
                        return;
                    case 1:
                        criarDetalheEmprestimoReserva(idEditar, Constantes.TipoItem.RESERVA);
                        gravarArrayReservaLinha();
                        break;
                    case 2:
                        opcao = 1;
                        do {
                            if (opcao != 1)
                                System.out.println("Número Inválido!");
                            else if (!RemoverItemReservaEmprestimo(idEditar, Constantes.TipoItem.RESERVA)) {
                                System.out.println("Não existem mais itens para remover!");
                                break;
                            }
                            opcao = lerInt("Deseja remover mais algum item? (1 - Sim, 2 - Não)", false, null);
                        } while (opcao != 2);
                        gravarArrayReservaLinha();
                        break;
                    default:
                        System.out.println("Escolha invalida! Tente novamente.");

                }
            }
        }while(!flag);
    }

    /**
     * Metodo para listar os detalhes de uma reserva.
     * Procura a reserva pelo ID e exibe os detalhes da reserva.
     *
     * @throws IOException Se ocorrer um erro durante a leitura dos dados.
     */
    public static void listarDetalhesReserva(int idReserva, Constantes.Etapa etapa) throws IOException {
        // Lista de apoio para editar os detalhes
        List<ReservaLinha> reservaLinhaDetails = new ArrayList<>();
        boolean flag = false;

        do {
            boolean hasReservas = hasReservas();

            if (!hasReservas) return;
            if (etapa == Constantes.Etapa.LISTAR) {
                idReserva = lerInt("Escolha o id da reserva (0 - para voltar): ", false, null);

                if (idReserva == 0) {
                    return;
                }
            }
            // Procura a reserva pelo ID e acrescenta a Lista de Detalhes para apresentar a reserva completa
            for (ReservaLinha reservaLinha : reservasLinha) {
                if (reservaLinha.getIdReserva() == idReserva) {
                    reservaLinhaDetails.add(reservaLinha);
                    flag = true;
                }
            }
            if (!flag) {
                System.out.println("Id Inválido!");
            } else {
                mostraDetalhesReservas(reservaLinhaDetails, 0, null);
            }
        }while(!flag);
    }

    /**
     * Metodo para remover um item de uma reserva ou empréstimo.
     * Solicita ao utilizador que escolha o tipo de item e o ID do item a ser removido.
     * Atualiza o estado do item para CANCELADO.
     *
     * @param id O ID da reserva ou empréstimo.
     * @param tipoServico O tipo de serviço (RESERVA ou EMPRESTIMO).
     * @return true se o item foi removido com sucesso, false caso contrário.
     */
    public static Boolean RemoverItemReservaEmprestimo(int id, Constantes.TipoItem tipoServico) {
        Constantes.TipoItem tipoItem = null;
        int idItem, opcao, i;
        boolean flag = false;
        do {
            opcao = lerInt("Escolha o tipo de item (1 - Livro, 2 - Revista, 3 - Jornal): ", false, null);
            switch (opcao) {
                case 1:
                    tipoItem = Constantes.TipoItem.LIVRO;
                    break;
                case 2:
                    tipoItem = Constantes.TipoItem.REVISTA;
                    break;
                case 3:
                    tipoItem = Constantes.TipoItem.JORNAL;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao < 1 || opcao > 3);
        if (tipoServico == Constantes.TipoItem.RESERVA)
            mostraDetalhesReservas(reservasLinha, id, tipoItem);
        else
            mostraDetalhesEmprestimos(emprestimosLinha, id, tipoItem);
        do {
            i = 0;
            idItem = lerInt("Escolha o ID do Item (0 para Retornar): ", false, null);
            if (idItem == 0)
                return false;

            if (tipoServico == Constantes.TipoItem.RESERVA) {
                for (ReservaLinha reservaLinha : reservasLinha) {
                    if (reservaLinha.getIdReserva() == id && reservaLinha.getIdItem() == idItem &&
                            reservaLinha.getTipoItem() == tipoItem && reservaLinha.getEstado() == Constantes.Estado.RESERVADO) {
                        reservaLinha.setEstado(Constantes.Estado.CANCELADO);
                        flag = true;
                    }
                    if (reservaLinha.getIdReserva() == id && reservaLinha.getEstado() == Constantes.Estado.RESERVADO) {
                        i++;
                    }
                }
                if (i == 0)
                    for (Reserva reserva : reservas)
                        if (reserva.getNumMovimento() == id) {
                            reserva.setEstado(Constantes.Estado.CANCELADO);
                            return false;
                        }
            } else {
                for (EmprestimoLinha emprestimoLinha : emprestimosLinha) {
                    if (emprestimoLinha.getIdEmprestimo() == id && emprestimoLinha.getIdItem() == idItem &&
                            emprestimoLinha.getTipoItem() == tipoItem && emprestimoLinha.getEstado() == Constantes.Estado.EMPRESTADO) {
                        emprestimoLinha.setEstado(Constantes.Estado.CANCELADO);
                        flag = true;
                    }
                    if (emprestimoLinha.getIdEmprestimo() == id && emprestimoLinha.getEstado() == Constantes.Estado.EMPRESTADO) {
                        i++;
                    }
                }
                if (i == 0)
                    for (Emprestimo emprestimo : emprestimos)
                        if (emprestimo.getNumMovimento() == id) {
                            emprestimo.setEstado(Constantes.Estado.CANCELADO);
                            return false;
                        }
            }
            if (!flag)
                System.out.println("Número Inválido!");
        } while (!flag);
        return true;
    }

    /**
     * Metodo para cancelar uma reserva na totalidade ou apenas alguns dos itens que lhe pertencem.
     * Atualiza o estado da reserva e dos itens para CANCELADO.
     *
     * @param estado O estado da reserva (CANCELADO).
     * @throws IOException Se ocorrer um erro durante a gravação dos dados.
     */
    public static void cancelarReserva( Constantes.Estado estado) throws IOException {

        boolean hasReservas = hasReservas();
        boolean flag=false;
        if (!hasReservas) return;

        do {
            int idCancelar = lerInt("Escolha o id da reserva (0 - para voltar): ", false, null);
            if (idCancelar == 0)
                return;

            for (Reserva reserva : reservas) {
                if (reserva.getNumMovimento() == idCancelar) {
                    reserva.setEstado(estado);
                    for (ReservaLinha reservaLinha : reservasLinha) {
                        if (reservaLinha.getIdReserva() == idCancelar) {
                            reservaLinha.setEstado(estado);
                            flag = true;
                        }
                    }
                }
            }
            if (!flag) {
                System.out.println("Id inválido");
            }
        }while (!flag);
            gravarArrayReservas();
            gravarArrayReservaLinha();
    }

    /**
     * Cria um arquivo CSV para armazenar os dados das reservas.
     * Este método grava os dados de uma reserva no ficheiro CSV.
     * Utiliza o FileWriter para escrever os dados no ficheiro.
     *
     * @param ficheiro O caminho do ficheiro CSV.
     * @param reserva O objeto Reserva cujos dados serão gravados.
     * @param firstLine Define se a gravação deve sobrescrever (false) ou adicionar (true) ao ficheiro.
     * @throws IOException Se ocorrer um erro ao gravar os dados no ficheiro.
     */
    public static void criarFicheiroCsvReservas(String ficheiro, Reserva reserva, Boolean firstLine) throws IOException {
        try (FileWriter fw = new FileWriter(ficheiro, firstLine)) {
            fw.write(String.join(";",
                    Integer.toString(reserva.getCodBiblioteca()),
                    Integer.toString(reserva.getNumMovimento()),
                    reserva.getDataInicio().toString(),
                    reserva.getDataFim().toString(),
                    Integer.toString(reserva.getClienteId()),
                    reserva.getEstado() + "\n"));
        }
    }

    /**
     * Lê os dados das reservas a partir de um ficheiro CSV.
     * Este método lê cada linha do ficheiro CSV, cria um objeto Reserva com os dados lidos e adiciona-o à lista de reservas.
     * Se o cliente associado à reserva não for encontrado, cria um cliente com dados padrão.
     *
     * @param ficheiro O caminho do ficheiro CSV.
     */
    public static void lerFicheiroCsvReservas(String ficheiro) {
        try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            String linha;
            while ((linha = readFile.readLine()) != null) {
                Cliente cliente = null;
                String[] dados = linha.split(Constantes.SplitChar);
                int codBiblioteca = Integer.parseInt(dados[0]);
                int codMovimento = Integer.parseInt(dados[1]);
                LocalDate dataInicio = LocalDate.parse(dados[2]);
                LocalDate dataFim = LocalDate.parse(dados[3]);
                int id = Integer.parseInt(dados[4]);
                for (Cliente clienteReserva : clientes) {
                    if (clienteReserva.getId() == id) {
                        cliente = clienteReserva;
                        break;
                    }
                }
                Constantes.Estado estado = Constantes.Estado.valueOf(dados[5]);

                List<ReservaLinha> reservaLinha = new ArrayList<>();

                for (ReservaLinha resLinha : reservasLinha) {
                    if (resLinha.getIdReserva() == codMovimento) {
                        reservaLinha.add(resLinha);
                    }
                }
                if (cliente == null)
                    cliente = new Cliente(0, "APAGADO", Constantes.Genero.INDEFINIDO, 0, 0, codBiblioteca);
                Reserva reserva = new Reserva(codBiblioteca, codMovimento, dataInicio, dataFim, cliente, reservaLinha, estado);
                reservas.add(reserva);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lista todas as reservas existentes.
     * Exibe uma mensagem se não houver reservas para mostrar.
     * Utiliza o método mostraTabelaReservas para exibir os dados das reservas.
     *
     * @param etapa A etapa do processo (LISTAR, EDITAR, etc.).
     * @return true se houver reservas para mostrar, false caso contrário.
     */
    public static boolean listaTodasReservas(Constantes.Etapa etapa) {
        if (reservas.isEmpty()) {
            System.out.println("Não existem reservas para mostrar.");
            return false;
        }

        mostraTabelaReservas(reservas, etapa);
        return true;
    }

    /**
     * Grava a lista de reservas em um ficheiro CSV.
     * Este método itera pela lista de reservas e grava os dados de cada reserva no ficheiro CSV.
     * Utiliza o método criarFicheiroCsvReservas para gravar os dados.
     *
     * @throws IOException Se ocorrer um erro de I/O durante as operações.
     */
    public static void gravarArrayReservas() throws IOException {
        for (int i = 0; i < reservas.size(); i++)
            criarFicheiroCsvReservas(Constantes.Path.RESERVA.getValue(), reservas.get(i), i != 0);
    }

    /*
     * ############################### TRATAMENTO DE DADOS RESERVAS - FIM ##############################################
     */

/*
 * ############################### TRATAMENTO DE DADOS DETALHES RESERVAS - INICIO ##############################################
 * */

/**
 * Método para inserir os detalhes de uma reserva atribuída a um cliente.
 * Este método solicita ao utilizador que insira o ID do item a ser reservado e valida se o item está disponível.
 * Se o item já estiver reservado ou emprestado no período especificado, uma exceção é lançada.
 *
 * @param reservaId O ID da reserva.
 * @param tipoItem O tipo de item a ser reservado (LIVRO, JORNAL, REVISTA).
 * @param dataInicio A data de início da reserva.
 * @param dataFim A data de fim da reserva.
 * @return Um objeto ReservaLinha contendo os detalhes da reserva.
 * @throws IllegalArgumentException Se o item já estiver reservado ou emprestado no período especificado.
 */
public static ReservaLinha inserirDetalhesReserva(int reservaId, Constantes.TipoItem tipoItem, LocalDate dataInicio, LocalDate dataFim) {
    int idItem = 0;
    int reservaLinhaId = getIdAutomatico(Constantes.TipoItem.RESERVALINHA, reservaId);
    boolean flag;

    // Lista todos os itens do tipo especificado
    switch (tipoItem) {
        case LIVRO:
            listaTodosLivros();
            break;
        case JORNAL:
            listaTodosJornalRevista(Constantes.TipoItem.JORNAL);
            break;
        case REVISTA:
            listaTodosJornalRevista(Constantes.TipoItem.REVISTA);
            break;
    }

    // Solicita ao utilizador que insira o ID do item e valida se o item existe
    do {
        flag = false;
        idItem = lerInt("Insira o ID do Item: ", false, null);
        switch (tipoItem) {
            case LIVRO:
                for (Livro livro : livros)
                    if (livro.getId() == idItem) {
                        flag = true;
                        break;
                    }
                break;
            case JORNAL:
                for (JornalRevista jornal : jornais)
                    if (jornal.getId() == idItem) {
                        flag = true;
                        break;
                    }
                break;
            case REVISTA:
                for (JornalRevista revista : revistas)
                    if (revista.getId() == idItem) {
                        flag = true;
                        break;
                    }
                break;
        }
        if (!flag)
            System.out.println("Número Inválido!");
    } while (!flag);

    // Verifica se o item já está reservado no período especificado
    for (Reserva reserva : reservas)
        for (ReservaLinha reservaLinha : reservasLinha)
            if (reservaLinha.getIdItem() == idItem && reservaLinha.getTipoItem() == tipoItem)
                if (reservaLinha.getEstado() == Constantes.Estado.RESERVADO && reservaLinha.getIdReserva() == reserva.getNumMovimento())
                    if ((reserva.getDataInicio().isBefore(dataFim) || reserva.getDataInicio().isEqual(dataFim)) &&
                            (reserva.getDataFim().isAfter(dataInicio) || reserva.getDataFim().isEqual(dataInicio)))
                        throw new IllegalArgumentException("Item já reservado!");

    // Verifica se o item já está emprestado no período especificado
    for (Emprestimo emprestimo : emprestimos)
        for (EmprestimoLinha emprestimoLinha : emprestimosLinha)
            if (emprestimoLinha.getIdItem() == idItem && emprestimoLinha.getTipoItem() == tipoItem &&
                    emprestimo.getEstado() == Constantes.Estado.EMPRESTADO && emprestimoLinha.getIdEmprestimo() == emprestimo.getNumMovimento() &&
                    (emprestimo.getDataInicio().isBefore(dataFim) || emprestimo.getDataInicio().isEqual(dataFim)) &&
                    (emprestimo.getDataFim().isAfter(dataInicio) || emprestimo.getDataFim().isEqual(dataInicio)))
                throw new IllegalArgumentException("Item já emprestado!");

    return new ReservaLinha(reservaId, reservaLinhaId, tipoItem, idItem, Constantes.Estado.RESERVADO);
}

/**
 * Método para criar o ficheiro de detalhes de uma reserva atribuída a um cliente.
 * Este método grava os detalhes de uma reserva no ficheiro CSV especificado.
 *
 * @param ficheiro O caminho do ficheiro CSV.
 * @param reservaLinha O objeto ReservaLinha contendo os detalhes da reserva.
 * @param firstLine Define se a gravação deve sobrescrever (false) ou adicionar (true) ao ficheiro.
 * @throws IOException Se ocorrer um erro ao gravar os dados no ficheiro.
 */
public static void criarFicheiroCsvReservasLinha(String ficheiro, ReservaLinha reservaLinha, Boolean firstLine) throws IOException {
    try (FileWriter fw = new FileWriter(ficheiro, firstLine)) {
        fw.write(String.join(Constantes.SplitChar,
                Integer.toString(reservaLinha.getIdReserva()),
                Integer.toString(reservaLinha.getIdReservaLinha()),
                reservaLinha.getTipoItem().toString(),
                Integer.toString(reservaLinha.getIdItem()),
                reservaLinha.getEstado().toString() + "\n"));
    }
}

/**
 * Método para ler o ficheiro de detalhes de reserva e carregar a informação no array reservasLinha.
 * Este método lê cada linha do ficheiro CSV, cria um objeto ReservaLinha com os dados lidos e adiciona-o à lista reservasLinha.
 *
 * @param ficheiro O caminho do ficheiro CSV.
 */
public static void lerFicheiroCsvReservasLinha(String ficheiro) {
    try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
        String linha = readFile.readLine();

        if (linha == null) {
            return;
        }

        do {
            String[] dados = linha.split(Constantes.SplitChar);
            int reservaId = Integer.parseInt(dados[0]);
            int reservaLinhaId = Integer.parseInt(dados[1]);
            Constantes.TipoItem tipoItem = Constantes.TipoItem.valueOf(dados[2]);
            int idItem = Integer.parseInt(dados[3]);
            Constantes.Estado estado = Constantes.Estado.valueOf(dados[4]);
            reservasLinha.add(new ReservaLinha(reservaId, reservaLinhaId, tipoItem, idItem, estado));
        } while ((linha = readFile.readLine()) != null);
    } catch (IOException e) {
        System.out.println(e.getMessage());
    }
}

/**
 * Método para gravar as alterações efetuadas no array reservasLinha no ficheiro reservasdtl.csv.
 * Este método itera pela lista reservasLinha e grava os dados de cada reserva no ficheiro CSV.
 *
 * @throws IOException Se ocorrer um erro de I/O durante as operações.
 */
public static void gravarArrayReservaLinha() throws IOException {
    for (int i = 0; i < reservasLinha.size(); i++) 
        criarFicheiroCsvReservasLinha(Constantes.Path.RESERVALINHA.getValue(), reservasLinha.get(i), i != 0);
}

/*
 * ############################### TRATAMENTO DE DADOS DETALHES RESERVAS - FIM ##############################################
 * */

/*
 * ############################### TRATAMENTO DE DADOS EMPRESTIMO - INICIO ##############################################
 * */

/**
 * Método responsável por ler os dados dos empréstimos a partir de um ficheiro CSV.
 * Este método lê cada linha do ficheiro, cria um objeto Emprestimo com os dados lidos e adiciona-o à lista de empréstimos.
 * Se o cliente associado ao empréstimo não for encontrado, cria um cliente com dados padrão.
 *
 * @param ficheiro O caminho do ficheiro CSV a ser lido.
 */
public static void lerFicheiroCsvEmprestimos(String ficheiro) {
    try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
        String linha = readFile.readLine();
        if (linha == null) {
            return;
        }
        do {
            Cliente cliente = null;
            String[] dados = linha.split(Constantes.SplitChar);
            int codBiblioteca = Integer.parseInt(dados[0]);
            int codMovimento = Integer.parseInt(dados[1]);
            LocalDate dataInicio = LocalDate.parse(dados[2]);
            LocalDate dataPrevFim = LocalDate.parse(dados[3]);
            LocalDate dataFim = LocalDate.parse(dados[4]);
            int id = Integer.parseInt(dados[5]);
            Constantes.Estado estado = Constantes.Estado.valueOf(dados[6]);
            for (Cliente clienteEmprestimo : clientes) {
                if (clienteEmprestimo.getId() == id) {
                    cliente = clienteEmprestimo;
                    break;
                }
            }
            if (cliente == null)
                cliente = new Cliente(0, "APAGADO", Constantes.Genero.INDEFINIDO, 0, 0, codBiblioteca); // TODO: codBiblioteca a ser desenvolvido posteriormente

            Emprestimo emprestimo = new Emprestimo(codBiblioteca, codMovimento, dataInicio, dataPrevFim, dataFim, cliente, estado); // TODO: codBiblioteca a ser desenvolvido posteriormente
            emprestimos.add(emprestimo);
        } while ((linha = readFile.readLine()) != null);
    } catch (IOException e) {
        System.out.println(e.getMessage());
    }
}

/**
 * Método que lista todos os empréstimos existentes.
 * Se não existirem empréstimos, uma mensagem informativa é apresentada.
 *
 * @param etapa A etapa do processo (LISTAR, EDITAR, etc.).
 */
public static void listaTodosEmprestimos(Constantes.Etapa etapa) {
    if (emprestimos.isEmpty())
        System.out.println("Não existem empréstimos para mostrar.");
    else
        mostraTabelaEmprestimos(emprestimos, etapa);
}

/**
 * Método responsável por ler os dados dos detalhes dos empréstimos a partir de um ficheiro CSV.
 * Este método lê cada linha do ficheiro, cria um objeto EmprestimoLinha com os dados lidos e adiciona-o à lista de detalhes dos empréstimos.
 *
 * @param ficheiro O caminho do ficheiro CSV a ser lido.
 */
public static void lerFicheiroCsvEmprestimosLinha(String ficheiro) {
    try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
        String linha = readFile.readLine();

        if (linha == null) {
            return;
        }

        do {
            String[] dados = linha.split(Constantes.SplitChar);
            int idEmprestimo = Integer.parseInt(dados[0]);
            int emprestimoLinhaId = Integer.parseInt(dados[1]);
            Constantes.TipoItem tipoItem = Constantes.TipoItem.valueOf(dados[2]);
            int idItem = Integer.parseInt(dados[3]);
            Constantes.Estado estado = Constantes.Estado.valueOf(dados[4]);
            emprestimosLinha.add(new EmprestimoLinha(idEmprestimo, emprestimoLinhaId, tipoItem, idItem, estado));
        } while ((linha = readFile.readLine()) != null);
    } catch (IOException e) {
        System.out.println(e.getMessage());
    }
}

/**
 * Método para criar um novo empréstimo.
 * Verifica se existem clientes e itens (livros, jornais, revistas) na biblioteca.
 * Solicita ao utilizador que insira os dados do empréstimo e adiciona itens ao empréstimo.
 * Grava o empréstimo e seus detalhes em arquivos CSV.
 *
 * @throws IOException Se ocorrer um erro durante a gravação dos dados no ficheiro.
 */
public static void criarEmprestimo() throws IOException {
    int opcao = 1;
    if (livros.isEmpty() && jornais.isEmpty() && revistas.isEmpty()) {
        System.out.println("Não existem Items nesta Biblioteca");
        return;
    }
    if (clientes.isEmpty()) {
        System.out.println("Não existem clientes nesta Biblioteca");
        return;
    }
    mostraTabelaClientes(clientes);
    int idEmprestimo = getIdAutomatico(Constantes.TipoItem.EMPRESTIMO, -1);

    emprestimos.add(inserirDadosEmprestimo(idEmprestimo, null));
    Emprestimo emprestimo = emprestimos.getLast();

    boolean firstEntry = true;
    do {
        if (opcao < 1 || opcao > 2) {
            System.out.println("Opção inválida! Tente novamente.");
        } else {
            if (!criarDetalheEmprestimoReserva(emprestimo.getNumMovimento(), Constantes.TipoItem.EMPRESTIMO) && firstEntry) {
                emprestimos.remove(emprestimos.getLast());
                return;
            }
            firstEntry = false;
        }

        opcao = lerInt("Deseja adicionar mais Items à Reserva? (1 - Sim, 2 - Não)", false, null);
    } while (opcao != 2);

    System.out.println("Emprestimo criada com sucesso!");

    gravarArrayEmprestimo();
    gravarArrayEmprestimoLinha();
}

/**
 * Método para inserir os dados de um novo empréstimo.
 * Solicita ao utilizador que insira os dados do cliente e a data de fim do empréstimo.
 * Valida os dados inseridos e cria um objeto Emprestimo.
 *
 * @param idEmprestimo O ID do empréstimo a ser inserido.
 * @param reserva A reserva associada ao empréstimo, se houver.
 * @return Um objeto Emprestimo com os dados inseridos.
 */
public static Emprestimo inserirDadosEmprestimo(int idEmprestimo, Reserva reserva) {
    Cliente cliente = null;
    LocalDate dataPrevFim;

    if (reserva == null) {
        do {
            int opcao = lerInt("Escolha a opção de validação do cliente (1 - ID, 2 - Contribuinte, 3 - Contacto): ", false, null);
            Constantes.ValidacaoCliente validacaoCliente;
            switch (opcao) {
                case 1:
                    validacaoCliente = Constantes.ValidacaoCliente.ID;
                    break;
                case 2:
                    validacaoCliente = Constantes.ValidacaoCliente.NIF;
                    break;
                case 3:
                    validacaoCliente = Constantes.ValidacaoCliente.CONTACTO;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    continue;
            }
            int valor = lerInt("Insira o " + validacaoCliente.toString().toLowerCase() + " : ", false, null);
            cliente = validarCliente(validacaoCliente, valor);
            if (cliente == null) {
                System.out.println("Cliente não encontrado. Tente novamente.");
            }
        } while (cliente == null);
        do {
            dataPrevFim = lerData("Insira a data de fim do empréstimo prevista (dd/MM/yyyy): ");
            if (dataPrevFim.isBefore(Constantes.getDatahoje())) {
                System.out.println("A data final prevista não pode ser anterior à data de início.");
            } else if (dataPrevFim.isAfter(Constantes.getDatahoje().plusDays(30))) {
                System.out.println("A empréstimo não pode ser superior a 30 dias.");
            }
        } while (dataPrevFim.isBefore(Constantes.getDatahoje()) || dataPrevFim.isAfter(Constantes.getDatahoje().plusDays(30)));
        return new Emprestimo(1, idEmprestimo, Constantes.getDatahoje(), dataPrevFim, dataPrevFim, cliente, Constantes.Estado.EMPRESTADO); // TODO: codBiblioteca a ser desenvolvido posteriormente
    } else {
        do {
            dataPrevFim = lerData("Insira a data de fim do empréstimo prevista (dd/MM/yyyy): ");
            if (dataPrevFim.isBefore(Constantes.getDatahoje())) {
                System.out.println("A data final prevista não pode ser anterior à data de início.");
            } else if (dataPrevFim.isAfter(Constantes.getDatahoje().plusDays(30))) {
                System.out.println("A empréstimo não pode ser superior a 30 dias.");
            }
        } while (dataPrevFim.isBefore(Constantes.getDatahoje()) || dataPrevFim.isAfter(Constantes.getDatahoje().plusDays(30)));
        return new Emprestimo(reserva.getCodBiblioteca(), idEmprestimo, Constantes.getDatahoje(), dataPrevFim, dataPrevFim, reserva.getCliente(), Constantes.Estado.EMPRESTADO); // TODO: codBiblioteca a ser desenvolvido posteriormente
    }
}

/**
 * Método para inserir os detalhes de um empréstimo.
 * Solicita ao utilizador que insira o ID do item a ser emprestado e valida se o item está disponível.
 * Se o item já estiver reservado ou emprestado no período especificado, uma exceção é lançada.
 *
 * @param emprestimoId O ID do empréstimo.
 * @param tipoItem O tipo de item a ser emprestado (LIVRO, JORNAL, REVISTA).
 * @param dataFim A data de fim do empréstimo.
 * @return Um objeto EmprestimoLinha contendo os detalhes do empréstimo.
 * @throws IllegalArgumentException Se o item já estiver reservado ou emprestado no período especificado.
 */
public static EmprestimoLinha inserirDetalhesEmprestimo(int emprestimoId, Constantes.TipoItem tipoItem, LocalDate dataFim) {
    int idItem;
    int emprestimoLinhaId = getIdAutomatico(Constantes.TipoItem.EMPRESTIMOLINHA, emprestimoId);
    boolean flag;

    switch (tipoItem) {
        case LIVRO:
            listaTodosLivros();
            break;
        case JORNAL:
            listaTodosJornalRevista(Constantes.TipoItem.JORNAL);
            break;
        case REVISTA:
            listaTodosJornalRevista(Constantes.TipoItem.REVISTA);
            break;
    }
    do {
        flag = false;
        idItem = lerInt("Insira o ID do Item: ", false, null);
        switch (tipoItem) {
            case LIVRO:
                for (Livro livro : livros)
                    if (livro.getId() == idItem) {
                        flag = true;
                        break;
                    }
                break;
            case JORNAL:
                for (JornalRevista jornal : jornais)
                    if (jornal.getId() == idItem) {
                        flag = true;
                        break;
                    }
                break;
            case REVISTA:
                for (JornalRevista revista : revistas)
                    if (revista.getId() == idItem) {
                        flag = true;
                        break;
                    }
                break;
        }
        if (!flag)
            System.out.println("Número Inválido!");
    } while (!flag);
    for (Reserva reserva : reservas)
        for (ReservaLinha reservaLinha : reservasLinha)
            if (reservaLinha.getIdItem() == idItem && reservaLinha.getTipoItem() == tipoItem)
                if (reservaLinha.getEstado() == Constantes.Estado.RESERVADO && reservaLinha.getIdReserva() == reserva.getNumMovimento())
                    if ((reserva.getDataInicio().isBefore(dataFim) || reserva.getDataInicio().isEqual(dataFim)) &&
                            (reserva.getDataFim().isAfter(Constantes.getDatahoje()) || reserva.getDataFim().isEqual(Constantes.getDatahoje())))
                        throw new IllegalArgumentException("Item já reservado!");

    for (Emprestimo emprestimo : emprestimos)
        for (EmprestimoLinha emprestimoLinha : emprestimosLinha)
            if (emprestimoLinha.getIdItem() == idItem && emprestimoLinha.getTipoItem() == tipoItem &&
                    emprestimo.getEstado() == Constantes.Estado.EMPRESTADO && emprestimoLinha.getIdEmprestimo() == emprestimo.getNumMovimento() &&
                    (emprestimo.getDataInicio().isBefore(dataFim) || emprestimo.getDataInicio().isEqual(dataFim)) &&
                    (emprestimo.getDataFim().isAfter(Constantes.getDatahoje()) || emprestimo.getDataFim().isEqual(Constantes.getDatahoje())))
                throw new IllegalArgumentException("Item já emprestado!");

    return new EmprestimoLinha(emprestimoId, emprestimoLinhaId, tipoItem, idItem, Constantes.Estado.EMPRESTADO);
}

/**
 * Método responsável por gravar a lista de empréstimos em um ficheiro CSV.
 * Este método itera pela lista de empréstimos e grava os dados de cada empréstimo no ficheiro CSV.
 *
 * @throws IOException Se ocorrer um erro de I/O durante as operações.
 */
public static void gravarArrayEmprestimo() throws IOException {
    for (int i = 0; i < emprestimos.size(); i++)
        criarFicheiroCsvEmprestimos(Constantes.Path.EMPRESTIMO.getValue(), emprestimos.get(i), i != 0);
}

/**
 * Método responsável por gravar a lista de detalhes dos empréstimos em um ficheiro CSV.
 * Este método itera pela lista de detalhes dos empréstimos e grava os dados de cada detalhe no ficheiro CSV.
 *
 * @throws IOException Se ocorrer um erro de I/O durante as operações.
 */
public static void gravarArrayEmprestimoLinha() throws IOException {
    for (int i = 0; i < emprestimosLinha.size(); i++)
        criarFicheiroCsvEmprestimosLinha(Constantes.Path.EMPRESTIMOLINHA.getValue(), emprestimosLinha.get(i), i != 0);
}

/**
 * Método para criar o ficheiro de detalhes de um empréstimo.
 * Este método grava os detalhes de um empréstimo no ficheiro CSV especificado.
 *
 * @param ficheiro O caminho do ficheiro CSV.
 * @param emprestimoLinha O objeto EmprestimoLinha contendo os detalhes do empréstimo.
 * @param firstLine Define se a gravação deve sobrescrever (false) ou adicionar (true) ao ficheiro.
 * @throws IOException Se ocorrer um erro ao gravar os dados no ficheiro.
 */
public static void criarFicheiroCsvEmprestimosLinha(String ficheiro, EmprestimoLinha emprestimoLinha, Boolean firstLine) throws IOException {
    try (FileWriter fw = new FileWriter(ficheiro, firstLine)) {
        fw.write(String.join(";",
                Integer.toString(emprestimoLinha.getIdEmprestimo()),
                Integer.toString(emprestimoLinha.getIdEmprestimoLinha()),
                emprestimoLinha.getTipoItem().toString(),
                Integer.toString(emprestimoLinha.getIdItem()),
                emprestimoLinha.getEstado().toString() + "\n"));
    }
}
   /**
 * Método para criar o ficheiro de empréstimos.
 * Este método grava os dados de um empréstimo no ficheiro CSV especificado.
 *
 * @param ficheiro O caminho do ficheiro CSV.
 * @param emprestimo O objeto Emprestimo contendo os dados do empréstimo.
 * @param firstLine Define se a gravação deve sobrescrever (false) ou adicionar (true) ao ficheiro.
 * @throws IOException Se ocorrer um erro ao gravar os dados no ficheiro.
 */
public static void criarFicheiroCsvEmprestimos(String ficheiro, Emprestimo emprestimo, Boolean firstLine) throws IOException {
    try (FileWriter fw = new FileWriter(ficheiro, firstLine)) {
        fw.write(String.join(";",
                Integer.toString(emprestimo.getCodBiblioteca()),
                Integer.toString(emprestimo.getNumMovimento()),
                emprestimo.getDataInicio().toString(),
                emprestimo.getDataPrevFim().toString(),
                emprestimo.getDataFim().toString(),
                Integer.toString(emprestimo.getClienteId()),
                emprestimo.getEstado().toString() + "\n"));
    }
}

/**
 * Método para listar os detalhes de um empréstimo.
 * Este método lista todos os empréstimos e permite ao utilizador ver os detalhes de um empréstimo específico.
 */
public static void listarDetalhesEmprestimo() {
    if (emprestimosLinha.isEmpty())
        System.out.println("Não há empréstimos nesta biblioteca.");
    else {
        mostraTabelaEmprestimos(emprestimos, Constantes.Etapa.LISTAR);
        int idEmprestimo = lerInt("Escolha o ID do empréstimo que deseja ver os detalhes (0 para Retornar): ", false, null);
        if (idEmprestimo != 0) {
            for (Emprestimo emprestimo : emprestimos) {
                if (emprestimo.getNumMovimento() == idEmprestimo) {
                    mostraDetalhesEmprestimos(emprestimosLinha, idEmprestimo, null);
                    break;
                }
            }
        }
    }
}

/**
 * Método para concluir ou cancelar um empréstimo.
 * Este método permite ao utilizador concluir ou cancelar um empréstimo, atualizando o estado do empréstimo e dos seus detalhes.
 *
 * @param etapa A etapa do processo (CONCLUIR ou CANCELAR).
 * @throws IOException Se ocorrer um erro durante a gravação dos dados.
 */
public static void concluirCancelarEmprestimo(Constantes.Etapa etapa) throws IOException {
    boolean flag = false;
    if (emprestimos.isEmpty()) {
        System.out.println("Não há empréstimos nesta biblioteca.");
    } else {
        listaTodosEmprestimos(etapa);
        do {
            int idEditar = lerInt("Escolha o ID do emprestimo que deseja " + etapa.toString().toLowerCase() + " (0 para Retornar): ", false, null);
            if (idEditar == 0)
                return;
            for (Emprestimo emprestimo : emprestimos) {
                if (emprestimo.getNumMovimento() == idEditar && emprestimo.getEstado() == Constantes.Estado.EMPRESTADO) {
                    if (etapa == Constantes.Etapa.CONCLUIR) {
                        emprestimo.setEstado(Constantes.Estado.CONCLUIDO);
                        emprestimo.setDataFim(Constantes.getDatahoje());
                    } else
                        emprestimo.setEstado(Constantes.Estado.CANCELADO);
                    flag = true;
                    for (EmprestimoLinha emprestimoLinha : emprestimosLinha) {
                        if (emprestimoLinha.getIdEmprestimo() == idEditar) {
                            if (etapa == Constantes.Etapa.CONCLUIR)
                                emprestimoLinha.setEstado(Constantes.Estado.CONCLUIDO);
                            else
                                emprestimoLinha.setEstado(Constantes.Estado.CANCELADO);
                        }
                    }
                }
            }
            if (!flag) {
                System.out.println("Id Inválido!");
            }
        } while (!flag);
        System.out.println(etapa.toString().toLowerCase() + " Empréstimo com sucesso!");
        gravarArrayEmprestimo();
        gravarArrayEmprestimoLinha();
    }
}

/**
 * Método para editar um empréstimo.
 * Este método permite ao utilizador adicionar ou remover itens de um empréstimo, ou editar a data final prevista.
 *
 * @param etapa A etapa do processo (EDITAR).
 * @throws IOException Se ocorrer um erro durante a gravação dos dados.
 */
public static void editarEmprestimo(Constantes.Etapa etapa) throws IOException {
    boolean flag = false;
    int idEditar, opcao;
    LocalDate dataPrevFim;

    if (emprestimos.isEmpty()) {
        System.out.println("Não há reservas nesta biblioteca.");
        return;
    }

    listaTodosEmprestimos(etapa);
    do {
        idEditar = lerInt("Escolha o ID do emprestimo que deseja editar (0 para Retornar): ", false, null);
        if (idEditar == 0)
            return;
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getNumMovimento() == idEditar && emprestimo.getEstado() == Constantes.Estado.EMPRESTADO) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("Id Inválido!");
        }
    } while (!flag);
    if (etapa == Constantes.Etapa.EDITAR) {
        mostraDetalhesEmprestimos(emprestimosLinha, idEditar, null);
        do {
            opcao = lerInt("Escolha uma opção :\n1 - Adicionar Item\n2 - Remover Item\n3 - Editar Data Final Prevista\n", false, null);
            switch (opcao) {
                case 1:
                    criarDetalheEmprestimoReserva(idEditar, Constantes.TipoItem.EMPRESTIMO);
                    break;
                case 2:
                    opcao = 1;
                    do {
                        if (opcao != 1)
                            System.out.println("Número Inválido!");
                        else if (!RemoverItemReservaEmprestimo(idEditar, Constantes.TipoItem.EMPRESTIMO)) {
                            System.out.println("Não existem mais itens para remover!");
                            break;
                        }
                        opcao = lerInt("Deseja remover mais algum item? (1 - Sim, 2 - Não)", false, null);
                    } while (opcao != 2);
                    break;
                case 3:
                    do {
                        dataPrevFim = lerData("Insira a data de fim do empréstimo prevista (dd/MM/yyyy): ");
                        if (dataPrevFim.isBefore(Constantes.getDatahoje())) {
                            System.out.println("A data final prevista não pode ser anterior à data de início.");
                        } else if (dataPrevFim.isAfter(Constantes.getDatahoje().plusDays(30))) {
                            System.out.println("O empréstimo não pode ser superior a 30 dias.");
                        }
                    } while (dataPrevFim.isBefore(Constantes.getDatahoje()) || dataPrevFim.isAfter(Constantes.getDatahoje().plusDays(30)));
                    emprestimos.get(idEditar - 1).setDataPrevFim(dataPrevFim);
                    emprestimos.get(idEditar - 1).setDataFim(dataPrevFim);
                    break;
                default:
                    System.out.println("Opção Inválida! Tente novamente.");
            }
        } while (opcao < 1 || opcao > 3);
    }
    gravarArrayEmprestimo();
    gravarArrayEmprestimoLinha();
}

/**
 * Método para atualizar o estado dos empréstimos atrasados.
 * Este método verifica se a data de fim do empréstimo é anterior à data de hoje e, se for, atualiza o estado do empréstimo para ATRASADO.
 *
 * @throws IOException Se ocorrer um erro durante a gravação dos dados.
 */
public static void AtualizarAtrasoEmprestimo() throws IOException {
    if (!emprestimos.isEmpty()) {
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getDataFim().isBefore(Constantes.getDatahoje()) && emprestimo.getEstado() == Constantes.Estado.EMPRESTADO) {
                emprestimo.setEstado(Constantes.Estado.ATRASADO);
                gravarArrayEmprestimo();
            }
        }
    }
}

/*
 * ############################### TRATAMENTO DE DADOS EMPRESTIMO - FIM ##############################################
 */
/*
 * ############################### TRATAMENTO DE DADOS LISTAGENS - INICIO ##############################################
 * */

/**
 * Lista todas as reservas e empréstimos de um cliente.
 * Este método verifica se há clientes cadastrados, solicita o ID do cliente e lista todas as reservas e empréstimos associados a esse cliente.
 * Exibe mensagens informativas se não houver clientes, reservas ou empréstimos.
 */
public static void listarTodasReservasEmprestimoCliente() {
    if (clientes.isEmpty()) {
        System.out.println("Não existem Clientes !");
    } else {
        mostraTabelaClientes(clientes);
    }
    int idCliente = lerInt("Insira o Id do Cliente que deseja ver as Reservas / empréstimos: ", false, null);

    List<Reserva> listagemReserva = new ArrayList<>();
    List<Emprestimo> listagemEmprestimo = new ArrayList<>();

    for (Reserva reserva : reservas) {
        if (reserva.getCliente().getId() == idCliente) {
            listagemReserva.add(reserva);
        }
    }
    for (Emprestimo emprestimo : emprestimos) {
        if (emprestimo.getCliente().getId() == idCliente) {
            listagemEmprestimo.add(emprestimo);
        }
    }
    if (listagemReserva.isEmpty()) {
        System.out.println("Não existem Reservas desse cliente!");
    } else {
        mostraTabelaReservas(listagemReserva, Constantes.Etapa.LISTAR);
    }
    if (listagemEmprestimo.isEmpty()) {
        System.out.println("Não existem Empréstimos desse cliente!");
    } else {
        mostraTabelaEmprestimos(listagemEmprestimo, Constantes.Etapa.LISTAR);
    }
}

/**
 * Lista todas as reservas e empréstimos de um cliente dentro de um intervalo de datas.
 * Este método verifica se há clientes cadastrados, solicita o ID do cliente e o intervalo de datas,
 * e lista todas as reservas e empréstimos associados a esse cliente dentro do intervalo especificado.
 * Exibe mensagens informativas se não houver clientes, reservas ou empréstimos dentro do intervalo.
 */
public static void listarTodasReservasEmprestimoClienteData() {
    if (clientes.isEmpty()) {
        System.out.println("Não existem Clientes !");
    } else {
        mostraTabelaClientes(clientes);
    }
    int idCliente = lerInt("Insira o Id do Cliente que deseja ver as Reservas e Empréstimos: ", false, null);

    List<Reserva> listagemReserva = new ArrayList<>();
    List<Emprestimo> listagemEmprestimo = new ArrayList<>();

    LocalDate dataInicio = lerData("Insira a data início do intervalo: ");
    LocalDate dataFim = lerData("Insira a data fim do intervalo: ");

    if (dataFim.isBefore(dataInicio)) {
        System.out.println("Data fim não pode ser inferior à data início!");
        return;
    }

    for (Reserva reserva : reservas) {
        if (reserva.getCliente().getId() == idCliente &&
            (reserva.getDataInicio().isAfter(dataInicio) || reserva.getDataInicio().isEqual(dataInicio)) &&
            (reserva.getDataFim().isBefore(dataFim) || reserva.getDataFim().isEqual(dataFim))) {
            listagemReserva.add(reserva);
        }
    }
    for (Emprestimo emprestimo : emprestimos) {
        if (emprestimo.getCliente().getId() == idCliente &&
            (emprestimo.getDataInicio().isAfter(dataInicio) || emprestimo.getDataInicio().isEqual(dataInicio)) &&
            (emprestimo.getDataFim().isBefore(dataFim) || emprestimo.getDataFim().isEqual(dataFim))) {
            listagemEmprestimo.add(emprestimo);
        }
    }
    if (listagemReserva.isEmpty()) {
        System.out.println("Não existem Reservas desse cliente nessas datas!");
    } else {
        mostraTabelaReservas(listagemReserva, Constantes.Etapa.LISTAR);
    }
    if (listagemEmprestimo.isEmpty()) {
        System.out.println("Não existem Empréstimos desse cliente nessas datas!");
    } else {
        mostraTabelaEmprestimos(listagemEmprestimo, Constantes.Etapa.LISTAR);
    }
}

/*
 * ############################### TRATAMENTO DE DADOS LISTAGENS - FIM ##############################################
 * */

    /*
     * ######################################## HELPERS - INICIO #######################################################
     * */

    public static void concluirReserva() throws IOException
    {
        boolean hasReservas = hasReservas();
        boolean flag=false;
        if(!hasReservas) return;

        // ******** MOSTRAR TODAS AS RESERVAS E ESCOLHER 1 *******
        mostraTabelaReservas(reservas, Constantes.Etapa.CONCLUIR);
        do {
            int idReserva = lerInt("Escolha o id da reserva (0 - para voltar): ", false, null);

            if (idReserva == 0){
                return;
            }
            //Atribui automaticamente o Id com base no último Id existente.
            int idEmprestimo = getIdAutomatico(Constantes.TipoItem.EMPRESTIMO, -1);

            // Faz a procura da reserva pelo id e retorna se encontrar
            for (Reserva reserva : reservas) {
                if (reserva.getNumMovimento() == idReserva) {
                    emprestimos.add(inserirDadosEmprestimo(idEmprestimo, reserva));
                    flag = true;
                    for (ReservaLinha reservalinha : reservasLinha) {
                        if (reservalinha.getIdReserva() == idReserva) {
                            int idItem = reservalinha.getIdItem();
                            Constantes.TipoItem tipoItem = reservalinha.getTipoItem();
                            int emprestimoLinhaId = getIdAutomatico(Constantes.TipoItem.EMPRESTIMOLINHA, idEmprestimo);
                            EmprestimoLinha emprestimoLinha = new EmprestimoLinha(idEmprestimo, emprestimoLinhaId, tipoItem, idItem, Constantes.Estado.EMPRESTADO);
                            emprestimosLinha.add(emprestimoLinha);

                            cancelarReserva(Constantes.Estado.CONCLUIDO);
                        }
                    }
                }
            }
            if (!flag) {
                System.out.println("Id inválido");
            }
        }while(!flag);

        gravarArrayEmprestimo();
        gravarArrayEmprestimoLinha();
    }

    public static Boolean criarDetalheEmprestimoReserva(int id, Constantes.TipoItem emprestimoReserva)
    {
        Constantes.TipoItem tipoItem = null;
        boolean itemExists = false;
        do {
            int tipoItemOpcao = lerInt("Escolha o tipo de item (1 - Livro, 2 - Revista, 3 - Jornal) (0 para Retornar): ", false, null);
            if(tipoItemOpcao==0)
                return false;
            switch (tipoItemOpcao) {
                case 1:
                    if (livros.isEmpty()) {
                        System.out.println("Não existem Livros para mostrar.");
                        continue;
                    }
                    else {
                        tipoItem = Constantes.TipoItem.LIVRO;
                        itemExists = true;
                    }
                    break;
                case 2:
                    if (revistas.isEmpty()) {
                        System.out.println("Não existem Revistas para mostrar.");
                    }
                    else {
                        tipoItem = Constantes.TipoItem.REVISTA;
                        itemExists = true;
                    }
                    break;
                case 3:
                    if (jornais.isEmpty()) {
                        System.out.println("Não existem Jornais para mostrar.");
                    }
                    else {
                        tipoItem = Constantes.TipoItem.JORNAL;
                        itemExists = true;
                    }
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        } while (!itemExists);

        if (emprestimoReserva == Constantes.TipoItem.EMPRESTIMO) {
            try {
                emprestimosLinha.add(inserirDetalhesEmprestimo(id, tipoItem, emprestimos.getLast().getDataFim()));
                return true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }else{
            try{
                reservasLinha.add(inserirDetalhesReserva(id, tipoItem, reservas.getLast().getDataInicio(), reservas.getLast().getDataFim()));
                return true;
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                return false;
            }
        }
    }

    /**
     * Metodo para atribuir automaticamente um ID com base no tipo de função.
     *
     * @param tipoItem O tipo de item para o qual o ID está sendo pesquisado.
     * @return O próximo ID disponível para o tipo de item especificado.
     */
    public static int getIdAutomatico(Constantes.TipoItem tipoItem, int reservaEmprestimoId)
    {
        int valor = 1;

        switch (tipoItem) {
            case BIBLIOTECA:
                for (Biblioteca biblioteca : bibliotecas) {
                    if (biblioteca.getCodBiblioteca() >= valor)
                        valor = biblioteca.getCodBiblioteca() + 1;
                }
                break;
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
                for (JornalRevista jornalRevista : jornais) {
                    if (jornalRevista.getId() >= valor)
                        valor = jornalRevista.getId() + 1;
                }
                break;
            case REVISTA:
                for (JornalRevista jornalRevista : revistas) {
                    if (jornalRevista.getId() >= valor)
                        valor = jornalRevista.getId() + 1;
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
            case RESERVALINHA:
                for (ReservaLinha reservaLinha : reservasLinha) {
                    if (reservaLinha.getIdReservaLinha() >= valor && reservaLinha.getIdReserva() == reservaEmprestimoId)
                        valor = reservaLinha.getIdReservaLinha() + 1;
                }
                break;
            case EMPRESTIMOLINHA:
                for (EmprestimoLinha emprestimoLinha : emprestimosLinha) {
                    if (emprestimoLinha.getIdEmprestimoLinha() >= valor && emprestimoLinha.getIdEmprestimo() == reservaEmprestimoId)
                        valor = emprestimoLinha.getIdEmprestimoLinha() + 1;
                }
                break;
            default:
                System.out.println("Erro ao gerar o ID automático");
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
    public static String lerString(String mensagem)
    {
        String campo;
        boolean flag;
        do {
            System.out.print(mensagem);
            campo = input.nextLine().trim();
            if (campo.isEmpty()) {
                System.out.println("Campo não pode estar vazio");
                flag = false;
            }else {flag=true;}
        }while (!flag);

        return campo ;
    }

    /**
     * Lê e valida um número inteiro a partir da entrada do usuário.
     *
     * @param mensagem A mensagem a ser exibida ao usuário antes de ler a entrada.
     * @return O número inteiro digitado pelo usuário.
     */
    public static int lerInt(String mensagem, Boolean isDate, Constantes.TipoItem tipoItem)
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
                    if (valor > 0 &&
                            ((valor >= 1455 && valor <= LocalDateTime.now().getYear() && tipoItem == Constantes.TipoItem.LIVRO) ||
                                    (valor >= 1605 && valor <= LocalDateTime.now().getYear() && tipoItem == Constantes.TipoItem.JORNAL) ||
                                    (valor >= 1731 && valor <= LocalDateTime.now().getYear() && tipoItem == Constantes.TipoItem.REVISTA)))
                        isInt = true;
                    else if(valor > 0 && valor <= 1455 && tipoItem == Constantes.TipoItem.LIVRO)
                        System.out.print("O primeiro livro impresso, assim como os conhecemos, foi A Bíblia de Gutenberg, impresso em 1455, Mainz, Alemanha... Por favor, insira um ano válido (>= 1455).\n");
                    else if(valor > 0 && valor <= 1605 && tipoItem == Constantes.TipoItem.JORNAL)
                        System.out.print("O primeiro jornal impresso, assim como os conhecemos, foi o Relation aller Fürnemmen und gedenckwürdigen Historien, impresso em 1605, Strasbourg, França... Por favor, insira um ano válido (>= 1605).\n");
                    else if(valor > 0 && valor <= 1731 && tipoItem == Constantes.TipoItem.REVISTA)
                        System.out.print("A primeira revista impressa, assim como as conhecemos, foi The Gentleman’s Magazine impressa, impressa em 1731, Londres, Inglaterra... Por favor, insira um ano válido (>= 1731).\n");
                    else if(valor < 0)
                        System.out.print("O ano não pode ser um número negativo... Por favor, insira um ano válido (>= 0 e < " + LocalDateTime.now().getYear() + ").\n");
                    else if(valor > LocalDateTime.now().getYear())
                        System.out.print("O ano não pode ser superior ao atual...Por favor, insira um ano válido (>= 0 e < " + LocalDateTime.now().getYear() + ").\n");
                } else {
                    isInt = true;
                }
            } catch (Exception e) {
                System.out.print("Número inserido não é válido.\n");
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
    public static char lerChar(String mensagem)
    {
        System.out.print(mensagem);
        return Character.toUpperCase(input.next().charAt(0));
    }

    /**
     * Lê uma data e hora preenchida pelo utilizador.
     * Este método solicita ao utilizador que insira uma data no formato "yyyy-MM-dd".
     * Continua a solicitar ao utilizador até que uma data válida seja introduzida.
     *
     * @param mensagem A mensagem a ser exibida ao utilizador a pedir que insira a data.
     * @return retorna a data inserida pelo utilizador.
     */
    private static LocalDate lerData(String mensagem)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            try {
                String dataStr = lerString(mensagem);
                return LocalDate.parse(dataStr, formatter);
            } catch (Exception e) {
                System.out.println("Data com formato inválido. Tente novamente.");
            }
        }
    }

    /**
     * Função para mostrar a lista de clientes da biblioteca
     * @param listaBibliotecas Recebe a lista de clientes, que pode ser inteira, ou apenas uma parte dela
     * */
    public static void mostraTabelaBibliotecas(List<Biblioteca> listaBibliotecas)
    {
        int idMaxLen = "Id".length();
        int nomeMaxLen = "Nome".length();
        int moradaMaxLen = "Morada".length();

        //percorre a lista, e retorna o tamanho máximo de cada item, caso seja diferente do cabeçalho
        for (Biblioteca biblioteca : listaBibliotecas) {
            idMaxLen = Math.max(idMaxLen, String.valueOf(biblioteca.getCodBiblioteca()).length());
            nomeMaxLen = Math.max(nomeMaxLen, biblioteca.getNome().length());
            moradaMaxLen = Math.max(moradaMaxLen, String.valueOf(biblioteca.getMorada()).length());
        }

        //Esta string cria as linhas baseado no tamanho máximo de cada coluna
        String formato = "| %-" + idMaxLen + "s | %-" + nomeMaxLen  + "s | %-" + moradaMaxLen + "s |\n";
        //Esta string cria a linha de separação
        String separador = "+-" + "-".repeat(idMaxLen) + "-+-" + "-".repeat(nomeMaxLen) + "-+-" + "-".repeat(moradaMaxLen)  + "-+";

        //Imprime a linha de separação (+---+---+ ...)
        System.out.println(separador);
        //Imprime o cabeçalho da tabela
        System.out.printf(formato, "Id", "Nome", "Morada");
        //Imprime a linha de separação
        System.out.println(separador);

        //Imprime os dados dos clientes
        for (Biblioteca biblioteca : listaBibliotecas) {
            System.out.printf(formato, biblioteca.getCodBiblioteca(), biblioteca.getNome(), biblioteca.getMorada());
        }

        System.out.println(separador);
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
        int generoMaxLen = "Gênero".length();
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
        System.out.printf(formato, "Id", "NIF", "Nome", "Gênero", "Contacto");
        //Imprime a linha de separação
        System.out.println(separador);

        //Imprime os dados dos clientes
        for (Cliente cliente : listaClientes) {
            if(cliente.getCodBiblioteca() == codBibliotecaSessao)
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
        int tituloMaxLen = "Titulo".length();
        int editoraMaxLen = "Editora".length();
        int categoriaMaxLen = "Categoria".length();
        int anoEdicaoMaxLen = "Ano Edicao".length();
        int isbnMaxLen = "ISBN".length();
        int autorMaxLen = "Autor".length();

        //percorre a lista, e retorna o tamanho máximo de cada item, caso seja diferente do cabeçalho
        for (Livro livro : listaLivros) {
            idMaxLen = Math.max(idMaxLen, String.valueOf(livro.getId()).length());
            tituloMaxLen = Math.max(tituloMaxLen, String.valueOf(livro.getTitulo()).length());
            editoraMaxLen = Math.max(editoraMaxLen, String.valueOf(livro.getEditora()).length());
            categoriaMaxLen = Math.max(categoriaMaxLen, livro.getCategoria().toString().length());
            anoEdicaoMaxLen = Math.max(anoEdicaoMaxLen, String.valueOf(livro.getAnoEdicao()).length());
            isbnMaxLen = Math.max(isbnMaxLen, String.valueOf(livro.getIsbn()).length());
            autorMaxLen = Math.max(autorMaxLen, String.valueOf(livro.getAutor()).length());
        }

        //Esta string cria as linhas baseado no tamanho máximo de cada coluna
        String formato = "| %-" + idMaxLen + "s | %-" + tituloMaxLen + "s | %-" + editoraMaxLen  + "s | %-" + categoriaMaxLen  + "s | %-" + anoEdicaoMaxLen + "s | %-" + isbnMaxLen + "s | %-" + autorMaxLen + "s |\n";
        //Esta string cria a linha de separação
        String separador = "+-" + "-".repeat(idMaxLen) + "-+-" + "-".repeat(tituloMaxLen) + "-+-" + "-".repeat(editoraMaxLen) + "-+-" + "-".repeat(categoriaMaxLen) + "-+-" + "-".repeat(anoEdicaoMaxLen) + "-+-" + "-".repeat(isbnMaxLen) + "-+-" + "-".repeat(autorMaxLen) + "-+";

        //Imprime a linha de separação (+---+---+ ...)
        System.out.println(separador);
        //Imprime o cabeçalho da tabela
        System.out.printf(formato, "Id", "Titulo", "Editora", "Categoria", "Ano Edição", "ISBN", "Autor");
        //Imprime a linha de separação
        System.out.println(separador);

        //int id, String titulo, String editora, String categoria, int anoEdicao, String isbn, String autor, int codBiblioteca

        //Imprime os dados dos livros
        for (Livro livro : listaLivros) {
            if(livro.getCodBiblioteca() == codBibliotecaSessao) 
                System.out.printf(formato, livro.getId(), livro.getTitulo(), livro.getEditora(), livro.getCategoria(), livro.getAnoEdicao(), livro.getIsbn(), livro.getAutor());
        }

        System.out.println(separador);
    }

    private static void mostraTabelaJornalRevista(List<JornalRevista> listaJornalRevista)
    {
        int idMaxLen = "Id".length();
        int tituloMaxLen = "Titulo".length();
        int editoraMaxLen = "Editora".length();
        int categoriaMaxLen = "Categoria".length();
        int anoPubMaxLen = "Ano de Publicação".length();
        int issnMaxLen = "ISSN".length();

        //percorre a lista, e retorna o tamanho máximo de cada item, caso seja diferente do cabeçalho
        for (JornalRevista jornalRevista : listaJornalRevista) {
            idMaxLen = Math.max(idMaxLen, String.valueOf(jornalRevista.getId()).length());
            tituloMaxLen = Math.max(tituloMaxLen, String.valueOf(jornalRevista.getTitulo()).length());
            editoraMaxLen = Math.max(editoraMaxLen, String.valueOf(jornalRevista.getEditora()).length());
            categoriaMaxLen = Math.max(categoriaMaxLen, jornalRevista.getCategoria().toString().length());
            anoPubMaxLen = Math.max(anoPubMaxLen, String.valueOf(jornalRevista.getDataPublicacao()).length());
            issnMaxLen = Math.max(issnMaxLen, String.valueOf(jornalRevista.getIssn()).length());
        }

        //Esta string cria as linhas baseado no tamanho máximo de cada coluna
        String formato = "| %-" + idMaxLen + "s | %-" + tituloMaxLen + "s | %-" + editoraMaxLen  + "s | %-" + categoriaMaxLen  + "s | %-" + anoPubMaxLen + "s | %-" + issnMaxLen + "s |\n";
        //Esta string cria a linha de separação
        String separador = "+-" + "-".repeat(idMaxLen) + "-+-" + "-".repeat(tituloMaxLen) + "-+-" + "-".repeat(editoraMaxLen) + "-+-" + "-".repeat(categoriaMaxLen) + "-+-" + "-".repeat(anoPubMaxLen) + "-+-" + "-".repeat(issnMaxLen) + "-+";

        //Imprime a linha de separação (+---+---+ ...)
        System.out.println(separador);
        //Imprime o cabeçalho da tabela
        System.out.printf(formato, "Id", "Titulo", "Editora", "Categoria", "Ano de Publicação", "ISSN");
        //Imprime a linha de separação
        System.out.println(separador);

        //int id, String titulo, String editora, String categoria, int anoEdicao, String isbn, String autor, int codBiblioteca

        //Imprime os dados dos jornais/revistas
        for (JornalRevista jornalRevista : listaJornalRevista) {
            if(jornalRevista.getCodBiblioteca() == codBibliotecaSessao)
                System.out.printf(formato, jornalRevista.getId(), jornalRevista.getTitulo(), jornalRevista.getEditora(), jornalRevista.getCategoria(), jornalRevista.getDataPublicacao(), jornalRevista.getIssn());
        }

        System.out.println(separador);
    }

    public static void mostraTabelaReservas(List<Reserva> listaReservas, Constantes.Etapa etapa)
    {
        int idMaxLen = "Id".length();
        int dataInicioLen = "Data Início".length();
        int dataFimLen = "Data Fim".length();
        int clienteMaxLen = "Cliente".length();
        int estadoMaxLen = "Estado".length();

        //percorre a lista, e retorna o tamanho máximo de cada item, caso seja diferente do cabeçalho
        for (Reserva reservas : listaReservas) {
            idMaxLen = Math.max(idMaxLen, String.valueOf(reservas.getNumMovimento()).length());
            dataInicioLen = Math.max(dataInicioLen, String.valueOf(reservas.getDataInicio()).length());
            dataFimLen = Math.max(dataFimLen, String.valueOf(reservas.getDataFim()).length());
            clienteMaxLen = Math.max(clienteMaxLen, String.valueOf(reservas.getClienteNome()).length());
            estadoMaxLen = Math.max(estadoMaxLen, String.valueOf(reservas.getEstado()).length());
        }

        //Esta string cria as linhas baseado no tamanho máximo de cada coluna
        String formato = "| %-" + idMaxLen  + "s | %-" + dataInicioLen + "s | %-" + dataFimLen  + "s | %-" + clienteMaxLen + "s | %-" + estadoMaxLen + "s |\n";
        //Esta string cria a linha de separação
        String separador = "+-" + "-".repeat(idMaxLen) + "-+-" + "-".repeat(dataInicioLen) + "-+-" + "-".repeat(dataFimLen) + "-+-" + "-".repeat(clienteMaxLen) + "-+-" + "-".repeat(estadoMaxLen) +"-+";

        //Imprime a linha de separação (+---+---+ ...)
        System.out.println(separador);
        //Imprime o cabeçalho da tabela
        System.out.printf(formato, "Id", "Data Início", "Data Fim", "Cliente", "Estado");
        //Imprime a linha de separação
        System.out.println(separador);

        //Imprime os dados das Reservas
        for (Reserva reserva : listaReservas) {
            boolean isEditCancelConclude = etapa == Constantes.Etapa.EDITAR || etapa == Constantes.Etapa.CANCELAR || etapa == Constantes.Etapa.CONCLUIR;
            boolean notCanceladoConcluido = reserva.getEstado() != Constantes.Estado.CONCLUIDO && reserva.getEstado() != Constantes.Estado.CANCELADO;
            if (reserva.getCodBiblioteca() == codBibliotecaSessao && (!isEditCancelConclude || notCanceladoConcluido))
                System.out.printf(formato, reserva.getNumMovimento(), reserva.getDataInicio(), reserva.getDataFim(), reserva.getClienteNome(), reserva.getEstado());
        }

        System.out.println(separador);
    }

    public static void mostraTabelaEmprestimos(List<Emprestimo> listaEmprestimos, Constantes.Etapa etapa)
    {
        int idMaxLen = "Id".length();
        int dataInicioLen = "Data Início".length();
        int dataPrevFimLen = "Data Final Prevista".length();
        int clienteMaxLen = "Cliente".length();
        int estadoMaxLen = "Estado".length();

        //percorre a lista, e retorna o tamanho máximo de cada item, caso seja diferente do cabeçalho
        for (Emprestimo emprestimo : listaEmprestimos) {
            idMaxLen = Math.max(idMaxLen, String.valueOf(emprestimo.getNumMovimento()).length());
            dataInicioLen = Math.max(dataInicioLen, String.valueOf(emprestimo.getDataInicio()).length());
            dataPrevFimLen = Math.max(dataPrevFimLen, String.valueOf(emprestimo.getDataFim()).length());
            clienteMaxLen = Math.max(clienteMaxLen, String.valueOf(emprestimo.getClienteNome()).length());
            estadoMaxLen = Math.max(estadoMaxLen, String.valueOf(emprestimo.getEstado()).length());
        }

        //Esta string cria as linhas baseado no tamanho máximo de cada coluna
        String formato = "| %-" + idMaxLen  + "s | %-" + dataInicioLen + "s | %-" + dataPrevFimLen  + "s | %-" + clienteMaxLen + "s | %-" + estadoMaxLen + "s |\n";
        //Esta string cria a linha de separação
        String separador = "+-" + "-".repeat(idMaxLen) + "-+-" + "-".repeat(dataInicioLen) + "-+-" + "-".repeat(dataPrevFimLen) + "-+-" + "-".repeat(clienteMaxLen) + "-+-" + "-".repeat(estadoMaxLen) + "-+";

        //Imprime a linha de separação (+---+---+ ...)
        System.out.println(separador);
        //Imprime o cabeçalho da tabela
        System.out.printf(formato, "Id", "Data Início", "Data Final Prevista", "Cliente", "Estado");
        //Imprime a linha de separação
        System.out.println(separador);

        //Imprime os dados dos clientes
        for (Emprestimo emprestimo : listaEmprestimos) {
            //Valida se é edição, cancelamento ou conclusão de empréstimo e se está emprestado, para simplificar a sintaxe no if
            boolean isEditCancelConclude = etapa == Constantes.Etapa.EDITAR || etapa == Constantes.Etapa.CANCELAR || etapa == Constantes.Etapa.CONCLUIR;
            boolean notCanceladoConcluido = emprestimo.getEstado() != Constantes.Estado.CONCLUIDO && emprestimo.getEstado() != Constantes.Estado.CANCELADO;

            //Caso seja Edição/Cancelamento/Conclusão só mostra os emprestados, caso contrário mostra tudo 
            if (emprestimo.getCodBiblioteca() == codBibliotecaSessao && (!isEditCancelConclude || notCanceladoConcluido)) 
                System.out.printf(formato, emprestimo.getNumMovimento(), emprestimo.getDataInicio(), emprestimo.getDataPrevFim(), emprestimo.getClienteNome(), emprestimo.getEstado());
        }

        System.out.println(separador);
    }

    public static void itemMaisRequisitadoData(){
        if(emprestimos.isEmpty() && reservas.isEmpty()) {
            System.out.println("Não existem Emprestimos ou Reservas!");
            return;
        }
        LocalDate dataInicio = lerData("Insira a data inicial (dd/MM/yyyy): ");
        LocalDate dataFim = lerData("Insira a data final (dd/MM/yyyy): ");

        if(dataFim.isBefore(dataInicio)){
            System.out.println("Data fim não pode ser inferior à data inicio!");
            return;
        }

        int diasMax=0, diasTemp=0, idItem=0;
        Constantes.TipoItem tipoItem=null;
        for (EmprestimoLinha emprestimoLinha : emprestimosLinha){
            for (EmprestimoLinha emprestimoLinha1 : emprestimosLinha){
                if(emprestimoLinha.getIdItem()==emprestimoLinha1.getIdItem()&&emprestimoLinha.getTipoItem()==emprestimoLinha1.getTipoItem()
                        &&emprestimoLinha1.getEstado()!= Constantes.Estado.CANCELADO)
                    diasTemp++;
            }
            for (ReservaLinha reservaLinha : reservasLinha){
                if(emprestimoLinha.getIdItem()==reservaLinha.getIdItem()&&emprestimoLinha.getTipoItem()==reservaLinha.getTipoItem()
                        &&emprestimoLinha.getEstado()!= Constantes.Estado.CANCELADO)
                    diasTemp++;
            }
            if(diasTemp>diasMax) {
                diasMax = diasTemp;
                idItem = emprestimoLinha.getIdItem();
                tipoItem = emprestimoLinha.getTipoItem();
            }
            diasTemp=0;
        }
        for (ReservaLinha reservaLinha : reservasLinha){
            for (EmprestimoLinha emprestimoLinha1 : emprestimosLinha){
                if(reservaLinha.getIdItem()==emprestimoLinha1.getIdItem()&&reservaLinha.getTipoItem()==emprestimoLinha1.getTipoItem()
                        &&emprestimoLinha1.getEstado()!= Constantes.Estado.CANCELADO)
                    diasTemp++;
            }
            for (ReservaLinha reservaLinha1 : reservasLinha){
                if(reservaLinha1.getIdItem()==reservaLinha.getIdItem()&&reservaLinha1.getTipoItem()==reservaLinha.getTipoItem()
                        &&reservaLinha.getEstado()!=Constantes.Estado.CANCELADO)
                    diasTemp++;
            }
            if(diasTemp>diasMax) {
                diasMax = diasTemp;
                idItem = reservaLinha.getIdItem();
                tipoItem = reservaLinha.getTipoItem();
            }
            diasTemp=0;
        }
        System.out.println("O item mais requisitado foi um(a) " + tipoItem.toString().toLowerCase() + " com um total de "+diasMax+" requisições:");
        if (tipoItem == Constantes.TipoItem.LIVRO) {
            for (Livro livro : livros) {
                if (livro.getId() == idItem && livro.getCodBiblioteca() == codBibliotecaSessao) {
                    mostraTabelaLivros(Collections.singletonList(livro));
                    break;
                }
            }
        } else if (tipoItem == Constantes.TipoItem.JORNAL) {
            for (JornalRevista jornal : jornais) {
                if (jornal.getId() == idItem && jornal.getCodBiblioteca() == codBibliotecaSessao) {
                    mostraTabelaJornalRevista(Collections.singletonList(jornal));
                    break;
                }
            }
        } else{
            for (JornalRevista revista : revistas) {
                if (revista.getId() == idItem && revista.getCodBiblioteca() == codBibliotecaSessao) {
                    mostraTabelaJornalRevista(Collections.singletonList(revista));
                    break;
                }
            }
        }


    }

    public static void emprestimoMedioData(){
        if(emprestimos.isEmpty()) {
            System.out.println("Não existem Emprestimos!");
            return;
        }
        LocalDate dataInicio = lerData("Insira a data inicial (dd/MM/yyyy): ");
        LocalDate dataFim = lerData("Insira a data final (dd/MM/yyyy): ");

        if(dataFim.isBefore(dataInicio)){
            System.out.println("Data fim não pode ser inferior à data inicio!");
            return;
        }

        int dias=0, i=0;
        for(Emprestimo emprestimo : emprestimos)
            if(emprestimo.getCodBiblioteca() == codBibliotecaSessao && emprestimo.getDataInicio().isAfter(dataInicio) && emprestimo.getDataInicio().isBefore(dataFim) || emprestimo.getDataInicio().isEqual(dataInicio) || emprestimo.getDataInicio().isEqual(dataFim)){
                dias+= (int) ChronoUnit.DAYS.between(emprestimo.getDataInicio(), emprestimo.getDataFim());
                i++;
            }
        if(i!=0)
            dias/=i;
        System.out.println("O tempo médio foi de "+dias+" dias.");
    }


    public static void listarTodasReservasEmprestimoData(){
        if(emprestimos.isEmpty() && reservas.isEmpty()) {
            System.out.println("Não existem Emprestimos ou Reservas!");
            return;
        }
        LocalDate dataInicio = lerData("Insira a data inicial (dd/MM/yyyy): ");
        LocalDate dataFim = lerData("Insira a data final (dd/MM/yyyy): ");

        if(dataFim.isBefore(dataInicio)){
            System.out.println("Data fim não pode ser inferior à data inicio!");
            return;
        }

        List<Reserva> listagemReserva = new ArrayList<>();
        List<Emprestimo> listagemEmprestimo = new ArrayList<>();
        for(Reserva reserva : reservas)
            if(reserva.getCodBiblioteca() == codBibliotecaSessao && reserva.getDataInicio().isAfter(dataInicio) && reserva.getDataInicio().isBefore(dataFim) || reserva.getDataInicio().isEqual(dataInicio) || reserva.getDataInicio().isEqual(dataFim))
                listagemReserva.add(reserva);
        for(Emprestimo emprestimo : emprestimos)
            if(emprestimo.getCodBiblioteca() == codBibliotecaSessao && emprestimo.getDataInicio().isAfter(dataInicio) && emprestimo.getDataInicio().isBefore(dataFim) || emprestimo.getDataInicio().isEqual(dataInicio) || emprestimo.getDataInicio().isEqual(dataFim))
                listagemEmprestimo.add(emprestimo);

        System.out.println("Reservas");
        mostraTabelaReservas(listagemReserva, Constantes.Etapa.LISTAR);
        System.out.println("Emprestimos");
        mostraTabelaEmprestimos(listagemEmprestimo, Constantes.Etapa.LISTAR);
    }

    public static void mostraDetalhesReservas(List<ReservaLinha> listaDetalhesReservas, int idEmprestimo, Constantes.TipoItem itemMostrar)
    {
        int idReservaLinhaMaxLen = "Id Reserva Linha".length();
        int idReservaMaxLen = "Id Reserva".length();
        int tipoItem = "Tipo Item".length();
        int idItemMaxLen = "Id Item".length();
        int tituloMaxLen = "Titulo".length();
        int editoraMaxLen = "Editora".length();
        int categoriaMaxLen = "Categoria".length();
        int issnMaxLen = "ISSN/ISBN".length();
        int anoMaxLen = "Data Pub.".length();
        int autorMaxLen = "Autor".length();
        int estadoMaxLen = "Estado".length();

        //percorre a lista, e retorna o tamanho máximo de cada item, caso seja diferente do cabeçalho
        for (ReservaLinha reservaLinha : listaDetalhesReservas) {
            idReservaMaxLen = Math.max(idReservaMaxLen, String.valueOf(reservaLinha.getIdReserva()).length());
            idReservaLinhaMaxLen = Math.max(idReservaLinhaMaxLen, String.valueOf(reservaLinha.getIdReservaLinha()).length());
            tipoItem = Math.max(tipoItem, String.valueOf(reservaLinha.getTipoItem()).length());
            idItemMaxLen = Math.max(idItemMaxLen, String.valueOf(reservaLinha.getIdItem()).length());
            estadoMaxLen = Math.max(estadoMaxLen, String.valueOf(reservaLinha.getEstado()).length());

            switch(reservaLinha.getTipoItem())
            {
                case LIVRO:
                    for (Livro livro : livros) {
                        if (livro.getId() == reservaLinha.getIdItem()) {
                            tituloMaxLen = Math.max(tituloMaxLen, livro.getTitulo().length());
                            editoraMaxLen = Math.max(editoraMaxLen, livro.getEditora().length());
                            categoriaMaxLen = Math.max(categoriaMaxLen, livro.getCategoria().toString().length());
                            issnMaxLen = Math.max(issnMaxLen, livro.getIsbn().length());
                            anoMaxLen = Math.max(anoMaxLen, String.valueOf(livro.getAnoEdicao()).length());
                            autorMaxLen = Math.max(autorMaxLen, livro.getAutor().length());
                            break;
                        }
                    }
                    break;
                case JORNAL:
                    for (JornalRevista jornal : jornais) {
                        if (jornal.getId() == reservaLinha.getIdItem()) {
                            tituloMaxLen = Math.max(tituloMaxLen, jornal.getTitulo().length());
                            editoraMaxLen = Math.max(editoraMaxLen, jornal.getEditora().length());
                            categoriaMaxLen = Math.max(categoriaMaxLen, jornal.getCategoria().toString().length());
                            issnMaxLen = Math.max(issnMaxLen, jornal.getIssn().length());
                            anoMaxLen = Math.max(anoMaxLen, String.valueOf(jornal.getDataPublicacao()).length());
                            break;
                        }
                    }
                    break;
                case REVISTA:
                    for (JornalRevista revista : revistas) {
                        if (revista.getId() == reservaLinha.getIdItem()) {
                            tituloMaxLen = Math.max(tituloMaxLen, revista.getTitulo().length());
                            editoraMaxLen = Math.max(editoraMaxLen, revista.getEditora().length());
                            categoriaMaxLen = Math.max(categoriaMaxLen, revista.getCategoria().toString().length());
                            issnMaxLen = Math.max(issnMaxLen, revista.getIssn().length());
                            anoMaxLen = Math.max(anoMaxLen, String.valueOf(revista.getDataPublicacao()).length());
                            break;
                        }
                    }
                    break;
            }
        }
        //
        //Esta string cria as linhas baseado no tamanho máximo de cada coluna
        String formato = "| %-" + idReservaMaxLen + "s | %-" + idReservaLinhaMaxLen + "s | %-" + tipoItem + "s | %-" + idItemMaxLen + "s | %-" + estadoMaxLen + "s | %-" + tituloMaxLen + "s | %-"
                + categoriaMaxLen + "s | %-" + editoraMaxLen + "s | %-" + issnMaxLen + "s | %-" + anoMaxLen +  "s | %-" + autorMaxLen + "s |\n";
        //Esta string cria a linha de separação
        String separador = "+-" + "-".repeat(idReservaMaxLen) + "-+-" + "-".repeat(idReservaLinhaMaxLen) + "-+-" + "-".repeat(tipoItem) + "-+-" + "-".repeat(idItemMaxLen) + "-+-"
                + "-".repeat(estadoMaxLen) + "-+-" + "-".repeat(tituloMaxLen) + "-+-" + "-".repeat(categoriaMaxLen) + "-+-"
                + "-".repeat(editoraMaxLen) + "-+-" + "-".repeat(issnMaxLen) + "-+-" + "-".repeat(anoMaxLen) + "-+-" + "-".repeat(autorMaxLen)  + "-+";

        //Imprime a linha de separação (+---+---+ ...)
        System.out.println(separador);
        //Imprime o cabeçalho da tabela
        System.out.printf(formato, "Id Reserva", "Id Reserva Linha", "Tipo Item", "Id Item", "Estado", "Titulo", "Categoria", "Editora", "ISSN/ISBN", "Data Pub.", "Autor");
        //Imprime a linha de separação
        System.out.println(separador);

        for (ReservaLinha reservaLinha : listaDetalhesReservas) {
            String titulo = "", editora = "", issn = "", autor = "";
            Constantes.Categoria categoria = null;
            int anoEdicao = 0;

            //Imprime os dados dos clientes
            switch(reservaLinha.getTipoItem())
            {
                case LIVRO:
                    for (Livro livro : livros) {
                        if (livro.getId() == reservaLinha.getIdItem()) {
                            titulo = livro.getTitulo();
                            editora = livro.getEditora();
                            categoria = livro.getCategoria();
                            issn = livro.getIsbn();
                            anoEdicao = livro.getAnoEdicao();
                            autor = livro.getAutor();
                            break;
                        }
                    }
                    break;
                case REVISTA:
                    for (JornalRevista jornal : jornais) {
                        if (jornal.getId() == reservaLinha.getIdItem()) {
                            titulo = jornal.getTitulo();
                            editora = jornal.getEditora();
                            categoria = jornal.getCategoria();
                            issn = jornal.getIssn();
                            anoEdicao = jornal.getDataPublicacao();
                            break;
                        }
                    }
                    break;
                case JORNAL:
                    for (JornalRevista revista : revistas) {
                        if (revista.getId() == reservaLinha.getIdItem()) {
                            titulo = revista.getTitulo();
                            editora = revista.getEditora();
                            categoria = revista.getCategoria();
                            issn = revista.getIssn();
                            anoEdicao = revista.getDataPublicacao();
                            break;
                        }
                    }
                    break;
            }
            if(itemMostrar==null && idEmprestimo==0)
                System.out.printf(formato, reservaLinha.getIdReserva(), reservaLinha.getIdReservaLinha(), reservaLinha.getTipoItem(), reservaLinha.getIdItem(), reservaLinha.getEstado(), titulo, categoria, editora, issn, anoEdicao, autor);
            else if (reservaLinha.getIdReserva()==idEmprestimo && itemMostrar==null || idEmprestimo!=0 && reservaLinha.getIdItem()==idEmprestimo && reservaLinha.getTipoItem()==itemMostrar)
                System.out.printf(formato, reservaLinha.getIdReserva(), reservaLinha.getIdReservaLinha(), reservaLinha.getTipoItem(), reservaLinha.getIdItem(), reservaLinha.getEstado(), titulo, categoria, editora, issn, anoEdicao, autor);
        }
        System.out.println(separador);
    }

    public static void mostraDetalhesEmprestimos(List<EmprestimoLinha> listaDetalhesEmprestimos, int idEmprestimo, Constantes.TipoItem itemMostrar)
    {
        int idMaxLen = "Id Emprestimo".length();
        int tipoItem = "Tipo Item".length();
        int idItemMaxLen = "Id Item".length();
        int tituloMaxLen = "Titulo".length();
        int editoraMaxLen = "Editora".length();
        int categoriaMaxLen = "Categoria".length();
        int issnMaxLen = "ISSN/ISBN".length();
        int anoMaxLen = "Data Pub.".length();
        int autorMaxLen = "Autor".length();
        int estadoMaxLen = "Estado".length();

        //percorre a lista, e retorna o tamanho máximo de cada item, caso seja diferente do cabeçalho
        for (EmprestimoLinha emprestimoLinha : listaDetalhesEmprestimos) {
            idMaxLen = Math.max(idMaxLen, String.valueOf(emprestimoLinha.getIdEmprestimo()).length());
            tipoItem = Math.max(tipoItem, String.valueOf(emprestimoLinha.getTipoItem()).length());
            idItemMaxLen = Math.max(idItemMaxLen, String.valueOf(emprestimoLinha.getIdItem()).length());
            estadoMaxLen = Math.max(estadoMaxLen, String.valueOf(emprestimoLinha.getEstado()).length());

            switch(emprestimoLinha.getTipoItem())
            {
                case LIVRO:
                    for (Livro livro : livros) {
                        if (livro.getId() == emprestimoLinha.getIdItem()) {
                            tituloMaxLen = Math.max(tituloMaxLen, livro.getTitulo().length());
                            editoraMaxLen = Math.max(editoraMaxLen, livro.getEditora().length());
                            categoriaMaxLen = Math.max(categoriaMaxLen, livro.getCategoria().toString().length());
                            issnMaxLen = Math.max(issnMaxLen, livro.getIsbn().length());
                            anoMaxLen = Math.max(anoMaxLen, String.valueOf(livro.getAnoEdicao()).length());
                            autorMaxLen = Math.max(autorMaxLen, livro.getAutor().length());
                            //estadoMaxLen = Math.max(estadoMaxLen, String.valueOf(emprestimoLinha.getEstado()).length());
                            break;
                        }
                    }
                    break;
                case JORNAL:
                    for (JornalRevista jornal : jornais) {
                        if (jornal.getId() == emprestimoLinha.getIdItem()) {
                            tituloMaxLen = Math.max(tituloMaxLen, jornal.getTitulo().length());
                            editoraMaxLen = Math.max(editoraMaxLen, jornal.getEditora().length());
                            categoriaMaxLen = Math.max(categoriaMaxLen, jornal.getCategoria().toString().length());
                            issnMaxLen = Math.max(issnMaxLen, jornal.getIssn().length());
                            anoMaxLen = Math.max(anoMaxLen, String.valueOf(jornal.getDataPublicacao()).length());
                            //estadoMaxLen = Math.max(estadoMaxLen, String.valueOf(emprestimoLinha.getEstado()).length());
                            break;
                        }
                    }
                    break;
                case REVISTA:
                    for (JornalRevista revista : revistas) {
                        if (revista.getId() == emprestimoLinha.getIdItem()) {
                            tituloMaxLen = Math.max(tituloMaxLen, revista.getTitulo().length());
                            editoraMaxLen = Math.max(editoraMaxLen, revista.getEditora().length());
                            categoriaMaxLen = Math.max(categoriaMaxLen, revista.getCategoria().toString().length());
                            issnMaxLen = Math.max(issnMaxLen, revista.getIssn().length());
                            anoMaxLen = Math.max(anoMaxLen, String.valueOf(revista.getDataPublicacao()).length());
                            //estadoMaxLen = Math.max(estadoMaxLen, String.valueOf(emprestimoLinha.getEstado()).length());
                            break;
                        }
                    }
                    break;
            }
        }

        //Esta string cria as linhas baseado no tamanho máximo de cada coluna
        String formato = "| %-" + idMaxLen + "s | %-" + tipoItem + "s | %-" + idItemMaxLen + "s | %-" + estadoMaxLen + "s | %-" + tituloMaxLen + "s | %-"
                + categoriaMaxLen + "s | %-" + editoraMaxLen + "s | %-" + issnMaxLen + "s | %-" + anoMaxLen + "s | %-" + autorMaxLen + "s |\n";
        //Esta string cria a linha de separação
        String separador = "+-" + "-".repeat(idMaxLen) + "-+-" + "-".repeat(tipoItem) + "-+-" + "-".repeat(idItemMaxLen) + "-+-"
                + "-".repeat(estadoMaxLen) + "-+-" + "-".repeat(tituloMaxLen) + "-+-" + "-".repeat(categoriaMaxLen) + "-+-" + "-".repeat(editoraMaxLen) + "-+-"
                + "-".repeat(issnMaxLen) + "-+-" + "-".repeat(anoMaxLen) + "-+-" + "-".repeat(autorMaxLen) + "-+";

        //Imprime a linha de separação (+---+---+ ...)
        System.out.println(separador);
        //Imprime o cabeçalho da tabela
        System.out.printf(formato, "Id Emprestimo", "Tipo Item", "Id Item", "Estado", "Titulo", "Categoria", "Editora", "ISSN/ISBN", "Data Pub.", "Autor");
        //Imprime a linha de separação
        System.out.println(separador);

        for (EmprestimoLinha emprestimoLinha : listaDetalhesEmprestimos) {
            String titulo = "", editora = "", issn = "", autor = "", estado = "";
            Constantes.Categoria categoria = null;
            int anoEdicao = 0;

            //Imprime os dados dos clientes
            switch(emprestimoLinha.getTipoItem())
            {
                case LIVRO:
                    for (Livro livro : livros) {
                        if (livro.getId() == emprestimoLinha.getIdItem()) {
                            titulo = livro.getTitulo();
                            editora = livro.getEditora();
                            categoria = livro.getCategoria();
                            issn = livro.getIsbn();
                            anoEdicao = livro.getAnoEdicao();
                            autor = livro.getAutor();
                            estado = emprestimoLinha.getEstado().toString();
                            break;
                        }
                    }
                    break;
                case REVISTA:
                    for (JornalRevista jornal : jornais) {
                        if (jornal.getId() == emprestimoLinha.getIdItem()) {
                            titulo = jornal.getTitulo();
                            editora = jornal.getEditora();
                            categoria = jornal.getCategoria();
                            issn = jornal.getIssn();
                            anoEdicao = jornal.getDataPublicacao();
                            estado = emprestimoLinha.getEstado().toString();
                            break;
                        }
                    }
                    break;
                case JORNAL:
                    for (JornalRevista revista : revistas) {
                        if (revista.getId() == emprestimoLinha.getIdItem()) {
                            titulo = revista.getTitulo();
                            editora = revista.getEditora();
                            categoria = revista.getCategoria();
                            issn = revista.getIssn();
                            anoEdicao = revista.getDataPublicacao();
                            estado = emprestimoLinha.getEstado().toString();
                            break;
                        }
                    }
                    break;
            }
            if(itemMostrar==null && idEmprestimo==0)
                System.out.printf(formato, emprestimoLinha.getIdEmprestimo(), emprestimoLinha.getTipoItem(), emprestimoLinha.getIdItem(), estado, titulo, categoria, editora, issn, anoEdicao, autor);
            else if (emprestimoLinha.getIdEmprestimo()==idEmprestimo && itemMostrar==null || idEmprestimo!=0 && emprestimoLinha.getIdEmprestimo()==idEmprestimo && emprestimoLinha.getTipoItem()==itemMostrar)
                System.out.printf(formato, emprestimoLinha.getIdEmprestimo(), emprestimoLinha.getTipoItem(), emprestimoLinha.getIdItem(), estado, titulo, categoria, editora, issn, anoEdicao, autor);
        }

        System.out.println(separador);
    }


    public static Constantes.Categoria selecionaCategoria(String mensagem)
    {
        int categoriaMaxLen = "Categoria".length();

        // percorre a lista, e retorna o tamanho máximo do código e da categoria
        for (Constantes.Categoria categoria : Constantes.Categoria.values()) {
            categoriaMaxLen = Math.max(categoriaMaxLen, (categoria.getCategoria() + " - " + categoria.name().replace('_', ' ')).length());
        }

        // Esta string cria as linhas baseado no tamanho máximo da categoria
        String formato = "| %-" + categoriaMaxLen + "s |\n";
        // Esta string cria a linha de separação
        String separador = "+-" + "-".repeat(categoriaMaxLen) + "-+";

        // Imprime a linha de separação (+---+---+ ...)
        System.out.println(separador);
        // Imprime o cabeçalho da tabela
        System.out.printf(formato, "Categoria");
        // Imprime a linha de separação
        System.out.println(separador);

        // Imprime as categorias
        for (Constantes.Categoria categoria : Constantes.Categoria.values()) {
            System.out.printf(formato, categoria.getCategoria() + " - " + categoria.name().replace('_', ' '));
        }

        System.out.println(separador);

        // Valida sa a categoria inserida existe, caso contrário pede para inserir novamente
        boolean categoriaValida = false;
        Constantes.Categoria categoriaInserida = null;
        while (!categoriaValida) {
            int categoriaInt = lerInt(mensagem, false, Constantes.TipoItem.LIVRO);
            for (Constantes.Categoria categoria : Constantes.Categoria.values()) {
                if (categoria.getCategoria() == categoriaInt) {
                    categoriaInserida = categoria;
                    categoriaValida = true;
                    break;
                }
            }
            if (!categoriaValida) {
                System.out.println("Categoria inválida! Tente novamente.");
            }
        }

        return categoriaInserida;
    }

    public static Constantes.Morada selecionaMorada(String mensagem)
    {
        int moradaMaxLen = "Morada".length();

        // percorre a lista, e retorna o tamanho máximo do código e da categoria
        for (Constantes.Morada morada : Constantes.Morada.values()) {
            moradaMaxLen = Math.max(moradaMaxLen, (morada.getMorada() + " - " + morada.name().replace('_', ' ')).length());
        }

        // Esta string cria as linhas baseado no tamanho máximo da categoria
        String formato = "| %-" + moradaMaxLen + "s |\n";
        // Esta string cria a linha de separação
        String separador = "+-" + "-".repeat(moradaMaxLen) + "-+";

        // Imprime a linha de separação (+---+---+ ...)
        System.out.println(separador);
        // Imprime o cabeçalho da tabela
        System.out.printf(formato, "Morada");
        // Imprime a linha de separação
        System.out.println(separador);

        // Imprime as categorias
        for (Constantes.Morada morada : Constantes.Morada.values()) {
            System.out.printf(formato, morada.getMorada() + " - " + morada.name().replace('_', ' '));
        }

        System.out.println(separador);

        // Valida sa a categoria inserida existe, caso contrário pede para inserir novamente
        boolean moradaValida = false;
        Constantes.Morada moradaInserida = null;
        while (!moradaValida) {
            int moradaInt = lerInt(mensagem, false, null);
            for (Constantes.Morada morada : Constantes.Morada.values()) {
                if (morada.getMorada() == moradaInt) {
                    moradaInserida = morada;
                    moradaValida = true;
                    break;
                }
            }
            if (!moradaValida) {
                System.out.println("Morada inválida! Tente novamente.");
            }
        }

        return moradaInserida;
    }


    public static Cliente validarCliente(Constantes.ValidacaoCliente validacaoCliente, int valor)
    {
        switch (validacaoCliente) {
            case ID:
                for (Cliente cliente : clientes) {
                    if (cliente.getId() == valor && cliente.getCodBiblioteca() == codBibliotecaSessao) {
                        return cliente;
                    }
                }
                return null;

            case NIF:
                for (Cliente cliente : clientes) {
                    if (cliente.getNif() == valor && cliente.getCodBiblioteca() == codBibliotecaSessao) {
                        return cliente;
                    }
                }
                return null;

            case CONTACTO:
                List<Cliente> clientesComMesmoContacto = new ArrayList<>();
                for (Cliente cliente : clientes) {
                    if (cliente.getContacto() == valor && cliente.getCodBiblioteca() == codBibliotecaSessao) {
                        clientesComMesmoContacto.add(cliente);
                    }
                }

                if (clientesComMesmoContacto.isEmpty()) {
                    System.out.println("Contacto não encontrado.");
                    return null;
                }
                else if (clientesComMesmoContacto.size() == 1)
                    return clientesComMesmoContacto.get(0);
                else {
                    System.out.println("Vários clientes encontrados com o mesmo contacto:");
                    mostraTabelaClientes(clientesComMesmoContacto);
                    int id = lerInt("Por favor, insira o ID do cliente: ", false, null);
                    for (Cliente cliente : clientesComMesmoContacto) {
                        if (cliente.getId() == id && cliente.getCodBiblioteca() == codBibliotecaSessao) {
                            return cliente;
                        }
                    }
                    System.out.println("ID não encontrado!");
                    return null;
                }
            default:
                throw new IllegalArgumentException("Campo desconhecido: " + validacaoCliente);
        }
    }

    private static boolean hasReservas()
    {
        if(reservas.isEmpty())
        {
            System.out.println("Não existem reservas para mostrar.");
            return false;
        }

        boolean hasReserva  = false;

        for(Reserva reserva : reservas){
            if(reserva.getEstado() == Constantes.Estado.RESERVADO && reserva.getCodBiblioteca() == codBibliotecaSessao){
                hasReserva = true;
                break;
            }
        }

        if(!hasReserva){
            System.out.println("Não existem reservas para mostrar.");
            return false;
        }

        return true;
    }

    private static void keyPress() {
        System.out.println("\nPressione Enter para continuar...");
        input.nextLine();
    }

    /*
     * ########################################## HELPERS - FIM ########################################################
     * */
}