package Dados;

import java.util.ArrayList;


/**
 * Classe criada para definidir propriedades dos sites
 * apenasLinksNaHome - define se a primeira pagina é busca
 * numeroNiveisAcessados - quantos niveis pode se acessar
 * hashLinks - guarda os links 
 * indicadorPagina - Indica o que simbolica um link de paginação no site
 * lockLinks - usa ReadWrite para manipular os links nas treads
 * 
 * Usamos ReadWriteLock pois queermos deixar que varias leituras acotençam ao mesmo tempo
 * mas temos de bloquear varias escitas[
 * Ainda temos de usar o await e o signall para esperar por novos links
 */
public class Site {
    private String endereco;
    private String codificacao;
    private String quebraDeLinha;
    private String tagInformacoes;
    private ArrayList<String> listaClassesIgnorar;

    public Site(String endereco, String codificacao, String quebraDeLinha, String tagInformacoes, ArrayList<String> listaClassesIgnorar) {
        this.endereco = endereco;
        this.codificacao = codificacao;
        this.quebraDeLinha = quebraDeLinha;
        this.tagInformacoes = tagInformacoes;
        this.listaClassesIgnorar = listaClassesIgnorar;
    }

    public ArrayList<String> getListaClassesIgnorar() {
        return listaClassesIgnorar;
    }

    public void setListaClassesIgnorar(ArrayList<String> listaClassesIgnorar) {
        this.listaClassesIgnorar = listaClassesIgnorar;
    }

    
    public String getTagInformacoes() {
        return tagInformacoes;
    }

    public void setTagInformacoes(String tagInformacoes) {
        this.tagInformacoes = tagInformacoes;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCodificacao() {
        return codificacao;
    }

    public void setCodificacao(String codificacao) {
        this.codificacao = codificacao;
    }
    
    public String getQuebraDeLinha() {
        return quebraDeLinha;
    }

    public void setQuebraDeLinha(String quebraDeLinha) {
        this.quebraDeLinha = quebraDeLinha;
    }
}
