package br.com.marcoaurelio.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController //Necessário para quando estamos construindo uma API, usando o conceito de RESTful
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;
    
    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {

        //método criado na interface para fazer um select no banco de dados e procurar uma determinada informação, que nesse caso é o nome;
        var user = this.userRepository.findByUserName(userModel.getUserName());

        if(user != null){ //caso o usuário já exista
            //enviar mensagem de erro
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");   
        }

        //criptografar a senha
        var passwordCriptografado = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordCriptografado);

        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }
}
