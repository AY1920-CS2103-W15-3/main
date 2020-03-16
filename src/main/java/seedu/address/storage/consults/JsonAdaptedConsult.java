package seedu.address.storage.consults;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Location;
import seedu.address.model.event.consult.Consult;

/**
 * Jackson-friendly version of {@link Consult}.
 */
class JsonAdaptedConsult {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Consult's %s field is missing!";
    private static final String INVALID_DATE_FORMAT = "Invalid date format!";

    private final String beginDateTime;
    private final String endDateTime;
    private final String eventLocation;

    /**
     * Constructs a {@code JsonAdaptedConsult} with the given consult details.
     */
    @JsonCreator
    public JsonAdaptedConsult(@JsonProperty("beginDateTime") String beginDateTime,
                              @JsonProperty("endDateTime") String endDateTime,
                              @JsonProperty("eventLocation") String eventLocation) {
        this.beginDateTime = beginDateTime;
        this.endDateTime = endDateTime;
        this.eventLocation = eventLocation;
    }

    /**
     * Converts a given {@code Consult} into this class for Jackson use.
     */
    public JsonAdaptedConsult(Consult source) {
        beginDateTime = source.getBeginDateTime().toString();
        endDateTime = source.getEndDateTime().toString();
        eventLocation = source.getLocation().eventLocation;
    }

    /**
     * Converts this Jackson-friendly adapted consult object into the model's {@code Consult} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted consult.
     */
    public Consult toModelType() throws IllegalValueException {

        if (beginDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "BEGIN DATE TIME"));
        }

        if (endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "END DATE TIME"));
        }

        LocalDateTime modelBeginDateTime;
        LocalDateTime modelEndDateTime;

        try {
            modelBeginDateTime = LocalDateTime.parse(this.beginDateTime);
            modelEndDateTime = LocalDateTime.parse(this.endDateTime);
        } catch (DateTimeParseException ex) {
            throw new IllegalValueException(INVALID_DATE_FORMAT);
        }

        if (eventLocation == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "EVENT LOCATION"));
        }

        final Location modelLocation = new Location(eventLocation);

        return new Consult(modelBeginDateTime, modelEndDateTime, modelLocation);
    }
}
