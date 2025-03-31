package seedu.address.ui;

import java.util.Comparator;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane attributes;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);

        ImageView phoneIcon = new ImageView(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/images/telephone-receiver.png"))));
        phoneIcon.setFitHeight(16);
        phoneIcon.setFitWidth(16);
        phoneIcon.setPreserveRatio(true);
        phone.setGraphic(phoneIcon);
        phone.setText(person.getPhone().value);

        ImageView emailIcon = new ImageView(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/images/email.png"))));
        emailIcon.setFitHeight(16);
        emailIcon.setFitWidth(16);
        emailIcon.setPreserveRatio(true);
        email.setGraphic(emailIcon);
        email.setText(person.getEmail().value);

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getAttributes().stream()
                .sorted()
                .forEach(attribute -> attributes.getChildren().add(createAttributeLabel(attribute)));
    }

    private static Label createAttributeLabel(Attribute attribute) {
        Label label = new Label(attribute.getDisplayText());
        if (attribute.hasSiteLink()) {
            label.setId("site-attribute-list");
        }
        return label;
    }
}
