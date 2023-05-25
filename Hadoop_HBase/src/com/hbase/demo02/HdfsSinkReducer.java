package com.hbase.demo02;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

/*
 * 实例程序：从HBase读数据到HDFS
 * 4.Reducer处理map中数据
 * 把map里面的一行数据继续写出去
 */

public class HdfsSinkReducer extends Reducer<Text,NullWritable,Text,NullWritable>{
	@Override
	protected void reduce(Text key,Iterable<NullWritable>values,Context context)
	throws IOException,InterruptedException{
		context.write(key, NullWritable.get());
	}
}
