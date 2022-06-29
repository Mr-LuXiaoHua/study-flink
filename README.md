### Flink学习记录
***
### 常用命令
```shell script
1.提交flink应用
bin/flink run -m 127.0.0.1:8081 -c com.example.datastream.wordcount.DataStreamApiWordCountStream -p 4 /opt/apps/study-flink-1.0.jar

-m 指定JobManager的地址
-c 指定要运行的类
-p 指定并行度

```

### Flink案例

* 使用DataStream 流批一体API统计单词数量
```shell script
com.example.datastream.wordcount.DataStreamApiWordCountBatch  --从文件读取数据进行单词统计
com.example.datastream.wordcount.DataStreamApiWordCountStream --从Kafka消费数据进行单词统计
```


* 基于 DataStream API 实现欺诈检测
```shell script
1.案例来源于 https://nightlies.apache.org/flink/flink-docs-release-1.14/docs/try-flink/datastream/
2.数据源改造成Kafka
3.核心代码  com.example.datastream.frauddetection.FraudDetectionJob
```