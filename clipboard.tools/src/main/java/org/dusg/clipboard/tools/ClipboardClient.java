package org.dusg.clipboard.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.Socket;

public class ClipboardClient {
    private String lastClipboard;

    public static void run() {
        new Thread(()->{
            try {
                new ClipboardClient().start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.exit(1);
        }).start();
    }

    private void start() throws IOException, InterruptedException {
        Socket socket = new Socket("10.8.52.67",18861);
        System.out.println("connect ot server " + socket.getInetAddress());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));) {
            while (true) {
                String text = readRemoteClipboard(reader, writer);
                if (!text.equals(lastClipboard)) {
                    ClipboardUtils.setClipboardContend(text);
                    lastClipboard = text;
                    System.out.println("update local clipboard: " + text);
                }

                String clipboardContend = ClipboardUtils.getClipboardContend();
                if (!clipboardContend.isEmpty() && !clipboardContend.equals(lastClipboard)) {
                    updateRemote(writer, clipboardContend);
                }
                Thread.sleep(500);
            }
        }
    }

    private void updateRemote(BufferedWriter writer, String clipboardContend) throws IOException {
        Gson gson = new GsonBuilder().create();
        ClipboardServer.CmdPackage cmdPackage = new ClipboardServer.CmdPackage();
        cmdPackage.setFunc("set");
        cmdPackage.setArg(clipboardContend);
        writer.write(gson.toJson(cmdPackage));
        writer.newLine();
        writer.flush();
    }

    private String readRemoteClipboard(BufferedReader reader, BufferedWriter writer) throws IOException {
        Gson gson = new GsonBuilder().create();
        ClipboardServer.CmdPackage cmdPackage = new ClipboardServer.CmdPackage();
        cmdPackage.setFunc("get");
        writer.write(gson.toJson(cmdPackage));
        writer.newLine();
        writer.flush();

        String line = reader.readLine();
        ClipboardServer.ResultPackage resultPackage = gson.fromJson(line, ClipboardServer.ResultPackage.class);
        if (resultPackage == null) {
            return "";
        }
        return resultPackage.getResult();
    }
}
