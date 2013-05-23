package com.tju.CanMonitor.Windows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.CharBuffer;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import com.tju.CanCommunication.Communication.ReceiveAnswer;
import com.tju.CanCommunication.Operation.CanInformation;
import com.tju.CanCommunication.Operation.Initialization;
import com.tju.CanCommunication.Operation.ReadAndDisplay;
import com.tju.CanCommunication.Operation.SendMsgAndCmd;
import com.tju.CanCommunication.Operation.Settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.PopupWindow;
import javafx.stage.Window;

public class MainWindow {

	private int _periedTime;
	private File _openFile;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextArea informationArea;

	@FXML
	private MenuButton mainBaudRate;

	@FXML
	private Button mainBlokeOff;

	@FXML
	private Button mainBlokeOn;

	@FXML
	private Button mainClosePort;

	@FXML
	private TextField mainCommandsACR0;

	@FXML
	private TextField mainCommandsACR1;

	@FXML
	private TextField mainCommandsACR2;

	@FXML
	private TextField mainCommandsACR3;

	@FXML
	private Button mainCommandsACROK;

	@FXML
	private TextField mainCommandsALC;

	@FXML
	private TextField mainCommandsAMR0;

	@FXML
	private TextField mainCommandsAMR1;

	@FXML
	private TextField mainCommandsAMR2;

	@FXML
	private TextField mainCommandsAMR3;

	@FXML
	private Button mainCommandsAMROK;

	@FXML
	private TextField mainCommandsBTR0;

	@FXML
	private TextField mainCommandsBTR1;

	@FXML
	private Button mainCommandsBTROK;

	@FXML
	private Button mainCommandsClear;

	@FXML
	private TextField mainCommandsECR;

	@FXML
	private TextField mainCommandsERRFlags;

	@FXML
	private Button mainCommandsFlushOK;

	@FXML
	private Button mainCommandsGetVersion;

	@FXML
	private TextField mainCommandsGetVersionTF;

	@FXML
	private Button mainCommandsReadOK;

	@FXML
	private TextField mainCommandsReadRegister;

	@FXML
	private Button mainCommandsReadRegisterOK;

	@FXML
	private Button mainCommandsReadSerial;

	@FXML
	private TextField mainCommandsReadSerialTF;

	@FXML
	private TextField mainCommandsRegisterData;

	@FXML
	private TextField mainCommandsRiERR;

	@FXML
	private TextField mainCommandsSB;

	@FXML
	private Button mainCommandsStepdriver;

	@FXML
	private TextField mainCommandsTXERRR;

	@FXML
	private TextField mainCommandsWithData;

	@FXML
	private TextField mainCommandsWriteRegister;

	@FXML
	private Button mainListenOnly;

	@FXML
	private Button mainListenOnlyOk;

	@FXML
	private Button mainNormalMode;

	@FXML
	private Button mainResetMode;

	@FXML
	private MenuButton mainSelectCom;

	@FXML
	private Button mainSelfTest;

	@FXML
	private Button mainSendOpen;

	@FXML
	private TextField mainSendOpenTF;

	@FXML
	private Button mainSendSendData;

	@FXML
	private TextField mainSendSendDataTF;

	@FXML
	private Button mainSendSendPeried;

	@FXML
	private TextField mainSendSendPeriedTF;

	@FXML
	private Button mainSet;

	@FXML
	private MenuButton mainSetupBitRate;

	@FXML
	private Button mainTimestampOff;

	@FXML
	private Button mainTimestampOn;

	@FXML
	private AnchorPane mainWindow;

	int transformToHex(char ch) {
		if (ch >= 'A' && ch <= 'F') {
			return ch - 'A';
		} else if (ch >= '0' && ch <= '9') {
			return ch - '0';
		}
		return -1;
	}
	
	@FXML
    void addMessage(ActionEvent event) {
    }

	@FXML
	void blockOff(MouseEvent event) {
		Settings mySetting = new Settings();
		mySetting.isBlockMode("0");
	}

	@FXML
	void blockOn(MouseEvent event) {
		Settings mySetting = new Settings();
		mySetting.isAddTimeFlag("1");
	}

	@FXML
	void sendFromFile(MouseEvent event) {
		String periedString = mainSendSendPeriedTF.getText().trim();
		if (periedString.length() == 0) {
			System.out.println("Please input peried Time");
			return;
		}
		_periedTime = Integer.parseInt(periedString);
		new SendFileCmd(_periedTime, _openFile).start();
	}

	@FXML
	void chooseFile(MouseEvent event) {

		FileChooser myFileChooser = new FileChooser();
		Window fileChooseWindow = new PopupWindow() {
		};
		File f = myFileChooser.showOpenDialog(fileChooseWindow);
		_openFile = f;
		String filePath = f.getAbsolutePath();
		mainSendOpenTF.setText(filePath);
	}
	
	@FXML
    void closeWindow(ActionEvent event) {
    }

	@FXML
	void timeStampOff(MouseEvent event) {
		Settings mySetting = new Settings();
		mySetting.isAddTimeFlag("0");
	}

	@FXML
	void timeStampOn(MouseEvent event) {
		Settings mySetting = new Settings();
		mySetting.isAddTimeFlag("1");
	}

	void sendDataAndCommandReal(String commandAndData) {
		char cmd = commandAndData.charAt(0);
		switch (cmd) {
		case 'T': {
			for (int i = 1; i < commandAndData.length(); i++) {
				if (transformToHex(commandAndData.charAt(i)) == -1) {
					System.out.println("Input is not right");
					return;
				}
			}
			if (transformToHex(commandAndData.charAt(1)) > 1) {
				System.out.println("Identifier error");
				return;
			}
			int dataLength = commandAndData.charAt(9) - '0';
			if (dataLength >= 0 && dataLength <= 8) {
				String tmp = commandAndData.substring(10);
				if (tmp.length() != 2 * dataLength) {
					System.out.println("Your input data is not right.");
					return;
				}
			} else {
				System.out.println("dataLength is not right!");
				return;
			}
			SendMsgAndCmd mySend = new SendMsgAndCmd();
			mySend.sendExtend29WithData(commandAndData);
		}
			break;

		case 't': {
			for (int i = 1; i < commandAndData.length(); i++) {
				if (transformToHex(commandAndData.charAt(i)) == -1) {
					System.out.println("Input is not right");
					return;
				}
			}
			if (transformToHex(commandAndData.charAt(1)) > 7) {
				System.out.println("Identifier error");
				return;
			}
			int dataLength = commandAndData.charAt(4) - '0';
			if (dataLength >= 0 && dataLength <= 8) {
				String tmp = commandAndData.substring(5);
				if (tmp.length() != 2 * dataLength) {
					System.out.println("Your input data is not right.");
					return;
				}
			} else {
				System.out.println("dataLength is not right!");
				return;
			}
			SendMsgAndCmd mySend = new SendMsgAndCmd();
			mySend.sendStandard11WithData(commandAndData);
		}
			break;
		case 'R': {
			for (int i = 1; i < commandAndData.length(); i++) {
				if (transformToHex(commandAndData.charAt(i)) == -1) {
					System.out.println("Input is not right");
					return;
				}
			}
			if (transformToHex(commandAndData.charAt(1)) > 1) {
				System.out.println("Identifier error");
				return;
			}
			int dataLength = commandAndData.charAt(9) - '0';
			if (dataLength >= 0 && dataLength <= 8) {

			} else {
				System.out.println("dataLength is not right!");
				return;
			}
			SendMsgAndCmd mySend = new SendMsgAndCmd();
			mySend.sendExtend29WithoutData(commandAndData);
		}
			break;
		case 'r': {
			for (int i = 1; i < commandAndData.length(); i++) {
				if (transformToHex(commandAndData.charAt(i)) == -1) {
					System.out.println("Input is not right");
					return;
				}
			}
			if (transformToHex(commandAndData.charAt(1)) > 7) {
				System.out.println("Identifier error");
				return;
			}
			int dataLength = commandAndData.charAt(4) - '0';
			if (dataLength >= 0 && dataLength <= 8) {

			} else {
				System.out.println("dataLength is not right!");
				return;
			}
			SendMsgAndCmd mySend = new SendMsgAndCmd();
			mySend.sendStandard11WithoutData(commandAndData);
		}
			break;
		default:
			System.out.println("This command is not a send Command");
			break;
		}
	}

	public boolean judgeRegisterValue(String str) {
		return transformToHex(str.charAt(0)) != -1
				&& transformToHex(str.charAt(1)) != -1;
	}

	@FXML
	void setACR(MouseEvent event) {
		String acr0 = mainCommandsACR0.getText().trim();
		String acr1 = mainCommandsACR1.getText().trim();
		String acr2 = mainCommandsACR2.getText().trim();
		String acr3 = mainCommandsACR3.getText().trim();
		if (acr0.length() == 2 && acr1.length() == 2 && acr2.length() == 2
				&& acr3.length() == 2) {
			if (judgeRegisterValue(acr0)) {
				if (judgeRegisterValue(acr1)) {
					if (judgeRegisterValue(acr2)) {
						if (judgeRegisterValue(acr3)) {
							Settings mySetting = new Settings();
							mySetting
									.setACR_Register(acr0 + acr1 + acr2 + acr3);
						} else {
							System.out.println("ACR3 is not right");
						}
					} else {
						System.out.println("ACR2 is not right");
					}
				} else {
					System.out.println("ACR1 is not right");
				}
			} else {
				System.out.println("ACR0 is not right");
			}
		} else {
			System.out
					.println("The length of ACR is not right, you should input two character");
		}
		CanInformation.setInformation(informationArea);
	}

	@FXML
	void setAMR(MouseEvent event) {
		String amr0 = mainCommandsAMR0.getText().trim();
		String amr1 = mainCommandsAMR1.getText().trim();
		String amr2 = mainCommandsAMR2.getText().trim();
		String amr3 = mainCommandsAMR3.getText().trim();
		if (amr0.length() == 2 && amr1.length() == 2 && amr2.length() == 2
				&& amr3.length() == 2) {
			if (judgeRegisterValue(amr0)) {
				if (judgeRegisterValue(amr1)) {
					if (judgeRegisterValue(amr2)) {
						if (judgeRegisterValue(amr3)) {
							Settings mySetting = new Settings();
							mySetting
									.setAMR_Register(amr0 + amr1 + amr2 + amr3);
						} else {
							System.out.println("AMR3 is not right");
						}
					} else {
						System.out.println("AMR2 is not right");
					}
				} else {
					System.out.println("AMR1 is not right");
				}
			} else {
				System.out.println("AMR0 is not right");
			}
		} else {
			System.out
					.println("The length of AMR is not right, you should input two character");
		}
		CanInformation.setInformation(informationArea);
	}

	@FXML
	void setBTR(MouseEvent event) {
		String btr0 = mainCommandsBTR0.getText().trim();
		String btr1 = mainCommandsBTR1.getText().trim();
		if (btr0.length() == 2 && btr1.length() == 2) {
			if (judgeRegisterValue(btr0)) {
				if (judgeRegisterValue(btr1)) {
					Settings mySetting = new Settings();
					mySetting.setBTR_Register(btr0 + btr1);
				} else {
					System.out.println("BTR1 is not right");
				}
			} else {
				System.out.println("BTR0 is not right");
			}
		} else {
			System.out
					.println("The length of BTR is not right, you should input two character");
		}
		CanInformation.setInformation(informationArea);
	}

	@FXML
	void setSJARegister(MouseEvent event) {
		String SJARegister = mainCommandsWriteRegister.getText().trim();
		String data = mainCommandsWithData.getText().trim();
		if (SJARegister.length() == 2 && data.length() == 2) {
			if (transformToHex(SJARegister.charAt(0)) >= 0
					&& transformToHex(SJARegister.charAt(1)) <= 7
					&& judgeRegisterValue(SJARegister)) {
				if (judgeRegisterValue(data)) {
					Settings mySetting = new Settings();
					mySetting.setSJA_Register(SJARegister + data);
				} else {
					System.out.println("Data is not right");
				}
			} else {
				System.out.println("Register is not right");
			}
		} else {
			System.out
					.println("The length is not right, you should input two character");
		}
		CanInformation.setInformation(informationArea);
	}

	@FXML
	void getErrorFlag(MouseEvent event) {
		ReadAndDisplay myRead = new ReadAndDisplay();
		ReceiveAnswer myAns = myRead.getSJAErrorFlag();
		String ans = myAns.getAnsString();
		mainCommandsERRFlags.setText(ans.substring(1, 3));
		mainCommandsSB.setText(ans.substring(3, 5));
		mainCommandsALC.setText(ans.substring(5, 7));
		mainCommandsECR.setText(ans.substring(7, 9));
		mainCommandsRiERR.setText(ans.substring(9, 11));
		mainCommandsTXERRR.setText(ans.substring(11));
	}

	@FXML
	void getSJARegisterValue(MouseEvent event) {
		String SJARegister = mainCommandsReadRegister.getText().trim();
		if (SJARegister.length() == 2) {
			if (transformToHex(SJARegister.charAt(0)) >= 0
					&& transformToHex(SJARegister.charAt(1)) <= 7
					&& judgeRegisterValue(SJARegister)) {
				ReadAndDisplay myRead = new ReadAndDisplay();
				ReceiveAnswer ans = myRead.getSJA_RegisterValue(SJARegister);
				mainCommandsRegisterData.setText(ans.getAnsString()
						.substring(1));
			} else {
				System.out.println("REgister is not right");
			}
		} else {
			System.out
					.println("The length is not right, you should input two character");
		}
	}

	@FXML
	void getSerial(MouseEvent event) {
		ReadAndDisplay myRead = new ReadAndDisplay();
		ReceiveAnswer ans = myRead.getSerialNumber();
		mainCommandsReadSerialTF.setText(ans.getAnsString());
	}

	@FXML
	void getVersion(MouseEvent event) {
		ReadAndDisplay myRead = new ReadAndDisplay();
		ReceiveAnswer ans = myRead.getHardwareVersion();
		mainCommandsGetVersionTF.setText(ans.getAnsString());
	}

	@FXML
	void sendDataAndComand(MouseEvent event) {
		String commandAndData = mainSendSendDataTF.getText().trim();
		sendDataAndCommandReal(commandAndData);
	}

	@FXML
	void setBitRate(MouseEvent event) {
		Settings mysetting = new Settings();
		mysetting.setBitRate(mainSetupBitRate.getText());
		CanInformation.setInformation(informationArea);
	}

	@FXML
	void flush(MouseEvent event) {
		Settings mySetting = new Settings();
		mySetting.clearAndInit();
	}

	@FXML
	void bitRateChoose(ActionEvent event) {
		MenuItem item = (MenuItem) event.getSource();
		mainSetupBitRate.setText(item.getText());
		CanInformation.setInformation(informationArea);
	}

	@FXML
	void changeBaudRate(ActionEvent event) {
		MenuItem item = (MenuItem) event.getSource();
		mainBaudRate.setText(item.getText());
		CanInformation._baudRate = item.getText();
		CanInformation.setInformation(informationArea);
	}

	@FXML
	void changePort(ActionEvent event) {
		MenuItem item = (MenuItem) event.getSource();
		mainSelectCom.setText(item.getText());
		CanInformation._portName = item.getText();
		CanInformation.setInformation(informationArea);
	}

	@FXML
	void resetMode(MouseEvent event) {
		Initialization init = new Initialization();
		init.closeCanbus();
		CanInformation.setInformation(informationArea);
	}

	@FXML
	void setListenOnly(MouseEvent event) {
		Initialization init = new Initialization();
		init.openCanBus("2");
		CanInformation.setInformation(informationArea);
	}

	@FXML
	void setNormalMode(MouseEvent event) {
		Initialization init = new Initialization();
		init.openCanBus("1");
		CanInformation.setInformation(informationArea);
	}

	@FXML
	void setSelfTest(MouseEvent event) {
		Initialization init = new Initialization();
		init.openCanBus("0");
		CanInformation.setInformation(informationArea);
	}

	@FXML
	void initialize() {
		assert informationArea != null : "fx:id=\"informationArea\" was not injected: check your FXML file 'main.fxml'.";
		assert mainBaudRate != null : "fx:id=\"mainBaudRate\" was not injected: check your FXML file 'main.fxml'.";
		assert mainBlokeOff != null : "fx:id=\"mainBlokeOff\" was not injected: check your FXML file 'main.fxml'.";
		assert mainBlokeOn != null : "fx:id=\"mainBlokeOn\" was not injected: check your FXML file 'main.fxml'.";
		assert mainClosePort != null : "fx:id=\"mainClosePort\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsACR0 != null : "fx:id=\"mainCommandsACR0\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsACR1 != null : "fx:id=\"mainCommandsACR1\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsACR2 != null : "fx:id=\"mainCommandsACR2\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsACR3 != null : "fx:id=\"mainCommandsACR3\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsACROK != null : "fx:id=\"mainCommandsACROK\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsALC != null : "fx:id=\"mainCommandsALC\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsAMR0 != null : "fx:id=\"mainCommandsAMR0\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsAMR1 != null : "fx:id=\"mainCommandsAMR1\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsAMR2 != null : "fx:id=\"mainCommandsAMR2\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsAMR3 != null : "fx:id=\"mainCommandsAMR3\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsAMROK != null : "fx:id=\"mainCommandsAMROK\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsBTR0 != null : "fx:id=\"mainCommandsBTR0\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsBTR1 != null : "fx:id=\"mainCommandsBTR1\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsBTROK != null : "fx:id=\"mainCommandsBTROK\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsClear != null : "fx:id=\"mainCommandsClear\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsECR != null : "fx:id=\"mainCommandsECR\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsERRFlags != null : "fx:id=\"mainCommandsERRFlags\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsFlushOK != null : "fx:id=\"mainCommandsFlushOK\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsGetVersion != null : "fx:id=\"mainCommandsGetVersion\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsGetVersionTF != null : "fx:id=\"mainCommandsGetVersionTF\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsReadOK != null : "fx:id=\"mainCommandsReadOK\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsReadRegister != null : "fx:id=\"mainCommandsReadRegister\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsReadRegisterOK != null : "fx:id=\"mainCommandsReadRegisterOK\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsReadSerial != null : "fx:id=\"mainCommandsReadSerial\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsReadSerialTF != null : "fx:id=\"mainCommandsReadSerialTF\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsRegisterData != null : "fx:id=\"mainCommandsRegisterData\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsRiERR != null : "fx:id=\"mainCommandsRiERR\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsSB != null : "fx:id=\"mainCommandsSB\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsStepdriver != null : "fx:id=\"mainCommandsStepdriver\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsTXERRR != null : "fx:id=\"mainCommandsTXERRR\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsWithData != null : "fx:id=\"mainCommandsWithData\" was not injected: check your FXML file 'main.fxml'.";
		assert mainCommandsWriteRegister != null : "fx:id=\"mainCommandsWriteRegister\" was not injected: check your FXML file 'main.fxml'.";
		assert mainListenOnly != null : "fx:id=\"mainListenOnly\" was not injected: check your FXML file 'main.fxml'.";
		assert mainListenOnlyOk != null : "fx:id=\"mainListenOnlyOk\" was not injected: check your FXML file 'main.fxml'.";
		assert mainNormalMode != null : "fx:id=\"mainNormalMode\" was not injected: check your FXML file 'main.fxml'.";
		assert mainResetMode != null : "fx:id=\"mainResetMode\" was not injected: check your FXML file 'main.fxml'.";
		assert mainSelectCom != null : "fx:id=\"mainSelectCom\" was not injected: check your FXML file 'main.fxml'.";
		assert mainSelfTest != null : "fx:id=\"mainSelfTest\" was not injected: check your FXML file 'main.fxml'.";
		assert mainSendOpen != null : "fx:id=\"mainSendOpen\" was not injected: check your FXML file 'main.fxml'.";
		assert mainSendOpenTF != null : "fx:id=\"mainSendOpenTF\" was not injected: check your FXML file 'main.fxml'.";
		assert mainSendSendData != null : "fx:id=\"mainSendSendData\" was not injected: check your FXML file 'main.fxml'.";
		assert mainSendSendDataTF != null : "fx:id=\"mainSendSendDataTF\" was not injected: check your FXML file 'main.fxml'.";
		assert mainSendSendPeried != null : "fx:id=\"mainSendSendPeried\" was not injected: check your FXML file 'main.fxml'.";
		assert mainSendSendPeriedTF != null : "fx:id=\"mainSendSendPeriedTF\" was not injected: check your FXML file 'main.fxml'.";
		assert mainSet != null : "fx:id=\"mainSet\" was not injected: check your FXML file 'main.fxml'.";
		assert mainSetupBitRate != null : "fx:id=\"mainSetupBitRate\" was not injected: check your FXML file 'main.fxml'.";
		assert mainTimestampOff != null : "fx:id=\"mainTimestampOff\" was not injected: check your FXML file 'main.fxml'.";
		assert mainTimestampOn != null : "fx:id=\"mainTimestampOn\" was not injected: check your FXML file 'main.fxml'.";
		assert mainWindow != null : "fx:id=\"mainWindow\" was not injected: check your FXML file 'main.fxml'.";

		CanInformation.setInformation(informationArea);
	}

	class SendFileCmd extends Thread {
		private int _threadPeriedTime;
		private File _threadOpenFile;

		public SendFileCmd(int periedTime, File file) {
			_threadPeriedTime = periedTime;
			_threadOpenFile = file;
		}

		public void run() {
			FileReader fileReader = null;
			try {
				fileReader = new FileReader(_threadOpenFile);

				char ch;
				char[] myCharBuffer = new char[10000];
				if (-1 != (fileReader.read(myCharBuffer))) {
					String str = new String(myCharBuffer);
					System.out.println(str);
					String[] cmds = str.split("\n");
					for (int i = 0; i < cmds.length; i++) {
						System.out.println("Sending: " + cmds[i]);
						if (cmds[i].trim().length() != 0) {
							sendDataAndCommandReal(cmds[i].trim());
						}
						try {
							Thread.sleep(_threadPeriedTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
