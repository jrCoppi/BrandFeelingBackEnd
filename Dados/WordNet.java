package Dados;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsável por realizar a lematização dos termos no WordNet através da MIT Java Wordnet Interface
 * Wordnet: https://wordnet.princeton.edu/
 * MIT Java Wordnet Interface: http://projects.csail.mit.edu/jwi/
 * @author junio
 */
public class WordNet {
    private final String pathToWn = "lib/wordnet";
    private Dictionary dicionario;
    private WordnetStemmer stemmer;

    public WordNet() {
        this.iniciaWordNet();
    }
    
    private void iniciaWordNet(){
        try {
            this.dicionario;
            this.dicionario.open();
            
            //Inicia a classe de steming
            this.stemmer;
        } catch (Exception ex) {
            //
        }
    }
    
    public String getRadicalPalavra(String palavra, char tipo){
        String retorno = palavra;
        List<String> stems;
        
        try {
            //
        } catch (Exception ex) {
            return palavra;
        }
        
        if(stems.isEmpty()){
           return retorno; 
        }
        
        for (int i = 0; i < stems.size(); i++) {
            retorno = stems.get(i);
        }
        
        return retorno;
    }
    
    private POS getPosFromChar(char tipo){
        switch(tipo){
            case 'v': {
                return POS.VERB;
            }
            case 'n': {
                return POS.NOUN;
            }
            case 's' :
            case 'a': {
                return POS.ADJECTIVE;
            }
            case 'r' : {
                return POS.ADVERB;
            }
        }
        
        return null;
    }
    

}
