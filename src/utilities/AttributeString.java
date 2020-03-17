package utilities;


public class AttributeString<T>{

    /*
    This is a utilty class which can be used to show a value with a name for it
    bit easier than trying to include methods to completely rewrite every individual string
    whenever the value associated with that string is updated by any amount, y'know?
    */

    private String attributeName;
    //holds the name of the attribute that this AttributeString is keeping track of

    private T value;
    //holds the value of the attribute that this AttributeString is keeping track of

    private String theString;
    //the final string itself with the attribute and the value


    public AttributeString(String attributeName, T value){
        //sets attributeName and value, before updating the text of it respectively
        this.attributeName = attributeName;
        this.value = value;
        updateText();
    }


    public String showValue(T value){
        this.value = value;
        return updateText();
    }//updates the value attribute, updates theString to have this new value, then returns theString.

    public String rename(String attributeName){
        this.attributeName = attributeName;
        return updateText();
    } //ditto but changing the attributeName instead

    private String updateText(){
        return theString = (attributeName + value);
    } //the string is the attributeName followed by the value

    public T getValue(){ return value; }
    //returns the 'value' attribute of this object

    public String getAttributeName(){ return attributeName; }
    //ditto but for 'attributeName' instead

    public String toString(){ return theString; }


}