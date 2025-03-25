import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.util.concurrent.*;

public class Overlay {
    private JLabel text = new JLabel("Pinging...");
    private JFrame frame = new JFrame();
    private JTextField ipInput = new JTextField("104.160.141.3"); // IP par défaut
    private ScheduledExecutorService scheduler;
    private boolean isPinging = false;

    public Overlay() {
        System.out.println("Overlay initialized...");

        // Configuration du JLabel
        text.setFont(new Font("Roboto", Font.BOLD, 20));
        text.setForeground(Color.GREEN);

        // Boutons pour PING, STOP et quitter
        JButton pingButton = new JButton("PING");
        pingButton.setFont(new Font("Roboto", Font.PLAIN, 14));
        pingButton.addActionListener(e -> startPing());

        JButton stopButton = new JButton("STOP");
        stopButton.setFont(new Font("Roboto", Font.PLAIN, 14));
        stopButton.addActionListener(e -> stopPing());

        // Bouton quitter (croix rouge)
        JButton quitButton = new JButton("✖");
        quitButton.setFont(new Font("Roboto", Font.BOLD, 16));
        quitButton.setForeground(Color.RED);
        quitButton.addActionListener(e -> System.exit(0));

        // Configuration du champ IP
        ipInput.setFont(new Font("Roboto", Font.PLAIN, 14));
        ipInput.setHorizontalAlignment(JTextField.CENTER);

        // Mise en page
        frame.setUndecorated(true); // Pas de bordures
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null); // Centre la fenêtre
        frame.setAlwaysOnTop(true); // Toujours au premier plan

        // Panneau principal
        JPanel panel = new JPanel();
        panel.setBackground(new Color(50, 50, 50)); // Fond sombre
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Alignement vertical
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marges

        // Ajouter des composants au panneau
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(quitButton);

        ipInput.setMaximumSize(new Dimension(200, 30)); // Taille fixe
        panel.add(ipInput);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Espace vertical

        text.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrage horizontal
        panel.add(text);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Panneau pour les boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(50, 50, 50));
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(pingButton);
        buttonPanel.add(stopButton);

        panel.add(buttonPanel);

        // Ajouter le panneau à la fenêtre
        frame.add(panel);
        frame.setVisible(true);

        // Initialise le ScheduledExecutorService
        scheduler = Executors.newScheduledThreadPool(1);
    }

    public void setPing(String string) {
        try {
            long n = Long.parseLong(string);
            if (n <= 60) {
                text.setForeground(Color.GREEN);
            } else if (n > 100) {
                text.setForeground(Color.RED);
            } else {
                text.setForeground(Color.ORANGE);
            }
            text.setText(n + " ms");
        } catch (NumberFormatException e) {
            text.setText("Invalid ping");
            text.setForeground(Color.RED);
        }
        text.updateUI();
    }

    private void startPing() {
        if (isPinging) {
            text.setText("Already pinging...");
            text.setForeground(Color.YELLOW);
            return;
        }
        String ip = ipInput.getText().trim();
        if (!isValidIP(ip)) {
            text.setText("Invalid IP address");
            text.setForeground(Color.RED);
            return;
        }
        text.setText("Pinging...");
        text.setForeground(Color.GREEN);

        scheduler.scheduleAtFixedRate(() -> {
            try {
                InetAddress address = InetAddress.getByName(ip);
                if (address.isReachable(5000)) {
                    SwingUtilities.invokeLater(() -> setPing("30")); // Exemple de valeur (remplacez par un calcul)
                } else {
                    SwingUtilities.invokeLater(() -> setPing("0")); // Host injoignable
                }
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> setPing("Error"));
            }
        }, 0, 1, TimeUnit.SECONDS);
        isPinging = true;
    }

    private void stopPing() {
        if (!isPinging) {
            text.setText("Not pinging...");
            text.setForeground(Color.YELLOW);
            return;
        }
        scheduler.shutdownNow();
        scheduler = Executors.newScheduledThreadPool(1); // Réinitialiser le scheduler
        text.setText("Ping stopped");
        text.setForeground(Color.RED);
        isPinging = false;
    }

    private boolean isValidIP(String ip) {
        try {
            String[] parts = ip.split("\\.");
            if (parts.length != 4) return false;
            for (String part : parts) {
                int i = Integer.parseInt(part);
                if (i < 0 || i > 255) return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
