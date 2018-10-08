package rmi;

import Dados.Site;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Classe de Acesso, controla os sites e categorias
 * @author junio
 */
public class Acesso {
    private ArrayList<Site> listaSites;
    private Integer categoria; //0 - Diario, 1 - Semanal, 2 - Quinzenal, 3 - Mensal

    public Acesso() {
        this.listaSites  = new ArrayList<>();
        this.categoria = 0;
    }

    public ArrayList<Site> getSitePesquisa() {
        return listaSites;
    }

    public void setListaSites(ArrayList<Site> listaSites) {
        this.listaSites = listaSites;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }
    
    //Incia a listagem com os sites - OMITIDO
    public void iniciaSites(){
    }
}
