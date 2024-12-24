import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DadosLivro extends TratamentoDados{

    private static Scanner input = new Scanner(System.in);
    private static List<Livro> livros = new ArrayList<>();

    public static Livro inserirDadosLivro(int id){
        int anoEdicao = 0;
        String titulo = "", editora = "", categoria = "", isbn = "", autor = "";
        boolean flag;
        System.out.print("\nPor favor, insira o Titulo do Livro: ");
        titulo = input.nextLine();
        System.out.print("\nPor favor, insira o Editora do Livro: ");
        editora = input.nextLine();
        System.out.print("\nPor favor, insira o Categoria do Livro: ");
        categoria = input.nextLine();
        System.out.print("\nPor favor, insira o Autor do Livro: ");
        autor = input.nextLine();
        do {
            System.out.print("\nPor favor, insira o ISBN do Livro: ");
            isbn = input.nextLine();
            flag=validarTamanho((isbn),9);
            if(!flag)
                System.out.print("ISBN Inválido! ex: 1234-5678: ");
        }while (!flag);
        System.out.print("\nPor favor, insira o Ano edição do Livro: ");
        anoEdicao = input.nextInt();

        return new Livro(id, titulo, editora, categoria, anoEdicao, isbn, autor,1);
    }

    /**
     * Metodo para criar novo Livro
     * */
    public static void criarLivro() {
        livros.add(inserirDadosLivro(pesquisarIdArrayLivro()));
    }

    /**
     * Metodo para editar o Livro
     * */
    public static Cliente editarLivro(int id) {
        return inserirDadosCliente(id);
    }

    public static void criarFicheiroCsvlivro(String ficheiro, Livro livro, Boolean firstLine) throws IOException {

        FileWriter fw = new FileWriter(ficheiro, firstLine);

        fw.append(Integer.toString(livro.getId()));
        fw.append(";");
        fw.append(livro.getTitulo());
        fw.append(";");
        fw.append(livro.getEditora());
        fw.append(";");
        fw.append(livro.getCategoria());
        fw.append(";");
        fw.append(Integer.toString(livro.getAnoEdicao()));
        fw.append(";");
        fw.append(livro.getAutor());
        fw.append(";");
        fw.append(livro.getIsbn());
        fw.append(";");
        fw.append(Integer.toString(1));
        fw.append(";");
        fw.append("\n");
        fw.flush();
        fw.close();
    }

    public static void gravarArraylivros() throws IOException {

        if(!livros.isEmpty()){
            for(int i = 0; i < livros.size(); i++){
                Livro livro = livros.get(i);
                criarFicheiroCsvlivro("Biblioteca_1/Livros/livros.csv", livro, i != 0);
            }
        }else {
            System.out.println("Array vazio");
        }
    }

    public static int pesquisarIdArrayLivro(){
        int valor = 0;
        if(!livros.isEmpty()){
            for (Livro livro : livros) {
                if (livro.getId() >= valor) {
                    valor = livro.getId();
                    valor++;
                }
            }
        }else {
            valor = 1;
        }
        return valor;
    }

    public static void lerFicheiroCsvLivros(String ficheiro){

        BufferedReader readFile;
        String linha;
        String csvDivisor = ";";
        //ArrayList<String> dados= new ArrayList<String>();

        try{
            readFile = new BufferedReader(new FileReader(ficheiro));
            while ((linha = readFile.readLine()) != null) {
                int     id = Integer.parseInt(linha.split(csvDivisor)[0]);
                String  titulo =linha.split(csvDivisor)[0],
                        editora = linha.split(csvDivisor)[1],
                        categoria = linha.split(csvDivisor)[2];

                int        anoEdicao = Integer.parseInt(linha.split(csvDivisor)[4]);
                String  isbn = linha.split(csvDivisor)[5],
                        autor = linha.split(csvDivisor)[6];

                Livro livro = new Livro(id, titulo, editora, categoria, anoEdicao, isbn, autor,1);
                livros.add(livro);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            //e.printStackTrace(); //remover o erro do ecra
        }
        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }
}
