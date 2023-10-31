package Controler;
import Modelo.*;

import java.io.*;
import java.util.ArrayList;

public class Save {
    private ArrayList<Personagem> personagens;

    public Save(ArrayList<Personagem> personagens) {
        this.personagens = personagens;
    }

    public void save(String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(personagens);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Personagem> load(String fileName) {
        ArrayList<Personagem> loadedPersonagens = new ArrayList<>();

        File file = new File(fileName);

        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
                loadedPersonagens = (ArrayList<Personagem>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Arquivo inexistente");
        }
        return loadedPersonagens;
    }
}
