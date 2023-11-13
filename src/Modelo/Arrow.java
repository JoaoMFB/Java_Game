/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Modelo.Wall;
import Auxiliar.Desenho;
import Controler.Tela;
import java.awt.Graphics;
import java.io.Serializable;

public class Arrow extends Personagem implements Serializable{
    private int arrowDirection;
    public Arrow(String sNomeImagePNG, int arrowDirection) {
        super(sNomeImagePNG);
        this.bMortal = true;  
        this.isArrow = true;
        this.arrowDirection = arrowDirection;
        this.whatIsIt = "Arrow";
    }

    
    public void autoDesenho() {
        super.autoDesenho();

        if(arrowDirection == 1){
            if(!this.moveUp())
                Desenho.acessoATelaDoJogo().removePersonagem(this);
        }
        if(arrowDirection == 2){
            if(!this.moveDown())
                Desenho.acessoATelaDoJogo().removePersonagem(this);
        }
        if(arrowDirection == 3){
            if(!this.moveRight())
                Desenho.acessoATelaDoJogo().removePersonagem(this);
        }
        if(arrowDirection == 4){
            if(!this.moveLeft())
                Desenho.acessoATelaDoJogo().removePersonagem(this);
        }
        
        
    }

    
}
