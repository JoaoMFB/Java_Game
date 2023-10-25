/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;


import java.io.Serializable;
import auxiliar.Posicao;

import java.io.Serializable;
import java.util.Random;

public class Zombie extends Personagem implements Serializable {

    public Zombie(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.isMonster = true;
        this.setbTransponivel(true);
    }
    public void autoDesenho(){
        Random rand = new Random();
        int iDirecao = rand.nextInt(4);
        
        if(iDirecao == 0)
            this.setPosicao(pPosicao.getLinha(), pPosicao.getColuna()+1);
        else if(iDirecao == 1)
            this.setPosicao(pPosicao.getLinha()+1, pPosicao.getColuna());
        else if(iDirecao == 2)
            this.setPosicao(pPosicao.getLinha(), pPosicao.getColuna()-1);
        else if(iDirecao == 3)
            this.setPosicao(pPosicao.getLinha()-1, pPosicao.getColuna());
        super.autoDesenho();
    }
}

