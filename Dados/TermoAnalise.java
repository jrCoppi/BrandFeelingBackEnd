package Dados;

/**
 * Classe que guarda um termo a ser análisado
 * @author junio
 */
public class TermoAnalise {
    private String termo;
    private String textoFormatado;
    private String marca;

    public TermoAnalise(String marca, String termo, String texto) {
        this.termo = termo;
        this.textoFormatado = texto;
        this.marca = marca;
    }

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public String getTextoFormatado() {
        return textoFormatado;
    }

    public void setTextoFormatado(String textoFormatado) {
        this.textoFormatado = textoFormatado;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
