import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;

public class JarInfo extends JFrame {

    private JarInfo() throws HeadlessException {
        super("JarInfo");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final JLabel label = new JLabel("Hello, world!");
        this.add(label);
        this.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(final DropTargetDropEvent event) {
                try {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    final java.util.List<File> transferData = (java.util.List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                    label.setText(transferData.toString());
                } catch (Exception ex) {
                    label.setText(ex.getMessage());
                    super.drop(event);
                }
            }
        });
        this.pack();
        this.setVisible(true);
    }

    public static void main(final String[] args) {
        new JarInfo();
    }
}
