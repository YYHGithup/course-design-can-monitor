package com.tju.CanCommunication.Operation;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.scene.BoundsAccessor;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CanInformation {
	public static String _portName;
	public static boolean _open;
	public static String _openModeId;
	public static String _bitRate;
	public static String _SJARegisterValue;
	public static String _ACRRegisterValue;
	public static String _BTRRegisterValue;
	public static String _AMRRegisterValue;
	public static boolean _isFlush = false;
	public static String _DocCmdCycle;
	public static String _baudRate;

	public static void setDefault() {
		_portName = "COM3";
		_openModeId = new String();
		_bitRate = new String();
		_SJARegisterValue = new String();
		_ACRRegisterValue = new String();
		_BTRRegisterValue = new String();
		_AMRRegisterValue = new String();
		_DocCmdCycle = new String();
		_baudRate = "19200";
		
	}

	public static void setInformation(TextArea infoText) {
		String ans = "";
		ans += "Mode: ";
		if (_open == false) {
			ans += "Reset Mode\n";
		} else {
			switch (_openModeId) {
			case "0":
				ans += "Self Test Mode\n";
				break;
			case "1":
				ans += "Normal Mode\n";
				break;
			case "2":
				ans += "Listen Only\n";
				break;
			default:
				break;
			}
		}
		ans += "BaudRate: "+_baudRate+"\n";
		ans += "Port: "+_portName + "\n";
		ans += "Bit Rate: ";
		if(_bitRate.equals("")){
			ans += "Not Set\n";
		}else{
			ans += _bitRate+"\n";
		}
		ans += "BTRRegisterValue: ";
		ans += _BTRRegisterValue+"\n";
		ans += "ACRRegisterValue: ";
		ans += _ACRRegisterValue+"\n";
		ans += "AMRRegisterValue: ";
		ans += _AMRRegisterValue+"\n";
		infoText.setText(ans);
	}

}
