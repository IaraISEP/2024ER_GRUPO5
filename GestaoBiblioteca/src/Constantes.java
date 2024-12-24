
public class Constantes {

    // Enum para Categorias
    // TODO : Servem de placeholder. A definir as Categorias
    public enum Categoria {
        LIVRO(1),
        REVISTA(2),
        JORNAL(3);

        private final int categoria;

        // Construtor do enum
        Categoria(int categoria) {
            this.categoria = categoria;
        }

        // Método para obter o valor
        public int getCategoria() {
            return categoria;
        }
    }

    // Enum for Estado do Emprestimo
    // TODO : Servem de placeholder. A definir os Estados
    public enum EstadoEmprestimo {
        DISPONIVEL,
        EMPRESTADO,
        ATRASADO,
        RESERVADO
    }

    public enum Genero {
        MASCULINO,
        FEMININO
    }
}