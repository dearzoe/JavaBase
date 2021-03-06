package com.github.kuangcp.di.autoscan;

import com.github.kuangcp.util.SpringHelper;
import org.junit.Test;


/**
 * 原理
 * *  启动spring容器，加载配置文件
 * *  spring容器解析到
 * <context:component-scan base-package="cn.itcast.spring0909.scan"></context:component-scan>
 * *  spring容器会在指定的包及子包中查找类上是否有@Component
 * *  如果@Component注解没有写任何属性
 *
 * @author Administrator
 */
public class AutoScanTest extends SpringHelper {

  @Override
  public String getXmlPath() {
    return "di/autoscan/applicationContext.xml";
  }

  @Test
  public void test() {
    Person person = (Person) context.getBean("person");
    person.say();
  }
}
