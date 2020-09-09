package org.dusg.clipboard.tools;

import org.eclipse.swt.widgets.Display;


public class Main {
    private static boolean exit = false;
    private static Display display;

    public static void main(String[] args) {
        display = new Display();
        if (args.length == 0) {
            ClipboardClient.run();
        } else if (args[0].equals("server")) {
            ClipboardServer.run();
        }
        while (!exit) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

}
