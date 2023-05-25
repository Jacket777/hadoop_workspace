package hdfs.dev.io;
import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path; 

//д����
public class WriteData {
	public static void main(String[] args) {
        try {
            Configuration conf = new Configuration();  
            conf.set("fs.default.name","hdfs://192.168.150.130:9000");
            conf.set("fs.hdfs.impl","org.apache.hadoop.hdfs.DistributedFileSystem");
            FileSystem fs = FileSystem.get(conf);
            byte[] buff = "Hello world55555".getBytes(); // Ҫд�������
            String filename = "test4"; //Ҫд����ļ���
            FSDataOutputStream os = fs.create(new Path(filename));
            os.write(buff,0,buff.length);
            System.out.println("Create:"+ filename);
            os.close();
            fs.close();
    } catch (Exception e) {  
            e.printStackTrace();  
    }  
	}
}