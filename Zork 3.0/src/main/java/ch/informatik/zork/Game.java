package ch.informatik.zork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
class Game {
    private Room currentRoom;
    private Room previousRoom;
    private ArrayList<Room> rooms;
    private Item treasure;
    private HashMap<String, Item> inventory = new HashMap<>();
    private boolean hasKey = false;
    private boolean hasTreasure = false;
    private boolean hasWon = false;


    public Game(Room startRoom, ArrayList<Room> rooms, Item treasure) {
        this.currentRoom = startRoom;
        this.rooms = rooms;
        this.treasure = treasure;
    }


    public void play() {
        Scanner scanner = new Scanner(System.in);

        String reset = "\u001B[0m";
        String red = "\u001B[31m";
        String green = "\u001B[32m";
        String yellow = "\u001B[33m";
        String blue = "\u001B[34m";
        String pink = "\u001B[38;5;205m";


        System.out.println(red +"                                     --------------------------------------------------------------------");
        System.out.println("                                     |                "+ blue +"Herzlich Willkommen bei ZorkGame!"+red+"                 |");
        System.out.println("                                     |  "+blue+"Dieses Spiel wurde von Kien Bui und Nermin Nokic programmiert!  "+red+"|");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("| "+pink+"Deine Aufgabe in diesem Spiel besteht daraus die einzelnen Räumen zu untersuchen, dies kannst du tun mit dem Befehl 'search'.              "+red+"|");
        System.out.println("| "+pink+"Suche nach dem goldener Schlüssel und das Buch, indem der Code steht, mit diesen Gegenständen kannst du dann die Schatzkiste öffnen!       "+red+"|");
        System.out.println("| "+pink+"Verwende 'Norden', 'Osten', 'Süden', 'Westen', um dich zu bewegen. Gib 'help' ein, um die Anleitung anzuzeigen.                            "+red+"|");
        System.out.println("| "+pink+"Du kannst Gegenstände mit 'pickup <Gegenstand>' aufsammeln und dein Inventar mit 'inv' anzeigen.                                           "+red+"|");
        System.out.println("| "+pink+"Sobald du alle Gegenstände gefunden hast, kannst du die Schatzkiste öffnen ('open treasure').                                              "+red+"|");
        System.out.println("| "+pink+"Um die Map zu öffnen benutze 'map'.                                                                                                        "+red+"|");
        System.out.println("| "+pink+"Verwende 'search' um den Raum zu durchsuchen.                                                                                              "+red+"|");
        System.out.println("| "+pink+"Verwende 'back' um in den vorherigen Raum zurückzukehren.                                                                                  "+red+"|");
        System.out.println("| "+pink+"Wenn du einen Gegenstand los werden willst benutze 'drop (Item Name)'.                                                                     "+red+"|");
        System.out.println("| "+pink+"Mit 'Tipp 1,2,3' erhältst du den Hinweis wo sich was befindet, nutze ihn Weise und nutze es nicht aus!                                     "+red+"|");
        System.out.println("| "+pink+"Verwende 'quit', um das Spiel zu beenden.                                                                                                  "+red+"|");
        System.out.println("| "+pink+"Das wars mit der kurzen Einführung! Wir wünschen dir viel Spass beim spielen :)                                                            "+red+"|");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------"+reset);


            while (!hasWon) {
                String currentRoomDescription = currentRoom.getDescription();

                System.out.println(yellow + "\nAktueller Raum:" + reset);
                System.out.println("[" + green + currentRoomDescription + reset + "]");
                System.out.print("Eingabe: ");
                String input = scanner.nextLine().toLowerCase();

                if (input.equals("quit")) {
                    System.out.println(red + "Das Spiel wurde beendet. Auf Wiedersehen :(" + reset);
                    break;
                } else if (input.equals("help")) {
                    displayHelp();
                    continue;
                } else if (input.startsWith("pickup ")) {
                    String itemName = input.substring(7).trim();
                    pickupItem(itemName);
                    continue;
                } else if (input.equals("inv")) {
                    showInventory();
                    continue;
                } else if (input.equals("open treasure")) {
                    openTreasure();
                    continue;
                } else if (input.equals("map")) {
                    printMap();
                    continue;
                } else if (input.equals("search")) {
                    search();
                    continue;
                } else if (input.equals("tipp 1") || input.equals("tipp 2") || input.equals("tipp 3")) {
                    getHint(input);
                    continue;
                } else if (input.equals("back")) {
                    if (previousRoom != null) {
                        Room temp = currentRoom; // Aktuellen Raum in temp speichern
                        currentRoom = previousRoom; // Aktuellen Raum auf den vorherigen Raum setzen
                        previousRoom = temp; // Vorherigen Raum auf den temporären (vorherigen) Raum setzen
                        System.out.println(pink + "Du gehst zurück in den vorherigen Raum." + reset);
                    } else {
                        System.out.println(red + "Es gibt keinen vorherigen Raum." + reset);
                    }
                    continue;
                }
                if (input.startsWith("drop ")) {
                    String itemName = input.substring(5).trim();
                    Item itemToDrop = null;

                    // Überprüfe, ob der Gegenstand im Inventar vorhanden ist
                    for (Item item : inventory.values()) {
                        if (item.getName().equalsIgnoreCase(itemName)) {
                            itemToDrop = item;
                            break;
                        }
                    }

                    if (itemToDrop != null) {
                        inventory.remove(itemToDrop.getName());
                        currentRoom.addItem(itemToDrop);
                        System.out.println(blue+"Du hast '" + itemToDrop.getName() + "' fallen gelassen."+reset);
                    } else {
                        System.out.println(red+"Du besitzt keinen Gegenstand mit dem Namen '" + itemName + "' in deinem Inventar."+reset);
                    }
                    continue;
                }

                Room nextRoom = null;

            if (input.equals("norden")) {
                nextRoom = currentRoom.getExit("norden");
            } else if (input.equals("westen")) {
                nextRoom = currentRoom.getExit("westen");
            } else if (input.equals("süden")) {
                nextRoom = currentRoom.getExit("süden");
            } else if (input.equals("osten")) {
                nextRoom = currentRoom.getExit("osten");
            }



                if (nextRoom != null) {
                    previousRoom = currentRoom; // Speichere den aktuellen Raum als vorherigen Raum
                    currentRoom = nextRoom;
                    System.out.println(blue + "Du gehst nach " + input + "." + reset);
                } else {
                    System.out.println(red + "Dieser Befehl ist mir leider unbekannt.\nVersuche es nochmals oder benutze 'help' für die Command Liste." + reset);
                }


                checkWinCondition();
        }
    }

    private void printMap() {
        // ANSI-Farbcodes für verschiedene Farben
        String reset = "\u001B[0m";
        String black = "\u001B[30m";
        String red = "\u001B[31m";
        String green = "\u001B[32m";
        String yellow = "\u001B[33m";
        String blue = "\u001B[34m";
        String purple = "\u001B[35m";
        String cyan = "\u001B[36m";
        String white = "\u001B[37m";
        String orange = "\u001B[38;5;202m";
        String pink = "\u001B[38;5;205m";
        String lime = "\u001B[38;5;154m";
        String brown = "\u001B[38;5;124m";
        String gray = "\u001B[38;5;240m";

        // Kartenanzeige mit Farben
        System.out.println("                   _____________________                   ");
        System.out.println("                   |   "+green+"Kartenanzeige"+reset+"   |                   ");
        System.out.println("|¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|");
        System.out.println("|                -------------                            |");
        System.out.println("|                |  " + red + "Rooftop  " + reset + "|                            |");
        System.out.println("|                -------------                            |");
        System.out.println("|                     | |                                 |");
        System.out.println("|  ---------    ---------------            -------------- |");
        System.out.println("|  |  " + pink + "Gym  " + reset + "| == |  " + lime + "Courtyard  " + reset + "| ========== |   " + brown + "Library  " + reset + "| |");
        System.out.println("|  ---------    ---------------            -------------- |");
        System.out.println("|                     | |                       | |       |");
        System.out.println("|                -------------                -------     |");
        System.out.println("|                | " + orange + "Cafeteria" + reset + " | ============== | " + yellow + "Lab" + reset + " |     |");
        System.out.println("|                -------------                -------     |");
        System.out.println("|                     | |                       | |       |");
        System.out.println("|                 -----------               ----------    |");
        System.out.println("|                 | " + purple + "Outside" + reset + " | ============= | " + gray + "Gblock" + reset + " |    |");
        System.out.println("|                 -----------               ----------    |");
        System.out.println("|                     | |                                 |");
        System.out.println("|                 ----------                              |");
        System.out.println("|                 | " + cyan + "Tavern" + reset + " |                              |");
        System.out.println("|                 ----------                              |");
        System.out.println("|                     | |                                 |");
        System.out.println("|                 ----------                              |");
        System.out.println("|                 | " + blue + "Office" + reset + " |                              |");
        System.out.println("|                 ----------                              |");
        System.out.println("|_________________________________________________________|" + reset);

        // Zeige Ausgänge für den aktuellen Raum in ASCII-Art
        System.out.println(blue+"\n  -------------------------");
        System.out.println("  |  Verfügbare Ausgänge  |");

        System.out.println("-----------------------------");
        // Überprüfe und zeige die tatsächlich verfügbaren Ausgänge an
        if (currentRoom.getExit("norden") != null) {
            System.out.print("|    Norden   |");
        } else {
            System.out.print("|             |");
        }
        if (currentRoom.getExit("Osten") != null) {
            System.out.println("    Osten    |");
        } else {
            System.out.println("             |");
        }

        System.out.println("-----------------------------");

        if (currentRoom.getExit("Westen") != null) {
            System.out.print("|    Westen   |");
        } else {
            System.out.print("|             |");
        }
        if (currentRoom.getExit("Süden") != null) {
            System.out.println("    Süden    |");
        } else {
            System.out.println("             |");
        }

        System.out.println("-----------------------------"+reset);
    }

    private void displayHelp() {

        String reset = "\u001B[0m";
        String blue = "\u001B[34m";
        String orange = "\u001B[38;5;202m";


        System.out.println(blue+"                       ----------------------                      ");
        System.out.println("                       | Verfügbare Befehle |                      ");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("|  "+orange+"Norden, Osten, Süden, Westen:"+blue+" Um dich in den Raum zu bewegen.  |");
        System.out.println("|  "+orange+"Pickup <Gegenstand>:"+blue+" Um einen Gegenstand aufzuheben.           |");
        System.out.println("|  "+orange+"Inv:"+blue+" Um dein Inventar anzuzeigen.                              |");
        System.out.println("|  "+orange+"Open treasure:"+blue+" Um die Schatzkiste zu öffnen.                   |");
        System.out.println("|  "+orange+"Map:"+blue+" Um eine Kartenanzeige anzuzeigen.                         |");
        System.out.println("|  "+orange+"Back:"+blue+" Um in den vorherigen Raum zurückzukehren                 |");
        System.out.println("|  "+orange+"Drop:"+blue+" Um einen Gegenstand abzulegen.                           |");
        System.out.println("|  "+orange+"Tipp 1,2,3:"+blue+" Um einen Tipp zu bekommen wo sich was befindet.    |");
        System.out.println("|  "+orange+"Quit:"+blue+" Um das Spiel zu beenden.                                 |");
        System.out.println("-------------------------------------------------------------------"+reset);
    }

    private void pickupItem(String itemName) {

        String reset = "\u001B[0m";
        String red = "\u001B[31m";
        String yellow = "\u001B[33m";


        Item item = currentRoom.removeItem(itemName);
        if (item != null) {
            inventory.put(item.getName(), item);
            System.out.println(yellow+"Du hast '" + item.getName() + "' aufgenommen."+reset);
        } else {
            System.out.println(red+"Gegenstand nicht gefunden."+reset);
        }
    }

    private void showInventory() {

        String reset = "\u001B[0m";
        String green = "\u001B[32m";
        String red = "\u001B[31m";
        String blue = "\u001B[34m";
        String yellow = "\u001B[33m";
        String orange = "\u001B[38;5;202m";

        System.out.println(orange + "Inventar:" + reset);
        if (inventory.isEmpty()) {
            System.out.println(red + "Dein Inventar ist leer." + reset);
        } else {
            for (Item item : inventory.values()) {
                System.out.println(yellow + item.getName() + reset + " - "+ green + item.getDescription() + blue + " [Gewicht: " + item.getWeight() + " kg]" + reset);
            }
        }
    }

    private void openTreasure() {

        String reset = "\u001B[0m";
        String red = "\u001B[31m";
        String yellow = "\u001B[33m";


        if (inventory.containsKey("Goldener Schlüssel")) {
            if (hasTreasure) {
                System.out.println(red+"Du hast die Schatzkiste bereits geöffnet."+reset);
            } else {
                if (currentRoom.isWinnerRoom()) {
                    if (inventory.containsKey("Buch")) {
                        System.out.println(yellow+"▓██   ██▓ ▒█████   █    ██     █     █░ ██▓ ███▄    █ ");
                        System.out.println(" ▒██  ██▒▒██▒  ██▒ ██  ▓██▒   ▓█░ █ ░█░▓██▒ ██ ▀█   █ ");
                        System.out.println("  ▒██ ██░▒██░  ██▒▓██  ▒██░   ▒█░ █ ░█ ▒██▒▓██  ▀█ ██▒");
                        System.out.println("  ░ ▐██▓░▒██   ██░▓▓█  ░██░   ░█░ █ ░█ ░██░▓██▒  ▐▌██▒");
                        System.out.println("  ░ ██▒▓░░ ████▓▒░▒▒█████▓    ░░██▒██▓ ░██░▒██░   ▓██░");
                        System.out.println("   ██▒▒▒ ░ ▒░▒░▒░ ░▒▓▒ ▒ ▒    ░ ▓░▒ ▒  ░▓  ░ ▒░   ▒ ▒ ");
                        System.out.println(" ▓██ ░▒░   ░ ▒ ▒░ ░░▒░ ░ ░      ▒ ░ ░   ▒ ░░ ░░   ░ ▒░");
                        System.out.println(" ▒ ▒ ░░  ░ ░ ░ ▒   ░░░ ░ ░      ░   ░   ▒ ░   ░   ░ ░ ");
                        System.out.println(" ░ ░         ░ ░     ░            ░     ░           ░ ");
                        System.out.println(" ░ ░"+reset);
                        hasWon = true;
                    } else {
                        System.out.println(red+"Du benötigst das Buch, um die Schatzkiste zu öffnen."+reset);
                    }
                } else {
                    System.out.println(red+"Du kannst die Kiste nur öffnen, wenn du dich draussen befindest. ( Aus Sicherheitsgründen )"+reset);
                }
            }
        } else {
            System.out.println(red+"Du benötigst den Goldenen Schlüssel, um die Schatzkiste zu öffnen."+reset);
        }
    }

    private void checkWinCondition() {

        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";

        if (currentRoom.isWinnerRoom() && inventory.containsKey("Buch") && hasTreasure) {
            hasWon = true;
        }
    }

    private void search() {

        String reset = "\u001B[0m";
        String red = "\u001B[31m";
        String pink = "\u001B[38;5;205m";
        String green = "\u001B[32m";
        String yellow = "\u001B[33m";

        ArrayList<Item> itemsInRoom = currentRoom.getItems();

        if (itemsInRoom.isEmpty()) {
            System.out.println(red+"Im Raum befinden sich keine Gegenstände."+reset);
        } else {
            System.out.println(pink+"Im Raum befinden sich folgende Gegenstände:"+reset);
            for (Item item : itemsInRoom) {
                System.out.println(yellow+"- " + item.getName() + ": " + green + item.getDescription()+reset);
            }
        }
    }

    public void getHint(String hintType) {
        if (hintType.equals("tipp 1")) {
            giveHint("Die Schatzkiste befindet sich an einem hohen Ort...");
        } else if (hintType.equals("tipp 2")) {
            giveHint("Das Buch befindet sich an einem Ort, wo sich Bücher wohl fühlen...");
        } else if (hintType.equals("tipp 3")) {
            giveHint("Der goldene Schlüssel ist an einem Ort, an dem man oft arbeitet...");
        } else {
            System.out.println("Ungültiger Tipp-Befehl. Versuche es erneut.");
        }
    }

    private void giveHint(String hint) {

        String reset = "\u001B[0m";
        String purple = "\u001B[35m";

        System.out.println(purple+"Tipp: " + hint+reset);
    }

        public void dropItem(String itemName) {

        String reset = "\u001B[0m";
        String red = "\u001B[31m";
        String pink = "\u001B[38;5;205m";


            if (inventory.containsKey(itemName)) {
                Item item = inventory.remove(itemName);
                currentRoom.addItem(item);
                System.out.println(pink+"Du hast '" + item.getName() + "' fallen gelassen."+reset);
            } else {
                System.out.println(red+"Du besitzt keinen Gegenstand mit dem Namen '" + itemName + "' in deinem Inventar."+reset);
            }
        }



    }
