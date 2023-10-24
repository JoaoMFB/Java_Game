package Controler;

import Modelo.Personagem;
import Modelo.Hero;
import Modelo.Box;
import auxiliar.Posicao;
import java.util.ArrayList;

public class ControleDeJogo {
    public void desenhaTudo(ArrayList<Personagem> e){
        for(int i = 0; i < e.size(); i++){
            e.get(i).autoDesenho();
        }
    }
    public void processaTudo(ArrayList<Personagem> umaFase) {
        Hero hero = (Hero) umaFase.get(0);
        Personagem pIesimoPersonagem;
        int keys = hero.getQttKeys();
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            if (hero.getPosicao().igual(pIesimoPersonagem.getPosicao())) {
                if (pIesimoPersonagem.isbTransponivel()) {
                    if (pIesimoPersonagem.isbKey()){
                        keys++;
                        hero.setQttKeys(keys);
                        System.out.format("Chaves coletadas: %d", keys);
                    }
                    if(pIesimoPersonagem.isbBox()){
                        int direcaoHero = hero.getDirecao();
                        pIesimoPersonagem.moveBox(direcaoHero);
                    }
                    if(!pIesimoPersonagem.isbBox()) {
                        umaFase.remove(pIesimoPersonagem);
                    }
                    //Implementar a morte do hero (reset) quando entra no monstro.
                    /*if(pIesimoPersonagem.isbMonster()) {
                        umaFase.remove(hero);
                    }*/
                }
                if(pIesimoPersonagem.isbDoor()){
                    pIesimoPersonagem.openDoor(hero.getQttKeys());
                }
            }
        }
    }

    /*Retorna true se a posicao p é válida para Hero com relacao a todos os personagens no array*/
    public boolean ehPosicaoValida(ArrayList<Personagem> umaFase, Posicao p){
        Personagem pIesimoPersonagem;
        for(int i = 1; i < umaFase.size(); i++){
            pIesimoPersonagem = umaFase.get(i);            
            if(!pIesimoPersonagem.isbTransponivel()) {
                if(pIesimoPersonagem.isbBox()){
                    return true;
                }

                if (pIesimoPersonagem.getPosicao().igual(p))
                    return false;
            }
        }        
        return true;
    }
}
