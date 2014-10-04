package com.smokebox.levelEditor.model;

/**
 * Created by Harald Wilhelmsen on 9/3/2014.
 */
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * A useful type of TextField where the field is associated with a DoubleProperty at instantiation and the necessary
 * listeners are put in place to keep the text and the value of the DoubleProperty in sync. That is, if either the text
 * in the field or the value of the DoubleProperty change, the other is updated to match it.
 *
 * Note that this class should not be used in conjunction with values that would be better represented by an
 * IntegerProperty or a LongProperty. Examples of these include indices and counts, since such values are more precisely
 * represented by binary integers. In such a case, a modification of this class should be used or made.
 *
 * @author John Zeringue <john.joseph.zeringue@gmail.com>
 */
public class NumberField extends TextField {

    /**
     * The DoubleProperty linked with this field
     */
    private final IntegerProperty intProperty;

    /**
     * Creates a new NumberField that is linked to the given DoubleProperty.
     *
     * @param intProperty the IntegerProperty to sync with this field
     */
    public NumberField(IntegerProperty intProperty) {
        super(intProperty.toString());
        this.intProperty = intProperty;
        setText(Integer.toString(intProperty.get()));

        /**
         * Add a ChangeListener to this field's textProperty so that the associated DoubleProperty is changed whenever
         * this field's text changes.
         */
        textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    try {
                        intProperty.set(Integer.valueOf(newValue));
                    } catch (NumberFormatException ex) {
                        /**
                         * Do nothing. newValue must not be able to be parsed as a double.
                         *
                         * There's a "better" way to check if newValue is parsable by Double.valueOf that is outlined at
                         * http://download.java.net/jdk8/docs/api/java/lang/Double.html#valueOf-java.lang.String- but
                         * it's simpler to catch the exception unless serious drawbacks to this approach are found.
                         */
                    }
                });

        /**
         * Add a ChangeListener to this field's associated DoubleProperty to change this field's text whenever the
         * associated DoubleProperty is changed.
         */
        intProperty.addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    setText(String.valueOf(newValue));
                });
    }

    /**
     * Returns the DoubleProperty that is linked with this field.
     *
     * @return this field's DoubleProperty
     */
    public IntegerProperty intProperty() {
        return intProperty;
    }
}