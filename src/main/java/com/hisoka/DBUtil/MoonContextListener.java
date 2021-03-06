package com.hisoka.DBUtil;

import com.hisoka.DBUtil.DBChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * {@link MoonContextListener} 将Spring的{@link org.springframework.web.context.ContextLoaderListener}进行了
 * <p>简单的包装，增添了一层数据库的检测.
 * <p>使用时，需要用{@link MoonContextListener}代替{@link org.springframework.web.context.ContextLoaderListener}在web.xml中的配置,如：
 * <code>
 * 		<listener><listener-class>com.hisoka.DBUtil.MoonContextListener</listener-class></listener>
 *	</code>
 *
 * @author Hinsteny
 * @date 2015年8月11日
 * @copyright: 2015 All rights reserved.
 */
public class MoonContextListener extends ContextLoaderListener{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		DBChecker dbChecker = new DBChecker(event.getServletContext());
		if(!dbChecker.isDBValid()){
			logger.warn("Would not init the spring context,cause by the DataBase issue.");
		}else{
			super.contextInitialized(event);
		}
		
	}
}
