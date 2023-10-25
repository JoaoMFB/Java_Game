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

    }
    
    public void autoDesenho(){
        super.autoDesenho();
        this.iContaIntervalos++;
        if(this.iContaIntervalos == Consts.TIMER){
            this.iContaIntervalos = 0;
            Arrow arrow = new Arrow("fireball.png", 4);
            arrow.setPosicao(pPosicao.getLinha(),pPosicao.getColuna()-1);
            Desenho.acessoATelaDoJogo().addPersonagem(arrow);
        }   
    }
    
    public int getQttLifes(){
        return qttLifes;
    }
    
    public void setQttLifes(int qttLifes){
        this.qttLifes = qttLifes;
    }
}
