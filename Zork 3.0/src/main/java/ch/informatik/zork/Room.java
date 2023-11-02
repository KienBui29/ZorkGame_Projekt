package ch.informatik.zork;

import java.util.ArrayList;
class Room {
    private String description;
    private boolean isWinnerRoom;
    private ArrayList<Item> items;
    private Room northExit, eastExit, southExit, westExit;

    private boolean isLocked;


    public Room(String description) {
        this.description = description;
        this.isWinnerRoom = false;
        this.items = new ArrayList<>();
        this.isLocked = true;
    }

    public void setExits(Room north, Room east, Room south, Room west) {
        northExit = north;
        eastExit = east;
        southExit = south;
        westExit = west;
    }

    public void setIsWinnerRoom(boolean isWinnerRoom) {
        this.isWinnerRoom = isWinnerRoom;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item removeItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public String getDescription() {
        return description;
    }

    public Room getExit(String direction) {
        if (direction.equalsIgnoreCase("norden")) {
            return northExit;
        } else if (direction.equalsIgnoreCase("osten")) {
            return eastExit;
        } else if (direction.equalsIgnoreCase("süden")) {
            return southExit;
        } else if (direction.equalsIgnoreCase("westen")) {
            return westExit;
        }
        return null;
    }

    public boolean isWinnerRoom() {
        return isWinnerRoom;
    }


    public void lock() {
        isLocked = true;
    }
}
