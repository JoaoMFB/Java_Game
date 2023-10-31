/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.Tela;
import java.io.Serializable;
import java.util.Random;
import java.awt.Graphics;



public class Ghast extends Personagem implements Serializable {
    private int iContaIntervalos;
    private int qttLifes = 3;
    public Ghast(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.isMonster = true;
        this.isGhast = true;
        this.setbTransponivel(true);
        this.iContaIntervalos = 0;
        this.whatIsIt = "Ghast";
        

    }
    @Override
    public boolean setPosicao(int linha, int coluna){
        if(this.pPosicao.setPosicao(linha, coluna)){
            if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
                this.voltaAUltimaPosicao();
            }
            return true;
        }
        return false;
    }
    public void voltaAUltimaPosicao(){
        this.pPosicao.volta();
    }
    public void autoDesenho(){
        super.autoDesenho();
        this.iContaIntervalos++;
        if(this.iContaIntervalos == Consts.TIMER){
            this.iContaIntervalos = 0;
            Fireball fireball = new Fireball("fireball.png", 4);
            fireball.setPosicao(pPosicao.getLinha(),pPosicao.getColuna()-1);
            Desenho.acessoATelaDoJogo().addPersonagem(fireball);
        }   
    }
    
    public int getQttLifes(){
        return qttLifes;
    }
    
    public void setQttLifes(int qttLifes){
        this.qttLifes = qttLifes;
    }
}
