package Leitura;

import rmi.Controle;
import Dados.Site;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe de leitura, inicia a leitura dos sites
 * @author junio
 */
public class Leitura {
    private Controle controle;
    private ArrayList<LeitorSite> arrLeitores; 

    public Leitura(){
        this.arrLeitores = new ArrayList();
    }
    
    public Controle getControle() {
        return controle;
    }

    public void setControle(Controle controle) {
        this.controle = controle;
    }
    
    public void iniciaLeitura(){ 
        LeitorSite leitor;
        controle.atualizaEstadoLeitura(1);
        
        for (Site site : this.controle.getAcesso().getSitePesquisa()) {
            // Dispara thread para cada site, guarda para ver se todos acabarem
            for (String marcaAtual : this.controle.getListaMarcas()) {
                //Dispara leitura
                leitor = new LeitorSite(marcaAtual,this.controle,site);
                leitor.start();
                arrLeitores.add(leitor);
            }
        }
    }
    
    public void finalizaLeitura(){
        for (LeitorSite leitor1 : arrLeitores) {
            try { 
                leitor1.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Leitura.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        controle.atualizaEstadoLeitura(2);
    }
}
