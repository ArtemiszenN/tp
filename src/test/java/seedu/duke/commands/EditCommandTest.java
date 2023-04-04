package seedu.duke.commands;

import org.junit.jupiter.api.Test;
import seedu.duke.objects.Inventory;
import seedu.duke.utils.parsers.AddParser;
import seedu.duke.utils.parsers.EditParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditCommandTest {
    Inventory inventory;

    /**
     * Test for all scenarios of user inputs for the "edit" command.
     */
    @Test
    public void editItemTest() {
        inventory = new Inventory();

        AddParser addParser = new AddParser("n/orange upc/123 qty/5 p/5",inventory);
        addParser.run();
        addParser = new AddParser("n/Cat upc/1111 qty/5 p/5",inventory);
        addParser.run();

        //Test 1: Empty Name
        System.out.println("Test 1: Empty Name:");
        EditParser editParser = new EditParser("upc/123 n/", inventory);
        editParser.run();
        assertEquals("", inventory.getItemInventory().get(0).getName());

        //Test 2: Same Quantity, Should have no output
        System.out.println("Test 2: Same Quantity, Should have no output");
        editParser = new EditParser("upc/123 n/oranges qty/5", inventory);
        editParser.run();
        assertEquals("oranges", inventory.getItemInventory().get(0).getName());
        assertEquals(5, inventory.getItemInventory().get(0).getQuantity());

        //Test 3: Name with multiple spaces.
        System.out.println("Test 3: Name with multiple spaces.");
        editParser = new EditParser("upc/123 n/orange and apples qty/6", inventory);
        editParser.run();
        assertEquals("orange and apples", inventory.getItemInventory().get(0).getName());
        assertEquals(6, inventory.getItemInventory().get(0).getQuantity());

        //Test 4: Negative Quantity Input.
        System.out.println("Test 4: Negative Quantity Input.");
        editParser = new EditParser("upc/123 qty/-5", inventory);
        editParser.run();
        assertEquals(6, inventory.getItemInventory().get(0).getQuantity());

        //Test 5: Change Price
        System.out.println("Test 5: Change Price");
        editParser = new EditParser("upc/123 p/4", inventory);
        editParser.run();
        assertEquals(4, inventory.getItemInventory().get(0).getPrice());

        //Test 6: Wrong Spacing between parameters.
        System.out.println("Test 6: Wrong Spacing between parameters.");
        editParser = new EditParser("upc/123 n/p/", inventory);
        editParser.run();
        assertEquals("p/", inventory.getItemInventory().get(0).getName());

        //Test 7: Item Does not exist.
        System.out.println("Test 7: Item Does not exist.");
        editParser = new EditParser("upc/12", inventory);
        editParser.run();

        //Test 8: Create category of fruit.
        System.out.println("Test 8: Create category of fruit.");
        editParser = new EditParser("upc/123 c/fruit", inventory);
        editParser.run();
        assertEquals("fruit", inventory.getItemInventory().get(0).getCategory());

        //Test 9: Create category with multiple spaces. (Not allowed at the moment).
        System.out.println("Test 9: Create category with multiple spaces. (Not allowed at the moment).");
        editParser = new EditParser("upc/123 c/fruit and vegetables", inventory);
        editParser.run();
        assertEquals("fruit", inventory.getItemInventory().get(0).getCategory());

        //Test 10: String input as price edit.
        System.out.println("Test 10: String input as price edit.");
        editParser = new EditParser("upc/123 p/double", inventory);
        editParser.run();
        assertEquals(4, inventory.getItemInventory().get(0).getPrice());

        //Test 11: Wrong Format
        System.out.println("Test 11: Wrong Format");
        editParser = new EditParser("upc/1111 {n/capybara", inventory);
        editParser.run();
        assertEquals("Cat", inventory.getItemInventory().get(1).getName());

        //Test 12: Price out of range.
        System.out.println("Test 12: Price out of range.");
        editParser = new EditParser("upc/1111 n/capybara p/99999999999999 qty/6", inventory);
        editParser.run();
        assertEquals(5, inventory.getItemInventory().get(1).getPrice());
        assertEquals(5, inventory.getItemInventory().get(1).getQuantity());
        assertEquals("Cat", inventory.getItemInventory().get(1).getName());

        //Test 13: Double Price Inputs with empty price.
        System.out.println("Test 13: Double Price Inputs with empty price.");
        editParser = new EditParser("upc/123 p/ p/45", inventory);
        editParser.run();
        assertEquals(4, inventory.getItemInventory().get(0).getPrice());

        //Test 14: Double price inputs with quantity input.
        System.out.println("Test 14: Double price inputs with quantity input.");
        editParser = new EditParser("upc/123 p/1 p/1 qty/100", inventory);
        editParser.run();
        assertEquals(4, inventory.getItemInventory().get(0).getPrice());
        assertEquals(5, inventory.getItemInventory().get(1).getQuantity());
    }

}
