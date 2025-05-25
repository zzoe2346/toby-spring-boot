package lecture;


import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class BootApplication {
    public static void main(String[] args) {
        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext(){
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
        applicationContext.registerBean(HelloController.class);
        applicationContext.registerBean(SimpleHelloService.class);
        applicationContext.refresh();//Template Method Pattern, 상속을 통해 기능 확장


    }
}
