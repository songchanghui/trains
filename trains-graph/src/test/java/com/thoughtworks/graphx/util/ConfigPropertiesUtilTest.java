package com.thoughtworks.graphx.util;

import org.junit.Test;

import java.util.logging.Logger;

/**
 * Created by songchanghui on 2019/2/13.
 */
public class ConfigPropertiesUtilTest {
    Logger logger = Logger.getLogger(ConfigPropertiesUtilTest.class.toString());

    @Test
    public void confLoaderTest() {
        logger.info(ConfigPropertiesUtil.get("graph_file_path"));

    }
}
