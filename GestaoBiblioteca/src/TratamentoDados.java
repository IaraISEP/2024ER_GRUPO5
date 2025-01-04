import java.io.*;

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
    private static List<JornalRevista> jornais = new ArrayList<>();
    private static List<JornalRevista> revistas = new ArrayList<>();
    private static List<Reserva> reservas = new ArrayList<>();
    private static List<ReservaDtl> reservasdtl = new ArrayList<>();

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
                new File(Constantes.Path.RESERVADTL.getValue()),
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
        clientes.add(inserirDadosCliente(pesquisarIdArray(Constantes.TipoItem.CLIENTE), Constantes.Etapa.CRIAR));
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
            if (readFile.readLine() == null) {
                System.out.println("O arquivo está vazio.");
                return;
            }
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
        boolean clienteExiste = false;
        if (!clientes.isEmpty()) {
            for (Cliente cliente : clientes) {

                if (cliente.getNif() == nif) {
                    //Se o NIF existir, valida se a etapa em que estamos é a de criação de utilizador. Caso seja, não permite inserir esse NIF
                    //Caso a etapa seja Editar e o NIF existir, valida se o user em questão é o mesmo que estamos a editar, e permite o NIF, caso contrário retorna o erro
                    if (etapa == Constantes.Etapa.CRIAR || (etapa == Constantes.Etapa.EDITAR && cliente.getId() != idCliente)) {
                        System.out.println("Nif existente!");
                        clienteExiste = true;
                        return 0;
                    } else if (cliente.getNif() == nif) {
                        return nif;
                    }
                }
            }
            System.out.println("Cliente nao existe nesta Biblioteca!");
            //return -1;
        }else{
            System.out.println("Não existem Clientes nesta Biblioteca.");
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
        livros.add(inserirDadosLivro(pesquisarProximoId()));
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
            if (readFile.readLine() == null) {
                System.out.println("O arquivo está vazio.");
                return;
            }
            String linha;
            String csvDivisor = ";";
            while ((linha = readFile.readLine()) != null) {
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
            }
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
     * ########################### TRATAMENTO DE DADOS JORNAIS/REVISTAS - INICIO #################################################
     */

    /**
     * Lê os jornais do arquivo CSV.
     */
    public static void lerFicheiroCsvJornaisRevistas(String ficheiro, Constantes.TipoItem tipoItem) {
        try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            if (readFile.readLine() == null) {
                System.out.println("O arquivo está vazio.");
                return;
            }
            String linha;
            String csvDivisor = ";";
            while ((linha = readFile.readLine()) != null) {
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
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adiciona um novo jornal ao sistema.
     */
    public static void criarJornal() throws IOException {
        jornais.add(inserirDadosJornalRevista(pesquisarProximoId(), Constantes.TipoItem.JORNAL));
        System.out.println("Jornal criado com sucesso!");
        gravarArrayJornal();
    }

    /**
     * Adiciona uma nova revista ao sistema.
     */
    public static void criarRevista() throws IOException {
        revistas.add(inserirDadosJornalRevista(pesquisarProximoId(), Constantes.TipoItem.REVISTA));
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
     * Lista um livro pelo ISBN fornecido.
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
                if (jornal.getIssn().equals(issn))
                    mostraTabelaJornalRevista(Collections.singletonList(jornal));
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
                    String.valueOf(jornalRevista.getCategoria()),
                    jornalRevista.getIssn(),
                    String.valueOf(jornalRevista.getDataPublicacao()),
                    String.valueOf(jornalRevista.getCodBiblioteca()),
                    String.valueOf(jornalRevista.getTipo()),
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

        int idApagar = lerInt("Escolha o ID do " + tipoItem.toString().toLowerCase() + " livro que deseja apagar: ", false, null);
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

    public static Reserva inserirDadosReserva(int id){
        int anoEdicao = 0,numMovimento=0,codBiblioteca=1, nif=0;
        LocalDateTime dataInicio = null, dataFim = null, dataRegisto=null;
        String titulo = "", editora = "", categoria = "", isbn = "", autor = "";
        boolean flag;
        numMovimento = id;
        do {
            nif = lerInt("\nPor favor, insira o Contribuinte do Cliente: ", false, null);
            int idCliente;
            nif = pesquisarNifArrayCliente(nif, null, -1);
            flag = validarTamanho(String.valueOf(nif),9);
            if(!flag)
                System.out.print("Contribuinte Inválido! ex: 123456789");
        }while (!flag);

        return new Reserva(codBiblioteca, numMovimento, dataInicio, dataFim, clientes,  livros, jornais, revistas, dataRegisto, nif, isbn);
    }

    /**
     * Metodo para criar nova Reserva
     * Verifica se existem clientes na Biblioteca
     * */
    public static void criarReserva() throws IOException {
        if(livros.isEmpty()){
            System.out.println("Não existem livros nesta Biblioteca");
            return;
        }
        if (clientes.isEmpty()){
            System.out.println("Não existem clientes nesta Biblioteca");
            return;
        }

        int idAuto = pesquisarIdArray(Constantes.TipoItem.RESERVA);
        reservas.add(inserirDadosReserva(idAuto));
        Reserva reserva = reservas.getLast();

        /*
         * TODO:
         *   Ao criar a reserva abrir a opção para editar os detalhes da reserva
         *   Escolher livro revista ou jornal
         *   selecionar as datas
         */

        do{
            reservasdtl.add(inserirDetalhesReserva(reserva));
            gravarArrayReservasDtl();
            ReservaDtl reservaDtl = reservasdtl.getLast();
            System.out.println("Deseja acrescentar mais Items a Reserva? (1 - Sim, 2 - Não)");
            int opcao = input.nextInt();
            input.nextLine();
            if (opcao == 2){
                break;
            }
            // Criar o historico dos movimentos
            criarFicheiroCsvReservasDtl("Biblioteca_1/Historico/reservas_h.csv", reservaDtl, true);
            reservas.add(reserva);
        }while(true);

        System.out.println("Reserva criada com sucesso!");

        gravarArrayReservas();
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

        // Procura a reserva pelo ID e, caso encontre, edita o cliente
        for(Reserva reserva : reservas) {
            if (reserva.getNumMovimento() == idEditar) {
                reservas.set(1, inserirDadosReserva(idEditar/*, Constantes.Etapa.EDITAR*/));
                System.out.println("Reserva editada com sucesso!");
                gravarArrayReservas();
                return;
            }
        }

        System.out.println("ID não encontrado!");
    }

    /*
     * TODO:
     *   Criar metodo para editar os detalhes da reserva
     *   Escolher livro revista ou jornal
     *   selecionar as datas
     */

    public static void criarFicheiroCsvReservas(String ficheiro, Reserva reserva, Boolean firstLine) throws IOException {
        try (FileWriter fw = new FileWriter(ficheiro, firstLine)) {
            fw.write(String.join(";",
                    Integer.toString(reserva.getCodBiblioteca()),
                    Integer.toString(reserva.getNumMovimento()),
                    Integer.toString(reserva.getNif())) + "\n");
        }
    }

    public static void lerFicheiroCsvReservas(String ficheiro){
        try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            if (readFile.readLine() == null) {
                System.out.println("O arquivo está vazio.");
                return;
            }
            String linha;
            String csvDivisor = ";", isbn="";
            //ArrayList<String> dados= new ArrayList<String>();

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
            System.out.println("Não existem reservas para mostrar.");
            return;
        }

        mostraTabelaReservas(reservas);
    }

    public static void gravarArrayReservas() throws IOException {
        if(reservas.isEmpty()) {
            new File(Constantes.Path.RESERVA.getValue()).delete();
            System.out.println("Array vazio");
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
     * Metodo para inserir os detalhes de uma reserva
     * atribuido a algum Cliente
     * @param reserva Recebe o valor da ultima reserva criada
     * */
    public static ReservaDtl inserirDetalhesReserva(Reserva reserva){
        /* TODO:
            Criar o resto da estrutura da reserva
            pedir para escolher os Items que quer usar na reserva
            Permitir adicionar mais que um item
            Mas não se já estiver a ser usado noutra Reserva/emprestimo
            ou as Datas não são compativeis
        */
        int idDetalhe = pesquisarIdArray(Constantes.TipoItem.RESERVADTL),
            numMovimento = reserva.getNumMovimento(),
            codBiblioteca = reserva.getCodBiblioteca(),
            nif = reserva.getNif();
        LocalDateTime dataInicio = LocalDateTime.now();
        LocalDateTime dataFim = dataInicio.plusDays(7); // Example duration
        LocalDateTime dataRegisto = LocalDateTime.now();



        String isbn ="";
        if (!livros.isEmpty()) {
            listaTodosLivros();
            do{
                isbn="teste";
            System.out.println("Escolha o(s) livro(s) que deseja adicionar a reserva: ");
            int idLivro = input.nextInt();

            /*
                TODO:
                    Percorre o array para encontrar o ISBN do Livro escolhido
                    Verificar se o Livro existe
                        Se existir verificar se está disponivel para reserva
            */
            for (Livro livro : livros) {
                if (livro.getId()==idLivro){
                    if (!reservasdtl.isEmpty()) {
                        for (ReservaDtl reservaDtl : reservasdtl){
                            if (reservaDtl.getIsbn().equals(livro.getIsbn())){
                                System.out.println("Livro já se econtra reservado");
                                isbn=null;
                                break;
                            }else {
                                isbn = livro.getIsbn();
                            }
                        }
                    }else {isbn = livro.getIsbn(); break;}
                }else {
                    System.out.println("ID não encontrado");
                    isbn=null;
                }
                if (isbn==null){break;}
            }
            }while (isbn==null);
        }else{
            System.out.println("Não existem livros nesta Biblioteca não pode reservar");
            return null;
        }


        return new ReservaDtl(idDetalhe, numMovimento, codBiblioteca, dataInicio, dataFim, clientes,  livros, jornais, revistas, dataRegisto, nif, isbn);
    }

    /**
     * Metodo para criar o ficheiro de detalhes de uma reserva
     * atribuido a algum Cliente
     * @param ficheiro Recebe o valor do Path do ficheiro a tratar
     * @param reservadtl Recebe o valor de uma ReservaDtl do Array
     * @param firstLine reescrever o ficheiro só e só se for a Pirmeira linha a ser inserida
     * */
    public static void criarFicheiroCsvReservasDtl(String ficheiro, ReservaDtl reservadtl, Boolean firstLine) throws IOException {
        try (FileWriter fw = new FileWriter(ficheiro, firstLine)) {
            fw.write(String.join(";",
                    Integer.toString(reservadtl.getIdDetalhe()),
                    Integer.toString(reservadtl.getCodBiblioteca()),
                    Integer.toString(reservadtl.getNumMovimento()),
                    reservadtl.getDataInicio().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                    reservadtl.getDataFim().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                    reservadtl.getDataRegisto().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                    Integer.toString(reservadtl.getNif()),
                    reservadtl.getIsbn())+ "\n");
        }
    }
    /**
     * Metodo para ler o Ficheiro de Detalhes de Reserva e carregar a
     * informação no Array ReservasDtl
     * @param ficheiro Recebe o valor do Path do ficheiro a tratar
     * */
    public static void lerFicheiroCsvReservasDtl(String ficheiro){
        try (BufferedReader readFile = new BufferedReader(new FileReader(ficheiro))) {
            if (readFile.readLine() == null) {
                System.out.println("O arquivo está vazio.");
                return;
            }
            String linha;
            String csvDivisor = ";";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            while ((linha = readFile.readLine()) != null) {
                String[] dados = linha.split(csvDivisor);
                int idDetalhe = Integer.parseInt(dados[0]);
                int codBiblioteca = Integer.parseInt(dados[1]);
                int idReserva = Integer.parseInt(dados[2]);
                LocalDateTime dataInicio = LocalDateTime.parse(dados[3], formatter);
                LocalDateTime dataFim = LocalDateTime.parse(dados[4], formatter);
                LocalDateTime dataRegisto = LocalDateTime.parse(dados[5], formatter);
                int nif = Integer.parseInt(dados[6]);
                String isbn = dados[7];

                ReservaDtl reservadtl = new ReservaDtl(idDetalhe, codBiblioteca, idReserva, dataInicio, dataFim, clientes, livros, jornais, revistas, dataRegisto, nif, isbn);

                reservasdtl.add(reservadtl);
            }
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
    public static void gravarArrayReservasDtl() throws IOException {
        if (reservasdtl.isEmpty()) {
            new File(Constantes.Path.RESERVADTL.getValue()).delete();
            System.out.println("Array vazio");
        }

        for (int i = 0; i < reservasdtl.size(); i++) {
            criarFicheiroCsvReservasDtl(Constantes.Path.RESERVADTL.getValue(), reservasdtl.get(i), i != 0);
        }
    }

    /*
     * ############################### TRATAMENTO DE DADOS DETALHES RESERVAS - FIM ##############################################
     * */

    /*
     * ######################################## HELPERS - INICIO #######################################################
     * */

    /**
     * Metodo para atribuir automaticamente um ID com base no tipo de função.
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
            case RESERVADTL:
                for (ReservaDtl reservadtl : reservasdtl) {
                    if (reservadtl.getIdDetalhe() >= valor)
                        valor = reservadtl.getIdDetalhe() + 1;
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
    /*
     * ########################################## HELPERS - FIM ########################################################
     * */
}
