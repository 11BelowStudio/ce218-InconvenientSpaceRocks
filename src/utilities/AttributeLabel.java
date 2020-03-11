package utilities;

import javax.swing.*;

public class AttributeLabel<T> extends JLabel {

    /*
    This is a custom JLabel which can be used to show a value with a bit of text
    bit easier than trying to include methods to completely rewrite each label individually
    whenever the value is updated by any amount, y'know?

    this is an enhancement of the AttributeLabel class I used within my CE203 assignment,
    which in turn was an enhancement of the AttributeLabel class I used within a game I made for a game jam in november,
    but this one is generic, allowing you to store whatever sort of attribute value thing you want in here

    initially it was just like the last AttributeLabel but the value was a generic type instead of an int
    and then I kinda got to the bit where I needed to draw strings in the view,
    so I made AttributeString<T> to effectively cut out the middleman in the form of the label
    and so this eventually went completely unused
    but I'm not yeeting the file just in case it comes in handy some day

    and yeah it's basically a wrapper class for an AttributeString that wraps it in a JLabel
    */

    private AttributeString<T> labelText;
    //and yeah it basically just holds the labelText AttributeString


    public AttributeLabel (String attributeName, T value){
        //sets attributeName and value, before updating the text of it respectively
        labelText = new AttributeString<>(attributeName,value);
        //this.attributeName = attributeName;
        //this.value = value;
        updateText();
    }

    public void showValue(T value){
        //this.value = value;
        labelText.showValue(value);
        updateText();
    }

    //updates the text on the label to show the new value, and updates the associated value attribute
    public void rename(String attributeName){ labelText.rename(attributeName); updateText(); }

    private void updateText(){ this.setText(labelText.toString()); }

    public T getValue(){
        return labelText.getValue();
    }
    //returns the 'value' attribute of this object

    public String getText(){ return super.getText(); }

    public String toString(){ return labelText.toString(); }


}