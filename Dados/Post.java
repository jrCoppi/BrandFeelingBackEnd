package Dados;

import java.util.ArrayList;

/**
 * Guarda uma postagem
 * @author junio
 */
public class Post {
    private String postagem;
    private String marca;

    public Post(String postagem, String marca) {
        this.setPostagem(postagem);
        this.setMarca(marca);
    }

    public String getPostagem() {
        return postagem;
    }

    public void setPostagem(String postagem) {
        this.postagem = postagem;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

   
}
