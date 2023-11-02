package ch.informatik.zork;

import java.util.ArrayList;

public class Zork2Game {
    public static void main(String[] args) {
        // Erstellen der Räume und Gegenstände
        Room outside, lab, tavern, gblock, office, library, courtyard, gym, cafeteria, rooftop;

        // Erstellen der Gegenstände
        Item key = new Item("Goldener Schlüssel", "Ein glänzender goldener Schlüssel. Ob man damit die Kiste öffnen kann?", 0.1);
        Item treasure = new Item("Schatzkiste", "Eine dunkle Schatzkiste. Sie sieht sehr alt aus...", 5.0);
        Item book = new Item("Buch", "Ein altes Buch. Wie es aussieht ist da ein Code drin...", 1.0);

        // Erstellen der Räume
        outside = new Room("outside");
        lab = new Room("lab");
        tavern = new Room("tavern");
        gblock = new Room("gblock");
        office = new Room("office");
        library = new Room("library");
        courtyard = new Room("courtyard");
        gym = new Room("gym");
        cafeteria = new Room("cafeteria");
        rooftop = new Room("rooftop");

        // Büro als verschlossenen Raum initialisieren
        office.lock();


        // Raum-Exits initialisieren
        outside.setExits(cafeteria, gblock, tavern, null);
        lab.setExits(library, null, gblock, cafeteria);
        tavern.setExits(outside, null, office, null);
        gblock.setExits(lab, null, null, outside);
        office.setExits(tavern, null, null, null);
        library.setExits(null, null, lab, courtyard);
        courtyard.setExits(rooftop, library, cafeteria, gym);
        gym.setExits(null, courtyard, null, null);
        cafeteria.setExits(courtyard, lab, outside, null);
        rooftop.setExits(null, null, courtyard, null);

        // Markieren Sie den Raum, in dem Sie gewinnen können
        outside.setIsWinnerRoom(true);

        // Gegenstände zu den Räumen hinzufügen
        office.addItem(key);
        rooftop.addItem(treasure);
        library.addItem(book);




        // Aktueller Raum festlegen
        Room currentRoom = outside;

        // Erstellen des Game-Objekts mit dem Schatz
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(outside);
        rooms.add(lab);
        rooms.add(tavern);
        rooms.add(gblock);
        rooms.add(office);
        rooms.add(library);
        rooms.add(courtyard);
        rooms.add(gym);
        rooms.add(cafeteria);
        rooms.add(rooftop);

        Game game = new Game(currentRoom, rooms, treasure);

        // Spiel starten
        game.play();
    }
}

