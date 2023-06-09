package com.ch06.mrdev;

import java.util.Map.Entry;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.util.*;

public class ConfigurationPrinter extends Configured implements Tool{
   static {
	   Configuration.addDefaultResource("hdfs-default.xml");
	   Configuration.addDefaultResource("hdfs-site.xml");
	   Configuration.addDefaultResource("yarn-default.xml");
	   Configuration.addDefaultResource("yarn-site.xml");
	   Configuration.addDefaultResource("mapred-default.xml");
	   Configuration.addDefaultResource("mapred-site.xml");	   
   }
	
	
	@Override
	public int run(String[] arg0) throws Exception {
	    Configuration conf = getConf();
	    for(Entry<String,String>entry:conf) {
	    	System.out.printf("%s=%s\n",entry.getKey(),entry.getValue());
	    } 
		return 0;
	}
   
	
	
	public static void main(String[] args)throws Exception {
		int exitCode = ToolRunner.run(new ConfigurationPrinter(), args);
		System.exit(exitCode);
	}

}