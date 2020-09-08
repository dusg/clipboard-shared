package org.dusg.clipboard.tools;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

import java.util.concurrent.atomic.AtomicReference;

public class ClipboardUtils {
    public static String getClipboardContend() {
        AtomicReference<String> ret = new AtomicReference<>();
        Display.getDefault().syncExec(() -> {
            Clipboard clipboard = new Clipboard(Display.getDefault());
            String contents = (String) clipboard.getContents(TextTransfer.getInstance());
            if (contents == null) {
                contents = "";
            }
            ret.set(contents);
            clipboard.dispose();
        });
        return ret.get();
    }

    public static void setClipboardContend(String text) {
        if (text.isEmpty()) {
            return;
        }
        Display.getDefault().asyncExec(() -> {
            Clipboard clipboard = new Clipboard(Display.getDefault());
            clipboard.setContents(new Object[]{text}, new Transfer[]{TextTransfer.getInstance()});
            clipboard.dispose();
        });
    }
}
