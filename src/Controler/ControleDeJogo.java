package Controler;

import Modelo.Personagem;
import Modelo.Hero;
import auxiliar.Posicao;
import java.util.ArrayList;

public class ControleDeJogo {
    protected boolean isFinished;
    protected int fase = 0;

    public void desenhaTudo(ArrayList<Personagem> e){
        for(int i = 0; i < e.size(); i++){
            e.get(i).autoDesenho();
        }
    }
    public void processaTudo(ArrayList<Personagem> umaFase) {
        Hero hero = (Hero) umaFase.get(0);
        Personagem pIesimoPersonagem;
        Personagem pJesimoPersonagem;
        int keys = hero.getQttKeys();
        int life = hero.getQttLifes();

        boolean finish = false;
        //percorre o vetor de objetos
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            // Verifica se o personagem do vetor é uma flecha, se for ela deve ser comparada com todos os outros elementos, nao apenas com o heroi
            if (pIesimoPersonagem.isbArrow()) {
                for (int j = 1; j < umaFase.size(); j++) {
                    pJesimoPersonagem = umaFase.get(j);
                    if (pJesimoPersonagem.getPosicao().igual(pIesimoPersonagem.getPosicao())){
                        //TODO ==> A flecha só mata quando o ultimo movimento do heroi foi up/down.
                        if (pJesimoPersonagem.isbMonster() || pJesimoPersonagem.isbGhast()) {
                            umaFase.remove(umaFase.get(j));
                        }
                    }
                }
            }
            // Se o porco for atingido por um zumbi ele morre
             if(pIesimoPersonagem.isbPig()){
                 for (int j = 1; j < umaFase.size(); j++) {
                     pJesimoPersonagem = umaFase.get(j);
                     if(pIesimoPersonagem.getPosicao().igual(pJesimoPersonagem.getPosicao()) && pJesimoPersonagem.isbMonster()){
                         umaFase.remove(umaFase.get(i));
                     }
                 }
             }


            // a partir daqui os objetos serao comparados apenas com o heroi, pois nao ha necessidade de comparar com todos os outros.
            if (hero.getPosicao().igual(pIesimoPersonagem.getPosicao())) {
                if (pIesimoPersonagem.isbTransponivel()) {
                    if (pIesimoPersonagem.isbKey()) {
                        keys++;
                        hero.setQttKeys(keys);
                        System.out.format("Chaves coletadas: %d\n", keys);
                    }
                    if (pIesimoPersonagem.isbPig()) {
                        int direcaoHero = hero.getDirecao();
                        pIesimoPersonagem.moveBox(direcaoHero);
                    }
                    if (pIesimoPersonagem.isbLife()) {
                        life++;
                        hero.setQttLifes(life);
                        System.out.format("Vidas atuais: %d\n", life);
                    }
                    if (!pIesimoPersonagem.isbPig() && !pIesimoPersonagem.isbMonster()) {
                        umaFase.remove(pIesimoPersonagem);
                    }
                    // Implementar a morte do hero (reset) quando entra no monstro.
                    if (pIesimoPersonagem.isbMonster()) {
                        if (hero.getQttLifes() != 0) {

                            resetaHeroi(hero);
                        } else {
                            hero.changeImg("");
                            System.out.println("Você morreu e não tem mais vidas, pressione 'R' para recomeçar a fase!\n");
                        }
                    }

                    // Precisa arrumar esse if, pq ta dando exception quando o steve morre por uma flecha, tanto do esqueleto quanto do ghast. (A morte por contato com o zumbi não acontece isso)
                    if (pIesimoPersonagem.isbArrow()) {
                        if (hero.getQttLifes() != 0) {
                            resetaHeroi(hero);

                        } else {
                            hero.changeImg("");
                            System.out.println("Você morreu e não tem mais vidas, pressione 'R' para recomeçar a fase!\n");
                        }
                    }
                    if (pIesimoPersonagem.isbDiamond()) {
                        setFinished(true);
                        this.setFase(this.getFase() + 1);
                        if (this.getFase() == 4) {
                            System.out.println("Parabéns, você zerou o jogo!");
                        } else {
                            System.out.println("Parabéns, você liberou a próxima fase! Aperte 'P' para continuar ou 'R' para refazer.\n");

                        }
                    }
                    if (pIesimoPersonagem.isbArco()) {
                        hero.setArco(true);
                    }
                    if (pIesimoPersonagem.isbEspada()) {
                        hero.setHasSword(true);

                    }

                }
                if (pIesimoPersonagem.isbDoor()) {
                    if (hero.getQttKeys() >= 1) {
                        umaFase.remove(umaFase.get(i));
                        hero.setQttKeys(hero.getQttKeys() - 1);
                    } else {
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
                    if (pIesimoPersonagem.isbPig()) {
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
