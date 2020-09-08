package org.dusg.clipboard.tools;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;


public class Main {
    private static boolean exit = false;
    private static Display display;

    public static boolean isExit() {
        return exit;
    }

    public static void setExit(boolean exit) {
        Main.exit = exit;
    }

    public static void main(String[] args) {
        //            for (String arg : args) {
//                System.out.println(arg);
//            }
        display = new Display();
        if (args.length == 0) {
            ClipboardClient.run();
        } else if (args[0].equals("server")) {
            ClipboardServer.run();
        }
//        display.asyncExec(() -> {
//            String cmd = args[0];
//            if (cmd.equals("get")) {
//                String text = null;
//                text = getClipboardText();
//                System.out.println(text);
//            } else if (cmd.equals("set")) {
//                String text = args[1];
//                try {
//                    setClipboardText(text);
//                } catch (InterruptedException ignored) {
//                }
//            }
//            exit = true;
//        });
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

    private static void setClipboardText(String text) throws InterruptedException {
        Clipboard clipboard = new Clipboard(display);
        clipboard.setContents(new Object[]{text}, new Transfer[]{TextTransfer.getInstance()});
        for (int i = 0; i < 50; i++) {
            if (!display.readAndDispatch()) {
                Thread.sleep(100);
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
