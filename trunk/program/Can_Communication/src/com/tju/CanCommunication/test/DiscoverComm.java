package com.tju.CanCommunication.test;

import java.util.Enumeration;
import java.util.HashSet;

import gnu.io.*;

public class DiscoverComm {

	static void listPorts() {
		java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier
				.getPortIdentifiers();
		/*while (portEnum.hasMoreElements()) {
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			System.out.println(portIdentifier.getName() + " - "
					+ getPortTypeName(portIdentifier.getPortType()));
		}*/
	}

	static String getPortTypeName(int portType) {
		switch (portType) {
		case CommPortIdentifier.PORT_I2C:
			return "I2C";
		case CommPortIdentifier.PORT_PARALLEL:
			return "Parallel";
		case CommPortIdentifier.PORT_RAW:
			return "Raw";
		case CommPortIdentifier.PORT_RS485:
			return "RS485";
		case CommPortIdentifier.PORT_SERIAL:
			return "Serial";
		default:
			return "unknown type";
		}
	}

	public static HashSet<CommPortIdentifier> getAvailableSerialPorts() {
		HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
		Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
		while (thePorts.hasMoreElements()) {
			CommPortIdentifier com = (CommPortIdentifier) thePorts
					.nextElement();
			switch (com.getPortType()) {
			case CommPortIdentifier.PORT_SERIAL:
				try {
					CommPort thePort = com.open("CommUtil", 50);
					thePort.close();
					h.add(com);
					System.out.println(com.getName());
				} catch (PortInUseException e) {
					System.out.println("Port, " + com.getName()
							+ ", is in use.");
				} catch (Exception e) {
					System.err.println("Failed to open port " + com.getName());
					e.printStackTrace();
				}
			}
		}
		return h;
	}

}
