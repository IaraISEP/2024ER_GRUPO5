
public class Constantes {

    public enum Categoria {
        LIVRO(1),
        REVISTA(2),
        JORNAL(3);

        private final int categoria;

        Categoria(int categoria) {
            this.categoria = categoria;
        }

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
        MASCULINO('M'),
        FEMININO('F');

        private final char genero;

        Genero(char genero) {
            this.genero = genero;
        }

        public char getGenero() {
            return genero;
        }

        public static Genero fromGenero(char genero) {
            for (Genero codigo : values()) {
                if (codigo.getGenero() == genero) {
                    return codigo;
                }
            }
            throw new IllegalArgumentException("Código de gênero inválido: " + genero);
        }
    }

    public enum TipoItem {
        CLIENTE,
        LIVRO,
        JORNAL,
        REVISTA,
        EMPRESTIMO,
        RESERVA,
        RESERVADTL
    }

    public enum Etapa {
        CRIAR,
        EDITAR;
    }
}
