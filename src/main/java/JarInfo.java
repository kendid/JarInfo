import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

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

                    textArea.setText(transferData.toString());
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

    public static void main(final String[] args) {
        new JarInfo();
    }
}
