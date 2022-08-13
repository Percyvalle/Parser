package com.example.parserwb;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public final class Parser {

    private final URL urlWB = new URL("https://www.wildberries.ru/webapi/menu/main-menu-ru-ru.json");

    private final URL urlFilter = new URL("https://catalog.wb.ru/catalog/bl_shirts/v4/filters?appType=1&couponsGeo=12,7,3,18,21&curr=rub&dest=-1029256,-2095259,-369838,-1784077&emp=0&kind=2&lang=ru&locale=ru&pricemarginCoeff=1.0&reg=0&regions=68,64,83,4,38,80,33,70,82,86,30,69,22,66,31,40,1,48&spp=0&subject=41;184;1429");
    private final HashMap<String, String> productTypes = new HashMap<>();
    private final ArrayList<String> podProductType = new ArrayList<>();

    public Parser() throws IOException, ParseException {
        add_product_types();
        add_filter_list();

        System.out.println("Parse Complete");
    }

    public void add_filter_list() throws IOException, ParseException {
        HttpURLConnection connection = (HttpURLConnection) urlFilter.openConnection();

        connection.setRequestMethod("GET");

        InputStreamReader connect_reader = new InputStreamReader(connection.getInputStream());
        BufferedReader input_reader = new BufferedReader(connect_reader);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(input_reader);
        JSONArray jsonArray = (JSONArray) ((JSONObject) jsonObject.get("data")).get("filters");
        JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);

        System.out.println(jsonObject1);

        connection.disconnect();
    }

    public void add_product_types() throws IOException, ParseException {
        HttpURLConnection connection = (HttpURLConnection) urlWB.openConnection();

        connection.setRequestMethod("GET");

        InputStreamReader connect_reader = new InputStreamReader(connection.getInputStream());
        BufferedReader input_reader = new BufferedReader(connect_reader);

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(input_reader);

        for (int i = 0; i < 32; i++){

            JSONObject jsonObject = (JSONObject) jsonArray.get(i);

            try {
                JSONArray jsonArray1 = (JSONArray) jsonObject.get("childs");

                for (Object podtype : jsonArray1) {
                    JSONObject jsonObject1 = (JSONObject) podtype;
                    podProductType.add((String) jsonObject1.get("name"));
                }

                productTypes.put(jsonObject.get("name").toString(), podProductType.toString());

                podProductType.clear();
            } catch (NullPointerException ignored) {
            }
        }

        connection.disconnect();
    }

    public HashMap<String,String> getProductTypes(){return productTypes;}
}
