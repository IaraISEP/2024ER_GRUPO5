import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Cliente {

    private int id;
    private String nome;
    private String genero;
    private long nif;
    private long contacto;
    private int opcao;
    private boolean status = true;

    private Scanner input = new Scanner(System.in);

    public Cliente() {

    }

    public Cliente(int id, String nome, String genero, long nif, long contacto) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.nif = nif;
        this.contacto = contacto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public long getNif() {
        return nif;
    }

    public void setNif(long nif) {
        this.nif = nif;
    }

    public long getContacto() {
        return contacto;
    }

    public void setContacto(long contacto) {
        this.contacto = contacto;
    }

    public void printFileCsv() throws IOException {
        FileWriter fw = new FileWriter("clientes.csv", true);
        fw.append(Integer.toString(this.getId()));
        fw.append(";");
        fw.append(this.getNome());
        fw.append(";");
        fw.append(getGenero());
        fw.append(";");
        fw.append(Long.toString(getNif()));
        fw.append(";");
        fw.append(Long.toString(getContacto()));
        fw.append("\n");
        fw.flush();
        fw.close();
    }

    public void createCliente(){
        System.out.print("\nPor favor, insira o Id do Cliente: ");
        setId(input.nextInt());
        input.nextLine();
        System.out.print("\nPor favor, insira o nome do Cliente: ");
        setNome(input.nextLine());
        while (status){
        System.out.print("\nPor favor, insira o Genero do Cliente (1-M | 2-F): ");
        opcao = input.nextInt();
            switch (opcao){
                case 1:
                    setGenero("Masculino");
                    status = false;
                    break;
                case 2:
                    setGenero("Feminino");
                    status = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    status = true;
                    break;
            }
        }
        System.out.print("\nPor favor, insira o nif do Cliente: ");
        setNif(input.nextLong());
        System.out.print("\nPor favor, insira o Contacto do Cliente: ");
        setContacto(input.nextLong());

        try {
            printFileCsv();
        } catch (IOException e) {
            System.err.println("Erro ao criar Cliente" + e.getMessage());
        }

        String json = String.format("{\n  \"id\": %d,\n  \"nome\": \"%s\",\n  \"genero\": \"%s\",\n  \"nif\": \"%d\",\n  \"contacto\": \"%d\"\n}",
                getId(), getNome(), getGenero(),getNif(),getContacto());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.format("cliente_%d.json",getId())))) {
            writer.write(json);
            System.out.println("Cliente criado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao criar Cliente" + e.getMessage());
        }
    }

    public void readCliente(){
        String arquivo = "clientes.csv";
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
