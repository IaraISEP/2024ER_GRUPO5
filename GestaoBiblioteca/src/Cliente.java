import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Cliente {

    private int id;
    private String nome;
    private String genero;
    private int nif;
    private int contacto;
    private int opcao;
    private boolean status = true;

    private Scanner input = new Scanner(System.in);

    private tratamentoDados dados = new tratamentoDados();

    public Cliente() {

    }

    public Cliente(int id, String nome, String genero, int nif, int contacto) {
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

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public int getContacto() {
        return contacto;
    }

    public void setContacto(int contacto) {
        this.contacto = contacto;
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
        setNif(input.nextInt());
        System.out.print("\nPor favor, insira o Contacto do Cliente: ");
        setContacto(input.nextInt());

        try {
            dados.printFileCsv("clientes.csv", getId(), getNome(), getGenero(), getNif(), getContacto());
        } catch (IOException e) {
            System.err.println("Erro ao criar Cliente" + e.getMessage());
        }

/*        String json = String.format("{\n  \"id\": %d,\n  \"nome\": \"%s\",\n  \"genero\": \"%s\",\n  \"nif\": \"%d\",\n  \"contacto\": \"%d\"\n}",
                getId(), getNome(), getGenero(),getNif(),getContacto());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.format("cliente_%d.json",getId())))) {
            writer.write(json);
            System.out.println("Cliente criado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao criar Cliente" + e.getMessage());
        }*/
    }

}
