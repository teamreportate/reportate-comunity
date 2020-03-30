package bo.com.reportate.sign.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Slf4j
public class FileUtil {

    public static byte[] toByte(String pathFile){
        try {
            Path path = Paths.get(pathFile);
            byte[] byteArray = Files.readAllBytes(path);
            return byteArray ;
        }
        catch (Exception e){
            log.error("error",e);
        }
        return null;
    }

    private static void printContent(File file) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            log.error("error",e);
        } finally {
            br.close();
        }

    }


    public static File getFileFromResources(String path) throws IOException {
        ClassLoader classLoader = FileUtil.class.getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());

        return file;
    }

    public static String byteToBase64(byte[] byteArray){
        byte[] encoded = Base64.getEncoder().encode(byteArray);
        return new String(encoded);
    }

    public static byte[] base64ToByte(String base64){
        byte[] decoded = Base64.getDecoder().decode(base64);
        return decoded;
    }
}
