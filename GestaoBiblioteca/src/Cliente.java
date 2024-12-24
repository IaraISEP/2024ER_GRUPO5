import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Cliente {

    private int id;
    private int codBiblioteca;
    private String nome;
    private String genero;
    private int nif;
    private int contacto;
    private int opcao;
    private boolean status = true;

    private Scanner input = new Scanner(System.in);

    private TratamentoDados dados = new TratamentoDados();

    public Cliente() {

    }

    public Cliente(int id, String nome, String genero, int nif, int contacto, int codBiblioteca) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.nif = nif;
        this.contacto = contacto;
        this.codBiblioteca = codBiblioteca;
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

    public void setNif(int nif) { this.nif = nif; }

    public int getContacto() {
        return contacto;
    }

    public void setContacto(int contacto) {
        this.contacto = contacto;
    }

    public void createCliente(){

        int val = 0;
        System.out.print("\nPor favor, insira o Id do Cliente: ");
        setId(validarInteiro());
        input.nextLine();
        do{
            System.out.print("\nPor favor, insira o nif do Cliente: ");
            val = validarInteiro();
            if (Integer.toString(val).length() != 9) {
                System.out.println("NIF Invalido ( 123456789 )"); }
        }
        while (Integer.toString(val).length() > 9 || Integer.toString(val).length() < 9);
        setNif(val);
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
        do{
            System.out.print("\nPor favor, insira o Contacto do Cliente: ");
            val = validarInteiro();
            input.nextLine();
            if (Integer.toString(val).length() != 9) {
                System.out.println("Contacto invalido! ( 123456789 )"); }
        }while (Integer.toString(val).length() > 9 || Integer.toString(val).length() < 9);
        setContacto(val);

        try {
            dados.printFileCsv("clientes.csv", getId(), getNome(), getGenero(), getNif(), getContacto());
        } catch (IOException e) {
            System.err.println("Erro ao criar Cliente" + e.getMessage());
        }

    }

    public int validarInteiro(){
        boolean isInt = false;
        int isIntVal = 0;
        while(!isInt){
            try {
                isIntVal = input.nextInt();
                isInt = true;
            } catch (Exception e) {
                System.out.println("Por favor, insira um número inteiro.");
                input.nextLine();
            }
        }
        return isIntVal;
    }

    public int getCodBiblioteca() {
        return codBiblioteca;
    }
}
