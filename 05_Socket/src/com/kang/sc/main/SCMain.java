package com.kang.sc.main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;

// Client
public class SCMain {
	public static void main(String[] args) {
		try {
			Socket s = new Socket("192.168.0.81", 6548);
			System.out.println("Connected");

			InputStream is = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			OutputStream os = s.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);

			Scanner k = new Scanner(System.in);

			JFrame jf = new JFrame("mt");
			JTextArea jta = new JTextArea();
			jf.add(jta);
			jf.setSize(300, 300);
			jf.setVisible(true);

			// Receive
			new Thread() {
				public void run() {
					while (true) {
						try {
							jta.append(br.readLine() + "\r\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
			}.start();

			// Send
			while (true) {
				System.out.print("What to send : ");
				String cmt = k.next();
				bw.write("Me)" + cmt + "\r\n");
				bw.flush();
				System.out.println(bw);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
