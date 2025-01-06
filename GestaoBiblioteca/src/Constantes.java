import java.time.LocalDate;

public class Constantes {
    public static String SplitChar = ";";
    public static int TempoMaxReservaDias = 7;
    public static LocalDate datahoje = LocalDate.now();

    public enum Categoria {
        ARTE(1),
        BANDA_DESENHADA(2),
        CIENCIAS(3),
        ECONOMIA(4),
        ENGENHARIA(5),
        GESTAO(6),
        HISTORIA(7),
        INFORMATICA(8),
        POLITICA(9),
        SAUDE(10),
        NOTICIAS(11),
        NEGOCIOS(12),
        DESPORTO(13),
        CULTURA(14),
        TECNOLOGIA(15),
        OPINIAO(16),
        ATUALIDADE(17),
        ENTRETENIMENTO(18),
        CELEBRIDADES(19),
        MODA(20),
        LIFESTYLE(21),
        AUTOMOVEIS(22),
        VIAGENS(23),
        OUTROS(24);
        private final int categoria;

        Categoria(int categoria) {
            this.categoria = categoria;
        }

        public int getCategoria() {
            return categoria;
        }
    }


    public enum Estado {
        DISPONIVEL,
        EMPRESTADO,
        ATRASADO,
        RESERVADO,
        CONCLUIDO,
        CANCELADO
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
        EMPRESTIMOLINHA,
        RESERVA,
        RESERVALINHA
    }

    public enum Path {
        CLIENTE("Biblioteca_1/Clientes/clientes.csv"),
        LIVRO("Biblioteca_1/Livros/livros.csv"),
        JORNAL("Biblioteca_1/Jornais/jornais.csv"),
        REVISTA("Biblioteca_1/Revistas/revistas.csv"),
        EMPRESTIMO("Biblioteca_1/Emprestimos/emprestimos.csv"),
        EMPRESTIMOLINHA("Biblioteca_1/Emprestimos/emprestimolinha.csv"),
        RESERVA("Biblioteca_1/Reservas/reservas.csv"),
        RESERVALINHA("Biblioteca_1/Reservas/Details/reservalinha.csv");

        private final String value;

        Path(String value) {
            this.value = value;
        }

        // Getter for the string value
        public String getValue() {
            return value;
        }
    }

    public enum Etapa {
        CRIAR,
        EDITAR;
    }

    public enum ValidacaoCliente {
        ID,
        NIF,
        CONTACTO;
    }
}
