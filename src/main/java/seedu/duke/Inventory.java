package seedu.duke;

import seedu.duke.exceptions.EditErrorException;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
    private static ArrayList<Item> items = new ArrayList<>();
    private static HashMap<String,Item> upcCodes = new HashMap<>();
    private static Trie trie = new Trie();
    private static HashMap<String,ArrayList<Item>> itemNameHash = new HashMap<>();

    public static void addItem(Item item) {
        if (upcCodes.containsKey(item.getUpc())) {
            Ui.printDuplicateAdd();
        } else {
            upcCodes.put(item.getUpc(),item);
            items.add(item);
            Ui.printSuccessAdd();
            ArrayList<Item> oldItems = new ArrayList<>();
            String itemName = item.getName().toLowerCase();
            if(itemNameHash.containsKey(oldItems)){
                oldItems=itemNameHash.get(itemName);
                itemNameHash.remove(itemName);
            }
            oldItems.add(item);
            itemNameHash.put(itemName,oldItems);
        }
    }

    /**
     * Searches for the item in the ArrayList and changes the item attributes according to the wishes of the user.
     *
     * @param editInfo The array of strings containing all edit information as defined by the user inputs.
     */
    public static void editItem(String[] editInfo) {
        try {
            Item updatedItem = retrieveItemFromArrayList(editInfo);
            Item oldItem = new Item(updatedItem.getName(), updatedItem.getUpc(), updatedItem.getQuantity().toString(),
                    updatedItem.getPrice().toString());
            for (int data = 1; data < editInfo.length; data += 1) {
                updateItemInfo(updatedItem, editInfo[data]);
            }
            String oldItemName = oldItem.getName();
            String newItemName = updatedItem.getName();
            if(oldItemName!=newItemName&&itemNameHash.get(oldItemName).size()==1){
                itemNameHash.remove(oldItemName);
            }else{
                ArrayList<Item> oldItems = itemNameHash.get(oldItemName);
                oldItems.remove(oldItemName);
                oldItems.add(updatedItem);
                itemNameHash.remove(oldItemName);
                itemNameHash.put(newItemName,oldItems);
            }
            upcCodes.remove(oldItem.getUpc());
            upcCodes.put(updatedItem.getUpc(),updatedItem);
            Ui.printEditDetails(oldItem, updatedItem);
        } catch (EditErrorException eee) {
            Ui.printItemNotFound();
        }
    }

    /**
     * Based on the user input, the appropriate attribute (Price, Quantity, Name etc.) of the item will be targeted
     * and subsequently edited to the user's respective input.
     *
     * @param item The target item in the ArrayList in which the user wants to edit.
     * @param data The user input which contains the information to be used to update the item attributes.
     */
    private static void updateItemInfo(Item item, String data) {
        if (data.contains("n/")) {
            String newName = data.replaceFirst("n/", "");
            item.setName(newName);
        } else if (data.contains("qty/")) {
            String updatedQuantity = data.replaceFirst("qty/", "");
            Integer newQuantity = Integer.valueOf(updatedQuantity);
            item.setQuantity(newQuantity);
        } else if (data.contains("p/")) {
            String updatedPrice = data.replaceFirst("p/", "");
            Double newPrice = Double.valueOf(updatedPrice);
            item.setPrice(newPrice);
        } else {
            Ui.printInvalidEditCommand();
        }
    }

    /**
     * Cycles through the ArrayList to obtain the item required to be interacted with by the user.
     *
     * @param editInfo The array of strings that contain the user inputs.
     * @return Returns the variable of type "Item", which is the item in question to be interacted with by the user.
     * @throws EditErrorException Exception related to all errors generated by the edit command.
     */
    public static Item retrieveItemFromArrayList(String[] editInfo) throws EditErrorException {
        String upcCode = editInfo[0].replaceFirst("upc/", "");
        if (!upcCodes.containsKey(upcCode)) {
            throw new EditErrorException();
        }
        Item selectedItem = upcCodes.get(upcCode);
        return selectedItem;
    }
    /**
     * Temporary List Method created by Kai Wen for Edit Function Testing.
     */
    public static void listAll() {
        for (Item x : items) {
            System.out.println((items.indexOf(x) + 1) + ".");
            System.out.println("Item Name: " + x.getName());
            System.out.println("UPC Code: " + x.getUpc());
            System.out.println("Quantity Available: " + x.getQuantity());
            System.out.println("Item Price: " + x.getPrice());
            System.out.println(" ");
        }
    }

    public static void removeItemAtIndex(int index) {
        String itemName = items.get(index).getName().toLowerCase();
        upcCodes.remove(items.get(index).getUpc());
        if(itemNameHash.get(itemName).size()==1){
            itemNameHash.remove(itemName);
        }else{
            ArrayList<Item> oldItems = itemNameHash.get(itemName);
            oldItems.remove(itemName);
            itemNameHash.remove(itemName);
            itemNameHash.put(itemName,oldItems);
        }
        items.remove(index);
    }

}