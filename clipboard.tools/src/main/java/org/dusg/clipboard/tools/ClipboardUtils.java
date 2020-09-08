package org.dusg.clipboard.tools;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

public class ClipboardUtils {
    public static String getClipboardContend() {
        Clipboard clipboard = new Clipboard(Display.getDefault());
        String contents = (String) clipboard.getContents(TextTransfer.getInstance());
        if (contents == null) {
            contents = "";
        }
        clipboard.dispose();
        return contents;
    }

    public static void setClipboardContend(String text) {
        Clipboard clipboard = new Clipboard(Display.getDefault());
        clipboard.setContents(new Object[]{text}, new Transfer[]{TextTransfer.getInstance()});
        clipboard.dispose();
    }
}
