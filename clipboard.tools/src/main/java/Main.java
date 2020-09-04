import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

import java.awt.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class Main {
    private static boolean exit = false;
    private static Display display;

    public static void main(String[] args) {
        //            for (String arg : args) {
//                System.out.println(arg);
//            }
        display = new Display();
        display.asyncExec(() -> {

            String cmd = args[0];
            if (cmd.equals("get")) {
                String text = null;
                text = getClipboardText();
                System.out.println(text);
            } else if (cmd.equals("set")) {
                String text = args[1];
                setClipboardText(text);
            }
            exit = true;
        });
        while (!exit) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
//        for (int i = 0; i < 100; i++) {
//            if (!display.readAndDispatch()) {
//                display.sleep();
//            }
//        }
        display.dispose();
    }

    private static void setClipboardText(String text) {
        Clipboard clipboard = new Clipboard(display);
        clipboard.setContents(new Object[]{text}, new Transfer[]{TextTransfer.getInstance()});
        for (int i = 0; i < 30; i++) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        while (true) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
            if (getClipboardText().equals(text)) {
                break;
            }
        }
    }

    private static String getClipboardText() {

        Clipboard clipboard = new Clipboard(display);
        String contents = (String) clipboard.getContents(TextTransfer.getInstance());
        if (contents == null) {
            contents = "";
        }
        return (String) contents;

//        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//        Transferable contents = clipboard.getContents(null);
//        if (contents == null) {
//            return "";
//        }
//        Object data = contents.getTransferData(DataFlavor.stringFlavor);
//
//        return (String) data;
    }
}
