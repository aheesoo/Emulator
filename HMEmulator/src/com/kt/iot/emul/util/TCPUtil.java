package com.kt.iot.emul.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * TCP 관련 유틸
 * @author	: chu byung jo(cbj38317@hyundai-uni.com)
 * @version	: 1.0
 * @date	: 2011. 11. 2.
 */
public class TCPUtil
{
	/**
	 * TCP 프로토콜을 이용하여 데이터를 전송한다.
	 * @param IP	: 목적지 IP
	 * @param port	: 목적지 port
	 * @param strSendData	: 전송할 데이터
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void sendData(String IP, int port, String sendData, int timeout) throws UnknownHostException, IOException
	{
		Socket socket = null;
		OutputStreamWriter outputStreamWriter = null;

		try
		{
			socket = new Socket();
			SocketAddress addr = new InetSocketAddress(IP, port);
			//특정 시간동안 연결이 안 될 경우 timeout이 발생
			socket.connect(addr, timeout);
			outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
			outputStreamWriter.write(sendData);

		}
		catch(UnknownHostException e)
		{
			throw e;
		}
		catch(IOException e)
		{
			throw e;
		}
		finally
		{
			if(outputStreamWriter != null)
			{
				outputStreamWriter.flush();
				outputStreamWriter.close();
			}
			if(socket != null)
			{
				socket.close();
			}
		}
	}

	/**
	 * TCP 프로토콜을 이용하여 데이터를 전송한다.
	 * @param IP	: 목적지 IP
	 * @param port	: 목적지 port
	 * @param sendData	: 전송할 데이터
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void sendData(String IP, int port, byte[] sendData, int timeout) throws UnknownHostException, IOException
	{
		Socket socket = null;
		OutputStream outputStream = null;

		try
		{
			socket = new Socket();
			SocketAddress addr = new InetSocketAddress(IP, port);
			//특정 시간동안 연결이 안 될 경우 timeout이 발생
			socket.connect(addr, timeout);
			outputStream = socket.getOutputStream();
			outputStream.write(sendData);
		}
		catch(UnknownHostException e)
		{
			throw e;
		}
		catch(IOException e)
		{
			throw e;
		}
		finally
		{
			if(outputStream != null)
			{
				outputStream.flush();
				outputStream.close();
			}
			if(socket != null)
			{
				socket.close();
			}
		}
	}

	/**
	 * TCP 프로토콜을 이용하여 데이터를 전송 후 수신한다.
	 * @param IP	: 목적지 IP
	 * @param port	: 목적지 port
	 * @param sendData	: 전송할 데이터
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static byte[] sendAndRecv(String IP, int port, byte[] sendData, int timeout, int maxRecvLength) throws UnknownHostException, IOException
	{
		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputStream = null;

		if(maxRecvLength <= 0)
		{
			maxRecvLength = 4096;
		}

		byte[] buffer = new byte[maxRecvLength];

		try
		{
			socket = new Socket();
			SocketAddress addr = new InetSocketAddress(IP, port);
			//특정 시간동안 연결이 안 될 경우 timeout이 발생
			socket.connect(addr, timeout);
			//접속 후에 read 하는 동안 특정한 시작동안 packet이 없을 경우 연결을 끊음
			socket.setSoTimeout(timeout);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			outputStream.write(sendData);

			int offset = 0;
		    int wanted = maxRecvLength;
		    int len = 0;
		    int totlen = 0;

			while( wanted > 0 )
		    {
		    	try{
		    		len = inputStream.read( buffer, offset, wanted );
			        if( len <= 0)
			        {
			        	break;
			        }
		    	}catch(Exception e)
		    	{
		    		break;
		    	}
		        wanted -= len;
		        offset += len;
		        totlen += len;
		    }

	        byte[] result = new byte[totlen];
	    	System.arraycopy(buffer, 0, result, 0, totlen );
	    	return result;
		}
		catch(UnknownHostException e)
		{
			throw e;
		}
		catch(IOException e)
		{
			throw e;
		}
		finally
		{
			if(inputStream != null)
			{
				inputStream.close();
			}
			if(outputStream != null)
			{
				outputStream.flush();
				outputStream.close();
			}
			if(socket != null)
			{
				socket.close();
			}
		}
	}

	/**
	 * URI를
	 * @param strURI	: URI ex)10.12.13.14:2031
	 * @return	: Object[2] 배열, Object[0]-->IP, Object[1]-->Port
	 */
	public static Object[] URIToAddress(String strURI)
	{
		Object[] tcpAddress = new Object[2];

		String[] URL = strURI.split(":");
		if(URL.length != 2)
		{
			//logger.warn("destURL error. destURL = "+destURL);
			return null;
		}

		InetAddress nowInetAddress = null;
		try
		{

			nowInetAddress  = InetAddress.getByName(URL[0]);
		}
		catch(UnknownHostException e)
		{
			//logger.warn("ip error. ip = "+URL[0]);
			return null;
		}

		int port = 0;
		try
		{

			port = Integer.parseInt(URL[1]);
		}
		catch(Exception e)
		{
			//logger.warn("port parseInt error.  port = "+URL[1]);
			return null;
		}
		tcpAddress[0] = nowInetAddress.getHostAddress();
		tcpAddress[1] = new Integer(port);
		return tcpAddress;
	}

	public static boolean isTCPAddress(String strURI)
	{
		String[] URL = strURI.split(":");
		if(URL.length != 2)
		{
			//logger.warn("destURL error. destURL = "+destURL);
			return false;
		}

		try
		{
			@SuppressWarnings("unused")
			InetAddress nowInetAddress = null;
			nowInetAddress  = InetAddress.getByName(URL[0]);
		}
		catch(UnknownHostException e)
		{
			//logger.warn("ip error. ip = "+URL[0]);
			return false;
		}


		try
		{
			@SuppressWarnings("unused")
			int port = 0;
			port = Integer.parseInt(URL[1]);
		}
		catch(Exception e)
		{
			//logger.warn("port parseInt error.  port = "+URL[1]);
			return false;
		}
		return true;
	}
}
