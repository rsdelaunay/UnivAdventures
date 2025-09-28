import java.io.File;
import java.util.ArrayList;

public class ListFiles {

    public static void main(String[] args) {
        if(args.length == 0){ //
            return;
        } else if (args.length == 1) { //diretorio
            File dir = new File(args[0]); //path
            ArrayList<File> listdir = getFiles(dir); //obter a lista de ficheiros do path
            for (File file : listdir) { //percorrer a lista
                System.out.println(file.toString()); //dar print (toString)
            }
        }else {
            if (args[0].equals("-r")) { //verificar se é para dar print a ficheiros de sub diretorios
                File dir = new File(args[1]); //path
                ArrayList<File> listdir = getFilesRec(dir); //obter a lista de ficheiros do path
                for (File file : listdir) { //percorrer a list
                    System.out.println(file.toString()); //dar print (toString)
                }
            } else {
                File dir = new File(args[0]); //verificar o mesmo caso
                ArrayList<File> listdir = getFiles(dir);
                getFiles(dir);
                for (File file : listdir) {
                    System.out.println(file.toString());
                }
            }
        }
    }

    public static ArrayList<File> getFiles(File dir) {
        ArrayList<File> list = new ArrayList<>(); //criar lista
        File[] files = dir.listFiles(); //criar array com todos os elementos do path
        if (files == null)
            System.err.println("diretório vazio"); //erro se tiver vazio
        else {
            for (File file : files) { //percorrer array
                if (file.isFile())  //se for um ficheiro
                    list.add(file); //adiciona a lista
            }
            return list;
        }
        return list;
    }

    public static ArrayList<File> getFilesRec(File dir) {
        ArrayList<File> list = new ArrayList<>();
        collectFilesRec(dir, list);
        return list;
    }
    private static void collectFilesRec(File f, ArrayList<File> list) {
        File[] files = f.listFiles(); //dividir o path num array
        if(files == null) //verificar se array está vazio
            return;
        for (File file : files) { //percorrer o array
            if (file.isDirectory()) //se for diretorio
                collectFilesRec(file,list); // percorrer esse diretorio e adicionar ficheiros a lista
            else {
                list.add(file); //adicionar ficheiros a lista
            }
        }
    }
}
