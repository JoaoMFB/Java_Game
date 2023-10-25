package Controler;

import Modelo.Personagem;
import Modelo.Hero;
import Modelo.Box;
import Modelo.Ghast;
import auxiliar.Posicao;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ControleDeJogo {
    protected boolean isFinished;
    protected int fase = 1;

    public void desenhaTudo(ArrayList<Personagem> e){
        for(int i = 0; i < e.size(); i++){
            e.get(i).autoDesenho();
        }
    }
    public void processaTudo(ArrayList<Personagem> umaFase) {
        Hero hero = (Hero) umaFase.get(0);
        Personagem pIesimoPersonagem;
        int keys = hero.getQttKeys();
        int life = hero.getQttLifes();
        int posGhast = procuraGhast(umaFase);
        
        boolean finish = false;
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            if (hero.getPosicao().igual(pIesimoPersonagem.getPosicao())) {
                if (pIesimoPersonagem.isbTransponivel()) {
                    if (pIesimoPersonagem.isbKey()){
                        keys++;
                        hero.setQttKeys(keys);
                        System.out.format("Chaves coletadas: %d\ns", keys);
                    }
                    if(pIesimoPersonagem.isbBox()){
                        int direcaoHero = hero.getDirecao();
                        pIesimoPersonagem.moveBox(direcaoHero);
                    }
                    if (pIesimoPersonagem.isbLife()){
                        life++;
                        hero.setQttLifes(life);
                        System.out.format("Vidas atuais: %d\n", life);
                    }
                    if(!pIesimoPersonagem.isbBox()) {
                        umaFase.remove(pIesimoPersonagem);
                    }
                    //Implementar a morte do hero (reset) quando entra no monstro.
                    if(pIesimoPersonagem.isbMonster()) {
                        if(hero.getQttLifes() != 0){
                            resetaHeroi(hero);
                        }
                        else{
                            hero.changeImg("");
                            System.out.println("Você morreu e não tem mais vidas, pressione 'R' para recomeçar a fase!\n");
                        }
                    }
                    if(pIesimoPersonagem.isbArrow()) {
                        if(hero.getQttLifes() != 0){
                            resetaHeroi(hero);
                        }
                        else{
                            hero.changeImg("");
                            System.out.println("Você morreu e não tem mais vidas, pressione 'R' para recomeçar a fase!\n");
                        }
                    }
                    if(pIesimoPersonagem.isbDiamond()){
                        System.out.println("Parabéns, você liberou a próxima fase! Aperte 'P' para continuar ou 'R' para refazer.\n");
                        setFinished(true);
                        this.setFase(this.getFase()+1);
                        if(this.getFase() == 4){
                            System.out.println("Parabéns, você zerou o jogo!");
                        }
                    }
                    if(pIesimoPersonagem.isbArco()){
                        hero.setArco(true);
                    }
                if(pIesimoPersonagem.isbDoor()){
                    if(hero.getQttKeys() >= 1){
                        pIesimoPersonagem.changeImg("open_door.png");
                        pIesimoPersonagem.autoDesenho();
                    }
                    else{
                        hero.voltaAUltimaPosicao();
                    }
                }
            }
        }
    
            if(posGhast > -1){
            Ghast ghast = (Ghast) umaFase.get(posGhast);
            System.out.println("oi");
                if (ghast.getPosicao().igual(pIesimoPersonagem.getPosicao())) {
                        if(pIesimoPersonagem.isbArrow()){
                            System.out.println("acerto");
                            ghast.setQttLifes(ghast.getQttLifes()-1);
                            if(ghast.getQttLifes() == 0){
                                ghast.changeImg("");
                            }
                        }
                    
                } 
        }
    }
}
    public int procuraGhast(ArrayList<Personagem> umaFase){
        for(int i = 1; i < umaFase.size(); i++){
            if(umaFase.get(i).isbGhast()){
                return i;
            }
            else{
                return -1;
            }
        }
        return -1;
    }

            
    public void resetaHeroi(Hero hero){
        hero.setPosicao(0, 0);
        hero.setQttLifes(hero.getQttLifes()-1);
        System.out.format("Você morreu, mas ainda restam: %d vidas. Tente novamente!\n", hero.getQttLifes());
    }


    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public int getFase() {
        return fase;
    }

    public void setFase(int fase) {
        this.fase = fase;
    }

    /*Retorna true se a posicao p é válida para Hero com relacao a todos os personagens no array*/
    public boolean ehPosicaoValida(ArrayList<Personagem> umaFase, Posicao p){
        Personagem pIesimoPersonagem;
        for(int i = 1; i < umaFase.size(); i++){
            pIesimoPersonagem = umaFase.get(i);            
            if(!pIesimoPersonagem.isbTransponivel()) {
                if (pIesimoPersonagem.getPosicao().igual(p)) {
                    if (pIesimoPersonagem.isbBox()) {
                        return true;
                    }
                    else if (pIesimoPersonagem.isbDoor()) {
                        return true;
                    }
                    else{return false;}
                }

            }
        }        
        return true;
    }
}
