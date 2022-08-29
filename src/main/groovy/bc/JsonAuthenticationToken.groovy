package bc

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class JsonAuthenticationToken extends UsernamePasswordAuthenticationToken{
    JsonAuthenticationToken(String username, String password){
        super(username, password)
    }
}
