package Dados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável por ter o dicionário de sentimentos do SentiWordNet
 * Código adaptado do exemplo fornecido no site : http://sentiwordnet.isti.cnr.it/code/SentiWordNetDemoCode.java
 * @author junio
 */
public class Dicionario {
    private HashMap<String, HashMap<String, Double>> dictionary;
    private final String pathToSWN;

    public Dicionario() {
        this.dictionary = new HashMap<String, HashMap<String, Double>>();
        this.popularDicionario();
    }

    public HashMap<String, HashMap<String, Double>> getDictionary() {
        return dictionary;
    }
    
    /** 
     * Popula o dicionario de palavras com base no sentiwordnet
     */
    public void popularDicionario(){
        BufferedReader csv = null;
        int lineNumber = 0,synTermRank = 0;
        Double synsetScore,score,sum;
        String line,wordTypeMarker,synTerm,word;
        String[] data,synTermsSplit,synTermAndRank,palavras;
        HashMap<String, HashMap<Integer, Double>> tempDictionary = new HashMap<String, HashMap<Integer, Double>>();
        Map<Integer, Double> synSetScoreMap;
                        
        try {
            csv = new BufferedReader(new FileReader(pathToSWN));
            
            while ((line = csv.readLine()) != null) {
                lineNumber++;

                // omitdo
            
            csv.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
