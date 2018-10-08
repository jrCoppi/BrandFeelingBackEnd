package rmi;

import Analise.Analise;
import Dados.PosTagger;
import Dados.Post;
import Dados.ResultadoBusca;
import Dados.Termo;
import Dados.TermoAnalise;
import Filtragem.Filtrar;
import Leitura.Leitura;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe de Controle do Servidor
 * @author lcoppi
 */
public class Controle {
    private Leitura leitura;
    private Filtrar filtrar;
    private Analise analise;
    private Acesso acesso;
    private PosTagger tagger;
    private ArrayList<String> listaMarcas;
    private ArrayList<Post> listaPostagem;
    private ArrayList<TermoAnalise> listaTermosTratar;
    private HashMap<String,Termo> hashTermos;// STRING = TERMO
    private HashMap<String,ResultadoBusca> hashResultados;
    private Semaphore semaforoInt;
    private Integer estadoLeitura; //0 - Não Iniciado, 1 - Iniciado, 2 - Finalizado
    private Integer nrPosts;
    
    public Controle(){
        this.semaforoInt = new Semaphore(1,true);
        this.tagger = new PosTagger();      
        this.analise = new Analise(this);
    }
    
    //inicia (ou reinicia) as variaveis e classes de controle
    public void iniciaControle(){
        this.listaMarcas = new ArrayList<>();
        this.listaTermosTratar = new ArrayList<>(); 
        this.hashResultados = new HashMap<>();
        this.listaPostagem  = new ArrayList<>();
        this.hashTermos = new HashMap<>();
        this.estadoLeitura = 4;
        this.acesso = new Acesso();
        this.nrPosts  = 0;
    }
    
    public ArrayList<String> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(ArrayList<String> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }
    
    public void setHashResultado(){
    }

    public ArrayList<Post> getListaPostagem() {
        return listaPostagem;
    }
    
    public HashMap<String, Termo> getHashTermos() {
        return hashTermos;
    }

    public Acesso getAcesso() {
        return acesso;
    }

    public PosTagger getTagger() {
        return tagger;
    }

    public void atualizaEstadoLeitura(Integer estado){
        this.estadoLeitura = estado;
    }
        
    //Dispara os mtodos de Leitura
    public void disparaPrograma(ArrayList<String> listaMarcas){   
        //Inicia análise primeiro para carregar os dicionários do WordNet
        this.analise.iniciaAnalise();
        
        this.leitura = new Leitura();
        this.leitura.setControle(this);
        this.leitura.iniciaLeitura();
        
        this.filtrar = new Filtrar();
        this.filtrar.setControle(this);
        this.filtrar.iniciaFiltragem();
        
        this.leitura.finalizaLeitura();
        this.filtrar.finalizaFiltrar();
        this.analise.finalizaAnalise();
        
        //Finaliza o processo, formata o resultado
        this.finalizaProcesso();
    }
    
    public void requisitaLugarSemaforo(){
        try {
            this.semaforoInt.acquire();
            //System.out.println(this.semaforoInt.availablePermits());
        } catch (InterruptedException ex) {
            Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public void liberaLugarSemaforo(){
        this.semaforoInt.release();
    }

    //Adiciona uma nova postagem a lista
    public synchronized void adicionarPost(String marca, String post){
        Post postagem = new Post(post, marca);
        this.getListaPostagem().add(postagem);
        this.nrPosts++;
        notifyAll();
    }
    
    //Retorna a proxima postagem disponivel
    public synchronized Post getProximoPost(){
        Post retorno = null;
       
        while(this.estadoLeitura == 0 || (this.getListaPostagem().isEmpty() && this.estadoLeitura == 1)){
           try {
                wait();
           } catch (InterruptedException ex) {
               
           }
        } 
        
        if(this.getListaPostagem().isEmpty()){
             return retorno;
        }
        
        //Busca a postagem, retorna ela e remove do array
        for (Post postagem : this.getListaPostagem()) {
            retorno = postagem;
            break;
        }
        this.getListaPostagem().remove(retorno);
        
        notifyAll();
        return retorno;
    }
    
    //Adiciona um termo a ser tratado pela análise para verificar qial tipo ele é
    public synchronized void adicionaTermoTratar(String marca, String termo, String postagem){
        this.listaTermosTratar.add(new TermoAnalise(marca, termo, postagem));
        notifyAll();
    }
    
    //Retorna o próximo termo a tratar disponivel
    public synchronized TermoAnalise getProximoTermoTratar(){
        TermoAnalise retorno = null;
       
        while(this.estadoLeitura == 0 || (this.listaTermosTratar.isEmpty() && this.estadoLeitura == 1)){
           try {
                wait();
           } catch (InterruptedException ex) {
               
           }
        } 
        
        if(this.listaTermosTratar.isEmpty()){
             return retorno;
        }
        
        //Busca a postagem, retorna ela e remove do array
        for (TermoAnalise termo : this.listaTermosTratar) {
            retorno = termo;
            break;
        }
        this.listaTermosTratar.remove(retorno);
        
        notifyAll();
        return retorno;
    }
    
    // Adiciona um novo termo ou aumenta o numero de oc, avisa que tem novo termo
    public synchronized void adicionaTermo(String marca, String termo, String tipo){   
        if(this.getHashTermos().get(termo) == null){
            this.getHashTermos().put(termo, new Termo(termo));
        }
        
        notifyAll();
    }
    
    //a tread de análise dos valores busca o proximo termo a ser analisado
    public synchronized Termo getProximoTermo(){
        Termo termo = null;
        Boolean termoEncontrado = false;
        
        //primeiro espera ter termos
        while(this.estadoLeitura == 0 || (this.getHashTermos().isEmpty() && this.estadoLeitura == 1)){
            try {
                wait();
            } catch (InterruptedException ex) {
                
            }
        }
          
        //Tenta buscar o próximo termo a ser análisado, espera caso não encontre
        do{
            Set<String> chaves = this.getHashTermos().keySet();  
            for (String chave : chaves) {
                if(!this.getHashTermos().get(chave).isAvaliado()){
                    termo = this.getHashTermos().get(chave);
                    termoEncontrado = true;
                    break;
                }
            }
            
            //Se deve continuar, faz o wait
            if(termoEncontrado == false){
                try {
                    wait();
                } catch (InterruptedException ex) {
                    
                }
            }
       }while(termoEncontrado == false && this.estadoLeitura == 1);
       
       return termo;
    }
        
    //SETA A NOTA PASSANDO A MARCA, O TIPO E O VALOR
    public synchronized void setNotaTermo(String termo, HashMap<String, Double> hashValores){
        this.getHashTermos().get(termo).setIsAvaliado(true);
        
        notifyAll();
    }
    
    //partes removidas por privacidade
    private void finalizaProcesso(){
        ResultadoBusca resultado;
        int nrOcorrencias,qtdIgnorar = 1;
        double valorMarca;
        Integer[] nrResultadosMarca;
        Integer nrResultdos;
       
        //Gera resultado por marca
        for (String marca : this.getListaMarcas()) {
            resultado = new ResultadoBusca();
            
            //...
        }
    }
}
