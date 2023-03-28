package org.example.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {

    private final static Logger log = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);


    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            int _byte = inputStream.read();

            while ((_byte = inputStream.read()) >= 0) {
                System.out.print((char) _byte);

            }

            String html = "<html><head><title>Simple Java HTTP Server</title><body><h1>This page was served using my Simple Java HTTP Server</h1></body></head></html>";

            final String CRLF = "\n\r"; // 13, 10 ASCII

            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status Line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF + // HEADER
                            CRLF +
                            html +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());



            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("Connection Processing Finished...");

        } catch (IOException e) {
            log.info("Problem with communication", e);
            e.printStackTrace();
        } finally {
            if (inputStream != null ) {
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
            if (outputStream != null ) {
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
            if (socket != null ) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }





        }
    }
}

