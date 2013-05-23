//Source file: C:\\Users\\Administrator\\Desktop\\com\\tju\\CanCommunication\\Operation\\Settings.java

package com.tju.CanCommunication.Operation;

import com.tju.CanCommunication.Communication.Command;
import com.tju.CanCommunication.Communication.ReceiveAnswer;
import com.tju.CanCommunication.Communication.Rs232Command;

public class Settings {

	/**
	 * @roseuid 5193494D025C
	 */
	public Settings() {

	}

	/**
	 * @roseuid 519347C30395
	 * ����CANbit-rate,ʹ��S���It works only after power up or if controller is in reset mode after command ��C��
	 *Return: [CR] for Open OK, [BEL] for Failure
	 */
	public void setBitRate(String bitRate) {
		if (CanInformation._open == false) {
			Command newCmd = new Command("S" + bitRate);
			Rs232Command rs232 = new Rs232Command(newCmd,
					CanInformation._portName);
			ReceiveAnswer ans = rs232.sendCommand();
			CanInformation._bitRate = bitRate;
		}		
	}

	/**
	 * @roseuid 5193480D00B7
	 * ʹ��C����ر�CAN ���ߣ� It works only if the controller was set to Operation mode with command ��O�� before.
	 * Return:[CR] for Open OK, [BEL] for Failure
	 */
	public void setCANMode(String modeId) {
		if (CanInformation._open == true) {
			Command newCmd = new Command("C");
			Rs232Command rs232 = new Rs232Command(newCmd,
					CanInformation._portName);
			ReceiveAnswer ans = rs232.sendCommand();
			System.out.println("���ؽ��  " + ans);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			newCmd = new Command("O" + modeId);
			rs232 = new Rs232Command(newCmd, CanInformation._portName);
			ans = rs232.sendCommand();
			System.out.println("���ؽ��  " + ans);
			CanInformation._openModeId = modeId;
		} else {
			if (judgeBitRateSet()) {
				Command newCmd = new Command("O" + modeId);
				Rs232Command rs232 = new Rs232Command(newCmd,
						CanInformation._portName);
				ReceiveAnswer ans = rs232.sendCommand();
				System.out.println("���ؽ��  " + ans);
				CanInformation._openModeId = modeId;
			} else {
				System.out.println("No S6");
			}
		}

	}

	/**
	 * @roseuid 5193482501D9
	 * Write SJA1000 register [rr] with data [dd]
	 * Return ��\r�� or ��\a�� , [BEL] for error of register range.
	 */
	public void setSJA_Register(String value) {
		Command newCmd = new Command("W" + value);
		Rs232Command rs232 = new Rs232Command(newCmd,
				CanInformation._portName);
		ReceiveAnswer ans = rs232.sendCommand();
		System.out.println("���ؽ��  " + ans);
		CanInformation._SJARegisterValue = value;
				
	}
	
	
	/**
	*Write acceptance code register [ACR] of SJA1000. 
	*This command works only if controller is setup with command ��S�� and in reset mode. 
	*Return: [CR] or [BEL]
	*/
	
	public void setACR_Register(String value){
		if (CanInformation._open == false) {
			Command newCmd = new Command("M" + value);
			Rs232Command rs232 = new Rs232Command(newCmd,
					CanInformation._portName);
			ReceiveAnswer ans = rs232.sendCommand();
			System.out.println("���ؽ��  " + ans);
			CanInformation._ACRRegisterValue = value;
		}
	}

	/**
	 * @roseuid 51934FE1024A
	 * Setup with BTR0/BTR1 CAN bit-rates where xx and yy is a user defined values of hex value string for SJA1000 bit rate register BTR0 and BTR1. 
	 * It works only after power up or if controller is in reset mode after command ��C��
	 * Return: [CR] for Open OK, [BEL] for Failure
	 */
	public void setBTR_Register(String value) {
		if(CanInformation._open == false){
			Command newCmd = new Command("s" + value);
			Rs232Command rs232 = new Rs232Command(newCmd,
					CanInformation._portName);
			ReceiveAnswer ans = rs232.sendCommand();
			System.out.println("���ؽ��  " + ans);
			CanInformation._BTRRegisterValue = value;
		}
	}
	
	/**
	 * @roseuid 51934FE1024A
	 * Write acceptance mask register [AMR] of SJA1000. 
	 * This command works only if controller is setup with command ��S�� and in reset mode. One filter mode is internally determined.
	 * Return: [CR] or [BEL]
	 */
	
	public void setAMR_Register(String value) {
		if (CanInformation._open == false) {
			Command newCmd = new Command("m" + value);
			Rs232Command rs232 = new Rs232Command(newCmd,
					CanInformation._portName);
			ReceiveAnswer ans = rs232.sendCommand();
			System.out.println("���ؽ��  " + ans);
			CanInformation._AMRRegisterValue = value;
		}
	}

	/**
	 * @roseuid 51934FFE033A
	 * This command flush the CAN-controller, and bring the controller to Reset mode. Controller��s buffers and variables are all initialized.
	 * Return: [CR] for Open OK, [BEL] for Failure when buffer is full. Maximum buffered Frames=32
	 */
	public void clearAndInit() {
		Command newCmd = new Command("f");
		Rs232Command rs232 = new Rs232Command(newCmd,
				CanInformation._portName);
		ReceiveAnswer ans = rs232.sendCommand();
		System.out.println("���ؽ��  " + ans);
		CanInformation._isFlush = true;
	}
	

	/**
	 * @roseuid 5193502F02EA
	 * �����ļ���������ڣ����ﲻ��Ҫ����CAN����
	 */
	public void setDocCmdCycle(String value) {
		CanInformation._DocCmdCycle = value;
	}
	

	/**
	 * @roseuid 5193505A0308
	 * ����Z0/Z1�����������Ƿ����ʱ�����Z1�������ʱ���
	 *
	 */
	public void isAddTimeFlag(String value) {
		Command newCmd = new Command("Z" + value);
		Rs232Command rs232 = new Rs232Command(newCmd,
				CanInformation._portName);
		ReceiveAnswer ans = rs232.sendCommand();
		System.out.println("���ؽ��  " + ans.getAnsString());
	}

	/**
	 * @roseuid 5193506A01AA
	 * ����B0/B1�����������Ƿ����÷�����Ϣ�Ƿ�������״̬��B1Ϊ��������״̬.reset ģʽ�²����Խ�������
	 */
	public void isBlockMode(String value) {
		if(CanInformation._open == true){
			Command newCmd = new Command("B" + value);
			Rs232Command rs232 = new Rs232Command(newCmd,
				CanInformation._portName);
			ReceiveAnswer ans = rs232.sendCommand();
			System.out.println("���ؽ��  " + ans.getAnsString());
		}
	}

	private boolean judgeBitRateSet() {
		return CanInformation._bitRate.compareTo("0") >= 0
				&& CanInformation._bitRate.compareTo("8") <= 0;
	}
}
