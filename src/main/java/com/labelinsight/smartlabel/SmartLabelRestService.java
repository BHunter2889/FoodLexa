package com.labelinsight.smartlabel;

import com.google.gson.Gson;
import com.labelinsight.smartlabel.domain.Allergen;
import com.labelinsight.smartlabel.domain.SmartLabelProduct;
import com.labelinsight.smartlabel.domain.SmartLabelResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SmartLabelRestService {
    private static final String SL_API_URL = "https://dev-enterprise-sl-api.labelinsight.com/api/v4/20ff77e9-75af-410d-98ff-bef2febb0df7/data/";
    private static final String API_KEY = "9qQUNqKiSo6HNuo1C41EBWD3RkLwjsN1PIKmbPKb";
    private static final String SEARCH_TITLE_PREFIX = "search?title=";
    private static final String UPC_PREFIX = "upc/";
    private static final String X_API_KEY = "x-api-key";

    private final Gson gson;

    public SmartLabelRestService() {
        gson = new Gson();
    }

    public SmartLabelProduct getProductByUPC(String UPC) throws IOException {
        HttpURLConnection httpURLConnection = getConnection(UPC_PREFIX + UPC);

        StringBuffer response = getResponse(httpURLConnection);

        SmartLabelResponse smartLabelResponse = getSmartLabelResponse(response);
        if (smartLabelResponse.getContent().isEmpty()) {
            return null;
        }
        return smartLabelResponse.getContent().get(0);
    }

    public List<SmartLabelProduct> getProductByTitle(String productTitle) throws IOException {
        HttpURLConnection httpURLConnection = getConnection(SEARCH_TITLE_PREFIX + URLEncoder.encode(productTitle, "UTF-8"));

        StringBuffer response = getResponse(httpURLConnection);

        SmartLabelResponse smartLabelResponse = getSmartLabelResponse(response);
        if (smartLabelResponse.getContent().isEmpty()) {
            return null;
        }
        return smartLabelResponse.getContent();
    }

    private boolean containsAllergen(SmartLabelProduct smartLabelProduct, String allergen) {
        List<Allergen> allergens = smartLabelProduct.getAllergenSection().getAllergens();
        for (Allergen allergen1 : allergens) {
            if (allergen1.getName().equals(allergen) && allergen1.getPresence() == null) {
                return false;
            }
        }
        return true;
    }

    public List<SmartLabelProduct> getProductByTitleFilterAllergen(String productTitle, String allergen) throws IOException {
        List<SmartLabelProduct> products = getProductByTitle(productTitle);
        return products.stream()
                .filter(smartLabelProduct ->
                        !containsAllergen(smartLabelProduct, allergen))
                .collect(Collectors.toList());
    }

    private SmartLabelResponse getSmartLabelResponse(StringBuffer response) {
        return gson.fromJson(response.toString(), SmartLabelResponse.class);
    }

    private StringBuffer getResponse(HttpURLConnection conn) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    private HttpURLConnection getConnection(String UPC) throws IOException {
        URL obj = new URL(SL_API_URL + UPC);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestProperty(X_API_KEY, API_KEY);
        return conn;
    }
}
