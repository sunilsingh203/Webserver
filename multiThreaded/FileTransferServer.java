import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.concurrent.*;

public class FileTransferServer {
    private static final int PORT = 8010;
    private static final String SERVER_FOLDER = "server_files/";
    private static final long MAX_FILE_SIZE = 1024 * 1024; // 1MB
    
    static {
        new File(SERVER_FOLDER).mkdirs(); // Create directory if not exists
    }

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            
            String command = in.readLine();
            if (command == null) return;

            if (command.startsWith("DOWNLOAD ")) {
                handleDownload(command.substring(9).trim(), out);
            } else if (command.startsWith("UPLOAD ")) {
                handleUpload(command.substring(7).trim(), in, out);
            } else {
                out.println("ERROR: Invalid command");
            }
        } catch (IOException e) {
            System.err.println("Client handling error: " + e.getMessage());
        } finally {
            try { clientSocket.close(); } catch (IOException e) {}
        }
    }

    private static void handleDownload(String fileName, PrintWriter out) {
        if (!isValidFileName(fileName)) {
            out.println("ERROR: Invalid filename");
            return;
        }

        Path filePath = Paths.get(SERVER_FOLDER + fileName);
        if (!Files.exists(filePath)) {
            out.println("ERROR: File not found");
            return;
        }

        try {
            out.println("FILE_START");
            Files.lines(filePath).forEach(out::println);
            out.println("FILE_END");
        } catch (IOException e) {
            out.println("ERROR: " + e.getMessage());
        }
    }

    private static void handleUpload(String fileName, BufferedReader in, PrintWriter out) throws IOException {
        if (!isValidFileName(fileName)) {
            out.println("ERROR: Invalid filename");
            return;
        }

        Path filePath = Paths.get(SERVER_FOLDER + fileName);
        out.println("READY");

        StringBuilder content = new StringBuilder();
        String line;
        while (!(line = in.readLine()).equals("FILE_END")) {
            content.append(line).append("\n");
            if (content.length() > MAX_FILE_SIZE) {
                out.println("ERROR: File too large");
                return;
            }
        }

        Files.write(filePath, content.toString().getBytes());
        out.println("UPLOAD_SUCCESS");
    }

    private static boolean isValidFileName(String fileName) {
        return !fileName.contains("..") && !fileName.startsWith("/") 
               && !fileName.isEmpty() && fileName.matches("[a-zA-Z0-9._-]+");
    }
}