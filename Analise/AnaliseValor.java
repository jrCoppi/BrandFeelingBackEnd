package Analise;

import Dados.Dicionario;
import Dados.Termo;
import java.util.HashMap;
import rmi.Controle;

/**
 * Esta tread ao receber busca um termo do dicionário com sua pos e gera o valor de cada um dos 5 tipos
 * @author junio
 */
public class AnaliseValor extends Thread {
    private Controle controle;
    private Dicionario dicionario;

    public AnaliseValor(Controle controle, Dicionario dicionario) {
        this.controle = controle;
        this.dicionario = dicionario;
    }

    @Override
    public void run(){
        this.avaliaTermo();
    }
    
    public void avaliaTermo(){
        try{
            Termo termo; 
            HashMap<String, Double> hashTemp;
            this.controle.requisitaLugarSemaforo();
            
            //busca os termos para setar o valor
            while((termo = this.controle.getProximoTermo()) != null){
                hashTemp = this.dicionario.getDictionary().get(termo.getTermo());
                
                //repassa o hash de valores do dicionário para este termo, pode ser nulo
                this.controle.setNotaTermo(termo.getTermo(), hashTemp);
            }

        } finally {
            this.controle.liberaLugarSemaforo();
        }
    }
}
