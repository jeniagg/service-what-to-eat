package what.to.eat.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import what.to.eat.config.TestPersistanceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestPersistanceConfig.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
public abstract class ServiceTest {
}
