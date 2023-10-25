/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;


import java.io.Serializable;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.ControleDeJogo;
import Controler.Tela;
import auxiliar.Posicao;

import java.io.Serializable;
import java.util.Random;

public class Zombie extends Personagem implements Serializable {

    private boolean iDirecao = true;

    private char direction;
    public Zombie(String sNomeImagePNG, char direction) {
        super(sNomeImagePNG);
        this.isMonster = true;
        this.setbTransponivel(true);
        this.direction = direction;
    }
    public void autoDesenho(){
        Random rand = new Random();
        int randomDirection = rand.nextInt(4);
        if(this.direction == 'v'){
            if (this.iDirecao)
                this.setPosicao(pPosicao.getLinha()+1, pPosicao.getColuna());
            else
                this.setPosicao(pPosicao.getLinha() - 1, pPosicao.getColuna());
            super.autoDesenho();
        }
        if(this.direction == 'h'){
            if (this.iDirecao)
                this.setPosicao(pPosicao.getLinha(), pPosicao.getColuna()+1);
            else
                this.setPosicao(pPosicao.getLinha(), pPosicao.getColuna()-1);
            super.autoDesenho();
        }if(this.direction == 'a') {
            if (randomDirection == 0)
                this.setPosicao(pPosicao.getLinha(), pPosicao.getColuna() + 1);
            else if (randomDirection == 1)
                this.setPosicao(pPosicao.getLinha() + 1, pPosicao.getColuna());
            else if (randomDirection == 2)
                this.setPosicao(pPosicao.getLinha(), pPosicao.getColuna() - 1);
            else if (randomDirection == 3)
                this.setPosicao(pPosicao.getLinha() - 1, pPosicao.getColuna());
            super.autoDesenho();
        }
    }

    private boolean validaPosicao(){
        if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
            this.voltaAUltimaPosicao();
            return false;
        }

        return true;
    }
    public void voltaAUltimaPosicao(){
        this.pPosicao.volta();
    }

    public boolean setPosicao(int linha, int coluna){
        if(this.pPosicao.setPosicao(linha, coluna)){
            if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
                this.voltaAUltimaPosicao();
                this.iDirecao = false;
            }
            return true;
        }
        return false;
    }
}

