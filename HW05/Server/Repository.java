package Server;

import java.io.*;

public class Repository {
    private String path;
    private File file;

    public Repository(String path) {
        this.path = path;
        file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println(file.getAbsolutePath());
                e.printStackTrace();
            }
        }

    }

    public void writeToFile(String Data) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(Data);
            sb.append(System.lineSeparator());
            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath(), true));
            bw.write(sb.toString());
            bw.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String userData = br.readLine();
            while (userData != null) {
                sb.append(userData);
                userData = br.readLine();
                if (userData != null) {
                    sb.append(System.lineSeparator());
                }
            }
            return sb.toString();
        } catch (IOException ex) {
            System.out.println("Файла не существует" + file);
            return null;
        }
    }
}
