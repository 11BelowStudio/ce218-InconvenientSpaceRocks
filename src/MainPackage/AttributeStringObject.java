package MainPackage;

import utilities.AttributeString;
import utilities.Vector2D;


public class AttributeStringObject<T> extends StringObject {

    private AttributeString<T> attributeString;

    AttributeStringObject(Vector2D p, Vector2D v, String attribute, T value, int a){
        super(p,v,a);
        makeAttributeString(attribute,value);
    }

    AttributeStringObject(Vector2D p, Vector2D v, String attribute, T value){
        super(p,v);
        makeAttributeString(attribute,value);
    }

    public AttributeStringObject<T> showValue(T value){
        attributeString.showValue(value);
        updateText();
        return this;
    }

    public AttributeStringObject<T> rename(String attributeName){ attributeString.rename(attributeName); updateText(); return this;}

    public T getValue(){ return attributeString.getValue();}

    private void updateText(){ setText(attributeString.toString()); }

    public AttributeStringObject<T> kill(){ super.kill(); return this; }

    public void update(){
        super.update();
        updateText();
    }

    private void makeAttributeString(String a, T v){
        attributeString = new AttributeString<>(a,v);
        setText(attributeString.toString());
    }
}
