package lecture;

 import org.assertj.core.api.Assertions;
 import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate;
 import org.springframework.http.HttpHeaders;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.MediaType;
 import org.springframework.http.ResponseEntity;

 import static org.assertj.core.api.Assertions.*;

class HelloApiTest {
    @Test
    void hello() {
        TestRestTemplate rest = new TestRestTemplate();

        ResponseEntity<String> spring = rest.getForEntity("http://localhost:8080/hello?name={name}", String.class, "Spring");

        assertThat(spring.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(spring.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE);
        assertThat(spring.getBody()).isEqualTo("Hello Spring");

    }
}