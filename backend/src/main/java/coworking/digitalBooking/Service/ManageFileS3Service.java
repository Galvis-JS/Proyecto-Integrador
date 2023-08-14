package coworking.digitalBooking.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

@Service
public class ManageFileS3Service {

    @Value("${aws.s3.bucket.name}")
    private String  bucketName;

    private String folderName = "img/";
    @Autowired
    private AmazonS3 s3Client;

    public String uploadFileToS3(MultipartFile multipartFile) throws IOException {
        String fileName = generateRandomFileName(multipartFile.getOriginalFilename());

        // Crear archivo temporal y transferir contenido del MultipartFile
        File file = convertMultipartFileToFile(multipartFile);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName + fileName, file);
        s3Client.putObject(putObjectRequest);

        // Eliminar archivo temporal
        file.delete();

        return generateS3FileUrl(fileName);
    }

    public void deleteFileFromS3(String url) {
        String fileName = getFileNameFromUrl(url);
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, folderName + fileName);
        s3Client.deleteObject(deleteObjectRequest);
    }

    private String generateRandomFileName(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        String randomName = generateRandomString();
        return randomName + extension;
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex);
        }
        return "";
    }

    private String generateRandomString() {
        // Longitud deseada de la cadena aleatoria
        int length = 10;

        // Caracteres permitidos en la cadena aleatoria
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Crear un generador seguro de números aleatorios
        SecureRandom random = new SecureRandom();

        // Crear una cadena aleatoria de la longitud deseada
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    private String generateS3FileUrl(String fileName) {
        return "https://" + bucketName + ".s3.amazonaws.com/"+ folderName + fileName;
    }

    private String getFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile(multipartFile.getOriginalFilename(), null);
        multipartFile.transferTo(file);
        return file;
    }
}