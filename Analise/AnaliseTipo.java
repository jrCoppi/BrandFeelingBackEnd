package Analise;

import Dados.TermoAnalise;
import Dados.WordNet;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import rmi.Controle;

/**
 * esta tread buca os termos e faz a distinguiçao do POS de cada termo
 * @author junio
 */
public class AnaliseTipo extends Thread {
    private Controle controle;
    private WordNet wordnet;
    private HashMap<String,String> listaIgnorar;
   
    public Controle getControle() {
        return controle;
    }

    public void setControle(Controle controle) {
        this.controle = controle;
    }

    public WordNet getWordnet() {
        return wordnet;
    }

    public void setWordnet(WordNet wordnet) {
        this.wordnet = wordnet;
    }
    
    public AnaliseTipo(Controle controle) {
        this.controle = controle;      
        this.listaIgnorar = new HashMap<>();
        //Inicia a classe de steming
        this.wordnet = new WordNet();
    }
    

    @Override
    public void run(){
        this.analisaTermos();
    }
    
    public void analisaTermos(){
        try{
            TermoAnalise termoAnalise;
            String tipo;
            String termoNovo;
            
            this.controle.requisitaLugarSemaforo();
            
            //busca os termos para fazer a POS
            while((termoAnalise = this.controle.getProximoTermoTratar()) != null){
                //....
                

                //Faz a lematização caso seja um tipo 
                if(tipo != ""){
                    termoNovo = this.getWordnet().getRadicalPalavra(termoAnalise.getTermo(), tipo.charAt(0));
                    //...
                    termoAnalise.setTermo(termoNovo);

                }


                this.controle.adicionaTermo(termoAnalise.getMarca(), termoAnalise.getTermo(), tipo);
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.controle.liberaLugarSemaforo();
        }
    }
    
    //omitido
    private String getTipoPos(TermoAnalise termoAnalise){
        String[] termo;
        
        //...
        
        return termo;
    }

}
