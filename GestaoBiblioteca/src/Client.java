import java.util.Scanner;
import java.io.*;

public class Client {

    private int id;
    private String nome;
    private String genero;
    private long nif;
    private long contacto;

    private Scanner ler_id = new Scanner(System.in);

    public Client() {

    }

    public Client(int id, String nome, String genero, long nif, long contacto) {
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

    public void createClient(){
        System.out.print("\nPor favor, insira o Id do Cliente: ");
        setId(ler_id.nextInt());
        ler_id.nextLine();
        System.out.print("\nPor favor, insira o nome do Cliente: ");
        setNome(ler_id.nextLine());
        System.out.print("\nPor favor, insira o Genero do Cliente: ");
        setGenero(ler_id.nextLine());
        System.out.print("\nPor favor, insira o nif do Cliente: ");
        setNif(ler_id.nextLong());
        System.out.print("\nPor favor, insira o Contacto do Cliente: ");
        setContacto(ler_id.nextLong());


        String json = String.format("{\n  \"id\": %d,\n  \"nome\": \"%s\",\n  \"genero\": \"%s\",\n  \"nif\": \"%d\",\n  \"contacto\": \"%d\"\n}",
                getId(), getNome(), getGenero(),getNif(),getContacto());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.format("cliente_%d.json",getId())))) {
            writer.write(json);
            System.out.println("Cliente criado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao criar Cliente" + e.getMessage());
        }
    }
}
