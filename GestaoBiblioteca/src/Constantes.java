
public class Constantes {

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

    public enum EstadoEmprestimo {
        DISPONIVEL(1),
        EMPRESTADO(2),
        ATRASADO(3),
        RESERVADO(4);

        private final int estadoEmprestimo;

        EstadoEmprestimo(int estadoEmprestimo) {
            this.estadoEmprestimo = estadoEmprestimo;
        }

        public int getEstadoEmprestimo() {
            return estadoEmprestimo;
        }
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
