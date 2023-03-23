package seedu.duke;


import seedu.duke.objects.Inventory;
import seedu.duke.types.Types;
import seedu.duke.utils.SessionManager;
import seedu.duke.utils.Storage;
import seedu.duke.utils.Ui;
import seedu.duke.utils.parser.Parser;

public class Duke {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    private Storage storage;
    private Ui ui;
    private Parser parser;
    private Inventory inventory;
    private SessionManager currentSession;


    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        inventory = new Inventory();
        currentSession = SessionManager.getInstance();
        inventory = currentSession.getSession();
        parser = new Parser(inventory, currentSession);
        ;
    }

    public void run() {
        while (true) {
            parser.mainParser();
        }
    }

    public static void main(String[] args) {
        new Duke(Types.SESSIONFILEPATH).run();
    }
}
