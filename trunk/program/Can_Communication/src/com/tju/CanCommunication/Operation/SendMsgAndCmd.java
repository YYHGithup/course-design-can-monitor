//Source file: C:\\Users\\Administrator\\Desktop\\com\\tju\\CanCommunication\\Operation\\SendMsgAndCmd.java

package com.tju.CanCommunication.Operation;

import com.tju.CanCommunication.Communication.Command;
import com.tju.CanCommunication.Communication.ReceiveAnswer;
import com.tju.CanCommunication.Communication.Rs232Command;


public class SendMsgAndCmd 
{
   
   /**
    * @roseuid 5193494D02CA
    */
   public SendMsgAndCmd() 
   {
    
   }
   
   /**
    * @roseuid 519353770011
    * 发送标准11bit数据帧，包括数据
    * 使用t命令,在界面类中必须添加检验函数，value值的第一个字符是否是"t"
    */
   public void sendStandard11WithData(String value) 
   {

		if (CanInformation._open == true) {
			Command newCmd = new Command(value);
			Rs232Command rs232 = new Rs232Command(newCmd,
					CanInformation._portName);
			ReceiveAnswer ans = rs232.sendCommand();
		}
   }
   
   /**
    *发送11位数据帧，不加数据部分
    *同样需要在界面类中添加判断函数，看发送数据第一位知否是“r”
    */
   public void sendStandard11WithoutData(String value) 
   {
	   if (CanInformation._open == true) {
			Command newCmd = new Command(value);
			Rs232Command rs232 = new Rs232Command(newCmd,
					CanInformation._portName);
			ReceiveAnswer ans = rs232.sendCommand();
		}
   }
   
   /**
    * @roseuid 5193540202DE
    * 发送29位贷数据的扩展数据帧，需要带数据帧
    * 需要在界面类中添加哦岸段函数，发送第一位是否是'T'
    */
   public void sendExtend29WithoutData(String value) 
   {
	   if (CanInformation._open == true) {
			Command newCmd = new Command(value);
			Rs232Command rs232 = new Rs232Command(newCmd,
					CanInformation._portName);
			ReceiveAnswer ans = rs232.sendCommand();
		}
   }
   
   /**
    * @roseuid 5193550201B4
    */
   public void sendExtend29WithData(String value) 
   {
	   if (CanInformation._open == true) {
			Command newCmd = new Command(value);
			Rs232Command rs232 = new Rs232Command(newCmd,
					CanInformation._portName);
			ReceiveAnswer ans = rs232.sendCommand();
		}
   }
   
   /**
    * @roseuid 519355330092
    * 从文件获得命令
    */
   public void getCmdFromFile(String value)
   {
	
   }
   
   /**
    * @roseuid 5193554200B0
    * 添加信号
    */
   public void addSignal() 
   {
	   
   }
   
   /**
    * @roseuid 519355A60308
    */
   public void addMsg() 
   {
    
   }
   
   /**
    * @roseuid 519355B701A0
    */
   public void addSignalToMsg() 
   {
    
   }
}
