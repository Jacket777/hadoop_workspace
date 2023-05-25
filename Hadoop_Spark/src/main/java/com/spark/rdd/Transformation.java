package com.spark.rdd;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

public class Transformation {
	
	public static void map() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("Map Application");
		JavaSparkContext sc = new JavaSparkContext(conf);
		//模拟集合,使用并行化的方式创建RDD
		List<String>asList = Arrays.asList("Jack","Tom","Marry");
		JavaRDD<String>listRDD = sc.parallelize(asList);
		JavaRDD<String>map = listRDD.map(new Function<String,String>(){
			public String call(String str) throws Exception {
				return "hello: "+str;
			}
		});
		//打印
        map.foreach(new VoidFunction<String>() {
			public void call(String t) throws Exception {
				System.out.println(t);	
			}	
        });	
	}

	
	/*
	 * 模拟一个集合，过滤出集合里面所有的偶数 
	 */
	public static void filter() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("filter application");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);
		JavaRDD<Integer>listRDD = sc.parallelize(list);
		JavaRDD<Integer> filter = listRDD.filter(new Function<Integer,Boolean>(){
			//返回布尔值
			public Boolean call(Integer i) throws Exception {
				return i%2==0;
			}
		});
		filter.foreach(new VoidFunction<Integer>() {
			public void call(Integer t) throws Exception {
				System.out.println(t);	
			}
		});		
	}
	
	
	/*
	 * 模拟一个集合
	 * 把每个单词独立拆开，然后每个单词为一行
	 */
	public static void flatMap() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("flatMap Application");
		JavaSparkContext sc = new JavaSparkContext(conf);
		//模拟集合,使用并行化的方式创建RDD
		List<String>list = Arrays.asList("i	jump","Marry	jump");
		JavaRDD<String>listRDD = sc.parallelize(list);
		//U代表返回值
		JavaRDD<String>flatMap = listRDD.flatMap(new FlatMapFunction<String,String>(){
			public Iterator<String> call(String t) throws Exception {
				String test[]=t.split("\t");
				return Arrays.asList(test).iterator();
			}	
		});

		flatMap.foreach(new VoidFunction<String>() {
				public void call(String t) throws Exception {
					System.out.println(t);	
				}	
	        });	
	}
	
	

	/*
	 * 4..根据key进行分组，数据应该是key-value分组
	 * Tuple2对象类似于Java里面的map对象
	 */
	public static void groupByKey() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("groupByKey");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
        List<Tuple2<String, String>>list =  Arrays.asList(
				new Tuple2<String, String>("USA", "Jack"),
				new Tuple2<String, String>("USA", "Tom"),
				new Tuple2<String, String>("UK", "Tim"),
				new Tuple2<String, String>("UK", "May"),
				new Tuple2<String, String>("USA", "Marry")
				);
        JavaPairRDD<String, String> listRDD = sc.parallelizePairs(list);
       JavaPairRDD<String, Iterable<String>> groupByKey = listRDD.groupByKey();
       groupByKey.foreach(new VoidFunction<Tuple2<String,Iterable<String>>>() {
		public void call(Tuple2<String, Iterable<String>> t) throws Exception {
			System.out.println(t._1);
			Iterator<String>iterator =t._2.iterator();
			while(iterator.hasNext()) {
				System.out.println(iterator.next()+" ");
			}
			System.out.println("----------------------");	
		}	   
	});
	}
	
	
	public static void reduceBykey() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("reduceBykey");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
        List<Tuple2<String, Integer>>list =  Arrays.asList(
				new Tuple2<String, Integer>("USA", 30),
				new Tuple2<String, Integer>("USA", 40),
				new Tuple2<String, Integer>("UK", 12),
				new Tuple2<String, Integer>("UK", 22),
				new Tuple2<String, Integer>("USA", 23)
				);
        JavaRDD<Tuple2<String, Integer>> listRDD = sc.parallelize(list);
        JavaPairRDD<String, Integer> mapToPair = listRDD.mapToPair(new PairFunction<Tuple2<String,Integer>, String, Integer>() {
			public Tuple2<String, Integer> call(Tuple2<String, Integer> t) throws Exception {
				return new Tuple2<String, Integer>(t._1, t._2);
			}
		});
        /*Integer, Integer, Integer
         * 第三个Integer为返回值类型
         * scala.reduceBykey(_+_)
         */
        JavaPairRDD<String, Integer> reduceByKey = mapToPair.reduceByKey(new Function2<Integer, Integer, Integer>() {
			public Integer call(Integer v1, Integer v2) throws Exception {
				return v1+v2;
			}
		});
        
        reduceByKey.foreach(new VoidFunction<Tuple2<String,Integer>>() {
			public void call(Tuple2<String, Integer> t) throws Exception {
				System.out.println(t._1+":"+t._2);	
			}
		});   
	}
	
	
	public static void sortBykey() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("sortBykey");
		JavaSparkContext sc = new JavaSparkContext(conf);
	       List<Tuple2<Integer,String>>list =  Arrays.asList(
					new Tuple2<Integer,String>(30,"USA"),
					new Tuple2<Integer,String>(40,"JPN"),
					new Tuple2<Integer,String>(12,"EN"),
					new Tuple2<Integer,String>(10,"FRN"),
					new Tuple2<Integer,String>(8, "RUSSIA")
					);
	       JavaPairRDD<Integer, String> listRDD = sc.parallelizePairs(list);
	       listRDD.sortByKey(false).foreach(new VoidFunction<Tuple2<Integer,String>>() {
			public void call(Tuple2<Integer, String> t) throws Exception {
				System.out.println(t._2+":"+t._1);	
			}
		});	
	}
	
	
	public static void join() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("join");
		JavaSparkContext sc = new JavaSparkContext(conf);
	    List<Tuple2<Integer,String>>listname =  Arrays.asList(
					new Tuple2<Integer,String>(1,"Jack"),
					new Tuple2<Integer,String>(2,"Tom"),
					new Tuple2<Integer,String>(3,"Marrt")
					);
	    List<Tuple2<Integer,Integer>>listscore =  Arrays.asList(
					new Tuple2<Integer,Integer>(1,80),
					new Tuple2<Integer,Integer>(2,90),
					new Tuple2<Integer,Integer>(3,100)
					);
	    JavaPairRDD<Integer, String>listnameRDD = sc.parallelizePairs(listname);
	    JavaPairRDD<Integer, Integer>listscoreRDD = sc.parallelizePairs(listscore);
	    JavaPairRDD<Integer, Tuple2<String, Integer>> join = listnameRDD.join(listscoreRDD);
	    join.foreach(new VoidFunction<Tuple2<Integer,Tuple2<String,Integer>>>() {
			public void call(Tuple2<Integer, Tuple2<String, Integer>> t) throws Exception {
				System.out.println("编号:"+t._1);
				System.out.println("姓名:"+t._2._1);
				System.out.println("得分:"+t._2._2);	
			}
		});	
	}
	
	
	public static void cogroup() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("join");
		JavaSparkContext sc = new JavaSparkContext(conf);
	    List<Tuple2<Integer,String>>listname =  Arrays.asList(
					new Tuple2<Integer,String>(1,"Jack"),
					new Tuple2<Integer,String>(2,"Tom"),
					new Tuple2<Integer,String>(3,"Marrt")
					);
	    List<Tuple2<Integer,Integer>>listscore =  Arrays.asList(
					new Tuple2<Integer,Integer>(1,80),//两门考试的分数
					new Tuple2<Integer,Integer>(2,90),
					new Tuple2<Integer,Integer>(3,100),
					new Tuple2<Integer,Integer>(1,98),//两门考试的分数
					new Tuple2<Integer,Integer>(2,79),
					new Tuple2<Integer,Integer>(3,85)
					
					);
	    JavaPairRDD<Integer, String>listnameRDD = sc.parallelizePairs(listname);
	    JavaPairRDD<Integer, Integer>listscoreRDD = sc.parallelizePairs(listscore);
	    //<1,tuple2<"Jack", {80,98}>
	    JavaPairRDD<Integer, Tuple2<Iterable<String>, Iterable<Integer>>> cogroup = listnameRDD.cogroup(listscoreRDD);
	    cogroup.foreach(new VoidFunction<Tuple2<Integer,Tuple2<Iterable<String>,Iterable<Integer>>>>() {
			public void call(Tuple2<Integer, Tuple2<Iterable<String>, Iterable<Integer>>> t) throws Exception {
				System.out.println("编号:"+t._1);
				Iterator<String>names = t._2._1.iterator();
			  //System.out.println("姓名集合:"+names);
				System.out.println("姓名集合:"+t._2._1);
				Iterator<Integer>scores = t._2._2.iterator();
			  //System.out.println("得分单:"+scores);
				System.out.println("得分单:"+t._2._2);	
			}
		});
	}
	
	/*
	 * 合并RDD，但不去重
	 */
	public static void union() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("union");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>lista = Arrays.asList(1,2,3,4);
		List<Integer>listb = Arrays.asList(4,5,6,7);
	    JavaRDD<Integer>listaRDD= sc.parallelize(lista);
	    JavaRDD<Integer>listbRDD = sc.parallelize(listb);
	    JavaRDD<Integer>union = listaRDD.union(listbRDD);
	    union.foreach(new VoidFunction<Integer>() {
			public void call(Integer t) throws Exception {
				System.out.println(t);	
			}
		});    
	}
	
	/*
	 * 求两个RDD交集
	 */
	public static void Intersection(){
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("Intersection");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>lista = Arrays.asList(1,2,3,4);
		List<Integer>listb = Arrays.asList(4,5,6,7);
	    JavaRDD<Integer>listaRDD= sc.parallelize(lista);
	    JavaRDD<Integer>listbRDD = sc.parallelize(listb);
	    JavaRDD<Integer> interRdd=listaRDD.intersection(listbRDD);
	    interRdd.foreach(new VoidFunction<Integer>() {
			public void call(Integer t) throws Exception {
				System.out.println(t);	
			}
		});  
	}
	

	/*
	 * 去重
	 */
	public static void distinct() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("Intersection");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>lista = Arrays.asList(1,2,3,4,44,4,4,5);
	    JavaRDD<Integer>listaRDD= sc.parallelize(lista);
	    JavaRDD<Integer> distRdd=listaRDD.distinct();
	    distRdd.foreach(new VoidFunction<Integer>() {
			public void call(Integer t) throws Exception {
				System.out.println(t);	
			}
		});	
	}
	
	
	/*
	 * 求笛卡积
	 */
	public static void cartesian() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("Intersection");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>lista = Arrays.asList(1,2,3,4);
		List<String>listb = Arrays.asList("a","b","c");
	    JavaRDD<Integer>listaRDD= sc.parallelize(lista);
	    JavaRDD<String>listbRDD = sc.parallelize(listb);
	    JavaPairRDD<Integer, String> cartesian=listaRDD.cartesian(listbRDD);
	    cartesian.foreach(new VoidFunction<Tuple2<Integer,String>>() {
			public void call(Tuple2<Integer, String> t) throws Exception {
				System.out.println(t._1+" "+t._2);
			}
		});
	}
	
	
	public static void mapPartitions() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("mapPartitions");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>list = Arrays.asList(1,2,3,4,5,6);
		JavaRDD<Integer>listRDD = sc.parallelize(list,2);//partition 1:123,partition 2:456
		listRDD.mapPartitions(new FlatMapFunction<Iterator<Integer>,String>() {
			// 每次处理分区数
			public Iterator<String> call(Iterator<Integer> t) 
					throws Exception {
				ArrayList<String> list = new ArrayList<String>();
				while(t.hasNext()) {
					Integer i = t.next();
					list.add("hello"+i);
				}
				return list.iterator();
			}	
		}).foreach(new VoidFunction<String>() {
			public void call(String t) throws Exception {
				System.out.println(t);
			}
		});		
	}
	
	
	//repartition coalesce 重新分区，窄依赖，宽依赖，shuffle
	
	/*
	 * filter 过滤之后---partiton数据变少
	 * 100 partition  task
	 * 100->50 partiton task
	 * 这个repartiton分区会进行shuffle操作
	 */
	public static void repartition() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("mapPartitions");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>list = Arrays.asList(1,2,3,4,5,6);
		JavaRDD<Integer>listRDD = sc.parallelize(list);
		listRDD.repartition(2).foreach(new VoidFunction<Integer>() {
			public void call(Integer t) throws Exception {
				System.out.println(t);	
			}
		});	
	}
	
	
	/*
	 * repartition 只是coalesce简易版本
	 * numPartitions:重新分成几个区
	 * shuffle:是否分区
	 */
	public static void coalesce() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("mapPartitions");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>list = Arrays.asList(1,2,3,4,5,6);
		JavaRDD<Integer>listRDD = sc.parallelize(list);
		/*
		 * numPartitions:重新分成几个区
		 * shuffle:是否分区
		 */
		listRDD.coalesce(2, true);	
	}
	
	
	/*
	 * 随机采样
	 */
	public static void sample() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("mapPartitions");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>list = Arrays.asList(1,2,3,4,5,6,7,8,9);
		JavaRDD<Integer>listRDD = sc.parallelize(list);
		/*
		 * 第一个参数: withReplacement:表示放回取样 true表示放回取样，false表示无放回
		 * 第二个参数: fraction:表示比例
		 * 
		 */
		listRDD.sample(true, 0.1).foreach(new VoidFunction<Integer>() {
			public void call(Integer t) throws Exception {
				System.out.println("====================="+t);		
			}	
		});
	}
	
	/*
	 * 单词计数
	 */
	public static void aggregatekey() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("aggregatekey");
		JavaSparkContext sc = new JavaSparkContext(conf);
		//模拟集合,使用并行化的方式创建RDD
		List<String>list = Arrays.asList("i	jump","Marry	jump");
		JavaRDD<String>listRDD = sc.parallelize(list);
		//U代表返回值
	    listRDD.flatMap(new FlatMapFunction<String,String>(){
			public Iterator<String> call(String t) throws Exception {
				String test[]=t.split("\t");
				return Arrays.asList(test).iterator();
			}	
		}).mapToPair(new PairFunction<String,String,Integer>() {
			public Tuple2<String, Integer> call(String t) 
					throws Exception {
				return new Tuple2<String, Integer>(t,1);}	
		})
	    /*其实reduceBy就是aggregateByKey简化版，aggregateByKey多提供了一个函数
	     * 类似于MapReduce的combine操作[就在map端执行reduce的操作]
	     * zeroValue：第一个参数代表的是每个key的初始值
	     * seqFunc:第二个参数 类似的map-side的本地聚合
	     * combFunc:第三个参数 类似于reduce的全局聚合
	     */
	    .aggregateByKey(0, new Function2<Integer,Integer,Integer>(){
			public Integer call(Integer v1, Integer v2) throws Exception {
				return v1+v2;
			}
	    	
	    }, 
	    	new Function2<Integer,Integer,Integer>(){
					public Integer call(Integer v1, Integer v2) throws Exception {
						return v1+v2;
					}
	    });	
	}
	
	
	public static void mapPartitonWithIndex() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("mapPartitions");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>list = Arrays.asList(1,2,3,4,5,6,7,8,9);
		JavaRDD<Integer>listRDD = sc.parallelize(list,2);
		listRDD.mapPartitionsWithIndex(new Function2<Integer,Iterator<Integer>,Iterator<String>>(){
			/*
			 * index 就是分区的索引
			 */
			public Iterator<String> call(Integer index, Iterator<Integer> iterator) 
					throws Exception {
				ArrayList<String> list = new ArrayList();
				while(iterator.hasNext()) {
					String result = iterator.next()+"===="+index;
					list.add(result);	
				}
				return list.iterator();
			}	
		}, true)
		.foreach(new VoidFunction<String>() {
			public void call(String t) throws Exception {
				System.out.println("========="+t);	
			}	
		});
		
	}
	
	
	public static void repartionAndSortWithinPartiton() {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("mapPartitions");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer>list = Arrays.asList(1,2,3,4,5,6,7,8,9);
		JavaRDD<Integer>listRDD = sc.parallelize(list,2);
	}
	
	
	
	public static void main(String[] args) {
		//map();
		//filter();
		//flatMap();
		//groupByKey();
		//reduceBykey();
		//sortBykey();
		//join();
		//cogroup();
		//union();
		//Intersection();
		//distinct();
		//cartesian();
		//mapPartitions();
		//repartition();
		//sample();
		mapPartitonWithIndex();
	}
	
	

}
