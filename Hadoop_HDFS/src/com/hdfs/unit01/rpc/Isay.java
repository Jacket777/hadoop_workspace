package com.hdfs.unit01.rpc;
/*
 * ��Ŀʵս--����Isay�ӿ�
 */
public interface Isay {
	/*
	 * ����say����
	 * @param userName �û�����
	 * @return ��Ӧ��
	 */
	public  static final long versionID = 1L;
	public String say(String userName);
}
