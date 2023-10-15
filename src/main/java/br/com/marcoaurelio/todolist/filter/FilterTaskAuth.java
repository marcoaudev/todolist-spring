package br.com.marcoaurelio.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.marcoaurelio.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath(); //pegar a rota que está passando pelo filtro
        
        //entrar nesse if somente se a rota for essa
        if (servletPath.startsWith("/tasks/")) {
            // pegar autentificação
            var authorization = request.getHeader("Authorization");

            var authEncoted = authorization.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(authEncoted);

            var authString = new String(authDecode);

            String[] credencials = authString.split(":");
            String username = credencials[0];
            String password = credencials[1];

            //procurar usuário
            var user = this.userRepository.findByUserName(username);

            // validar o usuário
            if (user == null) {
                response.sendError(401);
            } else {
                
                // validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    
                    // passa tudo para a próxima camada, que no caso é o controller
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }

            }
        }else{
            filterChain.doFilter(request, response);
        }

    }

}
