import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class FileTransferClient {
    private static final String SERVER = "localhost";
    private static final int PORT = 8010;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n1. Download file\n2. Upload file\n3. Exit");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    downloadFile(scanner);
                    break;
                case "2":
                    uploadFile(scanner);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void downloadFile(Scanner scanner) {
        System.out.print("Enter filename to download: ");
        String fileName = scanner.nextLine();

        try (Socket socket = new Socket(SERVER, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            out.println("DOWNLOAD " + fileName);
            String response = in.readLine();

            if (response.equals("FILE_START")) {
                System.out.println("\nFile contents:");
                String line;
                while (!(line = in.readLine()).equals("FILE_END")) {
                    System.out.println(line);
                }
                System.out.println("File download complete");
            } else {
                System.err.println("Error: " + response);
            }
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    private static void uploadFile(Scanner scanner) {
        System.out.print("Enter local file path: ");
        String localPath = scanner.nextLine();
        System.out.print("Enter server filename: ");
        String serverName = scanner.nextLine();

        if (!Files.exists(Paths.get(localPath))) {
            System.err.println("Local file not found");
            return;
        }

        try (Socket socket = new Socket(SERVER, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader fileReader = Files.newBufferedReader(Paths.get(localPath))) {
            
            out.println("UPLOAD " + serverName);
            String response = in.readLine();

            if (response.equals("READY")) {
                String line;
                while ((line = fileReader.readLine()) != null) {
                    out.println(line);
                }
                out.println("FILE_END");
                System.out.println("Server response: " + in.readLine());
            } else {
                System.err.println("Error: " + response);
            }
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }
}