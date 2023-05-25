package com.ch05.io;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.junit.Test;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.util.StringUtils;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class StringTextSetTest {
	@Test
	public void testSet()throws UnsupportedEncodingException{
		Text t = new Text("hadoop");
		t.set("pig");
		assertThat(t.getLength(),is(3));
		assertThat(t.getBytes().length, is(3));
	}
	
	
	@Test
	public void testWarning() {
		Text t = new Text("hadoop");
		t.set(new Text("pig"));
		assertThat(t.getLength(),is(3));
		assertThat("Byte length not shortened",t.getBytes().length, is(6));
	}
	
	
	@Test
	public void testToString() {
		assertThat(new Text("hadoop").toString(), is("hadoop"));
	}
	
	@Test
	public void testToByteWritable() throws IOException{
		BytesWritable b = new BytesWritable(new byte[] {6,7});
		byte[]bytes = serialize(b);
		assertThat(StringUtils.byteToHexString(bytes), is("000000020607"));
		b.setCapacity(11);
		assertThat(b.getLength(), is(2));
		assertThat(b.getBytes().length, is(11));	
	}
	
	
	
	public static byte[]serialize(Writable writable)throws IOException{
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		DataOutputStream dataOut= new DataOutputStream(out);
		writable.write(dataOut);
		dataOut.close();
		return out.toByteArray();
	}
}