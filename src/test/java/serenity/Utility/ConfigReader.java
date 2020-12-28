package serenity.Utility;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.jupiter.api.Test;

public class ConfigReader {

    private EnvironmentVariables environmentVariables;


    public  String getProperty(String propertyName){

        return EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty(propertyName);
    }






}
