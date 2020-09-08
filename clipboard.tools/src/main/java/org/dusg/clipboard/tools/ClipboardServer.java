package org.dusg.clipboard.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClipboardServer {
    public static void run() {
        new Thread(() -> {
            try {
                new ClipboardServer().start();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void start() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(18861);
        System.out.println("启动服务器....");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("客户端:" + socket.getInetAddress() + "已连接到服务器");
            Thread thread = new Thread(() -> serverClient(socket));
            thread.start();
            Thread.sleep(500);
        }
    }

    private void serverClient(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));) {
            while (true) {
                CmdPackage cmdPackage = readCmdPackage(reader);
                if (cmdPackage == null) {
                    continue;
                }

                if (cmdPackage.getFunc().equals("get")) {
                    sendResult(writer, ClipboardUtils.getClipboardContend());
                } else if (cmdPackage.getFunc().equals("set")) {
                    ClipboardUtils.setClipboardContend(cmdPackage.getArg());
                    System.out.println("update clipboard: " + cmdPackage.getArg());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResult(BufferedWriter writer, String text) throws IOException {
        Gson gson = new GsonBuilder().create();
        ResultPackage resultPackage = new ResultPackage();
        resultPackage.setResult(text);
        writer.write(gson.toJson(resultPackage));
    }

    private CmdPackage readCmdPackage(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line.isEmpty()) {
            return null;
        }
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(reader, CmdPackage.class);
    }

    public static class ResultPackage {
        private String result;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    static class CmdPackage {
        private String func;
        private String arg;

        public String getFunc() {
            return func;
        }

        public void setFunc(String func) {
            this.func = func;
        }

        public String getArg() {
            return arg;
        }

        public void setArg(String arg) {
            this.arg = arg;
        }
    }

}
