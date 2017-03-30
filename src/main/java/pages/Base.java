package pages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.xml.internal.bind.v2.TODO;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.support.PageFactory;
import utils.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class Base{
// TODO create all localized objects, logs, settings from json in this class fnd extend all of other classes from Base.slass
// TODO validate lines color if parameter is not valid (on registration page for example bg_state_mUseColor	-1754827 = red, and -11711155 = grey)


    public AppiumDriver driver;
    public Map tokenMap;
    public Hub hub;
    public User user;
    public Check check;
    public Email email;
    public PopUp popUp;
    public DashboardHeader header;
    public IntroPage introPage;
    public Dashboard dashboard;
    public Navigation nav;
    public MenuMainPage menuPage;
    public AddImagePage addImagePage;
    public DashboardHeader dashboardHeader;
    public MenuAccountPage accountPage;
    public RegistrationPage registrationPage;
    public AuthorizationPage authorizationPage;
    public ValidationCodePage validationCodePage;
    public DashboardRoomsPage roomsPage;
    public DashboardRemotePage remotePage;
    public Wait wait;
    public Sql sql;
    private Path path;
    private String localizeTextForKey;
    private String jsonString, collection, locale_;

    private Map localizeKeys, dbSettings, jsonCollection;
    private ArrayList<String> jsonStringArray;

    public Base(AppiumDriver driver, String locale_) {
        this.driver = driver;
        this.locale_ = locale_;
        hub = new Hub(driver);
        nav = new Navigation(driver);
        check = new Check(driver);
        popUp = new PopUp(driver);
        menuPage = new MenuMainPage(driver);
        introPage = new IntroPage(driver);
        dashboard = new Dashboard(driver);
        roomsPage = new DashboardRoomsPage(driver);
        remotePage = new DashboardRemotePage(driver);
        accountPage = new MenuAccountPage(driver);
        addImagePage = new AddImagePage(driver);
        dashboardHeader = new DashboardHeader(driver);
        registrationPage = new RegistrationPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        validationCodePage = new ValidationCodePage(driver);
        sql = new Sql();

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
//        page1 = PageFactory.initElements(driver, Page1.class);

    }

    public Base() {
    }


    public String getLocalizeTextForKey(String key){
        localizeTextForKey = getLocalizeKeys().get(key).toString();
        return localizeTextForKey;
    }

    private Map getLocalizeKeys() {
        log("Method is started");
        try {
            localizeKeys = new HashMap<>();

            log(2, "get Application start up path");
            path = getApplicationStartUp();

            log(2, "get json content ");
            collection = loadJSON(path + "/classes/lokaliseKeys/collection.json");
            jsonString = loadJSON(path + "/classes/lokaliseKeys/" + locale_ + ".json");

            JsonParser parser = new JsonParser();
            JsonObject jo = parser.parse(jsonString).getAsJsonObject();
            Gson gson = new Gson();

            log(2, "create localizeKeys map ");
            List collectionList = gson.fromJson(parser.parse(collection).getAsJsonObject().getAsJsonArray("paramsArray"), List.class);
            for (Object i : collectionList) {
                Map map = gson.fromJson(jo.get(i.toString()).getAsJsonObject(), HashMap.class);
                localizeKeys.putAll(map);
            }

        } catch (UnsupportedEncodingException e) {
            log(4, "UnsupportedEncodingException" + e);
            e.printStackTrace();
        } catch (MalformedURLException e) {
            log(4, "MalformedURLException" + e);
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            log(4, "FileNotFoundException" + e);
            e.printStackTrace();
        } catch (IOException e) {
            log(4, "IOException" + e);
            e.printStackTrace();
        }
        log("Method is finished");
        return localizeKeys;
    }


    public Map getDbSettings() {
        log("Method is started");
        try {
            dbSettings = new HashMap<>();

            log(2, "get Application start up path");
            path = getApplicationStartUp();

            log(2, "get json content ");
            jsonString = loadJSON(path + "/classes/db.json");

            JsonParser parser = new JsonParser();
            JsonObject jo = parser.parse(jsonString).getAsJsonObject();
            Gson gson = new Gson();

            log(2, "create DB settings map ");
            Map map = gson.fromJson(jo.get("DB").getAsJsonObject(), HashMap.class);
            dbSettings.putAll(map);

        } catch (Exception e) {
            log(4, "Exception\n" + e + "\n");
        }
        log("Method is finished");
        return dbSettings;
    }

//**********************************************************************************************************************
// JSON
//**********************************************************************************************************************

    public Map getJsonCollection(String filePath, String collection) {
        log("Method is started");
        jsonCollection = new HashMap();
        try {

            log(2, "get Application start up path");
            path = getApplicationStartUp();

            log(2, "get json content ");
            jsonString = loadJSON(path + "/classes/" + filePath);

            JsonParser parser = new JsonParser();
            JsonObject jo = parser.parse(jsonString).getAsJsonObject();
            Gson gson = new Gson();

            log(2, "create collection map ");
            Map map = gson.fromJson(jo.get(collection).getAsJsonObject(), HashMap.class);
            jsonCollection.putAll(map);

        } catch (Exception e) {
            log(4, "Exception\n" + e + "\n");
        }
        log("Method is finished");
        return jsonCollection;
    }

    public ArrayList<String> getJsonStringArray(String filePath, String collection) {
        log("Method is started");
        jsonStringArray = new ArrayList<>();
        try {

            log(2, "get Application start up path");
            path = getApplicationStartUp();

            log(2, "get json content from file");
            FileReader reader = new FileReader(path + "/classes/" + filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            log(2, "create collection array ");
            jsonStringArray.addAll((JSONArray) jsonObject.get(collection));

        } catch (Exception e) {
            log(4, "Exception\n" + e.getMessage() + "\n");
        }
        log("created " + jsonStringArray.size() + " elements in arrayList");
        printArray(jsonStringArray);
        log("Method is finished");
        return jsonStringArray;
    }

    private void printArray(ArrayList arrayList){
        for (Object element: arrayList) {
            System.out.println(element.toString());
        }
    }

//**********************************************************************************************************************

    private Path getApplicationStartUp() throws UnsupportedEncodingException, MalformedURLException {
        log("Method is started");
        URL startupUrl = getClass().getProtectionDomain().getCodeSource()
                .getLocation();
        Path path = null;
        try {
            path = Paths.get(startupUrl.toURI());
        } catch (Exception e) {

            try {
                log(3, "Exception e");
                path = Paths.get(new URL(startupUrl.getPath()).getPath());

            } catch (Exception ipe) {
                log(3, "Exception ipe");
                path = Paths.get(startupUrl.getPath());
            }
        }
        path = path.getParent();
        log("Method is finished");
        return path;
    }


    private String loadJSON(String path) throws IOException {
        log("Method is started");
        byte[] buf;
        try (RandomAccessFile f = new RandomAccessFile(path, "r")) {
            buf = new byte[(int) f.length()];
            f.read(buf);
        }
        log("Method is finished");
        return new String(buf);
    }

    //**********************************************************************************************************************
// LOG
//**********************************************************************************************************************
    public void log(int type, String message) {
        Throwable t = new Throwable();
        StackTraceElement trace[] = t.getStackTrace();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
        Date timeStamp = new Date();
        String typeOfMessage;
        switch (type) {
            case 1:
                typeOfMessage = "INFO ";
                break;
            case 2:
                typeOfMessage = "DEBUG";
                break;
            case 3:
                typeOfMessage = "WARN ";
                break;
            case 4:
                typeOfMessage = "ERROR";
                break;
            default:
                typeOfMessage = "NODEF";
                break;
        }

        // We need element with index 0 - it's current element "log"
        if (trace.length > 1) {
            StackTraceElement element = trace[1];
            if (type >= 3) {
                System.out.format("\033[31;49m[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n\033[39;49m", sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message);

            } else {
                System.out.format("[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message);
            }
        } else {
            System.out.format("[%s] [%s] where:{ no info }, what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, message);
        }
    }

    public void log(String message) {
        Throwable t = new Throwable();
        StackTraceElement trace[] = t.getStackTrace();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
        Date timeStamp = new Date();
        String typeOfMessage = "INFO ";

        // We need element with index 0 - it's current element "log"
        if (trace.length > 1) {
            StackTraceElement element = trace[1];
            System.out.format("[%s] [%s] where:{ %s.%s }[%d], what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, element.getClassName(), element.getMethodName(), element.getLineNumber(), message);
        } else {
            System.out.format("[%s] [%s] where:{ no info }, what:{ %s }\n", sdf.format(timeStamp), typeOfMessage, message);
        }
    }

}
