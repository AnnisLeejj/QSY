package MyUtils.SharePrefenceUtils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Set;

/**
 * Created by Lee on 2017/9/28.
 */

public class SPUtils {
    static SharedPreferences.Editor edit;
    static SharedPreferences preferences;
    static SPUtils instance;

    public static SPUtils init(Context context) {
        synchronized (SPUtils.class) {
            instance = new SPUtils();
            preferences = context.getSharedPreferences("myshare_prefrence", Context.MODE_PRIVATE);
            edit = preferences.edit();
            return instance;
        }
    }

    public void put(String key, Object obj) {
        if (obj instanceof Integer) {
            edit.putInt(key, (Integer) obj).commit();
        } else if (obj instanceof String) {
            edit.putString(key, (String) obj).commit();
        } else if (obj instanceof Boolean) {
            edit.putBoolean(key, (Boolean) obj).commit();
        } else if (obj instanceof Set) {
            edit.putStringSet(key, (Set) obj).commit();
        } else if (obj instanceof Long) {
            edit.putLong(key, (Long) obj).commit();
        } else if (obj instanceof Float) {
            edit.putFloat(key, (Float) obj).commit();
        } else {
            edit.putString(key, new Gson().toJson(obj)).commit();
        }
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public long getLong(String key) {
        return preferences.getLong(key, 0);
    }

    public float getFloat(String key) {
        return preferences.getFloat(key, 0);
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public Set<String> getSet(String key) {
        return preferences.getStringSet(key, null);
    }
}
