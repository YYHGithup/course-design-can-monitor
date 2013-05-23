//Source file: C:\\Users\\Administrator\\Desktop\\com\\tju\\CanCommunication\\Communication\\Rs232Command.java

package com.tju.CanCommunication.Communication;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;

public class Rs232Command extends Thread implements CmdCommunication {
	private Command _cmd;
	private ReceiveAnswer _ans;
	private String _portName;
	private boolean _canSet = true;

	/**
	 * @roseuid 5194775903C8
	 */
	public Rs232Command() {

	}

	public Rs232Command(Command newCmd) {
		_cmd = newCmd;
	}

	public Rs232Command(Command newCmd, String portName) {
		_cmd = newCmd;
		_portName = portName;
	}

	/**
	 * @return com.tju.CanCommunication.Communication.Command
	 * @roseuid 51947BEA02F1
	 */
	public Command getCmd() {
		return _cmd;
	}

	/**
	 * @param newCmd
	 * @roseuid 51947BFA01EE
	 */
	public void setCmd(Command newCmd) {
		_cmd = newCmd;

	}

	public void setPort(String newPort) {
		_portName = newPort;
	}

	public String getPort() {
		return _portName;
	}

	/**
	 * @return com.tju.CanCommunication.Communication.ReceiveAnswer
	 * @roseuid 51947C55038E
	 */
	public ReceiveAnswer getAns() {
		return _ans;
	}

	public ReceiveAnswer sendCommand() {
		while (!_canSet) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		_canSet = false;
		// Thread myThread = new Thread(sendThread);
		Rs232Command myThread = new Rs232Command(_cmd, _portName);
		myThread.start();
		try {
			myThread.join();
			_canSet = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		_ans = myThread.getAns();
		return _ans;
	}

	public void run() {
		startSend();
	}

	public synchronized void startSend() {
		System.out.println(Thread.currentThread().getName());

		Enumeration<CommPortIdentifier> portList = CommPortIdentifier
				.getPortIdentifiers(); // 得到当前连接上的端口

		CommUtil comm3 = new CommUtil(portList, _portName);
		comm3.send(_cmd.getFinalString());
		try {
			Thread.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		comm3.ClosePort();
		_ans = new ReceiveAnswer(comm3.getAnsString());
		if (_ans.getAnsString().charAt(0) == '\r') {
			System.out.println("OK");
		}else{
			System.out.println("Failed");
		}
		System.out.println(_ans.getAnsString());
	}
}