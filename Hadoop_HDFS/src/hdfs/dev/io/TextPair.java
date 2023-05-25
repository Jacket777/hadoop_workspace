package hdfs.dev.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.Text;

/*
 * 开发详解--5.6.自定义一个Writable类型的TextPair
 */

public class TextPair implements WritableComparable<TextPair>{
	private Text first;
	private Text second;
	
	public TextPair() {
		set(new Text(),new Text());}
	
	
	public TextPair(String first,String second) {
		set(new Text(first),new Text(second));}
	
	
	public TextPair(Text first,Text second) {
		set(first,second);}
	
	
	public void set(Text first,Text second) {
		this.first = first;
		this.second = second;}
	

	public Text getFirst() {
		return first;
	}

	public void setFirst(Text first) {
		this.first = first;
	}

	public Text getSecond() {
		return second;
	}

	public void setSecond(Text second) {
		this.second = second;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		first.readFields(in);
		second.readFields(in);	
	}

	@Override
	public void write(DataOutput out) throws IOException {
		first.write(out);
		second.write(out);
	}
	
	@Override
	public int hashCode() {
		return first.hashCode()*163+second.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof TextPair) {
			TextPair tp = (TextPair)o;
			return first.equals(tp.first)&&second.equals(tp.second);
		}
		return false;
	}

	@Override
	public String toString() {
		return first+"\t"+second;
	}
	
	@Override
	public int compareTo(TextPair tp) {
		int cmp = first.compareTo(tp.first);
		if(cmp!=0) {
			return cmp;
		}
		return second.compareTo(tp.second);
	}	
	
	
	
	public static class FirstComparator extends WritableComparator{
		private static final Text.Comparator TEXT_COMPARATOR = new Text.Comparator();
//		public FirstComparator() {
//			super(FirstComparator.class);
//		}
		
	}
}


	
	
	
	
	
	
	
