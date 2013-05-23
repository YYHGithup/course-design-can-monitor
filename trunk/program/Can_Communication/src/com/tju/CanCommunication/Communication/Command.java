//Source file: C:\\Users\\Administrator\\Desktop\\com\\tju\\CanCommunication\\Communication\\Command.java

package com.tju.CanCommunication.Communication;

public class Command {
	private char _cmdChar;
	private String _sourceData;
	private String _transformedData;
	private char _endChar = '\r';
	private String _sourceString;
	private String _finalString;

	/**
	 * @param sourceString
	 * @roseuid 51947F830272
	 */
	public Command(String sourceString) {
		_sourceString = sourceString;
		_cmdChar = _sourceString.charAt(0);
		_sourceData = _transformedData = _sourceString.substring(1);
		_finalString = _sourceString + _endChar;

	}

	/**
	 * @roseuid 51947759033B
	 */
	public Command() {

	}

	/**
	 * @roseuid 519650F202FC
	 */
	public void transformData() {

	}

	/**
	 * @return java.lang.String
	 * @roseuid 5196510F00AD
	 */
	public String getSourceData() {
		return _sourceData;
	}

	/**
	 * @return char
	 * @roseuid 51965125039D
	 */
	public char getCmdChar() {
		return _cmdChar;
	}

	/**
	 * @return java.lang.String
	 * @roseuid 5196513700F4
	 */
	public String getSourceString() {
		return _sourceString;
	}

	/**
	 * @return java.lang.String
	 * @roseuid 5196514B0014
	 */
	public String getFinalString() {
		return _finalString;
	}

	/**
	 * @return java.lang.String
	 * @roseuid 519651610058
	 */
	public String getTransformedData() {
		return _transformedData;
	}

	/**
	 * @return char
	 * @roseuid 519651700284
	 */
	public char getEndChar() {
		return _endChar;
	}
}
