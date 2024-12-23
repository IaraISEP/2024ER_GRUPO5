import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class tratamentoDadosClientes extends tratamentoDados{

    private static final Scanner input = new Scanner(System.in);
    private static final List<Cliente> clientes = new ArrayList<>();

    public static Cliente inserirDadosCliente(int id){
        int nif, contacto, opcao;
        String nome, genero="";
        boolean flag;
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

        return new Cliente(id, nome,genero,nif,contacto);
    }

    public static void criarCliente() {
        clientes.add(inserirDadosCliente(pesquisarIdFicheiroCliente()));
    }

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
        int idApagar, index=0;
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
                }
            }
            if(idFound){
                clientes.remove(index);
                System.out.println("Cliente apagado com sucesso!");
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
            System.out.println("Array vazio");
        }
    }

    public static void lerFicheiroCsvClientes(String ficheiro){

        BufferedReader readFile;
        String linha;
        String csvDivisor = ";";
        //ArrayList<String> dados= new ArrayList<String>();

        try{
            readFile = new BufferedReader(new FileReader(ficheiro));
            while ((linha = readFile.readLine()) != null) {
                int id = Integer.parseInt(linha.split(csvDivisor)[0]);
                String  nome =linha.split(csvDivisor)[1],
                        genero = linha.split(csvDivisor)[2];
                int nif = Integer.parseInt(linha.split(csvDivisor)[3]),
                        contacto = Integer.parseInt(linha.split(csvDivisor)[4]);

                Cliente cliente = new Cliente(id, nome,genero,nif,contacto);
                clientes.add(cliente);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            //e.printStackTrace(); //remover o erro do ecra
        }
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    public static int pesquisarIdFicheiroCliente(){
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
}
