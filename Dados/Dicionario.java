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

                // If it's a comment, skip this line.
                if (line.trim().startsWith("#")) {
                    continue;
                }
                
                // We use tab separation
                data = line.split("\t");
                wordTypeMarker = data[0];
                
                // Example line:
                // POS ID PosS NegS SynsetTerm#sensenumber Desc
                // a 00009618 0.5 0.25 spartan#4 austere#3 ascetical#2
                // ascetic#2 practicing great self-denial;...etc

                // Is it a valid line? Otherwise, through exception.
                if (data.length != 6) {
                    throw new IllegalArgumentException("Incorrect tabulation format in file, line: " + lineNumber);
                }
                
                // Calculate synset score as score = PosS - NegS
                synsetScore = Double.parseDouble(data[2]) - Double.parseDouble(data[3]);
                
                //valores neutros não serão adicionados
                if(synsetScore == 0.00){
                    continue;
                }
                
                // Get all Synset terms
                synTermsSplit = data[4].split(" ");
                
                // Percorre todos os termos de um synset
                for (String synTermSplit : synTermsSplit) {
                    // Divisão entre termo e rank
                    synTermAndRank = synTermSplit.split("#");
                    synTerm = synTermAndRank[0] + "#" + wordTypeMarker;

                    synTermRank = Integer.parseInt(synTermAndRank[1]);
                    // Cria um mapa, onde: term -> {score of synset#1, score of synset#2...}

                    // Add map to term if it doesn't have one
                    if (!tempDictionary.containsKey(synTerm)) {
                        tempDictionary.put(synTerm, new HashMap<Integer, Double>());
                    }

                    // Add synset link to synterm
                    tempDictionary.get(synTerm).put(synTermRank,synsetScore);
                }
            }
            
            // Go through all the terms.
            for (Map.Entry<String, HashMap<Integer, Double>> entry : tempDictionary.entrySet()) {
                word = entry.getKey();
                synSetScoreMap = entry.getValue();

                //Calcula o valor do termo com base no rank, onde o rank 1 recebe valor total, o 2 recebe metade e assim sucessivamente
                // Exemplo: termo rank 1/1, termo rank 2 /2 ....
                score = 0.0;
                sum = 0.0;
                for (Map.Entry<Integer, Double> setScore : synSetScoreMap.entrySet()) {
                    //Valor dividio pelo seu rank
                    score += setScore.getValue() / (double) setScore.getKey();
                }

                //aredonda
                score = Math.round(score * 100.0) / 100.0;
                
                //por fim adiciona a palavra ao dicionario, chave Palavra > Tipo > Valor
                palavras = word.split("#");
                
                //palavras com 1 caracter não são consideradas no programa
                if(palavras[0].length() == 1){
                    continue;
                }
                
                //se não existe no array cria
                if (!this.dictionary.containsKey(palavras[0])) {
                    this.dictionary.put(palavras[0], new HashMap<>());
                }
                
                this.dictionary.get(palavras[0]).put(palavras[1], score);
            }
            
            csv.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
