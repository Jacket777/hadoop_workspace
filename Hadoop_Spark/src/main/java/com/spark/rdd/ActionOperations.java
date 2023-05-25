package com.spark.rdd;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.cookie.PublicSuffixFilter;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

/*
 * Action各种操作
 */
public class ActionOperations {
	
	public static void reduce() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("mapPartitions");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>list = Arrays.asList(1,2,3,4,5);
		JavaRDD<Integer>listRDD = sc.parallelize(list);
		//reduce是一个action的操作
		Integer reduce = listRDD.reduce(new Function2<Integer,Integer,Integer>(){
			public Integer call(Integer v1, Integer v2) throws Exception {
				return v1+v2;
			}
		});
		System.out.println(reduce);	
	}
	
	/*
	 * 谨慎使用，会将其他地方的数据移到到至新的区，导致内存溢出
	 */
	public static void collect() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("collect");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>lista = Arrays.asList(1,2,3,4);
		List<Integer>listb = Arrays.asList(4,5,6,7);
	    JavaRDD<Integer>listaRDD= sc.parallelize(lista);
	    JavaRDD<Integer>listbRDD = sc.parallelize(listb);
	    JavaRDD<Integer>union = listaRDD.union(listbRDD);
	    List<Integer>collect = union.collect();
	    for(Integer integer:collect) {
	    	System.out.println(integer);
	    }	
	}
	
	public static void take() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("take");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>list = Arrays.asList(1,2,3,4);
		JavaRDD<Integer>listaRDD= sc.parallelize(list);
	    List<Integer>take = listaRDD.take(3);
	    for(Integer integer:take) {
	    	System.out.println(integer);
	    }	
	}
	
	public static void count() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("count");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>list = Arrays.asList(1,2,3,4);
		JavaRDD<Integer>listaRDD= sc.parallelize(list);
	    long count = listaRDD.count();
	    System.out.println(count);	
	}
	
	public static void first() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("first");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer> list = Arrays.asList(100,52,13,56);
		JavaRDD<Integer> listRDD = sc.parallelize(list);
		Integer first = listRDD.first();
		System.out.println(first);	
	}
	
	public static void takeOrdered() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("takeOrdered");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>list = Arrays.asList(1,20,13,4);
		JavaRDD<Integer>listaRDD= sc.parallelize(list);
		List<Integer>num = listaRDD.takeOrdered(3);
	    for(Integer integer:num) {
	    	System.out.println(integer);
	    }
	    List<Integer> top = listaRDD.top(3);
	    for(Integer integer:top) {
	    	System.out.println(integer);
	    }
	}
	
	public static void saveAsTextFile() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("union");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>lista = Arrays.asList(1,2,3,4);
		List<Integer>listb = Arrays.asList(4,5,6,7);
	    JavaRDD<Integer>listaRDD= sc.parallelize(lista);
	    JavaRDD<Integer>listbRDD = sc.parallelize(listb);
	    JavaRDD<Integer>union = listaRDD.union(listbRDD);
	    union.repartition(1).saveAsTextFile("union");//默认保存到HDFS，保存到本地报错
	}
	
	public static void countByKey() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("countBykey");
		JavaSparkContext sc = new JavaSparkContext(conf);
        List<Tuple2<String, Integer>>list =  Arrays.asList(
				new Tuple2<String, Integer>("USA", 30),
				new Tuple2<String, Integer>("USA", 40),
				new Tuple2<String, Integer>("UK", 12),
				new Tuple2<String, Integer>("UK", 22),
				new Tuple2<String, Integer>("USA", 23)
				);
        JavaPairRDD<String, Integer> listRDD = sc.parallelizePairs(list);
        Map<String, Long>countByKey = listRDD.countByKey();
        for(String key:countByKey.keySet()) {
        	System.out.println(key+":"+countByKey.get(key));
        }
        
	}
	
	public static void takeSample() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("takeSample");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>list = Arrays.asList(1,20,13,4);
		JavaRDD<Integer>listRDD= sc.parallelize(list);
		List<Integer> takeSample = listRDD.takeSample(true, 2);
		for(Integer integer: takeSample) {
			System.out.println(integer);
		}

	}
	
	public static void foreach() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("foreach Application");
		JavaSparkContext sc = new JavaSparkContext(conf);
		//模拟集合,使用并行化的方式创建RDD
		List<String>list = Arrays.asList("Jack","Tom","Marry");
		JavaRDD<String>listRDD = sc.parallelize(list);
		listRDD.foreach( new VoidFunction<String>() {
			public void call(String t) throws Exception {
				System.out.println("===>>>==="+t);	
			}
		});	
	}
	
	
	public static void main(String[] args) {
		//reduce();
		//collect();
		//take();
		//count();
		//first();
		//takeOrdered();
		//saveAsTextFile();HDFS运行通过
		//countByKey();
		foreach();
	}

}
