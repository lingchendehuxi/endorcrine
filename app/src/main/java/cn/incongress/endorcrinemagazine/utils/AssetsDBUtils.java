package cn.incongress.endorcrinemagazine.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.endorcrinemagazine.bean.CityBean;
import cn.incongress.endorcrinemagazine.bean.HospitalBean;


/**
 * Created by Administrator on 2015/7/9.
 */
public class AssetsDBUtils {
    private static final String CITY_CITYID = "cityId";

    public static List<CityBean> getAllCity() {
        List<CityBean> beans = new ArrayList<CityBean>();

        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = mg.getDatabase("hospital.db");
        Cursor cursor =db.query("city", null, null, null, null, null, "pinyin");
        if(cursor != null) {
            while(cursor.moveToNext()) {
                int cityId = cursor.getInt(cursor.getColumnIndex("cityId"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int provinceId = cursor.getInt(cursor.getColumnIndex("provinceId"));
                String pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
                String provinceName = getProvinceName(provinceId);
                beans.add(new CityBean(cityId,name,pinyin,provinceId, provinceName));
            }
        }
        cursor.close();
        return beans;
    }

    public static String getProvinceName(int provinceId) {
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = mg.getDatabase("hospital.db");
        Cursor cursor =db.query("province",null,"provinceId=?",new String[]{""+provinceId},null,null,null);
        String name = "";
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex("name"));
            }
        }
        cursor.close();
        return name;
    }

  public static List<HospitalBean> getHospitalsByCityId(int cityId) {
        List<HospitalBean> beans = new ArrayList<HospitalBean>();
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();

        SQLiteDatabase db = mg.getDatabase("hospital.db");
        Cursor cursor =db.query("hospital", null, "cityId=?", new String[]{"" + cityId}, null, null, null);

        if(cursor!= null) {
            while(cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int hospitalId = cursor.getInt(cursor.getColumnIndex("hospitalId"));
                beans.add(new HospitalBean(cityId,hospitalId,name));
            }
        }
        cursor.close();
        return beans;
    }

    public static List<HospitalBean> getHospitalByNameAndId(int cityId, String likeName) {
        List<HospitalBean> beans = new ArrayList<HospitalBean>();
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();

        SQLiteDatabase db = mg.getDatabase("hospital.db");
//        Cursor cursor =db.query("hospital", null, "cityId=? and name like /'/%?/%/'", new String[]{"" + cityId, likeName}, null, null, null);
//        Cursor cursor = db.execSQL("select * from hospital where cityId=" + cityId + "and name like '%" + likeName + "%'");
        Cursor cursor = db.rawQuery("select * from hospital where cityId=? and name like ?", new String[]{"" + cityId, "%" + likeName + "%"});

        if(cursor!= null) {
            while(cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int hospitalId = cursor.getInt(cursor.getColumnIndex("hospitalId"));
                beans.add(new HospitalBean(cityId,hospitalId,name));
            }
        }
        cursor.close();
        return beans;
    }

     /* public static List<SchoolProvinceBean> getAllSchoolProvince() {
        List<SchoolProvinceBean> beans = new ArrayList<SchoolProvinceBean>();
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();

        SQLiteDatabase db = mg.getDatabase("school.db");
        Cursor cursor = db.query("provinces", null, null, null, null, null, null, null);

        if(cursor != null) {
            while(cursor.moveToNext()) {
                int provinceId = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                beans.add(new SchoolProvinceBean(provinceId,name));
            }
        }
        cursor.close();
        return beans;
    }

    public static List<SchoolBean> getAllSchoolByProvinceId(String provinceId) {
        List<SchoolBean> beans = new ArrayList<SchoolBean>();
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();

        SQLiteDatabase db = mg.getDatabase("school.db");
        Cursor cursor = db.query("univs", null, "pid=?", new String[]{provinceId}, null, null, null);

        if(cursor != null) {
            while(cursor.moveToNext()) {
                int schoolId = cursor.getInt(cursor.getColumnIndex("id"));
                String schoolName = cursor.getString(cursor.getColumnIndex("name"));
                beans.add(new SchoolBean(schoolId,schoolName, Integer.parseInt(provinceId)));
            }
        }
        cursor.close();
        return beans;
    }

    public static List<SchoolBean> getAllSchoolByProvinceIdAndName(String provinceId, String likeName) {
        List<SchoolBean> beans = new ArrayList<SchoolBean>();
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();

        SQLiteDatabase db = mg.getDatabase("school.db");
        Cursor cursor = db.rawQuery("select * from univs where pid=? and name like ?", new String[]{"" + provinceId, "%" + likeName + "%"});

        if(cursor != null) {
            while(cursor.moveToNext()) {
                int schoolId = cursor.getInt(cursor.getColumnIndex("id"));
                String schoolName = cursor.getString(cursor.getColumnIndex("name"));
                beans.add(new SchoolBean(schoolId,schoolName, Integer.parseInt(provinceId)));
            }
        }
        cursor.close();
        return beans;
    }*/
}
