import java.io.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
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
    private static List<EmprestimoLinha> emprestimosLinha = new ArrayList<>();
    private static List<JornalRevista> jornais = new ArrayList<>();
    private static List<JornalRevista> revistas = new ArrayList<>();
    private static List<Reserva> reservas = new ArrayList<>();
    private static List<ReservaLinha> reservasLinha = new ArrayList<>();

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
                new File("Biblioteca_1/Reservas/Details"),
                new File("Biblioteca_1/Historico"),
        };
        for (File dir : dirs) {
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        File[] files = new File[]{
                new File(Constantes.Path.CLIENTE.getValue()),
                new File(Constantes.Path.LIVRO.getValue()),
                new File(Constantes.Path.JORNAL.getValue()),
                new File(Constantes.Path.REVISTA.getValue()),
                new File(Constantes.Path.EMPRESTIMO.getValue()),
                new File(Constantes.Path.RESERVA.getValue()),
                new File(Constantes.Path.RESERVALINHA.getValue()),
                new File("Biblioteca_1/Historico/reservas_h.csv"),
                new File("Biblioteca_1/Historico/emprestimos_h.csv")
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
            nif = lerInt("\nPor favor, insira o Contribuinte do Cliente: ", false, null);
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
            contacto = lerInt("\nPor favor, insira o Contacto do Cliente: ", false, null);
            flag=validarTamanho(String.valueOf(contacto),9);

            if(!flag)
                System.out.print("Número de contacto com formato inválido! ex: 912345678");
        } while (!flag);

        return new Cliente(id, nome, genero, nif, contacto,1);
    }

    /**
     * Metodo para criar novo Cliente
     * */
    public static void criarCliente() throws IOException {
        clientes.add(inserirDadosCliente(getIdAutomatico(Constantes.TipoItem.CLIENTE), Constantes.Etapa.CRIAR));
        gravarArrayClientes();
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
        int idEditar = lerInt("Escolha o ID do cliente que deseja editar: ", false, null);

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
        int idNif = lerInt("Digite o NIF do cliente que deseja encontrar: ", false, null);

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
            criarFicheiroCsvCliente(Constantes.Path.CLIENTE.getValue(), cliente, i != 0);
        }
    }

    /**
     * Lê os dados dos clientes a partir de um ficheiro e popula a lista de clientes.
     *
     * @param ficheiro O caminho para o ficheiro que contém os dados dos clientes.
     */
    public static void lerFicheiroCsvClientes(String ficheiro){
        try(BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            String linha = readFile.readLine();
            if (linha == null) {
                System.out.println("O arquivo está vazio.");
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

                // Cria um novo objeto Cliente e adiciona à lista
                Cliente cliente = new Cliente(id, nome, genero, nif, contacto,1); //TODO : codBiblioteca a ser desenvolvido posteriormente
                clientes.add(cliente);
            }while ((linha = readFile.readLine()) != null);
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
                    } else if (cliente.getNif() == nif) {
                        return nif;
                    }
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
     */

    /**
     * Adiciona um novo livro ao sistema.
     */
    public static void criarLivro() throws IOException {
        livros.add(inserirDadosLivro(getIdAutomatico(Constantes.TipoItem.LIVRO)));
        System.out.println("Livro criado com sucesso!");
        gravarArrayLivros();
    }

    /**
     * Lista todos os livros cadastrados no sistema.
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
     */
    public static void gravarArrayLivros() throws IOException {
        if (livros.isEmpty()) {
            new File(Constantes.Path.LIVRO.getValue()).delete();
            System.out.println("Biblioteca não tem contem livros.");
            return;
        }
        for (int i = 0; i < livros.size(); i++) {
            criarFicheiroCsvLivro(Constantes.Path.LIVRO.getValue(), livros.get(i), i != 0);
        }
    }

    /**
     * Cria um arquivo CSV para armazenar os dados dos livros.
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
     */
    public static void lerFicheiroCsvLivros(String ficheiro) {
        try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            String linha = readFile.readLine();
            if (linha == null) {
                System.out.println("O arquivo está vazio.");
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
                Livro livro = new Livro(id, codBiblioteca, titulo, editora, categoria, anoEdicao, isbn, autor);
                livros.add(livro);
            }while ((linha = readFile.readLine()) != null);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Solicita os dados do usuário para criar ou editar um livro.
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
            flag = validarTamanho(isbn, 9);
            if (!flag) {
                System.out.println("ISBN Invalido! ( Ex: 1111-1111 )");
                continue;
            }
            if (isbn.equals(pesquisarIsbn(isbn))) {
                System.out.println("ISBN já existe! Tente novamente.");
                flag = false;
            }
        }while(!flag);
        String autor = lerString("Insira o Autor do livro: ");

        return new Livro(id, 1, titulo, editora, categoria, anoEdicao, isbn, autor);
    }

    /**
     * Pesquisa um ISBN na lista de livros.
     */

    private static String pesquisarIsbn(String isbn) {
        for (Livro livro : livros) {
            if (livro.getIsbn().equals(isbn)) {
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
     * Lê os jornais do arquivo CSV.
     */
    public static void lerFicheiroCsvJornaisRevistas(String ficheiro, Constantes.TipoItem tipoItem) {
        try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            String linha = readFile.readLine();
            if (linha == null) {
                System.out.println("O arquivo está vazio.");
                return;
            }
            String csvDivisor = ";";
            do {
                String[] dados = linha.split(csvDivisor);
                int id = Integer.parseInt(dados[0]);
                String titulo = dados[1];
                String editora = dados[2];
                String issn = dados[3];
                int anoPub = Integer.parseInt(dados[4]);
                int codBiblioteca = Integer.parseInt(dados[5]);
                Constantes.TipoItem tipo = Constantes.TipoItem.valueOf(dados[6]);
                Constantes.Categoria categoria = Constantes.Categoria.valueOf(dados[7]);
                JornalRevista jornalRevista = new JornalRevista(id, titulo, editora, issn, anoPub, codBiblioteca, tipo, categoria);
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
     */
    public static void criarJornal() throws IOException {
        jornais.add(inserirDadosJornalRevista(getIdAutomatico(Constantes.TipoItem.JORNAL), Constantes.TipoItem.JORNAL));
        System.out.println("Jornal criado com sucesso!");
        gravarArrayJornal();
    }

    /**
     * Adiciona uma nova revista ao sistema.
     */
    public static void criarRevista() throws IOException {
        revistas.add(inserirDadosJornalRevista(getIdAutomatico(Constantes.TipoItem.REVISTA), Constantes.TipoItem.REVISTA));
        System.out.println("Revista criada com sucesso!");
        gravarArrayRevista();
    }

    /**
     * Solicita os dados do usuário para criar ou editar um jornal/revista.
     */
    private static JornalRevista inserirDadosJornalRevista(int id, Constantes.TipoItem tipoItem) {
        String titulo = lerString("Insira o Título do " + tipoItem.toString().toLowerCase() + ": ");
        String editora = lerString("Insira a Editora do " + tipoItem.toString().toLowerCase() + ": ");
        Constantes.Categoria categoria = selecionaCategoria("Insira a Categoria do " + tipoItem.toString().toLowerCase() + ": ");
        int anoPub = lerInt("Insira o ano de publicação do " + tipoItem.toString().toLowerCase() + ": ", true, tipoItem);
        String issn;
        boolean flag;
        do {
            issn = lerString("Insira o ISSN do " + tipoItem.toString().toLowerCase() + ": ");
            flag = validarTamanho(issn, 9);
            if (!flag || !issn.matches("^\\d{4}-\\d{3}[A-Z0-9]$")) {
                System.out.println("ISNN Invalido! ( Ex: 1111-111A )");
                flag = false;
            }
            if (issn.equals(pesquisarIssn(issn, tipoItem)) && flag) {
                System.out.println("ISNN já existe! Tente novamente.");
                flag = false;
            }
        }while(!flag);

        return new JornalRevista(id, titulo, editora, issn, anoPub, 1, tipoItem, categoria);
    }

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
     * Lista um Jornal/Revista pelo ISSN fornecido.
     */
    public static void listaJornalRevistaPorIssn(Constantes.TipoItem tipoItem) {
        if (tipoItem == Constantes.TipoItem.JORNAL && jornais.isEmpty()) {
            System.out.println("Não existem jornais para mostrar.");
            return;
        }
        else if (tipoItem == Constantes.TipoItem.REVISTA && revistas.isEmpty()) {
            System.out.println("Não existem revistas para mostrar.");
            return;
        }

        String issn = lerString("Digite o ISSN do " + tipoItem.toString().toLowerCase() + " que deseja encontrar: ");

        if (tipoItem == Constantes.TipoItem.JORNAL) {
            for (JornalRevista jornal : jornais) {
                if (jornal.getIssn().equals(issn)) {
                    mostraTabelaJornalRevista(Collections.singletonList(jornal));
                    return;
                }
            }
        } else {
            for (JornalRevista revista : revistas) {
                if (revista.getIssn().equals(issn)) {
                    mostraTabelaJornalRevista(Collections.singletonList(revista));
                    return;
                }
            }
        }
        System.out.println("O ISSN que inseriu não existe.");
    }

    /**
     * Grava a lista de jornais em um arquivo CSV.
     */
    public static void gravarArrayJornal() throws IOException {
        if (jornais.isEmpty()) {
            new File(Constantes.Path.JORNAL.getValue()).delete();
            System.out.println("Lista de jornais vazia. Arquivo excluído.");
            return;
        }
        for (int i = 0; i < jornais.size(); i++) {
            criarFicheiroCsvJornalRevista(Constantes.Path.JORNAL.getValue(), jornais.get(i), i != 0);
        }
    }

    /**
     * Grava a lista de revistas em um arquivo CSV.
     */
    public static void gravarArrayRevista() throws IOException {
        if (revistas.isEmpty()) {
            new File(Constantes.Path.REVISTA.getValue()).delete();
            System.out.println("Lista de revistas vazia. Arquivo excluído.");
            return;
        }
        for (int i = 0; i < revistas.size(); i++) {
            criarFicheiroCsvJornalRevista(Constantes.Path.REVISTA.getValue(), revistas.get(i), i != 0);
        }
    }

    /**
     * Cria um arquivo CSV para armazenar os dados dos jornais/revistas.
     */
    public static void criarFicheiroCsvJornalRevista(String ficheiro, JornalRevista jornalRevista, boolean append) throws IOException {
        try (FileWriter fw = new FileWriter(ficheiro, append)) {
            fw.write(String.join(";",
                    String.valueOf(jornalRevista.getId()),
                    jornalRevista.getTitulo(),
                    jornalRevista.getEditora(),
                    jornalRevista.getIssn(),
                    String.valueOf(jornalRevista.getDataPublicacao()),
                    String.valueOf(jornalRevista.getCodBiblioteca()),
                    String.valueOf(jornalRevista.getTipo()),
                    String.valueOf(jornalRevista.getCategoria()),
                    "\n"));
        }
    }

    /**
     * Edita os dados de uma REVISTA ou JORNAL existente.
     */
    public static void editarJornalRevista(Constantes.TipoItem tipoItem) throws IOException {
        if (tipoItem == Constantes.TipoItem.REVISTA && revistas.isEmpty()) {
            System.out.println("Não existem Revistas nesta Biblioteca.");
            return;
        } else if (tipoItem == Constantes.TipoItem.JORNAL && jornais.isEmpty()) {
            System.out.println("Não existem Jornais nesta Biblioteca.");
        }


        listaTodosJornalRevista(tipoItem);
        int idEditarJornalRevista = lerInt("Escolha o ID do " + tipoItem.toString().toLowerCase() + " que deseja editar: ", false, null);

        if (tipoItem == Constantes.TipoItem.REVISTA) {
            for (JornalRevista jornalRevista : revistas) {
                if (jornalRevista.getId() == idEditarJornalRevista) {
                    revistas.set(revistas.indexOf(jornalRevista), inserirDadosJornalRevista(idEditarJornalRevista, tipoItem));
                    System.out.println("Revista editada com sucesso!");
                    gravarArrayRevista();
                    return;
                }
            }
        }else{
            for (JornalRevista jornalRevista : jornais) {
                if (jornalRevista.getId() == idEditarJornalRevista) {
                    jornais.set(jornais.indexOf(jornalRevista), inserirDadosJornalRevista(idEditarJornalRevista, tipoItem));
                    System.out.println("Jornal editado com sucesso!");
                    gravarArrayJornal();
                    return;
                }
            }
        }
        System.out.println("ID do " + tipoItem.toString().toLowerCase() + " não encontrado.");
    }

    /**
     * Apaga uma REVISTA e JORNAL pelo ID.
     */
    public static void apagarJornalRevista(Constantes.TipoItem tipoItem) throws IOException {
        if (tipoItem == Constantes.TipoItem.REVISTA && revistas.isEmpty()) {
            System.out.println("Não existem Revistas nesta Biblioteca.");
            return;
        } else if (tipoItem == Constantes.TipoItem.JORNAL && jornais.isEmpty()) {
            System.out.println("Não existem Jornais nesta Biblioteca.");
        }
        listaTodosJornalRevista(tipoItem);

        int idApagar = lerInt("Escolha o ID do(a) " + tipoItem.toString().toLowerCase() + " que deseja apagar: ", false, null);
        JornalRevista jornalRevistaRemover = null;
        if (tipoItem == Constantes.TipoItem.REVISTA) {
            for (JornalRevista jornalRevista : revistas) {
                if (jornalRevista.getId() == idApagar) {
                    jornalRevistaRemover = jornalRevista;
                    break;
                }
            }
        } else if (tipoItem == Constantes.TipoItem.JORNAL) {
            for (JornalRevista jornalRevista : jornais) {
                if (jornalRevista.getId() == idApagar) {
                    jornalRevistaRemover = jornalRevista;
                    break;
                }
            }
        }
        if (jornalRevistaRemover == null) {
            System.out.println("ID "+ tipoItem.toString().toLowerCase() +" não encontrado.");
            return;
        }
        if(tipoItem == Constantes.TipoItem.REVISTA){
            revistas.remove(jornalRevistaRemover);
            gravarArrayRevista();
        }else if (tipoItem == Constantes.TipoItem.JORNAL){
            jornais.remove(jornalRevistaRemover);
            gravarArrayJornal();
        }
        System.out.println(tipoItem.toString().toLowerCase()+ " apagado(a) com sucesso!");
    }

    /*
     * ########################### TRATAMENTO DE DADOS JORNAIS/REVISTAS - FIM #################################################
     * */

    /*
     * ########################### TRATAMENTO DE DADOS RESERVAS - INICIO #################################################
     * */

    /**
     * Metodo para inserir os dados de uma reserva.
     * Este metodo solicita ao utilizador que selecione o cliente e insira as datas da reserva.
     * Garante que a data de início não é anterior a hoje e que a data de fim não é anterior à data de início.
     *
     * @param id O ID da reserva.
     * @return Um novo objeto Reserva com os detalhes fornecidos.
     */
    public static Reserva inserirDadosReserva(int id){
        Cliente cliente = null;
        LocalDate dataInicio;
        LocalDate dataFim;
        Constantes.Estado estado;

        // Verifica qual é a maneira como queremos procurar pelo cliente, para ser mais flexível
        do {
            int opcao = lerInt("Escolha a opção de validação do cliente (1 - ID, 2 - NIF, 3 - Contacto): ", false, null);
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
            if (dataInicio.isBefore(Constantes.getDatahoje())) {
                System.out.println("A data de início não pode ser anterior ao dia de hoje.");
            }
        } while (dataInicio.isBefore(Constantes.getDatahoje()));

        do {
            dataFim = lerData("Insira a data de fim da reserva (dd/MM/yyyy): ");
            if (dataFim.isBefore(dataInicio)) {
                System.out.println("A data de fim não pode ser anterior à data de início.");
            }
            else if(dataFim.isAfter(dataInicio.plusDays(Constantes.TempoMaxReservaDias))) {
                System.out.println("A reserva não pode ser superior a " + Constantes.TempoMaxReservaDias + " dias.");
            }
        } while (dataFim.isBefore(dataInicio) || dataFim.isAfter(dataInicio.plusDays(Constantes.TempoMaxReservaDias)));

        estado = Constantes.Estado.RESERVADO;
        return new Reserva(1, id, dataInicio, dataFim, cliente,null, estado);
    }

    /**
     * Metodo para criar a nova Reserva
     * Verifica se existem clientes na Biblioteca
     * */
    public static void criarReserva() throws IOException {
        //Mostra mensagem a informar que a Biblioteca não tem nada que seja possível reserva, e sai fora.
        if(livros.isEmpty() && jornais.isEmpty() && revistas.isEmpty()){
            System.out.println("Não existem Items nesta Biblioteca");
            return;
        }
        //Mostra mensagem a informar que não tem cliente.
        //TODO : Ao invés de não deixar prosseguir, pode perguntar se deseja criar um novo cliente e prosseguir para a sua criação.
        if (clientes.isEmpty()){
            System.out.println("Não existem clientes nesta Biblioteca");
            return;
        }

        //Atribui automaticamente o Id com base no último Id existente.
        int idReserva = getIdAutomatico(Constantes.TipoItem.RESERVA);

        //Cria a reserva
        reservas.add(inserirDadosReserva(idReserva));
        Reserva reserva = reservas.getLast();

        criarDetalheEmprestimoReserva(reserva.getNumMovimento(), Constantes.TipoItem.RESERVA);

        System.out.println("Reserva criada com sucesso!");

        gravarArrayReservas();
        gravarArrayReservaLinha();

        // Criar o historico dos movimentos
        //criarFicheiroCsvReservasDtl("Biblioteca_1/Historico/reservas_h.csv", reservaDtl, true);
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
        int idEditar = lerInt("Escolha o ID da reserva que deseja editar: ", false, null);
        listarDetalhesReserva(idEditar);

        int opcao = lerInt("Escolha uma opção :\n1 - Adicionar Item\n2 - Remover Item\n", false, null);
        switch (opcao) {
            case 1:
                criarDetalheEmprestimoReserva(idEditar, Constantes.TipoItem.RESERVA);
                gravarArrayReservaLinha();
                break;
            case 2:
                removerItemReserva(idEditar);
                gravarArrayReservaLinha();
                listarDetalhesReserva(idEditar);
                break;
            default:
                System.out.println("Escolha invalida! Tente novamente.");
        }
    }

    public static void listarDetalhesReserva(int idReserva) throws IOException {
        // Lista de apoio para editar os detalhes
        List<ReservaLinha> reservaLinhaDetails = new ArrayList<>();

        // Procura a reserva pelo ID e acrescenta a Lista de Detalhes para apresentar a reserva completa
        for(ReservaLinha reservaLinha : reservasLinha) {
            if (reservaLinha.getIdReserva() == idReserva) {
                reservaLinhaDetails.add(reservaLinha);
            }
        }
        mostraDetalhesReservas(reservaLinhaDetails);
    }

    public static void removerItemReserva(int idReserva) throws IOException {
        Constantes.TipoItem tipoItem;
        int idItem=0;
        do {
            int tipoItemOpcao = lerInt("Escolha o tipo de item (1 - Livro, 2 - Revista, 3 - Jornal): ", false, null);

            switch (tipoItemOpcao) {
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
                    continue;
            }
            idItem = lerInt("Escolha o ID do Item: ", false, null);

            int opcao = lerInt("Deseja remover mais Items da Reserva? (1 - Sim, 2 - Não)", false, null);
            if (opcao == 2){
                break;
            }
        } while(true);

        // Lista de apoio para editar os detalhes
        List<ReservaLinha> reservaLinhaDetails = new ArrayList<>();

        // Procura a reserva pelo ID e acrescenta a Lista de Detalhes para apresentar a reserva completa
        for(ReservaLinha reservaLinha : reservasLinha) {
            if (reservaLinha.getIdReserva() == idReserva && reservaLinha.getIdItem() == idItem && reservaLinha.getTipoItem() == tipoItem) {
                reservaLinha.setEstado(Constantes.Estado.CANCELADO);
            }
        }
        mostraDetalhesReservas(reservaLinhaDetails);
    }

    /**
     * Metodo para cancelar a reserva na totalidade ou apenas algunm
     * dos itens que lhe pertence
     *
     * */
    public static void cancelarReserva(int idCancelar, Constantes.Estado estado) throws IOException {
        // Verifica se a lista de clientes está vazia
        if(reservas.isEmpty()) {
            System.out.println("Não há reservas nesta biblioteca.");
            return;
        }
        // Lista todos os clientes
        listaTodasReservas();

        //int idCancelar = lerInt("Escolha o ID da reserva que deseja editar: ", false, null);

        for (Reserva reserva : reservas) {
            if (reserva.getNumMovimento() == idCancelar) {
                reserva.setEstado(estado);
                for (ReservaLinha reservaLinha : reservasLinha) {
                    if (reservaLinha.getIdReserva() == idCancelar) {
                        reservaLinha.setEstado(estado);
                    }
                }
            }
        }
        gravarArrayReservas();
        gravarArrayReservaLinha();
    }


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

    public static void lerFicheiroCsvReservas(String ficheiro){
        try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            String linha;
            Cliente cliente = null;
            while ((linha = readFile.readLine()) != null) {
                String[] dados = linha.split(Constantes.SplitChar);
                int codBiblioteca = Integer.parseInt(dados[0]);
                int codMovimento = Integer.parseInt(dados[1]);
                LocalDate dataInicio = LocalDate.parse(dados[2]);
                LocalDate dataFim = LocalDate.parse(dados[3]);
                int id = Integer.parseInt(dados[4]);
                for(Cliente clienteReserva : clientes) {
                    if (clienteReserva.getId() == id) {
                        cliente = clienteReserva;
                        break;
                    }
                }
                Constantes.Estado estado = Constantes.Estado.valueOf(dados[5]);
                List<ReservaLinha> reservaLinha = new ArrayList<>(); //TODO : Ir buscar as linhas da reserva

                Reserva reserva = new Reserva(codBiblioteca, codMovimento, dataInicio, dataFim, cliente, reservaLinha, estado);

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

    public static boolean listaTodasReservas() {
        if (reservas.isEmpty()) {
            System.out.println("Não existem reservas para mostrar.");
            return false;
        }

        mostraTabelaReservas(reservas);
        return true;
    }

    public static void gravarArrayReservas() throws IOException {
        if(reservas.isEmpty()) {
            new File(Constantes.Path.RESERVA.getValue()).delete();
            System.out.println("Array vazio");
            return;
        }

        for(int i = 0; i < reservas.size(); i++){
            criarFicheiroCsvReservas(Constantes.Path.RESERVA.getValue(), reservas.get(i), i != 0);
        }
    }

    /*
     * ############################### TRATAMENTO DE DADOS RESERVAS - FIM ##############################################
     * */

    /*
     * ############################### TRATAMENTO DE DADOS DETALHES RESERVAS - INICIO ##############################################
     * */

    /**
     * Metodo para inserir os detalhes de uma reserva atribuido a algum Cliente
     * @param reservaId Recebe o Id da Reserva
     * @param tipoItem Recebe o Tipo de Item a ser inserido
     * */
    public static ReservaLinha inserirDetalhesReserva(int reservaId, Constantes.TipoItem tipoItem)
    {
        //TODO: Validar se os items inseridos não estão noutra reserva
        //Talvez se possa criar um boolean nos itens a true/false, para ser mais fácil a validação,
        //ao invés de se ter que percorrer listas.
        int idItem=0;
        boolean idValido=false;
        Constantes.Estado estado = null;

        do {
            switch (tipoItem) {
                case LIVRO:
                    listaTodosLivros();
                    idItem = lerInt("Insira o ID do Livro: ", false, null);
                    idValido = validarIdLivro(idItem);
                    estado = Constantes.Estado.RESERVADO;
                    break;
                case REVISTA:
                    listaTodosJornalRevista(Constantes.TipoItem.REVISTA);
                    idItem = lerInt("Insira o ID da Revista: ", false, null);
                    idValido = validarIdRevista(idItem);
                    estado = Constantes.Estado.RESERVADO;
                    break;
                case JORNAL:
                    listaTodosJornalRevista(Constantes.TipoItem.JORNAL);
                    idItem = lerInt("Insira o ID do Jornal: ", false, null);
                    idValido = validarIdJornal(idItem);
                    estado = Constantes.Estado.RESERVADO;
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de item inválido: " + tipoItem);
            }

            if (!idValido) {
                System.out.println("ID inválido. Tente novamente.");
            }
        } while (!idValido);

        return new ReservaLinha(reservaId, tipoItem, idItem, estado);
    }

    /**
     * Metodo para criar o ficheiro de detalhes de uma reserva
     * atribuido a algum Cliente
     * @param ficheiro Recebe o valor do Path do ficheiro a tratar
     * @param reservaLinha Recebe o valor de uma ReservaLinha do Array
     * @param firstLine reescrever o ficheiro só e só se for a primeira linha a ser inserida
     * */
    public static void criarFicheiroCsvReservasLinha(String ficheiro, ReservaLinha reservaLinha, Boolean firstLine) throws IOException {
        try (FileWriter fw = new FileWriter(ficheiro, firstLine)) {
            fw.write(String.join(";",
                    Integer.toString(reservaLinha.getIdReserva()),
                    reservaLinha.getTipoItem().toString(),
                    Integer.toString(reservaLinha.getIdItem()),
                    reservaLinha.getEstado().toString()) + "\n");
        }
    }

    /**
     * Metodo para ler o Ficheiro de Detalhes de Reserva e carregar a
     * informação no Array ReservasDtl
     * @param ficheiro Recebe o valor do Path do ficheiro a tratar
     * */
    public static void lerFicheiroCsvReservasLinha(String ficheiro){
        try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            String linha = readFile.readLine();

            if (linha == null) {
                System.out.println("O ficheiro está vazio.");
                return;
            }

            do {
                String[] dados = linha.split(Constantes.SplitChar);
                int idReserva = Integer.parseInt(dados[0]);
                Constantes.TipoItem tipoItem = Constantes.TipoItem.valueOf(dados[1]);
                int idItem = Integer.parseInt(dados[2]);
                Constantes.Estado estado = Constantes.Estado.valueOf(dados[3]);
                reservasLinha.add(new ReservaLinha(idReserva, tipoItem, idItem, estado));
            } while ((linha = readFile.readLine()) != null);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        for (Reserva reserva : reservas) {
            System.out.println(reserva);
        }
    }

    /**
     * Metodo para gravar as alterações efetuadas no Array ReservasDtl
     * no Ficheiro reservasdtl.csv
     * */
    public static void gravarArrayReservaLinha() throws IOException {
        if (reservasLinha.isEmpty()) {
            new File(Constantes.Path.RESERVALINHA.getValue()).delete();
            System.out.println("Array vazio");
        }

        for (int i = 0; i < reservasLinha.size(); i++) {
            criarFicheiroCsvReservasLinha(Constantes.Path.RESERVALINHA.getValue(), reservasLinha.get(i), i != 0);
        }
    }

    /*
     * ############################### TRATAMENTO DE DADOS DETALHES RESERVAS - FIM ##############################################
     * */

    /*
     * ############################### TRATAMENTO DE DADOS EMPRESTIMO - INICIO ##############################################
     * */

    public static void lerFicheiroCsvEmprestimos(String ficheiro){
        try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            String linha = readFile.readLine();
            Cliente cliente = null;
            if (linha == null) {
                System.out.println("O arquivo está vazio.");
                return;
            }
            String csvDivisor = ";";
            do {
                String[] dados = linha.split(Constantes.SplitChar);
                int codBiblioteca = Integer.parseInt(dados[0]);
                int codMovimento = Integer.parseInt(dados[1]);
                LocalDate dataInicio = LocalDate.parse(dados[2]);
                LocalDate dataPrevFim = LocalDate.parse(dados[3]);
                LocalDate dataFim = LocalDate.parse(dados[4]);
                int id = Integer.parseInt(dados[5]);
                Constantes.Estado estado = Constantes.Estado.valueOf(dados[6]);
                for(Cliente clienteEmprestimo : clientes) {
                    if (clienteEmprestimo.getId() == id) {
                        cliente = clienteEmprestimo;
                        break;
                    }
                }
                List<EmprestimoLinha> emprestimoLinhas = new ArrayList<>(); //TODO : Ir buscar as linhas da reserva

                Emprestimo emprestimo = new Emprestimo(codBiblioteca, codMovimento, dataInicio, dataPrevFim, dataFim, cliente, estado);

                emprestimos.add(emprestimo);
            }while ((linha = readFile.readLine()) != null);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        for (Emprestimo emprestimo : emprestimos) {
            System.out.println(emprestimo);
        }
    }

    public static void listaTodosEmprestimos() {
        if (emprestimos.isEmpty()) {
            System.out.println("Não existem empréstimos para mostrar.");
            return;
        }

        mostraTabelaEmprestimos(emprestimos);
    }

    public static void lerFicheiroCsvEmprestimosLinha(String ficheiro){
        try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            String linha = readFile.readLine();

            if (linha == null) {
                System.out.println("O ficheiro está vazio.");
                return;
            }

            do {
                String[] dados = linha.split(Constantes.SplitChar);
                int idEmprestimo = Integer.parseInt(dados[0]);
                Constantes.TipoItem tipoItem = Constantes.TipoItem.valueOf(dados[1]);
                int idItem = Integer.parseInt(dados[2]);
                Constantes.Estado estado = Constantes.Estado.valueOf(dados[3]);
                emprestimosLinha.add(new EmprestimoLinha(idEmprestimo, tipoItem, idItem, estado));
            } while ((linha = readFile.readLine()) != null);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        for (EmprestimoLinha emprestimoLinha : emprestimosLinha) {
            System.out.println(emprestimoLinha);
        }
    }

    /**
     * Metodo para criar o Emprestimo
     * */
    public static void criarEmprestimo() throws IOException {
        //Mostra mensagem a informar que a Biblioteca não tem nada que seja possível reserva, e sai fora.
        if(livros.isEmpty() && jornais.isEmpty() && revistas.isEmpty()){
            System.out.println("Não existem Items nesta Biblioteca");
            return;
        }
        //Mostra mensagem a informar que não tem cliente.
        //TODO : Ao invés de não deixar prosseguir, pode perguntar se deseja criar um novo cliente e prosseguir para a sua criação.
        if (clientes.isEmpty()){
            System.out.println("Não existem clientes nesta Biblioteca");
            return;
        }
        mostraTabelaClientes(clientes);
        //Atribui automaticamente o Id com base no último Id existente.
        int idEmprestimo = getIdAutomatico(Constantes.TipoItem.EMPRESTIMO);

        //Cria a emprestimo
        emprestimos.add(inserirDadosEmprestimo(idEmprestimo, null));
        Emprestimo emprestimo = emprestimos.getLast();


        // TODO: Chamar metodo de criar detalhes do emprestimo
        criarDetalheEmprestimoReserva(emprestimo.getNumMovimento(), Constantes.TipoItem.EMPRESTIMO);

        System.out.println("Emprestimo criada com sucesso!");

        gravarArrayEmprestimo();
        gravarArrayEmprestimoLinha();

        // Criar o historico dos movimentos
        //criarFicheiroCsvReservasDtl("Biblioteca_1/Historico/reservas_h.csv", reservaDtl, true);
    }

    public static Emprestimo inserirDadosEmprestimo(int idEmprestimo, Reserva reserva){
        Cliente cliente = null;
        LocalDate dataPrevFim;

        // Verifica qual é a maneira como queremos procurar pelo cliente, para ser mais flexível
        if(reserva == null) {
            do {
                int opcao = lerInt("Escolha a opção de validação do cliente (1 - ID, 2 - NIF, 3 - Contacto): ", false, null);
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
            // É validado se a data final prevista introduzida é inferior à início e superior ao limite estipulado.
            do {
                dataPrevFim = lerData("Insira a data de fim do empréstimo prevista (dd/MM/yyyy): ");
                if (dataPrevFim.isBefore(Constantes.getDatahoje())) {
                    System.out.println("A data final prevista não pode ser anterior à data de início.");
                } else if (dataPrevFim.isAfter(Constantes.getDatahoje().plusDays(30))) {
                    System.out.println("A empréstimo não pode ser superior a 30 dias.");
                }
            } while (dataPrevFim.isBefore(Constantes.getDatahoje()) || dataPrevFim.isAfter(Constantes.getDatahoje().plusDays(30)));
            return new Emprestimo(1, idEmprestimo, Constantes.getDatahoje(), dataPrevFim, dataPrevFim, cliente, Constantes.Estado.EMPRESTADO);
        }
        else{
            do {
                dataPrevFim = lerData("Insira a data de fim do empréstimo prevista (dd/MM/yyyy): ");
                if (dataPrevFim.isBefore(Constantes.getDatahoje())) {
                    System.out.println("A data final prevista não pode ser anterior à data de início.");
                } else if (dataPrevFim.isAfter(Constantes.getDatahoje().plusDays(30))) {
                    System.out.println("A empréstimo não pode ser superior a 30 dias.");
                }
            } while (dataPrevFim.isBefore(Constantes.getDatahoje()) || dataPrevFim.isAfter(Constantes.getDatahoje().plusDays(30)));
            return new Emprestimo(reserva.getCodBiblioteca(), idEmprestimo, Constantes.getDatahoje(), dataPrevFim, dataPrevFim, reserva.getCliente(), Constantes.Estado.EMPRESTADO);
        }
    }

    public static EmprestimoLinha inserirDetalhesEmprestimo(int emprestimoId, Constantes.TipoItem tipoItem)
    {
        //TODO: Validar se os items inseridos não estão noutro empréstimo
        //Talvez se possa criar um boolean nos itens a true/false, para ser mais fácil a validação,
        //ao invés de se ter que percorrer listas.
        int idItem=0;
        boolean idValido=false;

        do {
            switch (tipoItem) {
                case LIVRO:
                    listaTodosLivros();
                    idItem = lerInt("Insira o ID do Livro: ", false, null);
                    idValido = validarIdLivro(idItem);
                    break;
                case REVISTA:
                        listaTodosJornalRevista(Constantes.TipoItem.REVISTA);
                        idItem = lerInt("Insira o ID da Revista: ", false, null);
                        idValido = validarIdRevista(idItem);
                    break;
                case JORNAL:
                        listaTodosJornalRevista(Constantes.TipoItem.JORNAL);
                        idItem = lerInt("Insira o ID do Jornal: ", false, null);
                        idValido = validarIdJornal(idItem);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de item inválido: " + tipoItem);
            }
            if (!idValido) {
                System.out.println("ID inválido. Tente novamente.");
            }
            for(EmprestimoLinha emprestimoLinha: emprestimosLinha){
                if(emprestimoLinha.getIdItem() == idItem && emprestimoLinha.getTipoItem() == tipoItem && emprestimoLinha.getEstado() == Constantes.Estado.EMPRESTADO || emprestimoLinha.getEstado() == Constantes.Estado.RESERVADO){
                    throw new IllegalArgumentException("Item já " + emprestimoLinha.getEstado().toString().toLowerCase());
                }
            }
        } while (!idValido);

        return new EmprestimoLinha(emprestimoId, tipoItem, idItem, Constantes.Estado.EMPRESTADO);
    }

    public static void gravarArrayEmprestimo() throws IOException {
        if(emprestimos.isEmpty()) {
            new File(Constantes.Path.EMPRESTIMO.getValue()).delete();
            System.out.println("Array vazio");
            return;
        }

        for(int i = 0; i < emprestimos.size(); i++){
            criarFicheiroCsvEmprestimos(Constantes.Path.EMPRESTIMO.getValue(), emprestimos.get(i), i != 0);
        }
    }

    public static void gravarArrayEmprestimoLinha() throws IOException {
        if(emprestimosLinha.isEmpty()) {
            new File(Constantes.Path.EMPRESTIMOLINHA.getValue()).delete();
            System.out.println("Array vazio");
            return;
        }

        for(int i = 0; i < emprestimosLinha.size(); i++){
            criarFicheiroCsvEmprestimosLinha(Constantes.Path.EMPRESTIMOLINHA.getValue(), emprestimosLinha.get(i), i != 0);
        }
    }

    public static void criarFicheiroCsvEmprestimosLinha(String ficheiro, EmprestimoLinha emprestimoLinha, Boolean firstLine) throws IOException {
        try (FileWriter fw = new FileWriter(ficheiro, firstLine)) {
            fw.write(String.join(";",
                    Integer.toString(emprestimoLinha.getIdEmprestimo()),
                    emprestimoLinha.getTipoItem().toString(),
                    Integer.toString(emprestimoLinha.getIdItem()),
                    emprestimoLinha.getEstado().toString()+ "\n"));
        }
    }

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

    public static void listarDetalhesEmprestimo(int idEmprestimo) throws IOException {
        // Lista de apoio para editar os detalhes
        List<EmprestimoLinha> emprestimoLinhaDetails = new ArrayList<>();

        // Procura o emprestimo pelo ID e acrescenta a Lista de Detalhes para apresentar a reserva completa
        for(EmprestimoLinha emprestimoLinha : emprestimosLinha) {
            if (emprestimoLinha.getIdEmprestimo() == idEmprestimo) {
                emprestimoLinhaDetails.add(emprestimoLinha);
            }
        }
        mostraDetalhesEmprestimos(emprestimoLinhaDetails);
    }

    /*
     * ############################### TRATAMENTO DE DADOS EMPRESTIMO - FIM ##############################################
     * */

    /*
     * ######################################## HELPERS - INICIO #######################################################
     * */

    public static void ConcluirReserva() throws IOException {

        // ******** MOSTRAR TODAS AS RESERVAS E ESCOLHER 1 *******
        mostraTabelaReservas(reservas);
        int idReserva = lerInt("Escolha o id da reserva: ", false, null);

        //Atribui automaticamente o Id com base no último Id existente.
        int idEmprestimo = getIdAutomatico(Constantes.TipoItem.EMPRESTIMO);

        // Faz a procura da reserva pelo id e retorna se encontrar
        for (Reserva reserva : reservas) {
            if (reserva.getNumMovimento() == idReserva) {
                emprestimos.add(inserirDadosEmprestimo(idEmprestimo, reserva));
                for (ReservaLinha reservalinha : reservasLinha) {
                    if (reservalinha.getIdReserva() == idReserva) {
                        int idItem = reservalinha.getIdItem();
                        Constantes.TipoItem tipoItem = reservalinha.getTipoItem();
                        EmprestimoLinha emprestimoLinha = new EmprestimoLinha(idEmprestimo, tipoItem, idItem, Constantes.Estado.EMPRESTADO);
                        emprestimosLinha.add(emprestimoLinha);

                        cancelarReserva(idReserva, Constantes.Estado.CONCLUIDO);
                    }
                }
            }
        }

        gravarArrayEmprestimo();
        gravarArrayEmprestimoLinha();
    }

    public static Constantes.TipoItem criarDetalheEmprestimoReserva(int id, Constantes.TipoItem emprestimoReserva) throws IOException {
        Constantes.TipoItem tipoItem = null;
        boolean flag=false, itemExists = true;
        do {
            do {
                int tipoItemOpcao = lerInt("Escolha o tipo de item (1 - Livro, 2 - Revista, 3 - Jornal): ", false, null);

                switch (tipoItemOpcao) {
                    case 1:
                        if (livros.isEmpty()) {
                            System.out.println("Não existem Livros para mostrar.");
                            itemExists = false;
                        }
                        else {
                            tipoItem = Constantes.TipoItem.LIVRO;
                            itemExists = true;
                        }
                        break;
                    case 2:
                        if (revistas.isEmpty()) {
                            System.out.println("Não existem Revistas para mostrar.");
                            itemExists = false;
                        }
                        else {
                            tipoItem = Constantes.TipoItem.REVISTA;
                            itemExists = true;
                        }
                    break;
                    case 3:
                        if (jornais.isEmpty()) {
                            System.out.println("Não existem Jornais para mostrar.");
                            itemExists = false;
                        }
                        else {
                            tipoItem = Constantes.TipoItem.JORNAL;
                            itemExists = true;
                        }           
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                        itemExists = false;
                        break;
                }
            } while (!itemExists);
            
            if (emprestimoReserva == Constantes.TipoItem.EMPRESTIMO)
                try
                {
                    emprestimosLinha.add(inserirDetalhesEmprestimo(id,tipoItem));
                    flag=true;
                }catch (IllegalArgumentException e){
                    System.out.println("Item já emprestado/reservado.");
                    flag=false;
                }
            else{
                reservasLinha.add(inserirDetalhesReserva(id, tipoItem));
                flag=true;
            }
            if(flag) {
                int opcao = lerInt("Deseja acrescentar mais Items a(o) " + emprestimoReserva.toString().toLowerCase() + "? (1 - Sim, 2 - Não): ", false, null);

                if (opcao == 2)
                    flag=true;
                else
                    flag=false;
            }
        } while(!flag);
        return tipoItem;
    }

    /**
     * Metodo para atribuir automaticamente um ID com base no tipo de função.
     *
     * @param tipoItem O tipo de item para o qual o ID está sendo pesquisado.
     * @return O próximo ID disponível para o tipo de item especificado.
     */
    public static int getIdAutomatico(Constantes.TipoItem tipoItem)
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
                    if (reservaLinha.getIdReserva() >= valor)
                        valor = reservaLinha.getIdReserva() + 1;
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
                    //TODO :
                    //  Podemos criar um easter egg aqui, com base no tipo de item (livro, revista, jornal)
                    //  Primeiro livro impresso, assim como os conhecemos, em 1455 em Mainz, foi a A Bíblia de Gutenberg
                    //  Primeiro jornal impresso, assim como os conhecemos, foi o Relation aller Fürnemmen und gedenckwürdigen Historien, impresso em 1605 em Strasbourg.
                    //  Primeira revista impressa, assim como as conhecemos, foi The Gentleman’s Magazine impressa, impressa em 1731 em Londres.
                    if (valor >= 1455 && valor <= LocalDateTime.now().getYear() && tipoItem == Constantes.TipoItem.LIVRO)
                        isInt = true;
                    else if(valor >= 1605 && valor <= LocalDateTime.now().getYear() && tipoItem == Constantes.TipoItem.JORNAL)
                        isInt = true;
                    else if(valor >= 1731 && valor <= LocalDateTime.now().getYear() && tipoItem == Constantes.TipoItem.REVISTA)
                        isInt = true;
                    else
                        System.out.print("Por favor, insira um ano válido (yyyy): ");
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
     * Lê uma data e hora preenchida pelo utilizador.
     *
     * Este método solicita ao utilizador que insira uma data no formato "yyyy-MM-dd".
     * Continua a solicitar ao utilizador até que uma data válida seja introduzida.
     *
     * @param mensagem A mensagem a ser exibida ao utilizador a pedir que insira a data.
     * @return retorna a data inserida pelo utilizador.
     */
    private static LocalDate lerData(String mensagem) {
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
            System.out.printf(formato, jornalRevista.getId(), jornalRevista.getTitulo(), jornalRevista.getEditora(), jornalRevista.getCategoria(), jornalRevista.getDataPublicacao(), jornalRevista.getIssn());
        }

        System.out.println(separador);
    }

    public static void mostraTabelaReservas(List<Reserva> listaReservas)
    {
        //TODO : Implementar a função de mostrar a tabela de reservas, com opção de mostrar detalhadamente o que cada reserva contém
        int idMaxLen = "Id".length();
        int bibliotecaMaxLen = "Biblioteca".length();
        int dataInicioLen = "Data Início".length();
        int dataFimLen = "Data Fim".length();
        int clienteMaxLen = "Cliente".length();
        int estadoMaxLen = "Estado".length();

        //percorre a lista, e retorna o tamanho máximo de cada item, caso seja diferente do cabeçalho
        for (Reserva reservas : listaReservas) {
            bibliotecaMaxLen = Math.max(bibliotecaMaxLen, String.valueOf(reservas.getCodBiblioteca()).length());
            idMaxLen = Math.max(idMaxLen, String.valueOf(reservas.getNumMovimento()).length());
            dataInicioLen = Math.max(dataInicioLen, String.valueOf(reservas.getDataInicio()).length());
            dataFimLen = Math.max(dataFimLen, String.valueOf(reservas.getDataFim()).length());
            clienteMaxLen = Math.max(clienteMaxLen, String.valueOf(reservas.getClienteNome()).length());
            estadoMaxLen = Math.max(estadoMaxLen, String.valueOf(reservas.getEstado()).length());
        }

        //Esta string cria as linhas baseado no tamanho máximo de cada coluna
        String formato = "| %-" + bibliotecaMaxLen + "s | %-" + idMaxLen  + "s | %-" + dataInicioLen + "s | %-" + dataFimLen  + "s | %-" + clienteMaxLen + "s | %-" + estadoMaxLen + "s |\n";
        //Esta string cria a linha de separação
        String separador = "+-" + "-".repeat(bibliotecaMaxLen) + "-+-" + "-".repeat(idMaxLen) + "-+-" + "-".repeat(dataInicioLen) + "-+-" + "-".repeat(dataFimLen) + "-+-" + "-".repeat(clienteMaxLen) + "-+-" + "-".repeat(estadoMaxLen) +"-+";

        //Imprime a linha de separação (+---+---+ ...)
        System.out.println(separador);
        //Imprime o cabeçalho da tabela
        System.out.printf(formato, "Biblioteca", "Id", "Data Início", "Data Fim", "Cliente", "Estado");
        //Imprime a linha de separação
        System.out.println(separador);

        //Imprime os dados dos clientes
        for (Reserva reserva : listaReservas) {
            System.out.printf(formato, reserva.getCodBiblioteca(), reserva.getNumMovimento(), reserva.getDataInicio(), reserva.getDataFim(), reserva.getClienteNome(), reserva.getEstado());
        }

        System.out.println(separador);
    }

    public static void mostraTabelaEmprestimos(List<Emprestimo> listaEmprestimos)
    {
        //TODO : Implementar a função de mostrar a tabela de emprestimos, com opção de mostrar detalhadamente o que cada reserva contém
        int idMaxLen = "Id".length();
        int bibliotecaMaxLen = "Biblioteca".length();
        int dataInicioLen = "Data Início".length();
        int dataPrevFimLen = "Data Final Prevista".length();
        int clienteMaxLen = "Cliente".length();

        //percorre a lista, e retorna o tamanho máximo de cada item, caso seja diferente do cabeçalho
        for (Emprestimo emprestimo : listaEmprestimos) {
            bibliotecaMaxLen = Math.max(bibliotecaMaxLen, String.valueOf(emprestimo.getCodBiblioteca()).length());
            idMaxLen = Math.max(idMaxLen, String.valueOf(emprestimo.getNumMovimento()).length());
            dataInicioLen = Math.max(dataInicioLen, String.valueOf(emprestimo.getDataInicio()).length());
            dataPrevFimLen = Math.max(dataPrevFimLen, String.valueOf(emprestimo.getDataFim()).length());
            clienteMaxLen = Math.max(clienteMaxLen, String.valueOf(emprestimo.getClienteNome()).length());
        }

        //Esta string cria as linhas baseado no tamanho máximo de cada coluna
        String formato = "| %-" + bibliotecaMaxLen + "s | %-" + idMaxLen  + "s | %-" + dataInicioLen + "s | %-" + dataPrevFimLen  + "s | %-" + clienteMaxLen + "s |\n";
        //Esta string cria a linha de separação
        String separador = "+-" + "-".repeat(bibliotecaMaxLen) + "-+-" + "-".repeat(idMaxLen) + "-+-" + "-".repeat(dataInicioLen) + "-+-" + "-".repeat(dataPrevFimLen) + "-+-" + "-".repeat(clienteMaxLen) + "-+";

        //Imprime a linha de separação (+---+---+ ...)
        System.out.println(separador);
        //Imprime o cabeçalho da tabela
        System.out.printf(formato, "Biblioteca", "Id", "Data Início", "Data Final Prevista", "Cliente");
        //Imprime a linha de separação
        System.out.println(separador);

        //Imprime os dados dos clientes
        for (Emprestimo emprestimo : listaEmprestimos) {
            System.out.printf(formato, emprestimo.getCodBiblioteca(), emprestimo.getNumMovimento(), emprestimo.getDataInicio(), emprestimo.getDataPrevFim(), emprestimo.getClienteNome());
        }

        System.out.println(separador);
    }

    public static void mostraDetalhesReservas(List<ReservaLinha> listaDetalhesReservas)
    {
        //TODO : Implementar a função de mostrar a tabela de reservas, com opção de mostrar detalhadamente o que cada reserva contém
        int idMaxLen = "Id Reserva".length();
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
            idMaxLen = Math.max(idMaxLen, String.valueOf(reservaLinha.getIdReserva()).length());
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
        String formato = "| %-" + idMaxLen + "s | %-" + tipoItem + "s | %-" + idItemMaxLen + "s | %-" + estadoMaxLen + "s | %-" + tituloMaxLen + "s | %-"
                + categoriaMaxLen + "s | %-" + editoraMaxLen + "s | %-" + issnMaxLen + "s | %-" + anoMaxLen +  "s |\n";
        //Esta string cria a linha de separação
        String separador = "+-" + "-".repeat(idMaxLen) + "-+-" + "-".repeat(tipoItem) + "-+-" + "-".repeat(idItemMaxLen) + "-+-"
                + "-".repeat(estadoMaxLen) + "-+-" + "-".repeat(tituloMaxLen) + "-+-" + "-".repeat(categoriaMaxLen) + "-+-"
                + "-".repeat(editoraMaxLen) + "-+-" + "-".repeat(issnMaxLen) + "-+-" + "-".repeat(anoMaxLen)  + "-+";

        //Imprime a linha de separação (+---+---+ ...)
        System.out.println(separador);
        //Imprime o cabeçalho da tabela
        System.out.printf(formato, "Id Reserva", "Tipo Item", "Id Item", "Estado", "Titulo", "Categoria", "Editora", "ISSN/ISBN", "Data Pub.", "Autor");
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
            System.out.printf(formato, reservaLinha.getIdReserva(), reservaLinha.getTipoItem(), reservaLinha.getIdItem(), reservaLinha.getEstado(), titulo, categoria, editora, issn, anoEdicao, autor);

        }

        System.out.println(separador);
    }

    public static void mostraDetalhesEmprestimos(List<EmprestimoLinha> listaDetalhesEmprestimos)
    {
        //TODO : Implementar a função de mostrar a tabela de reservas, com opção de mostrar detalhadamente o que cada reserva contém
        int idMaxLen = "Id Reserva".length();
        int tipoItem = "Tipo Item".length();
        int idItemMaxLen = "Id Item".length();
        int tituloMaxLen = "Titulo".length();
        int editoraMaxLen = "Editora".length();
        int categoriaMaxLen = "Categoria".length();
        int issnMaxLen = "ISSN/ISBN".length();
        int anoMaxLen = "Data Pub.".length();
        int autorMaxLen = "Autor".length();

        //percorre a lista, e retorna o tamanho máximo de cada item, caso seja diferente do cabeçalho
        for (EmprestimoLinha emprestimoLinha : listaDetalhesEmprestimos) {
            idMaxLen = Math.max(idMaxLen, String.valueOf(emprestimoLinha.getIdEmprestimo()).length());
            tipoItem = Math.max(tipoItem, String.valueOf(emprestimoLinha.getTipoItem()).length());
            idItemMaxLen = Math.max(idItemMaxLen, String.valueOf(emprestimoLinha.getIdItem()).length());

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
                            break;
                        }
                    }
                    break;
            }
        }

        //Esta string cria as linhas baseado no tamanho máximo de cada coluna
        String formato = "| %-" + idMaxLen + "s | %-" + tipoItem + "s | %-" + idItemMaxLen + "s | %-" + tituloMaxLen + "s | %-"
                + categoriaMaxLen + "s | %-" + editoraMaxLen + "s | %-" + issnMaxLen + "s | %-" + anoMaxLen + "s |\n";
        //Esta string cria a linha de separação
        String separador = "+-" + "-".repeat(idMaxLen) + "-+-" + "-".repeat(tipoItem) + "-+-" + "-".repeat(idItemMaxLen) + "-+-"
                + "-".repeat(tituloMaxLen) + "-+-" + "-".repeat(categoriaMaxLen) + "-+-" + "-".repeat(editoraMaxLen) + "-+-"
                + "-".repeat(issnMaxLen) + "-+-" + "-".repeat(anoMaxLen) + "-+";

        //Imprime a linha de separação (+---+---+ ...)
        System.out.println(separador);
        //Imprime o cabeçalho da tabela
        System.out.printf(formato, "Id Reserva", "Tipo Item", "Id Item", "Titulo", "Categoria", "Editora", "ISSN/ISBN", "Data Pub.", "Autor");
        //Imprime a linha de separação
        System.out.println(separador);

        for (EmprestimoLinha emprestimoLinha : listaDetalhesEmprestimos) {
            String titulo = "", editora = "", issn = "", autor = "";
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
                            break;
                        }
                    }
                    break;
            }
            System.out.printf(formato, emprestimoLinha.getIdEmprestimo(), emprestimoLinha.getTipoItem(), emprestimoLinha.getIdItem(), titulo, categoria, editora, issn, anoEdicao, autor);

        }

        System.out.println(separador);
    }

    public static Constantes.Categoria selecionaCategoria(String mensagem) {
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

    /**
     * Pesquisa um ISnN na lista de jornais ou revistas.
     */
    private static String pesquisarIssn(String issn, Constantes.TipoItem tipoItem) {
        if(tipoItem==Constantes.TipoItem.JORNAL) {
            for (JornalRevista jornalRevista : jornais) {
                if (jornalRevista.getIssn().equals(issn)) {
                    return issn;
                }
            }
        }
        else {
            for (JornalRevista jornalRevista : revistas) {
                if (jornalRevista.getIssn().equals(issn)) {
                    return issn;
                }
            }
        }
        return null;
    }

    public static Cliente validarCliente(Constantes.ValidacaoCliente validacaoCliente, int valor) {
        switch (validacaoCliente) {
            case ID:
                for (Cliente cliente : clientes) {
                    if (cliente.getId() == valor) {
                        return cliente;
                    }
                }
                return null;

            case NIF:
                for (Cliente cliente : clientes) {
                    if (cliente.getNif() == valor) {
                        return cliente;
                    }
                }
                return null;

            case CONTACTO:
                List<Cliente> clientesComMesmoContacto = new ArrayList<>();
                for (Cliente cliente : clientes) {
                    if (cliente.getContacto() == valor) {
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
                        if (cliente.getId() == id) {
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

    private static boolean validarIdLivro(int id) {
        for (Livro livro : livros) {
            if (livro.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private static boolean validarIdRevista(int id) {
        for (JornalRevista revista : revistas) {
            if (revista.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private static boolean validarIdJornal(int id) {
        for (JornalRevista jornal : jornais) {
            if (jornal.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /*
     * ########################################## HELPERS - FIM ########################################################
     * */
}
