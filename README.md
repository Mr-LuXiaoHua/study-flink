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

##### 使用 DataStream 流批一体API统计单词数量
```shell script
com.example.datastream.wordcount.DataStreamApiWordCountBatch  --从文件读取数据进行单词统计
com.example.datastream.wordcount.DataStreamApiWordCountStream --从Kafka消费数据进行单词统计


bin/flink run -m 127.0.0.1:8081 -c com.example.datastream.wordcount.DataStreamApiWordCountBatch -input /mnt/data/words.txt /opt/apps/study-flink-1.0.jar
-input 指定输入文件路径

```


##### 基于 DataStream API 实现欺诈检测
```shell script
1.案例来源于 https://nightlies.apache.org/flink/flink-docs-release-1.14/docs/try-flink/datastream/
2.数据源改造成Kafka
3.核心代码  com.example.datastream.frauddetection.FraudDetectionJob
```

##### 基于 DataStream API 记录一次完整Taxi行程(包括车程和付车费)
```shell script
1.案例来源于 https://github.com/apache/flink-training/blob/release-1.14/rides-and-fares/README_zh.md
2.对于一次Taxi行程, 如果包含车程开始事件和付车费事件,则认为是一次完整的Taxi行程, 对本次行程进行输出
3.核心代码  com.example.datastream.rideandfare.RideAndFareJob
```

##### 基于 DataStream API 计算每小时赚取最多小费的司机
```shell script
1.案例来源于 https://github.com/apache/flink-training/blob/release-1.14/hourly-tips/README_zh.md
2.首先使用一个小时长的窗口来计算每个司机在一小时内的总小费, 然后从该窗口结果流中找到每小时总小费最多的司机
3.核心代码  com.example.datastream.hourlytips.HourlyTipsJob
```
