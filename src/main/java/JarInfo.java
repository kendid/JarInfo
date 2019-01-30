import javax.swing.*;
import java.awt.*;

public class JarInfo extends JFrame {

    private JarInfo() throws HeadlessException {
        super("JarInfo");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new JLabel("Hello, world!"));
        this.pack();
        this.setVisible(true);
    }

    public static void main(final String[] args) {
        new JarInfo();
    }
}
