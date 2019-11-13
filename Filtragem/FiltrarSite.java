package Filtragem;

import Dados.PosTagger;
import Dados.Post;
import Dados.Site;
import java.util.HashMap;
import rmi.Controle;

/**
 * Classe responsável pela filtragem de cada site, faz os filtros e adiciona ao processo
 * @author junio
 */
public class FiltrarSite extends Thread  {
    private Controle controle;
    private Site site;
    private HashMap<String,String> stopList;
     
    public Controle getControle() {
        return controle;
    }

    public void setControle(Controle controle) {
        this.controle = controle;
    }

    public FiltrarSite(Controle controle, Site site) {
        this.controle = controle;
        this.site = site;
        this.stopList = new HashMap<>();
        this.populaStopList();
    }
    
    public void populaStopList(){
       String[] stopListArray = 
       {"a","an","and","any","are","as",
        "at","be","by","for","from","has",
        "he","her","i","in","is","it",
        "its","me","my","of","on","so",
        "that","the","to","was","were",
        "will","with","you"};
       
        for (String stopWord : stopListArray) {
            this.adicionaTermoIgnorar(stopWord);
        }
    }
    
    public void adicionaTermoIgnorar(String termo){
        this.getStopList().put(termo.toLowerCase(), termo.toLowerCase());
    }

    public HashMap<String, String> getStopList() {
        return stopList;
    }

    @Override
    public void run(){
        this.filtraTweets();
    }
    
    public void filtraTweets(){
        Post postagem;
        String texto;
        String textoClassificado;
        PosTagger tagger = this.controle.getTagger();
        
        try{
            this.controle.requisitaLugarSemaforo();
            
            //Enquanto tiver palavras que vieram da leitura
            while((postagem = this.controle.getProximoPost()) != null){
                //Refaz formatação, remove links, classes, html, usuários, números e formata
                texto = postagem.getPostagem();
                texto = texto.toLowerCase();
                texto = this.removeLink(texto.toLowerCase());
                //...
                texto = this.removeHtml(texto);        
                texto = this.tratarLinha(texto);
                //...
                texto = this.removeNumeros(texto);
                
                //Não restou nada
                if(texto.equals("")){
                    continue;
                }
                
                //Formata as classes contidas no texto
                //...

                String[] termos = texto.split(" ");
                for (String termo : termos) {
                    termo = termo.trim();
                    //se ta vazio ou na lista
                    if(termo.equals("") || this.termoNaLista(termo)){
                        continue;
                    }

                    this.getControle().adicionaTermoTratar(postagem.getMarca(), termo, textoClassificado);
                }
            }
        } finally {
            this.controle.liberaLugarSemaforo();
        }
    }
    
    private boolean termoNaLista(String termo){
        return (this.getStopList().get(termo.toLowerCase()) != null);
    }
    
     // Trata a linha usando replace para tags inuteis, tags html e numros
    // Alem de retirar caracteres especiais, sobranco apenas palvras
    private String tratarLinha(String str){
        String regexHtmlEntites = "&(.+?);";
        
        str = str.replaceAll(regexHtmlEntites, "");        
        str = str.replaceAll("\t", "");
        str = str.replaceAll("\n", "");
        str = str.replaceAll("\\...", "");
        str = str.replaceAll(":", "");
        str = str.replaceAll(";", "");
        str = str.replaceAll(",", "");
        //...
        return str.trim();
    }
    
    //alterado
    private String removeHtml(String str){
        return str;
    } 
    //alterado
    private String removeUsers(String str){
        return str;
    } 
    
    //remote links- alterado
    private String removeLink(String str){
        String regexTags = "";
        return str.replaceAll(regexTags, "");
    } 
    
    //alterado
    private String removeIngorados(String str){        
        return str;
    }
    
    private String removeNumeros(String str){
        String regexTags = "(?:\\d*\\.)?\\d+";
        return str.replaceAll(regexTags, "");
    }
}
