/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dados;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 *
 * @author junio
 */
public class PosTagger {
    private final String caminhoTagger = "lib/stanford-postagger/models/english-left3words-distsim.tagger";
    private MaxentTagger tagger; 

    public MaxentTagger getTagger() {
        return tagger;
    }

    public void setTagger(MaxentTagger tagger) {
        this.tagger = tagger;
    }

    public PosTagger() {
        this.tagger = new MaxentTagger(this.caminhoTagger);
    }
    
    
    public String getStringTagged(String texto){
        return this.getTagger().tagString(texto);
    }
    
}
