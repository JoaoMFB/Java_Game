package Modelo;

import java.io.Serializable;
import auxiliar.Posicao;
public class Monster extends Personagem implements Serializable {

    public Monster(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.isMonster = true;
        this.setbTransponivel(true);
        this.whatIsIt = "Monster";
    }
    public void autoDesenho(){
        super.autoDesenho();
    }
}
