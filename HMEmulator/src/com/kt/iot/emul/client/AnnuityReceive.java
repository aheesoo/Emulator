package com.kt.iot.emul.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


/*
 * 2015-01-14 김봉수
 * 개발목적 : 호스트에서 개발원과의 수신속도가 나오지 않아서 파일 수신 전문만 개발함
 * 특이사항 : 결번요청에 대해서는 테스트 불가로 결번은 없다는 가정하에 처리함 (전문을 한번씩 읽을때마다 1씩 붙여줌)
 *         개발원쪽으로 결번요청을 하게 되면 결번 데이터(0310/000)는 정상적으로 받게끔은 처리했음(강제적으로 결번을 요청하여 정상처리됨을 확인함)
 *         0310/000 전문은 한 블럭당 한번만 받게끔 되어있음 ( 3100/000 전문을 받고 다시 결번데이터를 요청하지 않고 다음 블럭  데이터를 받음 )
 *         
 *
 * 
 * AI9122 : 일 동의자정보 전송(철회포함)  전날 데이터를 04~07 시에 받아야함
 * AJ9122 : 월 동의자정보 전송(철회포함)  전달 데이터를 04~07 시에 받아야함
 * 
 * 
 *                 전문 흐름도
 * 1. (보험사->개발원)개시요청(지시) 0600/001                                                               
 * 2. (개발원->보험사)개시 응답 0610/001                                                                  
 *   2.1 (개발원->보험사)파일정보 수신 요청 0630/000                                                          
 *   2.2 (보험사->개발원)파일정보 수신 응답 0640/000                                                          
 *                                                                                              
 *      2.2.1 (개발원->보험사) 데이터 레코드 0320/000 (한 블럭당 최대 100개 sequence)                              
 *      2.2.2 (개발원->보험사) 결번확인요청 0620/000 (블럭당 최대 sequence 가 끝나면 바로 결번확인요청이 추가로 들어옴)             
 *      2.2.3 (보험사->개발원) 결번확인응답 0300/000 + Skip_bit                                             
 *                                                                                              
 *  //결번 데이터가 있을경우 데이터를 다 받고   2.2.2 부터 다시 시작                                                
 *  2.2.3.1 (개발원->보험사) 결번 데이터 0310/000 (결번확인응답의 결번갯수만큼)                                      
 *                                                                                              
 *                                                                                              
 * 3 (개발원->보험사) 개별업무 종료 0600/003                                                                
 * 4 (보험사->개발원) 개별업무 종료 응답 0610/003                                                             
 * 5 (개발원->보험사) 업무 종료 0600/004                                                                  
 * 6 (보험사->개발원) 업무 종료 응답 0610/004
 *                                                                
*/

public class AnnuityReceive {
 
 //운영서버 , 테스트서버 여부
 private static boolean isRealServer = true;
 
 //개발원 소켓 정보
 //개발 : 192.10.10.100:29290  /  운영 : 192.10.10.110:21290
 private static String ip = (true) ? "192.10.10.110" : "192.10.10.100";
 private static int port = (true) ? 21290 : 29290;
 
 //진생상태값을 저장하는 환경변수 파일 - 파일명은 args 로 받은 날짜로 사용
 private static String config_File_path = "";

 //호스트에 전송시킬 파일을 경로 - 파일명은 args 로 받은 날짜로 사용
 private static String ftp_File_path = "";
 
 public static void main(String[] args) {
  
  
  AnnuityReceive socket = new AnnuityReceive();
  
  long lStartTime     = 0;  // 프로세스 시작 시간 
        long lEndTime       = 0;  // 프로세스 종료 시간
        long lDurationTime  = 0;  // 프로세스 총 수행 시간 (msec)
        
  try {
   doLog("----------------------------------------------------------------------");
   doLog("실행 시작 : " + getDate("yyyy.MM.dd HH:mm:ss"));
   doLog("----------------------------------------------------------------------");
   lStartTime = System.currentTimeMillis(); // 프로세스 시작시간
   
    //개발원으로부터 데이터를 받아온다.
    socket.handle();
    
  } catch (Exception e) {
   //에러를 로그로 남기기위해서 처리
   System.out.println(getStackTrace(e));
   //e.printStackTrace();
  } finally {
   lEndTime = System.currentTimeMillis();   // 작업 완료 시간을 기록
            lDurationTime = lEndTime - lStartTime;   // 작업 총 소요시간 (단위 : 밀리세컨즈(ms))
   doLog("----------------------------------------------------------------------");
   doLog("실행 시간 : " + (lDurationTime/1000) + "초");
   doLog("실행 종료 : " + getDate("yyyy.MM.dd HH:mm:ss"));
   doLog("----------------------------------------------------------------------");
  }
 }
 
 
 /*
  * 개발원으로부터 데이터를 받는다.
 */
 public void handle() throws Exception {
  
  doLog("");
  doLog("");
  doLog("-----------------------------------  전문 시작  " + "-----------------------------------");
  doLog("개발원 IP : " + ip);
  doLog("개발원 port : " + port);
  
  FileWriter fw = null;
  
  //공통해더부분 고정 필드값
  String comm_001 = "";   //1> TRANSACTION CODE
//  String comm_002 = "";   //2> TCP/IP 송신 Byte
  String comm_003 = "YIP";  //3> 업무구분코드
//  String comm_004 = "";   //4> 전문종별코드
//  String comm_005 = "";   //5> 업무관리정보
  String comm_006 = "N04";  //6> 기관 코드
  String comm_007 = "R";   //7> 송수신 구분
  String comm_008 = "";   //8> 송수신 FLAG(미사용)
  String comm_009 = "";  //9> 거래구분코드
  String comm_010 = "000";  //10> 응답코드
  String comm_011 = ""; //11> 전문전송일자
  
  //전문 업무별로 필요할때 전문종별코드 / 업무관리정보 받아서 사용하자
  String code1 = ""; //전문종별코드
  String code2 = ""; //업무관리정보
  
  StringBuffer sb = new StringBuffer();
  byte[] sendTxt = null;
  byte[] ret = null;
  
  Socket clientSocket = null;
  OutputStream output = null;
  InputStream input = null;

  
  //소켓 timeout 시간  개발원쪽에서 60초로 설정되어있음
//  int timeout = 60000;

  try {
   //소켓연결
   clientSocket = new Socket(ip, port);
   
   //timeout 설정 
//   clientSocket.setSoTimeout(timeout);
   
   //input , output 생성
   input = clientSocket.getInputStream();
   output = clientSocket.getOutputStream();
   
   
   
   //보험사 -> 개발원
   //개시전문 요청(0600_001)
   sb.setLength(0);
   sb.append(fillSpace (comm_001, 9)); //1> TRANSACTION CODE
   sb.append(fillZero ("84", 7));  //2> TCP/IP 송신 Byte
   sb.append(fillSpace (comm_003, 3)); //3> 업무구분코드
   sb.append(fillSpace ("0600", 4)); //4> 전문종별코드
   sb.append(fillSpace ("001", 3)); //5> 업무관리정보
   sb.append(fillSpace (comm_006, 3)); //6> 기관 코드
   sb.append(fillSpace (comm_007, 1)); //7> 송수신 구분
   sb.append(fillSpace (comm_008, 1)); //8> 송수신 FLAG(미사용)
   sb.append(fillSpace (comm_009, 6)); //9> 거래구분코드
   sb.append(fillZero (comm_010, 3)); //10> 응답코드
   sb.append(fillZero (comm_011, 8)); //11> 전문전송일자
   sb.append(fillSpace ("", 20));  //12> 송신자명
   sb.append(fillSpace ("", 16));  //13> 송신자암호
   
   //String -> byte
   sendTxt = sb.toString().getBytes();
   doLog("보험사 -> 개발원 - 개시전문 요청(0600_001) [" + new String(sendTxt) + "]");
   
   //개발원 전송
   output.write(sendTxt);
   output.flush();
   
   
   //개발원 -> 보험사
   //개시전문 응답(0610_001)
   ret = getReadLine(input);
   doLog("개발원 -> 보험사 - 개시전문 응답(0610_001) [" + new String(ret) + "]");


   //파일정보 수신 요청  - 업무개시 이후 바로 업무종료지시(0600/004) 전문이 도착하면 수신할 전문이 존재하지 않는다는 의미
   //(0630_000) 개발원 -> 보험사   받을 파일이 존재
   //(0600_004) 개발원 -> 보험사   받을 파일이 존재하지 않음 종료처리
   ret = getReadLine(input);
   doLog("개발원 -> 보험사 - 파일정보 수신 요청 [" + new String(ret) + "]");
   
   code1 = new String(ret , 19, 4);
   code2 = new String(ret , 23, 3);
   if( (("0630").equals(code1) && ("000").equals(code2)) == false ){
    doLog("받을 파일이 존재하지 않음 종료처리");
    doLog("-----------------------------------  전문 끝  " + "-----------------------------------");
    return;
   }
   
   //보험사 -> 개발원
   //파일정보 수신 응답(0640_000)
   sb.setLength(0);
   sb.append(fillSpace (comm_001, 9)); //1> TRANSACTION CODE
   sb.append(fillZero ("84", 7));  //2> TCP/IP 송신 Byte
   sb.append(fillSpace (comm_003, 3)); //3> 업무구분코드
   sb.append(fillSpace ("0640", 4)); //4> 전문종별코드
   sb.append(fillSpace ("000", 3)); //5> 업무관리정보
   sb.append(fillSpace (comm_006, 3)); //6> 기관 코드
   sb.append(fillSpace (comm_007, 1)); //7> 송수신 구분
   sb.append(fillSpace (comm_008, 1)); //8> 송수신 FLAG(미사용)
   sb.append(fillSpace (comm_009, 6)); //9> 거래구분코드
   sb.append(fillZero (comm_010, 3)); //10> 응답코드
   sb.append(fillZero (comm_011, 8)); //11> 전문전송일자
   sb.append(fillSpace ("", 20));  //12> 송신자명
   sb.append(fillSpace ("", 16));  //13> 송신자암호
   
   //String -> byte
   sendTxt = sb.toString().getBytes();
   doLog("보험사 -> 개발원 - 파일정보 수신 응답(0640_000) [" + new String(sendTxt) + "]");
   
   //개발원 전송
   output.write(sendTxt);
   output.flush();
   
   
   //개발원 -> 보험사
   //데이터 레코드(0320_000)
   int block_no = 0; //현재 블럭
   int notVaildCnt = 0; //결번확인 (결번 갯수)
   int finalSeq = 0; //최종 시퀀스 번호
   String notVaildField = ""; //결번필드 (1:정상 , 0:결번);
   String dataGB = ""; //(11:공통 Header 부  / 22: 실 Data 부  / 33 : 공통 Trailer 부)
   
   boolean isNotVaildData = false; //현재 받고 있는 전문이 결번 데이터인지...
   
   
   //do_while : 0600_003(개별업무종료) 전문을 받을때까지 무한루프
   //do_while 안에 while : 블럭당 반복문 0620_000(결번확인요청) 전문을 받을때까지 무한루프
   //실 Data 부만 호스트용 파일로 write 한다.
   fw = new FileWriter(new File(ftp_File_path));
   do {
    //반복문으로 인하여  초기화후 사용
    notVaildField = "";
    notVaildCnt = 0;
    
    //전문을 먼저 읽는다.
    ret = getReadLine(input);
    System.out.println();
    doLog("개발원 -> 보험사 - 데이터 레코드 0320/000 (한 블럭당 최대 100개 sequence) , 0600/003 응답을 받을때까지");
    System.out.print(new String(ret));
    
    //전문을 한번씩 읽을때마다 1씩 붙여줌
    notVaildField += "1";
    
    code1 = new String(ret , 19, 4);
    code2 = new String(ret , 23, 3);
    if("0600".equals(code1) && "003".equals(code2)){
     //개발업무 종료를 만났으므로 do_while 빠져나옴
     break;
    }
    
    //실 Data 파일로 작성
    dataGB = new String(ret , 70, 2);
    if("22".equals(dataGB)){
     fw.write(new String(ret , "KSC5601"));
    }
    
    while( true ){
     
     //데이터 레코드(0320_000) 개발원 -> 보험사
     ret = getReadLine(input);
     System.out.print(new String(ret));
     block_no = Integer.parseInt(new String(ret , 48, 4));
     code1 = new String(ret , 19, 4);
     code2 = new String(ret , 23, 3);
     
     if("0310".equals(code1) && "000".equals(code2)){
      //현재 결번데이터를 받고 있으므로 isNotVaildData 를 true로 변경한다.
      isNotVaildData = true;
     }
     

     if("0620".equals(code1) && "000".equals(code2)){
      //결번확인요청을 만났으므로 현재블럭의 데이터를 모두 받았다. (결번데이터 포함)
      
      //최종 시퀀스 번호를 받는다.
      finalSeq = Integer.parseInt(new String(ret , 52, 5));
      
      if(isNotVaildData == true){
       //현재 받고 있는 데이터가  결번 데이터라면!!
       //전문에서 받은 최종시퀀스의 갯수만큼 셋팅
       notVaildField = "";
       for(int i=0; i<finalSeq; i++){
        notVaildField += "1";
       }
       //결번데이터 수신이 완료되었으므로 초기화
       isNotVaildData = false;
      }
      
      //while 문을 빠져나와서 개발원으로 결번확인응답(0300_000) 전송
      break;
     }
     
     //실 Data 파일로 작성
     dataGB = new String(ret , 70, 2);
     if("22".equals(dataGB)){
      fw.write(new String(ret , "KSC5601"));
     }
     
     //전문을 한번씩 읽을때마다 1씩 붙여줌
     notVaildField += "1";
     
    }
    
    //결번갯수 체크
    char[] cArray = notVaildField.toCharArray();
    for(char c : cArray){
     if(c == '0'){
      notVaildCnt++;
     }
    }
    
    //보험사 -> 개발원
    //결번확인응답(0300_000) 
    sb.setLength(0);
    sb.append(fillSpace (comm_001, 9)); //1> TRANSACTION CODE
    sb.append(fillZero ("162", 7)); //2> TCP/IP 송신 Byte
    sb.append(fillSpace (comm_003, 3)); //3> 업무구분코드
    sb.append(fillSpace ("0300", 4)); //4> 전문종별코드
    sb.append(fillSpace ("000", 3)); //5> 업무관리정보
    sb.append(fillSpace (comm_006, 3)); //6> 기관 코드
    sb.append(fillSpace (comm_007, 1)); //7> 송수신 구분
    sb.append(fillSpace (comm_008, 1)); //8> 송수신 FLAG(미사용)
    sb.append(fillSpace (comm_009, 6)); //9> 거래구분코드
    sb.append(fillZero (comm_010, 3)); //10> 응답코드
    sb.append(fillZero (comm_011, 8)); //11> 전문전송일자
    sb.append(fillZero (String.valueOf(block_no), 4));     //12> BLOCK-NO
    sb.append(fillZero (String.valueOf(finalSeq), 5));     //13> 최종 SEQUENCE - NO
    sb.append(fillZero (String.valueOf(notVaildCnt), 5));    //14> 결번갯수
    sb.append(fillSpace (notVaildField, 100));       //15> 결번확인 Field
    
    //String -> byte
    sendTxt = sb.toString().getBytes();
    
    System.out.println();
    System.out.println();
    doLog("보험사 -> 개발원 - 결번확인응답(0300_000) [" + sb.toString() + "]");
    System.out.println();
    
    //개발원 전송
    output.write(sendTxt);
    output.flush();
    
   } while ( true );   
   
   //보험사 -> 개발원
   //개별업무 종료 응답(0610_003)
   sb.setLength(0);
   sb.append(fillSpace (comm_001, 9)); //1> TRANSACTION CODE
   sb.append(fillZero ("84", 7));  //2> TCP/IP 송신 Byte
   sb.append(fillSpace (comm_003, 3)); //3> 업무구분코드
   sb.append(fillSpace ("0610", 4)); //4> 전문종별코드
   sb.append(fillSpace ("003", 3)); //5> 업무관리정보
   sb.append(fillSpace (comm_006, 3)); //6> 기관 코드
   sb.append(fillSpace (comm_007, 1)); //7> 송수신 구분
   sb.append(fillSpace (comm_008, 1)); //8> 송수신 FLAG(미사용)
   sb.append(fillSpace (comm_009, 6)); //9> 거래구분코드
   sb.append(fillZero (comm_010, 3)); //10> 응답코드
   sb.append(fillZero (comm_011, 8)); //11> 전문전송일자
   sb.append(fillSpace("", 20));  //12> 송신자명
   sb.append(fillSpace("", 16));  //13> 송신자암호
   
   //String -> byte
   sendTxt = sb.toString().getBytes();
   System.out.println();
   System.out.println();
   doLog("보험사 -> 개발원 - 개별업무 종료 응답(0610_003) [" + sb.toString() + "]");
   
   //개발원 전송
   output.write(sendTxt);
   output.flush();
   
   
   //개발원 -> 보험사
   //업무 종료(0600_004)
   ret = getReadLine(input);
   System.out.println();
   doLog("개발원 -> 보험사 - 업무 종료(0600_004) [" + new String(ret) + "]");
   
   
   //보험사 -> 개발원
   //개별업무 종료 응답(0610_004)
   sb.setLength(0);
   sb.append(fillSpace (comm_001, 9)); //1> TRANSACTION CODE
   sb.append(fillZero ("84", 7));  //2> TCP/IP 송신 Byte
   sb.append(fillSpace (comm_003, 3)); //3> 업무구분코드
   sb.append(fillSpace ("0610", 4)); //4> 전문종별코드
   sb.append(fillSpace ("004", 3)); //5> 업무관리정보
   sb.append(fillSpace (comm_006, 3)); //6> 기관 코드
   sb.append(fillSpace (comm_007, 1)); //7> 송수신 구분
   sb.append(fillSpace (comm_008, 1)); //8> 송수신 FLAG(미사용)
   sb.append(fillSpace (comm_009, 6)); //9> 거래구분코드
   sb.append(fillZero (comm_010, 3)); //10> 응답코드
   sb.append(fillZero (comm_011, 8)); //11> 전문전송일자
   sb.append(fillSpace("", 20));  //12> 송신자명
   sb.append(fillSpace("", 16));  //13> 송신자암호
   
   //String -> byte
   sendTxt = sb.toString().getBytes();
   System.out.println();
   doLog("보험사 -> 개발원 - 개별업무 종료 응답(0610_004) [" + sb.toString() + "]");
   
   //개발원 전송
   output.write(sendTxt);
   output.flush();
   
   doLog("----------------------------------- 전문 끝  " + "-----------------------------------");
   
  } catch (Exception e) {
   
   throw e;
   
  } finally {
   try {
    if( fw != null ) {
     fw.close();
    }
    if( output != null ) {
     output.close();
    }
    if( input != null ) {
     input.close();
    }
    if ( clientSocket != null ) {
     clientSocket.close();
    }
   } catch ( Exception e ) {
    e.printStackTrace();
    throw e;
   }
  }
 }
 
 
 /*
  * 소켓으로부터 전문의 사이즈를 먼저 읽고 , 나머지부분을 읽는다.
  * startOffset = 전문사이즈의 위치
  * lengthSize = 전문사이즈의 길이
  */
 public synchronized static byte[] getReadLine(InputStream input) throws Exception{
  
  int startOffset = 9;
  int lengthSize = 7;
  
  byte[] startArr = new byte[startOffset];
  byte[] sizeArr = new byte[lengthSize];
  int readOffsetSize = 0;
  int totalOffsetRead = 0;

  do {
   readOffsetSize = input.read( startArr, totalOffsetRead, startOffset - totalOffsetRead );
   totalOffsetRead += readOffsetSize;
  } while ( totalOffsetRead < startOffset && readOffsetSize != -1 );
  
  
  int readHeaderSize = 0;
  int totalHeaderRead = 0;
  do {
   readHeaderSize = input.read( sizeArr, totalHeaderRead, lengthSize - totalHeaderRead );
   totalHeaderRead += readHeaderSize;
  } while ( totalHeaderRead < lengthSize && readHeaderSize != -1 );
  
  int packetSize = 0;
  int readSize = 0;
  int totalRead = totalOffsetRead + totalHeaderRead;
  packetSize = Integer.parseInt( new String( sizeArr ) );
  
  byte[] ret = new byte[packetSize];
  System.arraycopy( startArr, 0, ret, 0, startArr.length );
  System.arraycopy( sizeArr, 0, ret, startArr.length, sizeArr.length );
  
  do {
   readSize = input.read( ret, totalRead, packetSize - totalRead );
   totalRead += readSize;
  } while ( totalRead < packetSize && readSize != -1 );
  
  return ret;
 }
 
 
 /*
  * 공백으로 채우기
  */
 public static String fillSpace(String instr, int strlen) {
  int comlen = 0;
  int filllen = 0;
  if (instr != null) {
   comlen = instr.getBytes().length;
   if (strlen > comlen) {
    filllen = strlen - comlen;
    for (int i = 0; i < filllen; i++) {
     instr += " ";
    }
   } else {
    try {
     instr = new String(instr.getBytes(), 0, strlen, "KSC5601");
    } catch (Exception e) {
     e.printStackTrace();
    }
   }
  } else {
   instr = space(strlen);
  }
  return instr;
 }

 public static String space(int n) {
  String strTmp = "";
  for (int i = 0; i < n; i++) {
   strTmp += " ";
  }
  return strTmp;
 }

 /*
  * "0" 으로 채우기
  */
 public static String fillZero(String str, int iLen) throws Exception {
  String strZero = "";

  try {
   int size = iLen - str.getBytes().length;

   for (int i = 0; i < size; i++) {
    strZero += "0";
   }

  } catch (Exception e) {
   e.printStackTrace();
  } finally {
  }

  return strZero + str;
 }

 /*
  * 로그남기기
  */
 public static void doLog(Object message) {
  System.out.println(getDate("yyyy.MM.dd HH:mm:ss") + "[" + message.toString() + "]");
 }

 /*
  * 현재일자 가져오기
  */
 public static String getDate(String format) {
  SimpleDateFormat formatter = new SimpleDateFormat(format);
  Date xDate = new Date();
  String xStrDate = formatter.format(xDate);
  return (xStrDate.trim());
 }

 /*
  * 현재일자 가져오기
  */
 public static String getDate() {
  return getDate("yyyyMMdd");
 }
 
 /*
  * 대상날짜를 기준으로 몇일전 , 몇일후 날짜 가져오기
  */
 public static String addDate(String strDate, int addDate) {
  Calendar calendar = Calendar.getInstance();
  Date baseDate = convertStringToDate(strDate , "yyyyMMdd");
  calendar.setTime(baseDate);
  calendar.add(5, addDate);

  Date addResult = new Date(calendar.getTimeInMillis());
  
  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
  String xStrDate = formatter.format(addResult);
  return (xStrDate.trim());
 }
 
 /*
  * 한달전 마지막날짜 가져오기
  */
 public static String getLastDateOfBeforeMonth(String strDate) {
  Calendar calendar = Calendar.getInstance();
  Date baseDate = convertStringToDate(strDate , "yyyyMMdd");
  calendar.setTime(baseDate);
  calendar.add(5, -1 * calendar.get(5));

  Date lastDateOfLastMonth = new java.sql.Date(calendar.getTimeInMillis());

  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
  String xStrDate = formatter.format(lastDateOfLastMonth);
  return (xStrDate.trim());

 }
 
 /*
  * String -> Date 로 변환
  */
 public static Date convertStringToDate(String strDate, String format) {
  if ((strDate == null) || (strDate.length() == 0)) {
   return null;
  }
  if (strDate.length() != format.length()) {
   return null;
  }
  try {
   SimpleDateFormat fmt = new SimpleDateFormat(format);
   return fmt.parse(strDate, new ParsePosition(0));
  } catch (Exception e) {
  }
  return null;
 }
 
 
 /*
  * StackTrace를 출력
  */
 public static String getStackTrace(Throwable e) {
  StackTraceElement[] st = e.getStackTrace();
  StringBuffer ret = new StringBuffer();
  ret.append(e.toString()).append("\n");
  for (int i = 0; i < st.length; i++) {
   if (st[i].getFileName() == null) {
    ret.append("    at ").append(st[i].getClassName()).append(".")
      .append(st[i].getMethodName()).append("\n");
   } else {
    ret.append("    at ").append(st[i].getClassName()).append(".")
      .append(st[i].getMethodName()).append("(")
      .append(st[i].getFileName()).append(":")
      .append(st[i].getLineNumber()).append(")\n");
   }
  }

  Throwable t = e.getCause();
  while (t != null) {
   StackTraceElement[] st1 = t.getStackTrace();
   ret.append("Caused by: ");
   for (int i = 0; i < st1.length; i++) {
    if (st1[i].getFileName() == null) {
     ret.append("    at ").append(st1[i].getClassName())
       .append(".").append(st1[i].getMethodName())
       .append("\n");
    } else {
     ret.append("    at ").append(st1[i].getClassName())
       .append(".").append(st1[i].getMethodName())
       .append("(").append(st1[i].getFileName())
       .append(":").append(st1[i].getLineNumber())
       .append(")\n");
    }
   }
   t = t.getCause();
  }
  return ret.substring(0);
 }
}
