package Leitura;

import Dados.Site;
import rmi.Controle;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * Classe que faz a mineração dos dados de um site
 * @author junio
 */
public class LeitorSite extends Thread {
    private Controle controle;
    private String marca;
    private Site site;

    public LeitorSite(String marcaAtual,Controle controle, Site site){
        this.controle = controle;
        this.marca = marcaAtual;
        this.site = site;
    }
    
    @Override
    public void run(){
        String enderecoInicial = this.site.getEndereco();
        
        // Tratar marca com espaços
        String marcaInterno = this.marca; 
        enderecoInicial = enderecoInicial.replaceAll("replace", marcaInterno);
                
        this.efetuaLeitura(enderecoInicial);
    }
    
    // Efetua a leitura de uma url (site), a partir de duas bibliotecas do java reecbe um conteudo
    // HTML de uma pagina web, a partir do corpo desta pagina busca os dados com base nos dados do site
    private void efetuaLeitura(String endereco){
        String inputLine;
        String linhaFormatada;
        Boolean dentroDoBody = false;
        Boolean dentroConteudo = false;
        
        try {
            this.controle.requisitaLugarSemaforo();
             
            // Usa classes do java pra criar uma url e buscar a pagina
            URLConnection connection = new URL(endereco).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    connection.getInputStream(),
                            this.site.getCodificacao()
                    )
            );
            
            //Le o HTML, processando para encontrar os preços
            while ((inputLine = in.readLine()) != null){
                
                //Esta dentro do Body, deve começar a ler
                if(inputLine.contains("<body") ){
                    dentroDoBody = true;
                }
                
                //Verifica se entrou no body
                if(!dentroDoBody){
                    continue;
                }
                   
                //....

                //Verifica se encontrou conteudo
                if(!dentroConteudo){
                    continue;
                }
                                
                //Caso seja apenas o final do conteudo cai fora
                

                linhaFormatada = this.removeHtml(inputLine); 
                linhaFormatada = this.tratarLinha(linhaFormatada);
                
                //Caso removendo HTML e os caracteres especiais não sobre nada continua
                if(linhaFormatada.equals("")){
                    continue;
                }

                //Chegou até aqui, salva a postagem
                this.controle.adicionarPost(marca, inputLine);
            }
        } catch (Exception ex) {
            //System.out.println("Erro ao acessar site; " + endereco + ex.getMessage());
        } finally {
            this.controle.liberaLugarSemaforo();
        }
    }

    // Trata a linha usando replace para tags inuteis, tags html e numros
    // Alem de retirar caracteres especiais, sobranco apenas palvras
    private String tratarLinha(String str){
        String regexHtmlEntites = "&(.+?);";
        
        str = str.replaceAll(regexHtmlEntites, "");        
        str = str.replaceAll("\t", "");
        str = str.replaceAll("\n", "");
        str = str.replaceAll("\\...", "");
        str = str.replaceAll(":", "");
        str = str.replaceAll(";", "");
        str = str.replaceAll("\\.", "");
        //str = str.replaceAll("/", "");
        str = str.replaceAll("\\}", "");
        str = str.replaceAll("\\{", "");
        str = str.replaceAll("\\)", "");
        str = str.replaceAll("\\(", "");
        
        return str.trim();
    }
    
    private String removeHtml(String str){
        String regexTags = "<(.+?)>";
        return str.replaceAll(regexTags, "");
    } 
}
