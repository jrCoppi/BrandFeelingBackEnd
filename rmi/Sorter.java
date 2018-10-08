package rmi;

import Dados.Termo;
import java.util.ArrayList;

/**
 * Classe responsável por fazer as funções de sort
 * @author junio
 */
public class Sorter {
    private int length;
    private static Sorter instance;
    private ArrayList<Termo> arrTermos;
    private String marca;
    
    private Sorter() {}
    
    public static Sorter getInstance(){
        if (instance == null)
            instance = new Sorter();
        return instance;
    }
    
    /**
     * Faz o sorte e retorna
     * @param termos
     * @param marca
     * @return 
     */
    public ArrayList<Termo> startSort(ArrayList<Termo> termos, String marca){
        this.arrTermos = termos;
        this.marca = marca;
        this.sort();
        return this.arrTermos;
    }
 
    /**
     * Inicia o sort
     */
    public void sort() {
        if (this.arrTermos == null || this.arrTermos.size() == 0) {
            return ;
        }

        length = this.arrTermos.size();
        quickSort(0, length - 1);
    }
 
    /**
     * Faz o quickSort
     * @param lowerIndex
     * @param higherIndex 
     */
    private void quickSort(int lowerIndex, int higherIndex) {
         
        Integer i = lowerIndex;
        Integer j = higherIndex;
        Integer indexPivot = lowerIndex+(higherIndex-lowerIndex)/2;

        int pivot = this.arrTermos.get(indexPivot).getOcorrenciaPorMarca(this.marca);
        while (i <= j) {
            while (this.arrTermos.get(i).getOcorrenciaPorMarca(this.marca) < pivot) {
                i++;
            }
            while (this.arrTermos.get(j).getOcorrenciaPorMarca(this.marca) > pivot) {
                j--;
            }
            if (i <= j) {
                exchangeNumbers(i, j);
                i++;
                j--;
            }
        }
        
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }
 
    /**
     * Troca os números na função de sort
     * @param i
     * @param j 
     */
    private void exchangeNumbers(int i, int j) {
        Termo termoTemp1 = this.arrTermos.get(i);
        Termo termoTemp2 = this.arrTermos.get(j);
        
        this.arrTermos.remove(i);
        this.arrTermos.add(i, termoTemp2);
        
        this.arrTermos.remove(j);
        this.arrTermos.add(j, termoTemp1);
    }
     
}
