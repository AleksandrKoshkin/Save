import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Main {
    public static void newFile(File file) {
        try {
            if (file.createNewFile())
                System.out.println("Файл создан");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveGame(String way, GameProgress progress) {
        try (FileOutputStream fos = new FileOutputStream(way);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String way, String[] save) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(way))) {
            for (String i : save) {
                try (FileInputStream file = new FileInputStream(i)) {
                    ZipEntry entry = new ZipEntry(i);
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[file.available()];
                    file.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        GameProgress progress1 = new GameProgress(21, 1, 5, 34);
        GameProgress progress2 = new GameProgress(36, 2, 8, 40);
        GameProgress progress3 = new GameProgress(54, 1, 10, 74);
        File newFile1 = new File("D:\\Java\\Games\\savegames", "save1.dat");
        File newFile2 = new File("D:\\Java\\Games\\savegames", "save2.dat");
        File newFile3 = new File("D:\\Java\\Games\\savegames", "save3.dat");
        newFile(newFile1);
        newFile(newFile2);
        newFile(newFile3);
        saveGame(newFile1.getAbsolutePath(), progress1);
        saveGame(newFile2.getAbsolutePath(), progress2);
        saveGame(newFile3.getAbsolutePath(), progress3);
        String[] save = new String[]{newFile1.getAbsolutePath(), newFile2.getAbsolutePath(), newFile3.getAbsolutePath()};
        zipFiles("D:\\Java\\Games\\savegames\\zip.zip", save);
        newFile1.delete();
        newFile2.delete();
        newFile3.delete();
    }
}