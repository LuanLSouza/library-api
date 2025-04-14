package example.com.libraryapi.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampoInvalidoException extends RuntimeException{
    private String campo;

    public CampoInvalidoException(String campo, String mensagem){
        super(mensagem);
        this.campo = campo;
    }
}
