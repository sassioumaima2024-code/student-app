package tn.iset;

import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class App {
    public static void main(String[] args) throws Exception {
        StudentDao dao = new StudentDao("jdbc:h2:mem:students;DB_CLOSE_DELAY=-1");
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", ex -> {
            try {
                String q = ex.getRequestURI().getQuery();
                if (ex.getRequestURI().getPath().equals("/add") && q != null && q.startsWith("name=")) {
                    dao.add(URLDecoder.decode(q.substring(5), StandardCharsets.UTF_8));
                }
                String body = "Etudiants : " + dao.findAll() + "\nConteneur : " + InetAddress.getLocalHost().getHostName();
                byte[] b = body.getBytes(StandardCharsets.UTF_8);
                ex.getResponseHeaders().add("Content-Type", "text/plain; charset=utf-8");
                ex.sendResponseHeaders(200, b.length);
                try (OutputStream os = ex.getResponseBody()) { os.write(b); }
            } catch (Exception e) {
                ex.sendResponseHeaders(500, -1);
            }
        });
        server.start();
        System.out.println("Serveur demarre sur le port 8080");
    }
}
