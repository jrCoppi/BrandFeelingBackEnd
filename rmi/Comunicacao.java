package rmi;

/**
 *
 * interface de comunicacao RMI
 */
import Dados.ResultadoBusca;
import java.rmi.*;
import java.util.ArrayList;
import java.util.HashMap;

public interface Comunicacao extends Remote {
   public HashMap<String,ResultadoBusca> efetuaLeitura(ArrayList<String> marcas, Integer categoria) throws RemoteException;
}