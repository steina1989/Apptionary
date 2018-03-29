package is.hi.apptionary.vinnsla;

/**
 * Sér um að búa til gild ID fyrir leiki. Hefur samband við firebase til að finna nýjasta ID og
 * býr til annað út frá því á reglulegan hátt.
 * Created by notandi on 3/15/2018.
 */

public class GameIdGenerator {

    /**
     * Skilar gildu unique ID fyrir leik
     * @return nýtt ID
     */
    public static String getId(){
        return "leikur";
    }
}
