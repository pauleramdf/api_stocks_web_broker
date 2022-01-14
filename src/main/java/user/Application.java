package user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Configuration
//    @EnableWebSecurity
//    class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.cors().and()
//                    .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
//                    .oauth2ResourceServer().jwt();
//        }
//    }
}