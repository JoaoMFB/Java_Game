package Controler;

import Modelo.*;
import auxiliar.Posicao;
import java.util.ArrayList;
import java.lang.Math;

public class ControleDeJogo {
    protected boolean isFinished;
    protected int fase = 0;
    protected Posicao posicaoEsquerda;
    protected Posicao posicaoDireita;
    protected Posicao posicaoBaixo;
    protected Posicao posicaoCima;



    public void desenhaTudo(ArrayList<Personagem> e){
        for(int i = 0; i < e.size(); i++){
            e.get(i).autoDesenho();
        }
    }
    public int processaTudo(ArrayList<Personagem> umaFase) {
        Hero hero = (Hero) umaFase.get(0);
        Personagem pIesimoPersonagem;
        Personagem pJesimoPersonagem;
        Personagem lifeCounter;
        Posicao posicaoHeroi = hero.getPosicao();

        int linhaHeroi = posicaoHeroi.getLinha();
        int colunaHeroi = posicaoHeroi.getColuna();
        this.posicaoEsquerda = new Posicao(linhaHeroi, colunaHeroi -1);
        this.posicaoDireita = new Posicao(linhaHeroi, colunaHeroi +1);
        this.posicaoCima = new Posicao(linhaHeroi - 1, colunaHeroi);
        this.posicaoBaixo = new Posicao(linhaHeroi + 1, colunaHeroi);

        boolean monsterNearby = isMonsterNearby(umaFase, linhaHeroi, colunaHeroi);

        if(this.getFase() == 3){
             for (int k = 1; k < umaFase.size(); k++) {
                 if(umaFase.get(k) instanceof Ghast){
                     
                     Ghast ghast = (Ghast) umaFase.get(k);
                     Posicao posicaoGhast = ghast.getPosicao();
                     if(hero.getDirecao() == 1 || hero.getDirecao() == 2){
                         if(posicaoGhast.getLinha() != linhaHeroi){
                            int direcaoHero = hero.getDirecao();
                            ghast.moveBox(direcaoHero);
                         }
                     }
                     for(int j = 1; j < umaFase.size(); j++){
                         pJesimoPersonagem = umaFase.get(j);
                         if (ghast.getPosicao().igual(pJesimoPersonagem.getPosicao())){
                             if(pJesimoPersonagem instanceof Wall){
                                 ghast.voltaAUltimaPosicao();
                             }
                         }
                     }
                 }
             }
         }

        for(int i = 1; i < umaFase.size(); i++){
            pIesimoPersonagem = umaFase.get(i);
            if(pIesimoPersonagem instanceof LifeCounter){
                switch(hero.getQttLifes()){
                    case(0):
                        pIesimoPersonagem.changeImg("life0.png");
                        break;
                    case (1):
                        pIesimoPersonagem.changeImg("life1.png");
                        break;
                    case (2):
                        pIesimoPersonagem.changeImg("life2.png");
                        break;
                    case (3):
                        pIesimoPersonagem.changeImg("life3.png");
                        break;
                    default:
                        break;
                }
            }
        }
        int keys = hero.getQttKeys();
        int life = hero.getQttLifes();
        if(life < 0){
            return -1;
        }





        if (monsterNearby) {
            int nearestMonsterDirection = getNearestMonsterDirection(umaFase, linhaHeroi, colunaHeroi);
            hero.setAttackDirection(nearestMonsterDirection);

        } else {
            hero.setKilled(false);
            hero.setAttackDirection(0); // Se não houver monstros por perto, o herói não ataca
        }
        if(hero.hasKilled()) {
            if (hero.getAttackDirection() != 0) {
                for (int i = 1; i < umaFase.size(); i++) {
                    pIesimoPersonagem = umaFase.get(i);

                    // Verifique se o herói está atacando na direção atual
                    if (pIesimoPersonagem.isbMonster()) {
                        if (this.posicaoEsquerda.igual(pIesimoPersonagem.getPosicao())) {
                            // Remova o monstro da fase
                            umaFase.remove(i);
                        }
                        else if (this.posicaoDireita.igual(pIesimoPersonagem.getPosicao())) {
                            // Remova o monstro da fase
                            umaFase.remove(i);
                        }
                        else if (this.posicaoCima.igual(pIesimoPersonagem.getPosicao())) {
                            // Remova o monstro da fase
                            umaFase.remove(i);
                        }
                        else if (this.posicaoBaixo.igual(pIesimoPersonagem.getPosicao())) {
                            // Remova o monstro da fase
                            umaFase.remove(i);
                        }
                    }
                }
            }
        }

        //percorre o vetor de objetos
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            // Verifica se o personagem do vetor é uma flecha, se for ela deve ser comparada com todos os outros elementos, nao apenas com o heroi
            if (pIesimoPersonagem instanceof Arrow) {
                for (int j = 1; j < umaFase.size(); j++) {
                    pJesimoPersonagem = umaFase.get(j);
                    if (pJesimoPersonagem.getPosicao().igual(pIesimoPersonagem.getPosicao())){

                        if (pJesimoPersonagem.isbMonster() && (pJesimoPersonagem instanceof Ghast)){
                            Ghast ghast = (Ghast) umaFase.get(j);        
                            if (ghast.getQttLifes() != 0){
                                ghast.setQttLifes(ghast.getQttLifes() - 1);
                            }
                            else{
                                umaFase.remove(ghast);
                                System.out.println("Parabéns, você zerou o jogo, obrigado por jogar! \nFeito por: \nDaniel Umeda Kuhn \nJoao Marcelo Battaglini");
                            }
                        }
                        else if(pJesimoPersonagem.isbMonster()) {
                            umaFase.remove(umaFase.get(j));
                        }
                        else if(pJesimoPersonagem instanceof Pig){
                            umaFase.remove(umaFase.get(i));
                        }
                        
                    }
                }
            }
            // Se o porco for atingido por um zumbi ele morre
             if(pIesimoPersonagem instanceof Pig){
                 for (int j = 1; j < umaFase.size(); j++) {
                     pJesimoPersonagem = umaFase.get(j);
                     if(pIesimoPersonagem.getPosicao().igual(pJesimoPersonagem.getPosicao()) && pJesimoPersonagem.isbMonster()){
                         umaFase.remove(umaFase.get(i));
                     }
                     else if(pIesimoPersonagem.getPosicao().igual(pJesimoPersonagem.getPosicao()) && (pJesimoPersonagem instanceof Fireball)){
                         umaFase.remove(umaFase.get(j));
                     }
                 }
             }

            // a partir daqui os objetos serao comparados apenas com o heroi, pois nao ha necessidade de comparar com todos os outros.
            if (hero.getPosicao().igual(pIesimoPersonagem.getPosicao())) {
                if (pIesimoPersonagem.isbTransponivel()) {
                    if (pIesimoPersonagem instanceof Key) {
                        keys++;
                        hero.setQttKeys(keys);
                        System.out.format("Chaves coletadas: %d\n", keys);
                    }
                    if (pIesimoPersonagem instanceof Pig) {
                        int direcaoHero = hero.getDirecao();
                        pIesimoPersonagem.moveBox(direcaoHero);
                    }
                    if (pIesimoPersonagem instanceof Life) {

                        if(life <= 3){
                            life++;
                            hero.setQttLifes(life);
                            System.out.format("Vidas atuais: %d\n", life);
                        }
                        else{
                            System.out.println("Você atingiu a capacidade máxima de vidas (3)");
                            System.out.println("Você atingiu a capacidade máxima de vidas (3)");
                        }
                    }
                    if (!(pIesimoPersonagem instanceof Pig) && !pIesimoPersonagem.isbMonster()) {
                        umaFase.remove(pIesimoPersonagem);
                    }

                    // Implementar a morte do hero (reset) quando entra no monstro.
                    if (pIesimoPersonagem.isbMonster()) {
                        if (hero.getQttLifes() != 0) {
                            resetaHeroi(hero);
                        }   else {
                            System.out.println("Você morreu e não tem mais vidas, a fase foi recomeçada.\n");
                            return -1;
                        }
                    }

                    if(pIesimoPersonagem instanceof Fireball){
                        if(hero.getQttLifes() != 0){
                            resetaHeroi(hero);
                        }
                        else{
                            System.out.println("Você morreu e não tem mais vidas, a fase foi recomeçada.\n");
                            return -1;
                        }
                        for (int j = 1; j < umaFase.size(); j++) {
                            pJesimoPersonagem = umaFase.get(j);
                            if (pIesimoPersonagem.getPosicao().igual(pJesimoPersonagem.getPosicao()) && pJesimoPersonagem.isbMonster()) {
                                umaFase.remove(umaFase.get(i));
                            }
                        }

                    }

                    if (pIesimoPersonagem instanceof Arrow) {
                        if (hero.getQttLifes() != 0) {
                            resetaHeroi(hero);
                        } else {
                            System.out.println("Você morreu e não tem mais vidas, a fase foi recomeçada.\n");
                            return -1;
                        }



                    }
                    if (pIesimoPersonagem instanceof Diamond) {
                        hero.setArco(false);
                        setFinished(true);
                        if (this.getFase() == 4) {
                            System.out.println("Parabéns, você zerou o jogo!");
                        } else {
                            System.out.println("Parabéns, você liberou a próxima fase! Aperte 'P' para continuar ou 'R' para refazer.\n");

                        }
                        return 1;
                    }
                    if (pIesimoPersonagem instanceof Arco) {
                        hero.setArco(true);
                    }
                    if (pIesimoPersonagem instanceof Espada) {
                        hero.setHasSword(true);

                    }

                }
                if (pIesimoPersonagem instanceof Door) {
                    if (hero.getQttKeys() >= 1) {
                        umaFase.remove(umaFase.get(i));
                        hero.setQttKeys(hero.getQttKeys() - 1);
                    } else {
                        hero.voltaAUltimaPosicao();
                    }
                }
            }


    }
        return 0;
}

    public void resetaHeroi(Hero hero){
        hero.setPosicao(0, 0);
        if(hero.getQttLifes() >= 0){
            hero.setQttLifes(hero.getQttLifes()-1);
        }

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
                    if (pIesimoPersonagem instanceof Pig) {
                        return true;
                    }
                    else if (pIesimoPersonagem instanceof Door) {
                        return true;
                    }

                    else{return false;}
                }

            }
        }        
        return true;
    }
    private boolean isMonsterNearby(ArrayList<Personagem> umaFase, int linha, int coluna) {
        for (int i = 1; i < umaFase.size(); i++) {
            Personagem pIesimoPersonagem = umaFase.get(i);

            if (pIesimoPersonagem.isbMonster()) {
                Posicao monsterPos = pIesimoPersonagem.getPosicao();
                int distance = Math.abs(monsterPos.getLinha() - linha) + Math.abs(monsterPos.getColuna() - coluna);

                if (distance == 1) {
                    return true; // Tem um monstro imediatamente ao lado
                }
            }
        }

        return false; // Sem monstros por perto
    }

    // Se tem monstro por perto,verifica em qual das 4 posiçoes adjascentes ao heroi ele está
    private int getNearestMonsterDirection(ArrayList<Personagem> umaFase, int linha, int coluna) {
        int minDistance = Integer.MAX_VALUE;
        int nearestDirection = 0;

        for (int i = 1; i < umaFase.size(); i++) {
            Personagem pIesimoPersonagem = umaFase.get(i);

            if (pIesimoPersonagem.isbMonster()) {
                Posicao monsterPos = pIesimoPersonagem.getPosicao();
                int distance = Math.abs(monsterPos.getLinha() - linha) + Math.abs(monsterPos.getColuna() - coluna);

                if (distance < minDistance) {
                    minDistance = distance;

                    // verifica em qual das 4 posiçoes adjascentes ao heroi, o monstro esta
                    if (monsterPos.getLinha() == linha - 1) {
                        nearestDirection = 3; // ataca pra cima
                    } else if (monsterPos.getLinha() == linha + 1) {
                        nearestDirection = 4; // ataca pra baixo
                    } else if (monsterPos.getColuna() == coluna - 1) {
                        nearestDirection = 1; // ataca pra esquerda
                    } else if (monsterPos.getColuna() == coluna + 1) {
                        nearestDirection = 2; // ataca pra direita
                    }
                }
            }
        }

        return nearestDirection;
    }
}
