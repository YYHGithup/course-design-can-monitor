//Source file: C:\\Users\\Administrator\\Desktop\\com\\tju\\CanCommunication\\Operation\\Initialization.java

package com.tju.CanCommunication.Operation;

import com.tju.CanCommunication.Communication.Command;
import com.tju.CanCommunication.Communication.ReceiveAnswer;
import com.tju.CanCommunication.Communication.Rs232Command;

/**
 * @author 这个类的包含的操作有： 1.选择PC端端口并与硬件连接 2.打开CAN总线 3.关闭CAN总线
 */
public class Initialization implements Operation {

	/**
	 * @roseuid 5193494D0216
	 */
	public Initialization() {

	}

	/**
	 * @param a
	 * @roseuid 5193464000B3
	 */
	public void choosePort(String newPort) {
		CanInformation._portName = newPort;
	}

	/**
	 * @roseuid 519346500095
	 */
	public void openCanBus(String value) {
		if (CanInformation._open == false) {
			Command newCmd = new Command("O" + value);
			Rs232Command rs232 = new Rs232Command(newCmd,
					CanInformation._portName);
			ReceiveAnswer ans = rs232.sendCommand();
			CanInformation._open = true;
			CanInformation._openModeId = value;
		}
	}

	/**
	 * @roseuid 5193465901B7
	 */
	public void closeCanbus() {
		Command newCmd = new Command("C");
		Rs232Command rs232 = new Rs232Command(newCmd, CanInformation._portName);
		ReceiveAnswer ans = rs232.sendCommand();
		if (ans.getAnsString().charAt(0) == '\r') {
			CanInformation._open = false;
		}
	}
}
