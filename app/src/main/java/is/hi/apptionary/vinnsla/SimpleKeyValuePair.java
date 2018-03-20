package is.hi.apptionary.vinnsla;

/**
 * Created by notandi on 3/20/2018.
 */

public class SimpleKeyValuePair {
    String key;
    String value;
    public SimpleKeyValuePair(){

    }
    public SimpleKeyValuePair(String key, String value){
        this.key=key;
        this.value=value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString(){
        return key+value;
    }
}
