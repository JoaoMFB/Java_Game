package Modelo;

import java.io.Serializable;

public class Life extends Personagem implements Serializable {


    public Life(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.isLife = true;
        this.whatIsIt = "Life";
    }
    public void autoDesenho(){
        super.autoDesenho();
    }


}
