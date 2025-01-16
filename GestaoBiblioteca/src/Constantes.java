import java.time.LocalDate;

public class Constantes {
    public static String SplitChar = ";";
    public static int TempoMaxReservaDias = 7;
    private static final LocalDate datahoje = LocalDate.now();
    public static final int QtdTotalCharMenu = 50; // Quantidade de MenuChar que vai mostrar, para criar o título do menu
    public static final String MenuChar = "#";
    public static final int qtdPad = 10; // Quantidade de espaços que vai ter entre o MenuChar e o titulo
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

    public static LocalDate getDatahoje() {
        return datahoje;
    }

    public enum Estado {
        DISPONIVEL,
        EMPRESTADO,
        ATRASADO,
        RESERVADO,
        CONCLUIDO,
        CANCELADO
    }

    public enum Morada {
        PORTO(1),
        COIMBRA(2),
        LISBOA(3),
        ALGARVE(4);

        private final int morada;

        Morada(int morada) {
            this.morada = morada;
        }
        public int getMorada() {
            return morada;
        }
    }

    public enum Genero {
        MASCULINO('M'),
        FEMININO('F'),
        INDEFINIDO('I');

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
        BIBLIOTECA,
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
        BIBLIOTECA("Dados/Bibliotecas/bibliotecas.csv"),
        CLIENTE("Dados/Clientes/clientes.csv"),
        LIVRO("Dados/Livros/livros.csv"),
        JORNAL("Dados/Jornais/jornais.csv"),
        REVISTA("Dados/Revistas/revistas.csv"),
        EMPRESTIMO("Dados/Emprestimos/emprestimos.csv"),
        EMPRESTIMOLINHA("Dados/Emprestimos/emprestimolinha.csv"),
        RESERVA("Dados/Reservas/reservas.csv"),
        RESERVALINHA("Dados/Reservas/Details/reservalinha.csv");

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
        EDITAR,
        LISTAR,
        CONCLUIR,
        CANCELAR
    }

    public enum ValidacaoCliente {
        ID,
        NIF,
        CONTACTO
    }
}
