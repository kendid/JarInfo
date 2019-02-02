import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class JarInfo extends JFrame {

    private JarInfo() throws HeadlessException {
        super("JarInfo");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(640, 480));

        final JTextArea textArea = new JTextArea("Drop a .JAR file onto this window");
        this.add(textArea);

        final DropTarget dt = new DropTarget() {
            @Override
            public synchronized void drop(final DropTargetDropEvent event) {
                try {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    final List<File> transferData = (List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                    textArea.setText(retrieveJarInfo(transferData));
                } catch (Exception ex) {
                    textArea.setText(ex.getMessage());
                    super.drop(event);
                }
            }
        };

        textArea.setDropTarget(dt);
        this.setDropTarget(dt);

        this.pack();
        this.setVisible(true);
    }

    private String retrieveJarInfo(final List<File> files) {
        if (files.size() != 1) {
            return "Please drop one and only one .JAR file onto this window";
        }

        final File file = files.get(0);
        if (!file.getName().toLowerCase().endsWith(".jar")) {
            return "Please drop one and only one .JAR file onto this window";
        }

        return retrieveJarInfo(file);
    }

    private String retrieveJarInfo(final File file) {
        if (!file.canRead()) {
            return "No read access on file " + file.getName();
        }

        try {
            final ZipFile zipFile = new ZipFile(file);

            StringBuilder returnString = new StringBuilder();
            final Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
            while (enumeration.hasMoreElements()) {
                final ZipEntry zipEntry = enumeration.nextElement();
                returnString.append(zipEntry.getName()).append("\n");
            }
            return returnString.toString().trim();
        } catch (final IOException e) {
            return "I/O Error during processing of file " + file.getName() + ": " + e.getMessage();
        }
    }

    public static void main(final String[] args) {
        new JarInfo();
    }
}
