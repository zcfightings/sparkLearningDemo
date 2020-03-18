package com.meitu.zhaochen.spark

import org.apache.spark.{SparkConf, SparkContext}

object SparkWordCount {
  def main(args: Array[String]): Unit = {
    //这里创建一个配置，
    //在配置中设定了运行模式为local（参见spark的运行模式）
    //设定作业名字，这个名字是在web上可见的，方便yarn web上快速找到该作业
    val conf = new SparkConf().setMaster("local[*]").setAppName("SparkWordCount")
    //初始化SparkContext,它是spark程序的灵魂和入口，包含了spark的配置、作业的配置、上下文环境等 。
    val sc = new SparkContext(conf)
    //使用textFile读取一个text文件至rdd中。当前数据格式为Rdd[String]
    val rdd = sc.textFile("file:///Users/zc/Desktop/wordCount.txt")
    //flatMap为一个输入 多个输出 将目的是将（"hello world"）变为 （"hello"）("world")
    //map是一进一出，对flatMap的结果做处理，将（"hello"）("world")映射为（"hello",1）("world",1)
    //reduceByKey，相当于按key做group by，然后对valueList做括号中的操作，
    //例子做为_+_，代表对valueList迭代求和
    val counts = rdd.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
    //将最终结果保存到本地目录
    counts.saveAsTextFile("file:///Users/zc/Desktop/output")
  }


}
