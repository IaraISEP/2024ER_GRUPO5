import java.util.List;

// Class que tornará dinâmica a gestão de uma ou mais bibliotecas
// Irá armazenar os dados dos clientes, livros, jornais, revistas, reservas e emprestimos
public class Biblioteca {
    private String nome;
    private int codBiblioteca;
    private List<Livro> livro;
    private List<Jornal> jornal;
    private List<Cliente> cliente;
    private List<Emprestimo> emprestimos;
    private List<Reserva> reservas;
}
