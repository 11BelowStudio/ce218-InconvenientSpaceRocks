package game;

import utilities.AttributeString;
import utilities.Vector2D;

import static game.Constants.DT;

public class AttributeStringObject<T> extends StringObject {

    private AttributeString<T> attributeString;

    AttributeStringObject(Vector2D p, Vector2D v, String attribute, T value, int a){
        super(p,v,a);
        attributeString = new AttributeString<>(attribute,value);
        setText(attributeString.toString());
    }

    AttributeStringObject(Vector2D p, Vector2D v, String attribute, T value){
        super(p,v);
        setText(attributeString.toString());
    }

    public void showValue(T value){
        attributeString.showValue(value);
        updateText();
    }

    public void rename(String attributeName){
        attributeString.rename(attributeName);
        updateText();
    }

    public T getValue(){
        return attributeString.getValue();
    }

    private void updateText(){
        setText(attributeString.toString());
    }

    public AttributeStringObject<T> killThis(){
        super.kill();
        return this;
    }

    public void update(){
        if (position.y < 0){
            dead = true;
        } else {
            position.addScaled(velocity, DT);
        }
        updateText();
    }
}
