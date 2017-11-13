package ae.qmatic.tacme.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import ae.qmatic.tacme.model.AppointmentBookingListModel;
import ae.qmatic.tacme.model.RemoteBookingListModel;

/**
 * Created by mdev3 on 8/24/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    Context c;
    public static String DATABASENAME = "MyBooking";
    public static String TABLENAME = "MyBookingTable";
    public static String TABLENAME2 = "MyAppointmentTable";
    public static String ID = "id";
    public static String SERVICENAME = "servicename";
    public static String SERVICEID = "serviceid";
    public static String BRANCHNAME = "branchname";
    public static String BRANCHID= "branchid";
    public static String TICKET = "ticket";
    public static String TIME = "time";
    public static String COLOR = "colorcode";
    public static String STATUS = "status";
    public static String WAITQUEUE  = "waitqueue";
    public static String AVERAGESERVINGTIME = "averagetime";
    public static String VISITID= "visitID";



    public static String EMAIL= "emailid";
    public static String FIRSTNAME= "firstname";
    public static String LASTNAME= "lastname";
    public static String PHONE= "phone";
    public static String DATE= "date";
    public static String EXTERNALID= "externalid";
    public static String IDS= "Ids";
    public static String ORDERTIME= "ordertime";



    public DatabaseHandler(Context context) {
        super(context, DATABASENAME, null, 1);
        c = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE if not exists MyBookingTable(id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SERVICEID
                + " TEXT,"
                + SERVICENAME
                + " TEXT,"
                + BRANCHID
                + " TEXT,"
                + BRANCHNAME
                + " TEXT,"
                + TICKET
                + " TEXT,"
                + TIME
                + " TEXT,"
                + STATUS
                + " TEXT,"
                + WAITQUEUE
                + " TEXT,"
                + AVERAGESERVINGTIME
                + " TEXT,"
                + VISITID
                + " TEXT,"
                + COLOR
                + " TEXT )");


        db.execSQL("CREATE TABLE if not exists MyAppointmentTable(id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SERVICEID
                + " TEXT,"
                + SERVICENAME
                + " TEXT,"
                + BRANCHID
                + " TEXT,"
                + BRANCHNAME
                + " TEXT,"
                + EMAIL
                + " TEXT,"
                + FIRSTNAME
                + " TEXT,"
                + LASTNAME
                + " TEXT,"
                + PHONE
                + " TEXT,"
                + DATE
                + " TEXT,"
                + EXTERNALID
                + " TEXT,"
                + TIME
                + " TEXT,"
                + IDS
                + " TEXT,"
                + STATUS
                + " TEXT,"
                + ORDERTIME
                + " TEXT )");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLENAME);
        db.execSQL("DROP TABLE IF EXISTS" + TABLENAME2);
        onCreate(db);
    }

    public void addData(String mServiceid,String mSerViceName, String mBranchid, String mBranchName, String mTicket, String mTime, String mColor,String mStatus, String visitid) {
        Log.e("Sqlite","opened");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues mContentValues 		= 		new ContentValues();
        mContentValues.put(SERVICEID, mServiceid);
        mContentValues.put(SERVICENAME, mSerViceName);
        mContentValues.put(BRANCHID, mBranchid);
        mContentValues.put(BRANCHNAME, mBranchName);
        mContentValues.put(TICKET, mTicket);
        mContentValues.put(TIME, mTime);
        mContentValues.put(STATUS, mStatus);
        mContentValues.put(WAITQUEUE, "2");
        mContentValues.put(AVERAGESERVINGTIME, "475");
        mContentValues.put(VISITID, visitid);
        mContentValues.put(COLOR, mColor);
        db.insert(TABLENAME, null, mContentValues);
        db.close();
    }

    public ArrayList<RemoteBookingListModel> getListRemoteBooking() 	{
        ArrayList<RemoteBookingListModel> mArraylist = new ArrayList<RemoteBookingListModel>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from MyBookingTable", null);
        if (cursor.getCount() != 0)
        {
            if (cursor.moveToFirst())
            {
                do {
                    RemoteBookingListModel item = new RemoteBookingListModel();
                    Log.e("ServiceName ",""+cursor.getString(cursor.getColumnIndex(SERVICENAME)));
                    item.setService_id(cursor.getString(cursor.getColumnIndex(SERVICEID)));
                    item.setService_name(cursor.getString(cursor.getColumnIndex(SERVICENAME)));
                    item.setBranch_id(cursor.getString(cursor.getColumnIndex(BRANCHID)));
                    item.setBranch_name(cursor.getString(cursor.getColumnIndex(BRANCHNAME)));
                    item.setTicket(cursor.getString(cursor.getColumnIndex(TICKET)));
                    item.setColor(cursor.getString(cursor.getColumnIndex(COLOR)));
                    item.setStatus(cursor.getString(cursor.getColumnIndex(STATUS)));
                    item.setWaitingQueue(cursor.getString(cursor.getColumnIndex(WAITQUEUE)));
                    item.setAverageServingTime(cursor.getString(cursor.getColumnIndex(AVERAGESERVINGTIME)));
                    item.setVisitId(cursor.getString(cursor.getColumnIndex(VISITID)));
                    item.setTime(cursor.getString(cursor.getColumnIndex(TIME)));
                    mArraylist.add(item);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return mArraylist;
    }


    public void deleteRemoteBooking(String mticket) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from " + TABLENAME
                + " where ticket='" + mticket + "'");
        db.close();
    }


    public void addAppointment(String mServiceid,String mSerViceName, String mBranchid, String mBranchName, String mEmail, String mFName, String mLName,String mPhone, String mDate, String mExternalId, String mTime, String mID, String status, String odertime) {
        Log.e("Sqlite","opened");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues mContentValues 		= 		new ContentValues();
        mContentValues.put(SERVICEID, mServiceid);
        mContentValues.put(SERVICENAME, mSerViceName);
        mContentValues.put(BRANCHID, mBranchid);
        mContentValues.put(BRANCHNAME, mBranchName);
        mContentValues.put(EMAIL, mEmail);
        mContentValues.put(FIRSTNAME, mFName);
        mContentValues.put(LASTNAME, mLName);
        mContentValues.put(PHONE, mPhone);
        mContentValues.put(DATE, mDate);
        mContentValues.put(EXTERNALID, mExternalId);
        mContentValues.put(TIME, mTime);
        mContentValues.put(IDS,mID);
        mContentValues.put(STATUS,status);
        mContentValues.put(ORDERTIME,odertime);
        db.insert(TABLENAME2, null, mContentValues);
        db.close();
    }

    public ArrayList<AppointmentBookingListModel> getListAppointmentBooking() 	{
        ArrayList<AppointmentBookingListModel> mArraylist = new ArrayList<AppointmentBookingListModel>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from MyAppointmentTable ORDER BY ordertime DESC", null);
        if (cursor.getCount() != 0)
        {
            if (cursor.moveToFirst())
            {
                do {
                    AppointmentBookingListModel item = new AppointmentBookingListModel();
                    Log.e("ServiceName ",""+cursor.getString(cursor.getColumnIndex(SERVICENAME)));
                    item.setServiceid(cursor.getString(cursor.getColumnIndex(SERVICEID)));
                    item.setServiceName(cursor.getString(cursor.getColumnIndex(SERVICENAME)));
                    item.setBranchid(cursor.getString(cursor.getColumnIndex(BRANCHID)));
                    item.setBranchName(cursor.getString(cursor.getColumnIndex(BRANCHNAME)));
                    item.setEmailid(cursor.getString(cursor.getColumnIndex(EMAIL)));
                    item.setfName(cursor.getString(cursor.getColumnIndex(FIRSTNAME)));
                    item.setlName(cursor.getString(cursor.getColumnIndex(LASTNAME)));
                    item.setPhone(cursor.getString(cursor.getColumnIndex(PHONE)));
                    item.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                    item.setExternalid(cursor.getString(cursor.getColumnIndex(EXTERNALID)));
                    item.setTime(cursor.getString(cursor.getColumnIndex(TIME)));
                    item.setIds(cursor.getString(cursor.getColumnIndex(IDS)));
                    item.setStatus(cursor.getString(cursor.getColumnIndex(STATUS)));
                    item.setOrderTime(cursor.getString(cursor.getColumnIndex(ORDERTIME)));
                    mArraylist.add(item);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return mArraylist;
    }

    public void updateAppointmentStatus(String id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STATUS, status);
        db.update(TABLENAME2, contentValues, "Ids=?", new String[]{id});

        db.close();
    }

}
