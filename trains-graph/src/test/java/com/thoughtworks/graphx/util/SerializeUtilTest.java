package com.thoughtworks.graphx.util;

import com.thoughtworks.graphx.Graph;
import com.thoughtworks.graphx.factory.GraphProxyFactory;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * 序列化 反序列化测试
 * Created by songchanghui on 2019/2/13.
 */
public class SerializeUtilTest {
    public static Logger logger = Logger.getLogger(SerializeUtilTest.class.toString());
    @Test
    public void serializeTest(){
        try {
            logger.info("动态代理工厂->创建GraphLoader,切面构建Graph");
            Graph graph = GraphProxyFactory.newGraph();
            logger.info("图顶点数:"+graph.getVertices().size());
            logger.info("图边数:"+graph.getEdges().size());
            //序列化graph
            SerializeUtil.serialize(graph,GraphProxyFactory.SERIALIZE_PATH);
            Graph graph1 = SerializeUtil.unSerialize(GraphProxyFactory.SERIALIZE_PATH,Graph.class);
            logger.info("图顶点数:"+graph1.getVertices().size());
            logger.info("图边数:"+graph1.getEdges().size());
        } catch (IllegalAccessException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (URISyntaxException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
    }

}
