//Source file: C:\\Users\\Administrator\\Desktop\\com\\tju\\CanCommunication\\Operation\\ReadAndDisplay.java

package com.tju.CanCommunication.Operation;

import com.tju.CanCommunication.Communication.Command;
import com.tju.CanCommunication.Communication.ReceiveAnswer;
import com.tju.CanCommunication.Communication.Rs232Command;

public class ReadAndDisplay {

	/**
	 * @roseuid 5193494D0298
	 */
	public ReadAndDisplay() {

	}

	/**
	 * @roseuid 519351DC02EA �����Ӳ���İ汾�ţ�ʹ��V����
	 */
	public ReceiveAnswer getHardwareVersion() {
		Command newCmd = new Command("V");
		Rs232Command rs232 = new Rs232Command(newCmd, CanInformation._portName);
		ReceiveAnswer ans = rs232.sendCommand();
		return ans;
	}

	/**
	 * @roseuid 519351ED0088 ʹ��N������Ӳ�����к�
	 */
	public ReceiveAnswer getSerialNumber() {
		Command newCmd = new Command("N");
		Rs232Command rs232 = new Rs232Command(newCmd, CanInformation._portName);
		ReceiveAnswer ans = rs232.sendCommand();
		return ans;
	}

	/**
	 * @roseuid 51935210025E ��ȡSJA�Ĵ�����ֵ,ʹ��Grr����
	 */
	public ReceiveAnswer getSJA_RegisterValue(String value) {
		Command newCmd = new Command("G" + value);
		Rs232Command rs232 = new Rs232Command(newCmd, CanInformation._portName);
		ReceiveAnswer ans = rs232.sendCommand();
		return ans;
	}

	/**
	 * @roseuid 5193522C02AE ʹ��F�����ȡSJA ErrorFlag
	 */
	public ReceiveAnswer getSJAErrorFlag() {
		Command newCmd = new Command("F");
		Rs232Command rs232 = new Rs232Command(newCmd, CanInformation._portName);
		ReceiveAnswer ans = rs232.sendCommand();
		return ans;
	}

	/**
	 * @roseuid 5193523F009C ʹ��F�����ȡSJA1000��s SR,ALC,ECR,RXERR,TXERR
	 *          register from CAN
	 */
	public void getOtherRegisterValue() {
		Command newCmd = new Command("F");
		Rs232Command rs232 = new Rs232Command(newCmd, CanInformation._portName);
		ReceiveAnswer ans = rs232.sendCommand();
	}

	/**
	 * @roseuid 5193524802F4
	 * 
	 */
	public void displaySendingCmd() {

	}

	/**
	 * @roseuid 5193527B00F7
	 */
	public void displayReceivingCmd() {

	}

	/**
	 * @roseuid 5193528E0331 �����õ���Ϣ������Ϣ��ɾ���ź�
	 * 
	 */
	public String deleteSignalFromMsg(String value) {
		String msg = "";
		char cmd = value.charAt(0);
		switch (cmd) {
		case 't':
		case 'r':
			int length_t = value.charAt(4) - '0';
			msg = value.substring(5, 5 + length_t);
			break;
		case 'T':
		case 'R':
			int length_T = value.charAt(9) - '0';
			msg = value.substring(10, 10 + length_T);
			break;
		}
		return msg;
	}

	/**
	 * @roseuid 519352A90219
	 */
	public void displayData() {

	}
}
