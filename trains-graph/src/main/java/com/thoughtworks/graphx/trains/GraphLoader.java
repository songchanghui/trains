package com.thoughtworks.graphx.trains;


import com.thoughtworks.graphx.Graph;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

/**
 * 有向图-装载接口
 * Created by songchanghui on 2019/2/8.
 */
public interface GraphLoader {
    /**
     * 装载 file 生成图
     *
     * @param file_path
     * @param regex
     * @param limit
     * @return
     * @throws IOException
     */
    Graph loadFile(String file_path, String regex, int limit) throws IOException, URISyntaxException;

}
