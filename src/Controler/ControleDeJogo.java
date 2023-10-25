package Controler;

import Modelo.Personagem;
import Modelo.Hero;
import Modelo.Box;
import auxiliar.Posicao;
import java.util.ArrayList;

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
                        setFinished(true);
                        this.setFase(this.getFase()+1);
                        if(this.getFase() == 4){
                            System.out.println("Parabéns, você zerou o jogo!");
                        }
                        else{
                            System.out.println("Parabéns, você liberou a próxima fase! Aperte 'P' para continuar ou 'R' para refazer.\n");

                        }
                    }
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
