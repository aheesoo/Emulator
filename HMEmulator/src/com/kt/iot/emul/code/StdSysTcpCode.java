package com.kt.iot.emul.code;

import java.util.HashMap;

public class StdSysTcpCode
{
	/**
	 *  헤더타입
	 * @since	: 2014. 11. 26.
	 * @author	: CBJ
	 * <PRE>
	 * Revision History
	 * ----------------------------------------------------
	 * 2014. 11. 26. CBJ: 최초작성
	 * ----------------------------------------------------
	 * </PRE>
	 */
	public static enum HdrType
	{
		/** 기본 */
		BASIC( (byte)1 ),
		/** 경량헤더 */
		LIGHT_WEIGHT( (byte)2 ),
		;
		private final Byte value;

        private HdrType(Byte value) {
                this.value = value;
        }

        public boolean equals(Byte obj)
    	{
        	return value.equals(obj);
    	}

		/**
		 * @return the value
		 */
		public Byte getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value.toString();
		}

		// value에 해당되는 enum을 반환하기 위한 Map 생성 및 설정
		private static final HashMap<Byte, HdrType> map = new HashMap<Byte, HdrType>();
		static {
			for(HdrType it : values()) {
				map.put(it.getValue(), it);
			}
		}
		// value에 해당되는 enum을 반환
		public static HdrType fromByte(Byte value) {
			return map.get(value);
		}
	}

	/**
	 *  메시지타입
	 * @since	: 2014. 11. 26.
	 * @author	: CBJ
	 * <PRE>
	 * Revision History
	 * ----------------------------------------------------
	 * 2014. 11. 26. CBJ: 최초작성
	 * ----------------------------------------------------
	 * </PRE>
	 */
	public static enum MsgType
	{
		/** 요청 */
		REQUEST( (byte)1 ),
		/** 응답 */
		RESPONSE( (byte)2 ),
		/** 리포트 */
		REPORT( (byte)3 ),
		;
		private final Byte value;

        private MsgType(Byte value) {
                this.value = value;
        }

        public boolean equals(Byte obj)
    	{
        	return value.equals(obj);
    	}

		/**
		 * @return the value
		 */
		public Byte getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value.toString();
		}

		// value에 해당되는 enum을 반환하기 위한 Map 생성 및 설정
		private static final HashMap<Byte, MsgType> map = new HashMap<Byte, MsgType>();
		static {
			for(MsgType it : values()) {
				map.put(it.getValue(), it);
			}
		}
		// value에 해당되는 enum을 반환
		public static MsgType fromByte(Byte value) {
			return map.get(value);
		}
	}

	/**
	 *  메시지교환패턴
	 * @since	: 2014. 11. 26.
	 * @author	: CBJ
	 * <PRE>
	 * Revision History
	 * ----------------------------------------------------
	 * 2014. 11. 26. CBJ: 최초작성
	 * ----------------------------------------------------
	 * </PRE>
	 */
	public static enum MsgExchPtrn
	{
		/** 요청 */
		ONE_WAY( (byte)1 ),
		/** 요청-응답 */
		ONE_WAY_ACK( (byte)2 ),
		/** 요청-응답-응답회신 */
		THREE_WAY( (byte)3 ),
		;
		private final Byte value;

        private MsgExchPtrn(Byte value) {
                this.value = value;
        }

        public boolean equals(Byte obj)
    	{
        	return value.equals(obj);
    	}

		/**
		 * @return the value
		 */
		public Byte getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value.toString();
		}

		// value에 해당되는 enum을 반환하기 위한 Map 생성 및 설정
		private static final HashMap<Byte, MsgExchPtrn> map = new HashMap<Byte, MsgExchPtrn>();
		static {
			for(MsgExchPtrn it : values()) {
				map.put(it.getValue(), it);
			}
		}
		// value에 해당되는 enum을 반환
		public static MsgExchPtrn fromByte(Byte value) {
			return map.get(value);
		}
	}

	/**
	 * <PRE>
	 *  ClassName : GwCode
	 * </PRE>
	 * @version : 1.0
	 * @date    : 2014. 1. 28. 오후 8:51:17
	 * @author  : byw
	 * @brief   : 암호화유형: 1006
	 */
	public static enum EcodType
	{
		/** AES 128 */
		AES_128( (byte)0x1 ),
		/** AES 256 */
		AES_256( (byte)0x2 ),
		/** DES 128 */
		DES_64( (byte)0x11 ),
		/** DES 256 */
		DES_EDE_192( (byte)0x12 ),
		/** SEED 128 */
		SEED_128( (byte)0x21 ),
		/** ARIA 128 */
		ARIA_128( (byte)0x31 ),
		/** ARIA 256 */
		ARIA_192( (byte)0x32 ),
		/** ARIA 256 */
		ARIA_256( (byte)0x33 ),
		;

		private final Byte value;

        private EcodType(Byte value) {
                this.value = value;
        }

        public boolean equals(Byte obj)
    	{
        	return value.equals(obj);
    	}

		/**
		 * @return the value
		 */
		public Byte getValue() {
			return value;
		}

		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return value.toString();
		}


		// value에 해당되는 enum을 반환하기 위한 Map 생성 및 설정
		private static final HashMap<Byte, EcodType> map = new HashMap<Byte, EcodType>();
		static {
			for(EcodType it : values()) {
				map.put(it.getValue(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static EcodType fromByte(Byte value) {
			return map.get(value);
		}

	}

	/**
	 *  압축유형
	 * @since	: 2014. 11. 26.
	 * @author	: CBJ
	 * <PRE>
	 * Revision History
	 * ----------------------------------------------------
	 * 2014. 11. 26. CBJ: 최초작성
	 * ----------------------------------------------------
	 * </PRE>
	 */
	public static enum CmpreType
	{
		/** 허프만 */
		HUFFMAN( (byte)1 ),
		/** 런랭스 */
		RUN_LENGTH( (byte)2 ),
		/** 샤논파노 */
		SHANNON_FANO( (byte)3 ),
		;
		private final Byte value;

        private CmpreType(Byte value) {
                this.value = value;
        }

        public boolean equals(Byte obj)
    	{
        	return value.equals(obj);
    	}

		/**
		 * @return the value
		 */
		public Byte getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value.toString();
		}

		// value에 해당되는 enum을 반환하기 위한 Map 생성 및 설정
		private static final HashMap<Byte, CmpreType> map = new HashMap<Byte, CmpreType>();
		static {
			for(CmpreType it : values()) {
				map.put(it.getValue(), it);
			}
		}
		// value에 해당되는 enum을 반환
		public static CmpreType fromByte(Byte value) {
			return map.get(value);
		}
	}

	/**
	 *  인코딩유형
	 * @since	: 2014. 11. 26.
	 * @author	: CBJ
	 * <PRE>
	 * Revision History
	 * ----------------------------------------------------
	 * 2014. 11. 26. CBJ: 최초작성
	 * ----------------------------------------------------
	 * </PRE>
	 */
	public static enum EncdngType
	{
		/** userDefined */
		USER_DEFINED( (byte)0x1 ),
		/** xml */
		XML( (byte)0x2 ),
		/** json */
		JSON( (byte)0x3 ),
		/** gpb */
		GPB( (byte)0x10 ),
		/** thrift */
		THRIFT( (byte)0x11 ),
		/** avro */
		AVRO( (byte)0x12 ),
		/** pcre */
		PCRE( (byte)0x13 ),
		;

		private final Byte value;

        private EncdngType(Byte value) {
                this.value = value;
        }

        public boolean equals(Byte obj)
    	{
        	return value.equals(obj);
    	}

		/**
		 * @return the value
		 */
		public Byte getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value.toString();
		}

		// value에 해당되는 enum을 반환하기 위한 Map 생성 및 설정
		private static final HashMap<Byte, EncdngType> map = new HashMap<Byte, EncdngType>();
		static {
			for(EncdngType it : values()) {
				map.put(it.getValue(), it);
			}
		}
		// value에 해당되는 enum을 반환
		public static EncdngType fromByte(Byte value) {
			return map.get(value);
		}
	}

	public static enum MthdType
	{
		/** 플랫폼 및 어댑터 버전 조회 */
		VER_GWVER_RQT( ((short)111) ),
		/** 외부시스템 TCP 로그인  */
		ATHN_LOGINATHN_EXTRSYS_TCP( ((short)212) ),
		/** 장치 TCP 로그인 */
		ATHN_LOGINATHN_DEV_TCP( ((short)214) ),
		/** 외부시스템 TCP 채널 인증 */
		ATHN_COMMCHATHN_EXTRSYS_TCP( ((short)222) ),
		/** 장치 TCP 채널 인증 */
		ATHN_COMMCHATHN_DEV_TCP( ((short)224) ),
		/** KeepAlive TCP 채널 */
		KEEP_ALIVE_COMMCHATHN_TCP( ((short)231) ),
		/** 데이터유형 조회 */
		INITA_DATATYPE_RETV( ((short)311) ),
		/** 외부시스템정보 조회 */
		INITA_EXTRSYS_RETV( ((short)321) ),
		/** 외부시스템정보 갱신보고 */
		INITA_EXTRSYS_UDATERPRT( ((short)322) ),
		/** 장치정보 조회 */
		INITA_DEV_RETV( ((short)331) ),
		/** 장치정보 갱신보고 */
		INITA_DEV_UDATERPRT( ((short)332) ),
		/** 장치 정보 조회 (플랫폼) */
		INITA_DEV_RETV_PLTFRM( ((short) 333)),
		/** 장치정보 갱신보고 (플랫폼) */
		INITA_DEV_UDATERPRT_PLTFRM ( ((short)334) ),
		/** 수집설정 필터링조건조회 */
		COLEC_SETUP_FLTRCONDRETV( ((short)341) ),
		/** 수집설정 필터링조건해지 */
		COLEC_SETUP_FLTRCONDTRMN( ((short)343) ),
		/** 수집설정 이벤트조건조회 */
		COLEC_SETUP_EVCONDRETV( ((short)344) ),
		/** 수집설정 이벤트조건해지 */
		COLEC_SETUP_EVCONDTRMN( ((short)346) ),
		/** 수집 통합데이터 */
		COLEC_ITGDATA_RECV( ((short)411) ),
		/** 수집 센싱데이터 */
		COLEC_SNSNDATA_RECV( ((short)421) ),
		/** 수집 위치데이터 */
		COLEC_LODATA_RECV( ((short)431) ),
		/** 수집 상태데이터 */
		COLEC_STTUSDATA_RECV( ((short)441) ),
		/** 수집 이진데이터 */
		COLEC_BINDATA_RECV( ((short)451) ),
		/** 수집 이벤트 */
		COLEC_EVDATA_RECV( ((short)461) ),
		/** 시스템제어 실시간제어 */
		CONTL_SYS_RTIMECONTL( ((short)511) ),
		/** 시스템제어 설정변경 */
		CONTL_SYS_SETUPCHG( ((short)512) ),
		/** 장치제어 실시간제어 */
		CONTL_DEV_RTIMECONTL( ((short)521) ),
		/** 장치제어 설정변경 */
		CONTL_DEV_SETUPCHG( ((short)522) ),
		/** 장치제어 펌웨어업데이트 */
		CONTL_DEV_FRMWRUDATE( ((short)523) ),
		/** 통합데이터 전달 */
		CONTL_ITGCNVY_DATA( ((short)525) ),
		/** 시스템점검 체크패킷수신 */
		CHK_SYS_CHKPACKTRCV( ((short)612) ),
		/** 장치점검 체크패킷수신 */
		CHK_DEV_CHKPACKTRCV( ((short)622) ),
		/** 최종값 쿼리 */
		QUERY_LASTVAL( (short) 711 ),
		/** 집계값 쿼리 */
		QUERY_SUMVAL( (short) 721 ),
		/** 펌웨어 정보 요청 */
		FRMWR_INFO_RQT( ((short)811)),
		/** 펌웨어 업데이트 상태 전송 */
		FRMWR_UDATE_STTUS( (short) 813),
		/** 펌웨어 데이터 TCP 요청 */
		FRMWR_DATA_TCP_RQT( (short) 816),
		/** 패키지 데이터 TCP 요청 */
		PKG_DATA_TCP_RQT((short) 817),
		/** 통합로그수집 */
		LOG_ITG_LOG( ((short)821)),
		;

		private final Short value;

        private MthdType(Short value) {
                this.value = value;
        }

        public boolean equals(Short obj)
    	{
        	return value.equals(obj);
    	}

		/**
		 * @return the value
		 */
		public Short getValue() {
			return value;
		}

		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return value.toString();
		}


		// value에 해당되는 enum을 반환하기 위한 Map 생성 및 설정
		private static final HashMap<Short, MthdType> map = new HashMap<Short, MthdType>();
		static {
			for(MthdType it : values()) {
				map.put(it.getValue(), it);
			}
		}

		// value에 해당되는 enum을 반환
		public static MthdType fromShort(Short value) {
			return map.get(value);
		}
	}
}
