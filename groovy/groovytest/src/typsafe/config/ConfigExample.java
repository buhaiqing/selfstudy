package typsafe.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;

import java.util.Map;

/**
 * Created by buha on 11/21/2015.
 */
public class ConfigExample {
    public static void main(String[] args) {
        Config cf = ConfigFactory.load();
        for (Map.Entry<String, ConfigValue> entry : cf.entrySet()) {
            System.out.println(entry.getKey());
        }
        Config sample = cf.getConfig("sample");

        System.out.println(sample.getInt("a"));
        System.out.println(sample.getInt("b"));
//        System.out.println(cf.getString("user.home"));
//        System.out.println(cf.getString("name"));
//        System.out.println(cf.getInt("age"));
//        System.out.println(cf.getString("city"));
//        System.out.println(cf.getString("country"));
    }
}
