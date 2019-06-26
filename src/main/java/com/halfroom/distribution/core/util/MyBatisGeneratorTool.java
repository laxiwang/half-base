package com.halfroom.distribution.core.util;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;


public class MyBatisGeneratorTool {
	public static void generateMbgConfiguration() throws Exception {
        /*
         * Mybatis自带Generator工具生成相应东西
         */
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("./src/main/java/com/halfroom/distribution/core/util/build-mybatis.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
        } catch (IOException e) {
           e.printStackTrace();
        } catch (XMLParserException e) {
        	 e.printStackTrace();
        }
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        try {
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (InvalidConfigurationException e) {
            // TODO Auto-generated catch block
        	 e.printStackTrace();
        } catch (SQLException e) {	
            // TODO Auto-generated catch block
        	 e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        	 e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
        	 e.printStackTrace();
        }

        System.out.println("生成Mybatis配置成功！");
    }
	public static void main(String[] args) throws Exception {
		//generateMbgConfiguration();
	}
}
