package is.hi.apptionary.randomwords;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.io.File;
import java.sql.Connection;
import java.util.Random;

import static android.database.sqlite.SQLiteDatabase.OPEN_READONLY;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by steina on 15.2.2018.
 */


public class RandomWord extends SQLLiteAssetHelper {

    public static String DB_NAME;
    private static String DB_PATH;


    public RandomWord(){
        Uri path = Uri.parse("file:///android_asset/words.db");
        DB_NAME = path.toString();

    }

    private int numRows(){
        File file = new File(DB_NAME);
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_NAME,null, OPEN_READONLY);
        Cursor res = db.rawQuery("SELECT MAX(id) FROM WORDS",null);
        return res.moveToFirst() ? res.getInt(0) : 0;
    }


    public static void main(String [] args){
        RandomWord rw = new RandomWord();
        System.out.println(rw.numRows());

    }



}
