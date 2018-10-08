package Analise;

import Dados.Dicionario;
import Dados.WordNet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.Controle;

/**
 * Start as classes de análise, sendo elas do tipo (onde é feita o POS Tagger) ou de Valor
 * - Análise de Tipo : Onde é feita a análise do POS Tagger
 * - Análise de Valor : Onde é feito o preenchimento dos valores de acordo com o SentiWordNet 
 * @author junio
 */
public class Analise {
    private Controle controle;
    private ArrayList<AnaliseTipo> arrAnalise;
    private AnaliseValor analiseValor;
    private Dicionario dicionario;
    private WordNet wordnet;
 
    public Analise(Controle controle) {
        this.controle = controle;
        this.dicionario = new Dicionario();
        this.wordnet = new WordNet();
    }
    
    public void iniciaAnalise(){
        this.arrAnalise = new ArrayList<AnaliseTipo>();
        
        //análise por tipo
        for (int i = 0; i < this.controle.getAcesso().getSitePesquisa().size(); i++) {
            AnaliseTipo analise = new AnaliseTipo(this.controle);
            analise.start();
            
            this.arrAnalise.add(analise);
        }
        
        //análise por valor
        this.analiseValor = new AnaliseValor(this.controle, this.dicionario);
        this.analiseValor.start();
    }
    
    public void finalizaAnalise(){
        try {
            for (AnaliseTipo analise : arrAnalise) {
                analise.join();
            }
            
            this.analiseValor.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Analise.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
