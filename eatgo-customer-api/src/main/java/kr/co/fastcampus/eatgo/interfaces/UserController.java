package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> create(
            @RequestBody User resource
    ) throws URISyntaxException {

//        User user = User.builder()
//                .id(1004L)
//                .email("tester@example.com")
//                .name("Tester")
//                .password("test")
//                .build();


        String email = resource.getEmail();
        String name = resource.getName();
        String test = resource.getPassword();

        User user = userService.registerUser(email, name, test);

        String url = "/users/"+ user.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }

}
