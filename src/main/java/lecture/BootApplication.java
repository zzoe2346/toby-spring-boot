package lecture;


import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration // 스프링 구성 정보를 가진 클래스다 라는 표시
public class BootApplication {
    @Bean
    public HelloController helloController(HelloService helloService) {// 의존 오브젝트를 파라미터로하면 스프링 컨테이너가 알아서 넘겨준다.
        return new HelloController(helloService);
    }

    @Bean
    public HelloService helloService() {
        return new SimpleHelloService();
    }

    public static void main(String[] args) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(){
            @Override
            protected void onRefresh() {
                super.onRefresh();
                ServletWebServerFactory servletWebServerFactory = new TomcatServletWebServerFactory();
                servletWebServerFactory.getWebServer(servletContext -> {
                            servletContext.addServlet("dispatcherServlet",
                                    new DispatcherServlet(this)
                            ).addMapping("/*");
                        })
                        .start();

            }
        };
        applicationContext.register(BootApplication.class);
        applicationContext.refresh();//Template Method Pattern, 상속을 통해 기능 확장


    }
}
