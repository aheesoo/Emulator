package com.kt.iot.emul;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {



	public static void main(String[] args) {



		ServerSocket serverSocket = null; // ServerSocket

		Socket socket = null; // Socket

		InetAddress inetAddress = null; // 접속한 Client의 주소 파악

		BufferedReader br = null;

		InputStream is = null;

		OutputStream os = null;

		PrintWriter pw = null;

		int port = 9081; // port번호 지정

		String receiveMsg = null; // Client의 받은 문자열 저장




		try {

			serverSocket = new ServerSocket(port);

			System.out.println("-----접속 대기중-----");

			socket = serverSocket.accept(); // Client의 접속 대기

			inetAddress = socket.getInetAddress(); // Client의 ip 받아옴

			System.out.println(inetAddress.getHostAddress() + " 님이 접속했습니다.");

			is = socket.getInputStream(); // 입력스트림

			os = socket.getOutputStream(); // 출력스트림

			pw = new PrintWriter(new OutputStreamWriter(os));

//			pw = new PrintWriter(new OutputStreamWriter(os), true); // true-autoFlush

			br = new BufferedReader(new InputStreamReader(is));

			while ((receiveMsg = br.readLine()) != null) {

				System.out.println("Client로 부터 받은 문자열 : " + receiveMsg);

				pw.println(receiveMsg); // 받은메시지 출력

				pw.flush(); // 버퍼 비움

			}

			pw.close(); // 스트림닫기

			br.close(); // 버퍼닫기

			socket.close(); // 소켓닫기

		} catch (Exception e) { // 예외처리

			e.printStackTrace();

		}

	}

}
