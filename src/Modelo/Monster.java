package Modelo;

import java.io.Serializable;
import auxiliar.Posicao;
public class Monster extends Personagem implements Serializable {

    public Monster(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.isMonster = true;
        this.setbTransponivel(true);
    }
    public void autoDesenho(){
        super.autoDesenho();
    }
}
