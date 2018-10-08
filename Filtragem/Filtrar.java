package Filtragem;

import Dados.Site;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.Controle;

/**
 * Classe responsável pela filtragem, dispara as treads de filtros
 * @author junio
 */
public class Filtrar  {
    private Controle controle;
    private ArrayList<FiltrarSite> arrFiltrar;
     
    public Controle getControle() {
        return controle;
    }

    public void setControle(Controle controle) {
        this.controle = controle;
        this.arrFiltrar = new ArrayList<>();
    }
    
    public void iniciaFiltragem(){
        FiltrarSite filtro;
       
        for (Site site : this.controle.getAcesso().getSitePesquisa()) {
            //Dispara leitura
            filtro = new FiltrarSite(this.controle,site);
            
            //Seta as marcas para ignorar
            for (String marca : this.controle.getListaMarcas()) {
                filtro.adicionaTermoIgnorar(marca);
            }
            
            filtro.start();
            arrFiltrar.add(filtro);
        }
    }
    
     public void finalizaFiltrar(){
        for (FiltrarSite filtro : arrFiltrar) {
            try { 
                filtro.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Filtrar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}