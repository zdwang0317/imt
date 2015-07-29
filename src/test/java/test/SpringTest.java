package test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wzd.service.IWipService;
import wzd.service.impl.pi.WipServiceImpl;

public class SpringTest {
	/*@Autowired
	private static IWipService b;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			ApplicationContext ctx2 = new ClassPathXmlApplicationContext("spring.xml");
			ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-hibernate.xml");
			b = new WipServiceImpl();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		b.getListOfOptions();
	}*/

}
