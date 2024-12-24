import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
/** Representa um Cliente
 * @author ER_GRUPO_5
 * @since 2024
 */
public class Cliente {

    private int id;
    private String nome;
    private String genero;
    private int nif;
    private int contacto;
    private int opcao;
    private boolean status = true;

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

    public void setNif(int nif) { this.nif = nif; }

    public int getContacto() {
        return contacto;
    }

    public void setContacto(int contacto) {
        this.contacto = contacto;
    }
}
