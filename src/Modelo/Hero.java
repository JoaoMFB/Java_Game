package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.ControleDeJogo;
import Controler.Tela;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Hero extends Personagem implements Serializable{
    public Hero(String sNomeImagePNG) {
        super(sNomeImagePNG);
    }

    public void voltaAUltimaPosicao(){
        this.pPosicao.volta();
    }
    private boolean arco = false;
    private int qttKeys = 0;
    private int qttLifes = 0;
    private int direcao = -1; // -1 significa que o herói não se moveu ainda

    private boolean hasSword = false;
    private char lastDirection; // 'U' para cima, 'D' para baixo, 'L' para esquerda, 'R' para direita


    public boolean setPosicao(int linha, int coluna){
        if(this.pPosicao.setPosicao(linha, coluna)){
            if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
                this.voltaAUltimaPosicao();
            }
            return true;
        }
        return false;       
    }

    public boolean hasSword() {
        return hasSword;
    }

    public void setHasSword(boolean hasSword) {
        this.hasSword = hasSword;
    }

    public boolean getArco(){
        return arco;
    }
    
    public void setArco(boolean equipado){
        this.arco = equipado;
    }
    
    public int getQttKeys() {
        return qttKeys;
    }

    public void setQttKeys(int qttKeys) {
        this.qttKeys = qttKeys;
    }

    public int getQttLifes() {
        return qttLifes;
    }

    public void setQttLifes(int qttLifes) {
        this.qttLifes = qttLifes;
    }

    /*TO-DO: este metodo pode ser interessante a todos os personagens que se movem*/
    private boolean validaPosicao(){
        if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
            this.voltaAUltimaPosicao();
            return false;
        }
        return true;       
    }

    public int getDirecao() {
        return direcao;
    }


    public boolean moveUp() {
        if(super.moveUp()) {
            direcao = 1;
            return validaPosicao();
        }
        return false;
    }

    public boolean moveDown() {
        if(super.moveDown()) {
            direcao = 2;
            return validaPosicao();
        }
        return false;
    }

    public boolean moveRight() {
        if(super.moveRight()) {
            direcao = 3;
            return validaPosicao();
        }
        return false;
    }

    public boolean moveLeft() {
        if(super.moveLeft()) {
            direcao = 4;
            return validaPosicao();
        }
        return false;
    }    
    
    public void shootRight(){
        Arrow arrow = new Arrow("arrowright.png", 3);
        arrow.setPosicao(pPosicao.getLinha(),pPosicao.getColuna()+1);
        Desenho.acessoATelaDoJogo().addPersonagem(arrow);
    }
    public void shootLeft(){
        Arrow arrow = new Arrow("arrowleft.png", 4);
        arrow.setPosicao(pPosicao.getLinha(),pPosicao.getColuna()-1);
        Desenho.acessoATelaDoJogo().addPersonagem(arrow);
    }
    public void shootUp(){
        Arrow arrow = new Arrow("arrowup.png", 1);
        arrow.setPosicao(pPosicao.getLinha() - 1,pPosicao.getColuna());
        Desenho.acessoATelaDoJogo().addPersonagem(arrow);
    }
    public void shootDown(){
        Arrow arrow = new Arrow("arrowdown.png", 2);
        arrow.setPosicao(pPosicao.getLinha() + 1,pPosicao.getColuna());
        Desenho.acessoATelaDoJogo().addPersonagem(arrow);
    }
}
