package br.com.fiap.usuarios;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest
class UsuariosApplicationTest {
	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		assertThat(applicationContext).isNotNull();
	}

	@Test
	void deveValidarTodosContextosBean() {
		String[] allBeanNames = applicationContext.getBeanDefinitionNames();

		for (String beanName : allBeanNames) {
			Object bean = applicationContext.getBean(beanName);
			assertThat(bean).isNotNull();
		}
	}
}
