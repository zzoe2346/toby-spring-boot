package lecture;


import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration // 스프링 구성 정보를 가진 클래스다 라는 표시
@ComponentScan
public class BootApplication {

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }
    public static void main(String[] args) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();

                ServletWebServerFactory servletWebServerFactory = this.getBean(ServletWebServerFactory.class);
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
                dispatcherServlet.setApplicationContext(this); // 이거 없어도 스프링이 알아서 이 작업을 함

                servletWebServerFactory.getWebServer(servletContext -> {
                            servletContext.addServlet("dispatcherServlet", dispatcherServlet)
                                    .addMapping("/*");
                        })
                        .start();
            }
        };
        applicationContext.register(BootApplication.class);
        applicationContext.refresh();//Template Method Pattern, 상속을 통해 기능 확장
    }
}
