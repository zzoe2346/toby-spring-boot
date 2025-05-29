package lecture;

import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MySpringApplication {
    public static void run(Class<?> applicationClass, String... args) {
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
        applicationContext.register(applicationClass);
        applicationContext.refresh();//Template Method Pattern, 상속을 통해 기능 확장
    }
}
