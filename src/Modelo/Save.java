package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Save implements Serializable {
    private ArrayList<Personagem> personagens;
    private int contador;

    public Save(ArrayList<Personagem> personagens, int contador) {
        this.personagens = personagens;
        this.contador = contador;
    }

    public ArrayList<Personagem> getPersonagens() {
        return personagens;
    }

    public int getContador() {
        return contador;
    }
}
