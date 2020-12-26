package Utility;

import com.github.javafaker.Faker;
import java.util.LinkedHashMap;
import java.util.Map;

public class SpartanUtil {
//spartan MyUrl = 54.224.154.167:8000
    public static Map<String, Object> getRandomSpartanRequestPayLoad(){

        Faker faker = new Faker();
        Map<String, Object> payLoadMap = new LinkedHashMap<>();
        payLoadMap.put("name", faker.name().firstName() );
        payLoadMap.put("gender", faker.demographic().sex() );
        payLoadMap.put("phone", faker.number().numberBetween(5000000000L, 9999999999L));

        return payLoadMap;
    }

}

