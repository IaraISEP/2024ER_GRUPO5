import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class tratamentoDados {


    public tratamentoDados() {

    }

    public void printFileCsv(String ficheiro, int Id, String nome, String genero, int nif, int contacto) throws IOException {

        FileWriter fw = new FileWriter(ficheiro, true);
        fw.append(Integer.toString(Id));
        fw.append(";");
        fw.append(nome);
        fw.append(";");
        fw.append(genero);
        fw.append(";");
        fw.append(Long.toString(nif));
        fw.append(";");
        fw.append(Long.toString(contacto));
        fw.append("\n");
        fw.flush();
        fw.close();
    }

    public void lerFicehiro(String ficheiro){

        String arquivo = ficheiro;
        BufferedReader readFile = null;
        String linha = null;
        String csvDivisor = ";";
        ArrayList<String> dados= new ArrayList<String>();

        try{
            readFile = new BufferedReader(new FileReader(arquivo));
            while ((linha = readFile.readLine()) != null) {
                dados.add(linha);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        for (String dado : dados) {
            System.out.println(dado);
        }
    }
}