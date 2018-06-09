package zlei.bysj.cslk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zlei1 on 2018/3/30.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

        public static final String CREATE_TABLE = "create table roaddata (" +
                "id integer primary key autoincrement, " +
                "value text)";

        private Context mContext;

        public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);

            mContext = context;
        }

        /**
         * 数据库已经创建过了， 则不会执行到，如果不存在数据库则会执行
         * @param db
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE); // 执行这句才会创建表

        }

        /**
         * 创建数据库时不会执行，增大版本号升级时才会执行到
         * @param db
         * @param oldVersion
         * @param newVersion
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // 在这里面可以把旧的表 drop掉 从新创建新表，
            // 但如果数据比较重要更好的做法还是把旧表的数据迁移到新表上，比如升级qq聊天记录被删掉肯定招骂
        }
}
