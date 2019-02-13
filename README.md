# songchanghui--trains--java
Trains is a Java library that can be used to  to help the railroad provide its customers with information about the routes.
The local commuter railroad services a number of towns in Kiwiland.  Because of monetary concerns, all of the tracks are 'one-way.'  That is,
a route from Kaitaia to Invercargill does not imply the existence of a route from Invercargill to Kaitaia.  
In fact, even if both of these routes do happen to exist, they are distinct and are not necessarily the same distance!
##Trains Goals 工程目标
* The distance of the route A-B-C.
* The distance of the route A-D.
* The distance of the route A-D-C.
* The distance of the route A-E-B-C-D.
* The distance of the route A-E-D.
* The number of trips starting at C and ending at C with a maximum of 3 stops.  In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
* The number of trips starting at A and ending at C with exactly 4 stops.  In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
* The length of the shortest route (in terms of distance to travel) from A to C.
* The length of the shortest route (in terms of distance to travel) from B to B.
* The number of different routes from C to C with a distance of less than 30.  In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
---

##Getting started 快速使用

###API 接口说明
---
 接口所在包 com.thoughtworks.graphx.trains

* 1 TrainsAPI.getDistance(String[] vertex).
   计算n个地点之间的距离
    1. The distance of the route A-B-C.
    2. The distance of the route A-D.
    3. The distance of the route A-D-C.
    4. The distance of the route A-E-B-C-D.
    5. The distance of the route A-E-D.
       
            TrainsAPI trainsAPI = new TrainsImpl();
            String[] in = {"A", "B", "C"};
            String distance = trainsAPI.getDistance(in);
            
* 2  TrainsAPI.getTripsLessInStops(String startVertex, String endVertex, int maxStops);
    计算两个地点之间（不超过n站路）的全部路径
    
    1. The number of trips starting at C and ending at C with a maximum of 3 stops.
       
            TrainsAPI trainsAPI = new TrainsImpl();
            String number = trainsAPI.getTripsLessInStops("C", "C", 3);
             
* 3 TrainsAPI.getTripsInExactlyStops(String startVertex, String endVertex, int exactlyStops);
    计算两个地点之间（n站路）的全部路径
    
    1. The number of trips starting at A and ending at C with exactly 4 stops.
    
            TrainsAPI trainsAPI = new TrainsImpl();
            String number = trainsAPI.getTripsInExactlyStops("A", "C", 4);
* 4 TrainsAPI.getShortestDistance(String startVertex, String endVertex, int maxDistance);
    计算两个地点之间的最短距离（Dijkstra算法）
    
    1. The length of the shortest route (in terms of distance to travel) from A to C.
    2. The length of the shortest route (in terms of distance to travel) from B to B.
  
            TrainsAPI trainsAPI = new TrainsImpl();
            String length = trainsAPI.getShortestDistance("A", "C");
* 5 TrainsAPI.getTripsLessInDistance(String startVertex, String endVertex, int maxDistance);
    计算两个地点之间（不超过最大距离）的全部路径
    
    1. The number of different routes from C to C with a distance of less than 30.
      
            TrainsAPI trainsAPI = new TrainsImpl();
            String number = trainsAPI.getTripsLessInDistance("C", "C", 30);
            
###Test 测试说明
---

* MainTest
    整体问题测试类:用以测试Trains Goals全部问题
    
    input: 
    Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
    List: {"A", "B", "C"}{"A", "D"}{"A", "D", "C"}{"A", "E", "B" , "C" ,"D"{"A", "E", "D"}{"C", "C", "3"}{"A", "C", "4"}"A", "C"}{"B", "B"}{"C", "C" ,"30"})
    output:[9, 5, 13, 22, NO SUCH ROUTE, 2, 3, 9, 9, 7]
    
* com.thoughtworks.graphx.trains.TrainsAPITest
    接口测试包:测试全部接口的的结果
    
* com.thoughtworks.graphx.trains.GraphLoaderTest
    图装载测试类:通过此测试类测试图装载过程
    
* com.thoughtworks.graphx.util.SerializeUtilTest
    图序列化反序列化测试类:通过此类测试Graph的序列化与反序列化
    
## 工程说明
    工程包含trains-api和trains-graph 子工程
* trains-api
    为用户提供接口服务，包含接口、接口测试
* trains-graph
    核心子工程，包含，Graph,Pregel计算模型，及算法包
    1. 使用代理工厂模式, GraphProxyFactory构建Graph图
        >> 使用工厂创建图代理对象，可以对其进行扩展，通过扩展可以加载其他类型图文件，目前仅支持AB5类型的数据；
        使用切面，通过对加载方法的增强，构建一个完整的图；
        对已构建的图进行java序列化，持久到磁盘，再次使用时，无需对图进行再次装载，对齐进行反序列化即可；
        对已修改的图需要重新构图。
        
    2. 使用Pregel计算模型
        >> Pregel在概念模型上遵循BSP模型，整个计算过程由若干顺序执行的超级步（Super Step）组成，系统从一个“超级步”迈向下一个“超级步”，直到达到算法的终止条件;
        Pregel在编程模型上遵循以图节点为中心的模式，在超级步S中，每个图节点可以汇总从超级步S-1中其他节点传递过来的消息，改变图节点自身的状态，并向其他节点发送消息，这些消息经过同步后，会在超级步S+1中被其他节点接收并做出处理;
        Pregel包含3个抽象方法：
        initialMessage:初始化消息
        vertexProgram:处理接收的消息
        pathFilter:消息传递路径过滤函数
        实现具体算法时，需要实现以上3个抽象方法即可，计算时使用Pregel的run方法执行算法。
        
     3. 算法包
         >>工程中提供的算法包:com.thoughtworks.graphx.lib，通过实现Pregel扩展算法库;
         目前实现的算法有:
         DijkstraShortestPath：使用Dijkstra计算队短路问题;
         Distance:计算n个地点之间的距离;
         TripsExactlyStops:计算两个地点之间（n站路）的全部路径;
         TripsMaxDistance:计算两个地点之间（不超过最大距离）的全部路径;
         TripsMaxStops:计算两个地点之间（不超过n站路）的全部路径;
         
## 配置项说明  
         系统配置文件,目录:trains-graph/resources/config.properties 
         graph_file_path:定义图数据路径
         graph_file_regex:定义图数据-切分-正则表达式
         graph_file_regex_limit:定义图数据-切分-正则表达式的切分数量 

## 开发环境
* JDK :jdk 1.8
* IntelliJ IDEA :15.03
* 操作系统 :win 10

## 目录结构
* Sources:src/main/java
* Resources:src/main/resources
* test:src/test

## 设计思路
### 题目
看到题目后，立即发现此问题为图论中，有向图单源路径问题，包含距离问题，路径，最短路几种题目。

### 设计
问题定义后，此题的关键点在于，数据结构及算法。
#### 数据结构
1. 在邻接矩阵和邻接表之间再三考虑后,最终借鉴Google的pregel图数据结构，遗憾之处是目前并未实现分布式数据结构。
2. 根据面向对象的思想，一个图 应该包含点、边 以及之间采用消息机制传递的消息数据。因此图的几个重要点已经确定，edge 边
    vertex 点，edgeTriplet 边三元组(消息数据)。
3. 图构建时，应尽可能方便扩展，扩展时应尽量简单，因此把构建图和解析文件分开，在图数据文件发生改变，只需实现解析文件，生成点和边的集合即可完成扩展。
4. 图构建后，为方便下次使用，采用java序列化的方案，将图持久化。

#### 算法
1. 数据结构已经确定，只需要在此数据结构上完成计算功能，以顶点为核心传递处理消息完成pregel的计算，在图上计算很多，因此考虑的首要问题是，应该满足算法的扩展。
最终采用模板模式完成pregel的模型，并抽象出若干方法，如 消息处理，控制超步计算结束，过滤路径的方法交由具体的算法实现。

#### 尾语
最终实现了此次设计。
1. 面向接口开发
2. 方便扩展图文件的加载
3. 方便扩展图计算算法

    


      

    
    