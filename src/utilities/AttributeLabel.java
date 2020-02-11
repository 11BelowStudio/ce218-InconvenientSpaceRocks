package utilities;

import javax.swing.*;

public class AttributeLabel<T> extends JLabel {

    /*
    This is a custom JLabel which can be used to show a value with a bit of text
    bit easier than trying to include methods to completely rewrite each label individually
    whenever the value is updated by any amount, y'know?
    */

    private AttributeString<T> labelText;
    //and yeah it basically just holds the labelText string


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

    public void rename(String attributeName){
        //this.attributeName = attributeName;
        labelText.rename(attributeName);
        updateText();
    }

    private void updateText(){
        //this.setText(this.attributeName + this.value);
        this.setText(labelText.toString());
    }

    public T getValue(){
        return labelText.getValue();
    }
    //returns the 'value' attribute of this object

    public String getText(){
        return super.getText();
    }

    public String toString(){
        return labelText.toString();
    }


}