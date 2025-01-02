
public class Constantes {

    public enum CategoriaLivro {
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
        OUTROS(11);

        private final int categoriaLivro;

        CategoriaLivro(int categoria) {
            this.categoriaLivro = categoria;
        }

        public int getCategoriaLivro() {
            return categoriaLivro;
        }
    }

    public enum CategoriaJornal {
        NOTICIAS(1),
        POLITICA(2),
        NEGOCIOS(3),
        DESPORTO(4),
        CULTURA(5),
        TECNOLOGIA(6),
        SAUDE(7),
        OPINIAO(8),
        OUTROS(9);

        private final int categoriaJornal;

        CategoriaJornal(int categoriaJornal) {
            this.categoriaJornal = categoriaJornal;
        }

        public int getCategoriaJornal() {
            return categoriaJornal;
        }
    }

    public enum CategoriaRevista {
        ATUALIDADE(1),
        NOTICIAS(2),
        ENTRETENIMENTO(3),
        CELEBRIDADES(4),
        MODA(5),
        LIFESTYLE(6),
        SAUDE(7),
        TECNOLOGIA(8),
        AUTOMOVEIS(9),
        DESPORTOS(10),
        NEGOCIOS(10),
        VIAGENS(10),
        OUTROS(11);

        private final int categoriaRevista;

        CategoriaRevista(int categoriaRevista) {
            this.categoriaRevista = categoriaRevista;
        }

        public int getCategoriaRevista() {
            return categoriaRevista;
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
