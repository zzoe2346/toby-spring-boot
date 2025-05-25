package lecture;


import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration // 스프링 구성 정보를 가진 클래스다 라는 표시
@ComponentScan
public class BootApplication {

    public static void main(String[] args) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
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
