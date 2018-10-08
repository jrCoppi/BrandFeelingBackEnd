package rmi;

import Dados.ResultadoBusca;
import rmi.Comunicacao;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servidor para leitura
 */
public class ServidorLeitura extends UnicastRemoteObject implements Comunicacao {
    private Integer idServidor;
    private Controle controle;
    
    public ServidorLeitura(Integer id) throws RemoteException {
        this.idServidor = id;
        //Cada servidor tem seu controle para evitar erros
        this.controle = new Controle();
    }

    @Override
    public HashMap<String, ResultadoBusca> efetuaLeitura(ArrayList<String> marcas, Integer categoria) throws RemoteException {
        //Dispara leitura e processamento
        controle.iniciaControle();
        controle.getAcesso().iniciaSites();
        
        //seta marca
        controle.setListaMarcas(marcas);
        controle.setHashResultado();
        
        controle.disparaPrograma(marcas);
        
      //  System.out.println("____________");
        return null;
    }
}
