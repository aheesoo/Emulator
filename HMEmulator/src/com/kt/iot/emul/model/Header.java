package com.kt.iot.emul.model;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Header {
	
	public static final int RESULT_SUCCESS = 0x00000000;
	
	public static final int CMD_RESPONSE = 0x80000000;
	
	public static final int CMD_REG = 0x00000001;
	public static final int CMD_KEEPALIVE = 0x00000002;
	public static final int CMD_DETECT = 0x00000003;
	public static final int CMD_UCLOUD_STORE = 0x00000004;
	public static final int CMD_UCLOUD_TOKEN = 0x00000006;
	public static final int CMD_SDCARD = 0x00000007;
	
	public static final int CMD_REQ_CONFIG_SEARCH = 0x00800001;
	public static final int CMD_REQ_CONFIG_SET = 0x00800002;
	public static final int CMD_REQ_SCH_SEARCH = 0x00800003;
	public static final int CMD_REQ_SCH_SET = 0x00800004;
	public static final int CMD_VOICE = 0x0080000A;
	public static final int CMD_REQ_ACTIVE_SET = 0x0080000B;
	public static final int CMD_REQ_RESOL_SET = 0x0080000C;
	public static final int CMD_REQ_DETECT_SET = 0x0080000D;
	public static final int CMD_REQ_SENSE_SET = 0x0080000E;
	public static final int CMD_REQ_REVERT_SET = 0x0080000F;
	public static final int CMD_REQ_MOVEPNS_SET = 0x00800011;
	public static final int CMD_REQ_STORAGEPNS_SET = 0x00800012;
	public static final int CMD_REQ_SAVE_SET = 0x00800013;
	public static final int CMD_REQ_DETECT_SCH_SEARCH = 0x00800014;
	public static final int CMD_REQ_DETECT_SCH_SET = 0x00800015;
	public static final int CMD_REQ_RECORD_SCH_SEARCH = 0x00800016;
	public static final int CMD_REQ_RECORD_SCH_SET = 0x00800017;	
	public static final int CMD_REQ_MSENSE_SET = 0x00800018;
	public static final int CMD_REQ_VSENSE_SET = 0x00800019;
		
	static int HEADER_NAME_SIZE = 8;	
	
	String name;
	int version;
	int command;
	int commandId;
	int dataSize;

	public Header(DataInputStream dais){
	
		try {		
			byte[] namebuff = new byte[HEADER_NAME_SIZE];
			dais.read(namebuff);
			this.name = new String(ByteBuffer.wrap(namebuff).array());
			this.version = dais.readInt();
			this.command = dais.readInt();
			this.commandId = dais.readInt();
			this.dataSize = dais.readInt();
			
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
	}

	public Header(String name, int command, int commandId, int dataSize) {

		this.name = name;
		this.command = command;
		this.commandId = commandId;
		this.dataSize = dataSize;
	}

	public String toString(){		
		return new String("name > " + name + ", version > " + version + ", command > " + Integer.toHexString(command) + ", commandID > " + commandId + ", datasize > "+ dataSize );
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getVersion() {
		return version;
	}


	public void setVersion(int version) {
		this.version = version;
	}


	public int getCommand() {
		return command;
	}


	public void setCommand(int command) {
		this.command = command;
	}


	public int getCommandId() {
		return commandId;
	}


	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}


	public int getDataSize() {
		return dataSize;
	}


	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}	
	
}
