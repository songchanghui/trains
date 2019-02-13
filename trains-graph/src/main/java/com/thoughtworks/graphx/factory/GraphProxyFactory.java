package com.thoughtworks.graphx.factory;

import com.thoughtworks.graphx.Graph;
import com.thoughtworks.graphx.handler.Handler;
import com.thoughtworks.graphx.trains.GraphLoader;
import com.thoughtworks.graphx.util.ConfigPropertiesUtil;
import com.thoughtworks.graphx.util.ParmUtil;
import com.thoughtworks.graphx.util.SerializeUtil;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * 图装载-代理工厂
 * 核心类，用于构建Graph
 * Created by songchanghui on 2019/2/11.
 */
public class GraphProxyFactory {
    public static Logger logger = Logger.getLogger(GraphProxyFactory.class.toString());
    public static final String GRAPH_TEXTFILE_LOADER_CLASSNAME = "com.thoughtworks.graphx.trains.impl.GraphTextFileLoader";//
    public static final String GRAPH_LOADER_HANDLER_CLASSNAME = "com.thoughtworks.graphx.handler.GraphLoaderHandler";//
    public static final String GRAPH_FILE_PATH = "graph_file_path";
    public static final String GRAPH_FILE_REGEX = "graph_file_regex";
    public static final String GRAPH_FILE_REGEX_LIMIT = "graph_file_regex_limit";
    public static final String NO_SUCH_ROUTE = "NO SUCH ROUTE";
    public static final String GRAPH_FILE_SPLIT = ">";
    public static final String SERIALIZE_PATH = "graph.obj";


    public static Graph newGraph() throws IllegalAccessException, ClassNotFoundException, InstantiationException, IOException, URISyntaxException {
        String file_path = ConfigPropertiesUtil.get(GRAPH_FILE_PATH);
        String regex = ConfigPropertiesUtil.get(GRAPH_FILE_REGEX, "");
        int limit = Integer.parseInt(ConfigPropertiesUtil.get(GRAPH_FILE_REGEX_LIMIT));
        Graph graph = newGraphLoader().loadFile(file_path, regex, limit);
        SerializeUtil.serialize(graph,SERIALIZE_PATH);
        return graph;
    }

    /**
     * 获取graph
     * @return
     */
    public static Graph getGraph() throws ClassNotFoundException, IOException, URISyntaxException, InstantiationException, IllegalAccessException {
        Graph graph = SerializeUtil.unSerialize(SERIALIZE_PATH,Graph.class);
        if(graph == null){
            graph = newGraph();
        }
        return graph;
    }
    /**
     * 获取默认 GraphLoader
     *
     * @return
     */
    public static String getDefultGraphLoaderClassName() {
        return GRAPH_TEXTFILE_LOADER_CLASSNAME;
    }

    /**
     * 获取默认 GraphLoader Handler
     *
     * @return
     */
    public static String getDefultGraphLoaderHandlerClassName() {
        return GRAPH_LOADER_HANDLER_CLASSNAME;
    }

    /**
     * 代理 创建GraphLoader
     *
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static GraphLoader newGraphLoader() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return newGraphLoader(getDefultGraphLoaderClassName(), getDefultGraphLoaderHandlerClassName());
    }

    /**
     * 代理 创建GraphLoader
     *
     * @param classGraphTextFileLoader
     * @param classGraphLoaderHandler
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static GraphLoader newGraphLoader(String classGraphTextFileLoader, String classGraphLoaderHandler) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        GraphLoader graphLoader = null;
        logger.info("反射创建：" + classGraphTextFileLoader + ";" + classGraphLoaderHandler);
        Class<?> c = Class.forName(classGraphTextFileLoader);
        Handler handler = (Handler) Class.forName(classGraphLoaderHandler).newInstance();
        graphLoader = (GraphLoader) Proxy.newProxyInstance(
                c.getClassLoader(),
                c.getInterfaces(),
                (proxy, method, args) -> {
                    handler.before(args);
                    Object object = method.invoke(c.newInstance(), args);
                    Object[] argsAfter = ParmUtil.addElement(args, object, ParmUtil.ZERO);
                    handler.after(argsAfter);
                    return object;
                });
        return graphLoader;
    }
}
