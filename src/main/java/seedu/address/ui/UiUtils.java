package seedu.address.ui;

import static seedu.address.ui.HelpWindow.USERGUIDE_URL;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * A utility class for Ui-related methods.
 */
public class UiUtils {
    private static final Logger logger = LogsCenter.getLogger(UiUtils.class);

    /**
     * Opens the link in the user's default browser.
     * If the link could not be opened (most likely because the user is on Linux), it is copied instead
     * and a dialog opens to notify the user that the link has been copied.
     *
     * @param link The link to open.
     */
    public static void browse(String link) {
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (IOException | URISyntaxException | UnsupportedOperationException e) {
            logger.fine(String.format("Copying the link (%s) as it failed to open.", USERGUIDE_URL));
            copyLinkAndShowDialog(link);
        }
    }

    /**
     * Copies the link and displays a dialog indicating that the link has been copied to the system clipboard.
     *
     * @param link The link to copy.
     */
    private static void copyLinkAndShowDialog(String link) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(link);
        clipboard.setContent(url);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Link copied");
        alert.setContentText("The link has been copied to your clipboard.");

        // Solution below adapted from
        // https://stackoverflow.com/questions/27976345/how-do-you-set-the-icon-of-a-dialog-control-java-fx-java-8
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/images/address_book_32.png"));

        alert.showAndWait();
    }
}
