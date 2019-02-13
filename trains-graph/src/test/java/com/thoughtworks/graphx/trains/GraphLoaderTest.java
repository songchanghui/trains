package com.thoughtworks.graphx.trains;

import com.thoughtworks.graphx.Graph;
import com.thoughtworks.graphx.factory.GraphProxyFactory;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * 图装载-测试类
 * Created by songchanghui on 2019/2/10.
 */
public class GraphLoaderTest {
    public static Logger logger = Logger.getLogger(GraphLoaderTest.class.toString());
    @Test
    public void graphTextFileLoaderTest() {
        try {
            logger.info("动态代理工厂->创建GraphLoader,切面构建Graph");
            Graph graph = GraphProxyFactory.newGraph();
            logger.info("图顶点数:"+graph.getVertices().size());
            logger.info("图边数:"+graph.getEdges().size());
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
