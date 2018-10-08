package Dados;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Classe que contem um Termo
 * @author junio
 */
public class Termo implements Serializable {
    private String termo;
    
    //Tipo do termo, 0 - positivo, 1 - negativo, 2 - neutro
    private HashMap<String,Integer> tipoTermo;
    private HashMap<String,Integer[]> nrResultadosMarca;
    
    private HashMap<String,Integer> nrOcorrenciasPorMarca;
    private boolean isAvaliado;
    
    //a chave é concatenado marca + tipo de palavra
    private HashMap<String,Integer> nrOcorrenciasPorMarcaTipo;
    
    //chave = tipo de palavra, valor = valor da análise
    private HashMap<String, Double> valorPorTipo; 
    
    public Termo(String termo) {
        this.termo = termo;
        this.nrOcorrenciasPorMarcaTipo = new HashMap<>();
        this.nrOcorrenciasPorMarca = new HashMap<>();
        this.valorPorTipo = new HashMap<>();
        this.isAvaliado = false;
        this.nrResultadosMarca = new HashMap<>();
        this.tipoTermo = new HashMap<>();
    }

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public HashMap<String, Integer[]> getNrResultadosMarca() {
        return nrResultadosMarca;
    }

    public void setNrResultadosMarca(HashMap<String, Integer[]> nrResultadosMarca) {
        this.nrResultadosMarca = nrResultadosMarca;
    }

    public HashMap<String, Integer> getNrOcorrenciasPorMarca() {
        return nrOcorrenciasPorMarca;
    }

    public void setNrOcorrenciasPorMarca(HashMap<String, Integer> nrOcorrenciasPorMarca) {
        this.nrOcorrenciasPorMarca = nrOcorrenciasPorMarca;
    }

    public boolean isAvaliado() {
        return isAvaliado;
    }

    public void setIsAvaliado(boolean isAvaliado) {
        this.isAvaliado = isAvaliado;
    }

    public HashMap<String, Integer> getNrOcorrenciasPorMarcaTipo() {
        return nrOcorrenciasPorMarcaTipo;
    }

    public HashMap<String, Double> getValorPorTipo() {
        return valorPorTipo;
    }

    public void setValorPorTipo(HashMap<String, Double> valorPorTipo) {
        this.valorPorTipo = valorPorTipo;
    }

    public HashMap<String, Integer> getTipoTermo() {
        return tipoTermo;
    }

    public void setTipoTermo(HashMap<String, Integer> tipoTermo) {
        this.tipoTermo = tipoTermo;
    }

    //Atualiza o contador de ocorrências por marca e tipo, concatenando - ocultado
    public void updateOcorrenciaMarca(String marca,String tipo) {
    }
    
    //ocultado
    public void calculaPorcentagemTermo(String marca){
    }
    
    //ocultado
    public Integer getOcorrenciaPorMarca(String marca){    
    }
    
    //ocultado
    public double getValorTotalMarca(String marca){
       
    }
}
