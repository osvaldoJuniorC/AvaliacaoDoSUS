package br.com.juniorosc.quarta.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Trabalho_UNG";
    public static final int DB_VERSION = 1;
    public static final String TABLE_AVALIACAO = "avaliacao";

    public static final String SCRIPT_TABLE_AVALIACAO = "CREATE TABLE IF NOT EXISTS" + TABLE_AVALIACAO +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " hospital VARCHAR (200) NOT NULL," +
            " especialidade VARCHAR (200) NOT NULL," +
            " especialista VARCHAR (200) NOT NULL," +
            " nota INTEGER (4) NOT NULL); ";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);


    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(SCRIPT_TABLE_AVALIACAO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL("DROP TABLE IF EXIXTS" + TABLE_AVALIACAO);
        onCreate(sqLiteDatabase);
    }
}
