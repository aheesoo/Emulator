package com.kt.iot.emul.vo;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.kt.iot.emul.code.GwCode.UseYn;
import com.kt.iot.emul.code.StdSysTcpCode.CmpreType;
import com.kt.iot.emul.code.StdSysTcpCode.EcodType;
import com.kt.iot.emul.code.StdSysTcpCode.EncdngType;
import com.kt.iot.emul.code.StdSysTcpCode.HdrType;
import com.kt.iot.emul.code.StdSysTcpCode.MsgExchPtrn;
import com.kt.iot.emul.code.StdSysTcpCode.MsgType;
import com.kt.iot.emul.code.StdSysTcpCode.MthdType;
import com.kt.iot.emul.util.ConvertUtil;


/**
 * TCP 헤더
 * @since	: 2014. 12. 4.
 * @author	: CBJ
 * <PRE>
 * Revision History
 * ----------------------------------------------------
 * 2014. 12. 4. CBJ: 최초작성
 * ----------------------------------------------------
 * </PRE>
 */
public class TcpHdrVO implements Serializable, Cloneable
{
	/** 직렬화아이디 */
	private static final long serialVersionUID = -6695332591205825668L;

	public static final int PACKET_MIN_LEGNTH = 35;
	/** 메인버전 */
	protected byte mainVer = 0x01;
	/** 서브버전 */
	protected byte subVer = 0x01;
	/** 헤더타입 */
	protected HdrType hdrType = HdrType.BASIC;
	/** 헤더길이 */
	protected Short hdrLen = PACKET_MIN_LEGNTH;

	// Message Detail
	/** 메세지타입 */
	protected MsgType msgType;
	/** 메세지교환패턴 */
	protected MsgExchPtrn msgExchPtrn = MsgExchPtrn.ONE_WAY_ACK;
	/** 메서드타입 */
	protected MthdType mthdType;
	/** Transaction ID */
	protected Long trmTransactionId;
	/** Authentication Token */
	protected UUID authTkn = null;

	// Encryption
	/** 암호화여부 */
	protected UseYn ecodUseYn = UseYn.NO;
	/** 암호화유형 */
	protected EcodType ecodType;

	// Compression
	/** 압축여부 */
	protected UseYn cmpreUseYn = UseYn.NO;
	/** 압축유형 */
	protected CmpreType cmpreType;

	// Encoding
	/** 인코딩타입 */
	protected EncdngType encdngType = EncdngType.JSON;

	/** Result Code */
	protected Short rsltCd = 0;

	// Header Extension Field
	/** 헤더확장 */
	protected List<HdrExVO> hdrExVOs = new ArrayList<HdrExVO>();

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	public TcpHdrVO()
	{

	}

	public TcpHdrVO ( byte mainVer, byte subVer, HdrType hdrType, MsgType msgType, MsgExchPtrn msgExchPtrn, MthdType mthdType,
			Long trmTransactionId, UUID authTkn, UseYn ecodUseYn, EcodType ecodType, UseYn cmpreUseYn, CmpreType cmpreType,
			EncdngType encdngType, Short rsltCd, List<HdrExVO> hdrExVOs )
	{
		this.mainVer = mainVer;
		this.subVer = subVer;
		this.hdrType = hdrType;
		this.msgType = msgType;
		this.msgExchPtrn = msgExchPtrn;
		this.mthdType = mthdType;
		this.trmTransactionId = trmTransactionId;
		this.authTkn = authTkn;
		this.ecodUseYn = ecodUseYn;
		this.ecodType = ecodType;
		this.cmpreUseYn = cmpreUseYn;
		this.cmpreType = cmpreType;
		this.encdngType = encdngType;
		this.rsltCd = rsltCd;
		this.hdrExVOs = hdrExVOs;
	}

	public static class HdrExVO
	{
		/* Key */
		private String key;
		/* Value */
		private byte[] value;

		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public byte[] getValue() {
			return value;
		}
		public void setValue(byte[] value) {
			this.value = value;
		}
	}

	public void setPacket(byte[] packet) throws Exception
	{
		if(packet == null)
		{
			throw new Exception("packet is null.");
		}

		if(packet.length < PACKET_MIN_LEGNTH)
		{
			throw new Exception("packet.length  error. packet.length = "+packet.length);
		}

		byte byte0x0F =  (byte)0x0F;
		byte byte0x03 =  (byte)0x03;
		byte byte0x01 =  (byte)0x01;
		byte byte0x7F =  (byte)0x7F;

		//1. 버전(0)
		this.mainVer = packet[0];
		this.mainVer = (byte) ((mainVer>>4));
		this.mainVer = (byte)(mainVer & byte0x0F);

		this.subVer = packet[0];
		this.subVer = (byte) ((subVer<<4));
		this.subVer = (byte) ((subVer>>4));
		this.subVer = (byte)(subVer & byte0x0F);

		//2. 헤더타입입력(1)
		this.hdrType = HdrType.fromByte(packet[1]);

		//3. 헤더길이(2~3)
		this.hdrLen =ConvertUtil.bytesToshort(packet, 2);

		//3. 메세지아이디 입력(4~5);
		byte messageType = packet[4];
		messageType = (byte) ((messageType>>6));
		messageType = (byte)(messageType & byte0x03);
		this.msgType = MsgType.fromByte(messageType);

		byte messageExchangePattern = packet[4];
		messageExchangePattern = (byte) ((messageExchangePattern>>4));
		messageExchangePattern = (byte)(messageExchangePattern & byte0x03);
		this.msgExchPtrn = MsgExchPtrn.fromByte(messageExchangePattern);

		byte[] arrMethodType = new byte[2];
		arrMethodType[0] = packet[4];
		arrMethodType[0] = (byte)(arrMethodType[0]  & byte0x0F);
		arrMethodType[1] = packet[5];
		Short methodType = ConvertUtil.bytesToshort(arrMethodType);
		System.out.println("methodType ---> "+methodType);
		if(methodType == 712){
			methodType = 711;
		}else if(methodType == 812){
			methodType = 813;
		}
		this.mthdType = MthdType.fromShort(methodType);

		//4. 전송트랜잭션ID입력(6~13)
		this.trmTransactionId = ConvertUtil.bytesTolong(packet, 6);

		//5. 채널인증토큰(14~29)
		this.authTkn = ConvertUtil.bytesToUUID(packet, 14);

		//6. 암호화정보(30)
		byte encryptUsage = packet[30];
		encryptUsage = (byte) ((encryptUsage>>7));
		encryptUsage = (byte)(encryptUsage & byte0x01);
		if(encryptUsage == 0x01)
		{
			ecodUseYn = UseYn.YES;
		}
		else
		{
			ecodUseYn = UseYn.NO;
		}

		byte encryptAlg = packet[30];
		encryptAlg = (byte) ((encryptAlg<<1));
		encryptAlg = (byte) ((encryptAlg>>1));
		encryptAlg = (byte)(encryptAlg & byte0x7F);
		this.ecodType = EcodType.fromByte(encryptAlg);

		//7. 압축정보(31)
		byte compressUsage = packet[31];
		compressUsage = (byte) ((compressUsage>>7));
		compressUsage = (byte)(compressUsage & byte0x01);
		if(compressUsage == 0x01)
		{
			cmpreUseYn = UseYn.YES;
		}
		else
		{
			cmpreUseYn = UseYn.NO;
		}
		byte compressAlg = packet[31];
		compressAlg = (byte) ((compressAlg<<1));
		compressAlg = (byte) ((compressAlg>>1));
		compressAlg = (byte)(compressAlg & byte0x7F);
		this.cmpreType = CmpreType.fromByte(compressAlg);

		//8. 인코딩정보(32)
		byte encoding = packet[32];
		encdngType = EncdngType.fromByte(encoding);

		//9. 응답코드(33~34)
		this.rsltCd = ConvertUtil.bytesToshort(packet, 33);
	}

	public byte[] toPacket() throws Exception
	{
		ByteBuffer byteBuffer = ByteBuffer.allocate(PACKET_MIN_LEGNTH);

		//1. 버전입력
		// Check Parameter Limitations
		if ( mainVer > 0x0F )
		{
			Exception exception = new Exception("Invalid Main Version. main version = " + this.mainVer);
			throw exception;
		}
		if ( subVer > 0x0F )
		{
			Exception exception = new Exception("Invalid Sub Version. main version = " + this.subVer);
			throw exception;
		}

		// Combine mainVer & subVer
		byte version = ((byte) ((mainVer<<4) | subVer));
		byteBuffer.put(version);

		//2. 헤더타입입력
		byte headerType = Byte.valueOf(hdrType.getValue());
		byteBuffer.put(headerType);

		//3. 헤더길이
		//TODO 향후 확장고려해서 업데이트
		byteBuffer.putShort((short) 35);

		//3. 메세지아이디 입력
		byte messageType = Byte.valueOf(msgType.getValue());
		byte messageExchangePattern = Byte.valueOf(msgExchPtrn.getValue());

		Short methodType = 0;
		if(mthdType != null)
		{
			methodType = Short.valueOf(mthdType.getValue());
		}

		//TODO methodType 값 체크
		byte[] arrMethodId = ConvertUtil.shortTobytes(methodType);

		byte tempOne = 0;
//		System.arraycopy(methodType, 0, tempOne, 0, 1);
		messageType = (byte) (messageType << 6);
		messageExchangePattern = (byte) (messageExchangePattern << 4);
		tempOne = (byte) (tempOne | messageType);
		tempOne = (byte) (tempOne | messageExchangePattern );

		arrMethodId[0] = (byte) (tempOne | arrMethodId[0] );
		byteBuffer.put(arrMethodId);

		//4. 전송트랜잭션ID입력
		byteBuffer.putLong(this.trmTransactionId);

		//5. 채널인증토큰
		if(authTkn == null)
		{
			byteBuffer.put(new byte[16]);
		}
		else
		{
			byteBuffer.put(ConvertUtil.UUIDToBytes(authTkn));
		}

		//6. 암호화정보
		byte encryptUsage = 0;
		if(ecodUseYn == null || ecodUseYn == UseYn.NO)
		{
			encryptUsage = 0;
		}
		else
		{
			encryptUsage = 1;
		}
		encryptUsage = (byte) (encryptUsage << 7);

		byte encryptAlg = 0;
		if(ecodType != null)
		{
			encryptAlg = Byte.valueOf(ecodType.getValue());
		}
		byte encrypt = (byte)(encryptUsage | encryptAlg );
		byteBuffer.put(encrypt);

		//7. 압축정보
		byte compressUsage = 0;
		if(cmpreUseYn == null || cmpreUseYn == UseYn.NO)
		{
			compressUsage = 0;
		}
		else
		{
			compressUsage = 1;
		}
		compressUsage = (byte) (compressUsage << 7 );

		byte compressAlg = 0;
		if(ecodType != null)
		{
			compressAlg = cmpreType.getValue();
		}
		byte compress = (byte) (compressUsage | compressAlg);
		byteBuffer.put(compress);

		byte encoding = 0;
		//8. 인코딩정보
		if(encdngType != null)
		{
			encoding = encdngType.getValue();
		}
		byteBuffer.put(encoding);

		//9. 응답코드
		if(rsltCd == null)
		{
			rsltCd = 0;
		}
		byteBuffer.putShort(rsltCd);

//		byte[] headerExtension;
//		if ( hdrExs.size() > 0)
//		{
//			Integer extensionLength = 0;
//			Integer keySize = 0;
//			Integer valueSize = 0;
//			for ( int i = 0 ; i < hdrExs.size();i++)
//			{
//				keySize = hdrExs.get(i).getKey().length();
//				extensionLength += keySize;
//				extensionLength += 1;
//				valueSize = hdrExs.get(i).getValue().length();
//				extensionLength += valueSize;
//				extensionLength += 2;
//			}
//			headerExtension = new byte[extensionLength];
//			// Put Data
//		}

		return byteBuffer.array();
	}

	public byte getMainVer() {
		return mainVer;
	}

	public void setMainVer(byte mainVer) {
		this.mainVer = mainVer;
	}

	public byte getSubVer() {
		return subVer;
	}

	public void setSubVer(byte subVer) {
		this.subVer = subVer;
	}

	public HdrType getHdrType() {
		return hdrType;
	}

	public void setHdrType(HdrType hdrType) {
		this.hdrType = hdrType;
	}

	public MsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}

	public MsgExchPtrn getMsgExchPtrn() {
		return msgExchPtrn;
	}

	public void setMsgExchPtrn(MsgExchPtrn msgExchPtrn) {
		this.msgExchPtrn = msgExchPtrn;
	}

	public MthdType getMthdType() {
		return mthdType;
	}

	public void setMthdType(MthdType mthdType) {
		this.mthdType = mthdType;
	}

	public Long getTrmTransactionId() {
		return trmTransactionId;
	}

	public void setTrmTransactionId(Long trmTransactionId) {
		this.trmTransactionId = trmTransactionId;
	}

	public UUID getAuthTkn() {
		return authTkn;
	}

	public void setAuthTkn(UUID authTkn) {
		this.authTkn = authTkn;
	}

	public UseYn getEcodUseYn() {
		return ecodUseYn;
	}

	public void setEcodUseYn(UseYn ecodUseYn) {
		this.ecodUseYn = ecodUseYn;
	}

	public EcodType getEcodType() {
		return ecodType;
	}

	public void setEcodType(EcodType ecodType) {
		this.ecodType = ecodType;
	}

	public UseYn getCmpreUseYn() {
		return cmpreUseYn;
	}

	public void setCmpreUseYn(UseYn cmpreUseYn) {
		this.cmpreUseYn = cmpreUseYn;
	}

	public CmpreType getCmpreType() {
		return cmpreType;
	}

	public void setCmpreType(CmpreType cmpreType) {
		this.cmpreType = cmpreType;
	}

	public EncdngType getEncdngType() {
		return encdngType;
	}

	public void setEncdngType(EncdngType encdngType) {
		this.encdngType = encdngType;
	}

	public Short getRsltCd() {
		return rsltCd;
	}

	public void setRsltCd(Short rsltCd) {
		this.rsltCd = rsltCd;
	}

	public List<HdrExVO> getHdrExVOs() {
		return hdrExVOs;
	}

	public void setHdrExVOs(List<HdrExVO> hdrExVOs) {
		this.hdrExVOs = hdrExVOs;
	}


}
