//Source file: C:\\Users\\Administrator\\Desktop\\com\\tju\\CanCommunication\\Communication\\ReceiveAnswer.java

package com.tju.CanCommunication.Communication;


public class ReceiveAnswer 
{
   private String _ansString;
   private String _dataString;
   private String _cmdString;
   private String _stateString;
   
   /**
    * @param ansStr
    * @roseuid 51947FB20284
    */
   public ReceiveAnswer(String ansStr)
   {
	   _ansString = ansStr;
	   
    
   }
   
   /**
    * @roseuid 51947A51001E
    */
   public ReceiveAnswer() 
   {
    
   }
   
   /**
    * @return java.lang.String
    * @roseuid 51965377012E
    */
   public String getAnsString() 
   {
    return _ansString;
   }
   
   /**
    * @return java.lang.String
    * @roseuid 51965394002D
    */
   public String getDataString() 
   {
    return _dataString;
   }
   
   /**
    * @return java.lang.String
    * @roseuid 519653A10284
    */
   public String getCmdString() 
   {
    return _cmdString;
   }
}
