/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.Tela;
import java.awt.Graphics;
import java.io.Serializable;

public class Skeleton extends Monster implements Serializable{
    private int iContaIntervalos;
    private int arrowDirection;
    
    public Skeleton(String sNomeImagePNG, int arrowDirection) {
        super(sNomeImagePNG);
        this.bTransponivel = false;
        this.iContaIntervalos = 0;
        this.arrowDirection = arrowDirection;
        this.isMonster = true;
    }

    public void autoDesenho() {
        super.autoDesenho();

        this.iContaIntervalos++;
        if(this.iContaIntervalos == Consts.TIMER){
            this.iContaIntervalos = 0;
            if(arrowDirection == 1){
                BadArrow arrow = new BadArrow("arrowup.png", 1);
                arrow.setPosicao(pPosicao.getLinha()-1,pPosicao.getColuna());
                Desenho.acessoATelaDoJogo().addPersonagem(arrow);
            }
            if(arrowDirection == 2){
                Arrow arrow = new Arrow("arrowdown.png", 2);
                arrow.setPosicao(pPosicao.getLinha()+1,pPosicao.getColuna());
                Desenho.acessoATelaDoJogo().addPersonagem(arrow);
            }
            if(arrowDirection == 3){
                Arrow arrow = new Arrow("arrowright.png", 3);
                arrow.setPosicao(pPosicao.getLinha(),pPosicao.getColuna()+1);
                Desenho.acessoATelaDoJogo().addPersonagem(arrow);
            }
            if(arrowDirection == 4){
                Arrow arrow = new Arrow("arrowleft.png", 4);
                arrow.setPosicao(pPosicao.getLinha(),pPosicao.getColuna()-1);
                Desenho.acessoATelaDoJogo().addPersonagem(arrow);
            }
        }
    }



}