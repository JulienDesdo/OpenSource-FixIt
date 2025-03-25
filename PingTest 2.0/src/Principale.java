import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import java.time.*;

public class Principale {
    public static boolean pingInProgress = false;
    public static String host = "104.160.141.3";
    public static Overlay over; // Déclaration, mais pas initialisation ici

    public static void main(String[] args) {
        System.out.println("Application starting...");

        // Initialisation explicite d'Overlay
        over = new Overlay();

        // Initialisation de la tâche planifiée
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            if (!pingInProgress) {
                long pingMs = ping(host).toMillis();
                //System.out.println(pingMs); // Affichage dans la console
                over.setPing(Long.toString(pingMs)); // Mise à jour de l'interface
            }
        }, 0, 1, TimeUnit.SECONDS);

        // Ajout d'un shutdown hook pour nettoyage lors de la fermeture
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Application shutting down...");
            scheduler.shutdownNow();
        }));
    }

    public static Duration ping(String host) {
        pingInProgress = true;
        Instant startTime = Instant.now();
        try {
            InetAddress address = InetAddress.getByName(host);
            if (address.isReachable(5000)) { // Timeout de 5 secondes
                pingInProgress = false;
                return Duration.between(startTime, Instant.now());
            }
        } catch (IOException e) {
            System.err.println("Ping error: " + e.getMessage());
        }
        pingInProgress = false;
        return Duration.ZERO; // Retourne zéro si l'hôte est injoignable
    }
}
