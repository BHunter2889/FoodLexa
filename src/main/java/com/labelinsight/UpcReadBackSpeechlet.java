package com.labelinsight;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.labelinsight.smartlabel.SmartLabelRestService;
import com.labelinsight.smartlabel.domain.SmartLabelProduct;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class UpcReadBackSpeechlet implements Speechlet {
    private static final String PRODUCT_BRAND_INTENT = "ProductBrandIntent";
    private static final String UPC_QUERY_INTENT = "UPCQueryIntent";
    private static final String PRODUCT_TITLE_QUERY_INTENT = "ProductTitleQueryIntent";

    @Override
    public void onSessionStarted(final SessionStartedRequest sessionStartedRequest, final Session session) throws SpeechletException {

    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
        return SpeechletResponse.newTellResponse(new PlainTextOutputSpeech());
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest intentRequest, final Session session) throws SpeechletException {
        Intent intent = intentRequest.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if (UPC_QUERY_INTENT.equals(intentName)) {
            return getUpcResponse(intent, session);
        } else if(PRODUCT_BRAND_INTENT.equals(intentName)){
            return getBrandResponse(session);
        } else if(PRODUCT_TITLE_QUERY_INTENT.equals(intentName)){
            return getTitleSearchResponse(intent, session);
        } else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest sessionEndedRequest, final Session session) throws SpeechletException {

    }

    private SpeechletResponse getTitleSearchResponse(Intent intent, final Session session) {
        try {
            List<SmartLabelProduct> smartLabelProducts = getSmartLabelProducts(intent);
            if(productsPresent(smartLabelProducts)) {
                return buildSmartLabelProductListResponse(smartLabelProducts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Sorry I couldn't find any products that matched");
        return SpeechletResponse.newTellResponse(speech);
    }

    private boolean productsPresent(List<SmartLabelProduct> smartLabelProducts) {
        return smartLabelProducts != null && !smartLabelProducts.isEmpty();
    }

    private SpeechletResponse buildSmartLabelProductListResponse(List<SmartLabelProduct> smartLabelProducts) {
        SpeechletResponse speechletResponse = new SpeechletResponse();
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(getProductListOutputText(smartLabelProducts));
        speechletResponse.setOutputSpeech(speech);
        speechletResponse.setNullableShouldEndSession(true);
        return speechletResponse;
    }

    private List<SmartLabelProduct> getSmartLabelProducts(Intent intent) throws IOException {
        SmartLabelRestService smartLabelRestService = new SmartLabelRestService();
        if(intent.getSlot("productTitle").getValue() != null) {
            return smartLabelRestService.getProductByTitle(intent.getSlot("productTitle").getValue());
        }
        return Collections.emptyList();
    }

    private String getProductListOutputText(List<SmartLabelProduct> smartLabelProducts) {
        StringBuilder outputSpeechBuilder = new StringBuilder("I found " + smartLabelProducts.size() + " products that match.");
        smartLabelProducts.forEach(smartLabelProduct -> outputSpeechBuilder.append(" ").append(smartLabelProduct.getTitle()).append("."));
        return outputSpeechBuilder.toString();
    }

    private SpeechletResponse getUpcResponse(final Intent intent, final Session session) {
        try {
            SpeechletResponse speech = getSmartLabelResponse(intent, session);
            if (speech != null) return speech;
        } catch (IOException e) {
            e.printStackTrace();
        }
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Sorry I couldn't find that product");
        return SpeechletResponse.newTellResponse(speech);
    }

    private SpeechletResponse getSmartLabelResponse(final Intent intent, final Session session) throws IOException {
        SmartLabelProduct smartLabelProduct = getSmartLabelProduct(intent);
        if (smartLabelProduct != null) {
            return getSpeechletResponseForSmartLabelProduct(session, smartLabelProduct);
        }
        return null;
    }

    private SmartLabelProduct getSmartLabelProduct(Intent intent) throws IOException {
        SmartLabelRestService smartLabelRestService = new SmartLabelRestService();
        return smartLabelRestService.getProductByUPC(intent.getSlot("UPC").getValue());
    }

    private SpeechletResponse getSpeechletResponseForSmartLabelProduct(final Session session, SmartLabelProduct smartLabelProduct) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("I found, " + smartLabelProduct.getBrand() + ", " + smartLabelProduct.getTitle() + " " + smartLabelProduct.getProductSize());
        session.setAttribute("currentProduct", smartLabelProduct);
        return getSpeechletResponse(speech);
    }

    private SpeechletResponse getSpeechletResponse(PlainTextOutputSpeech speech) {
        SpeechletResponse speechletResponse = SpeechletResponse.newTellResponse(speech);
        speechletResponse.setNullableShouldEndSession(false);
        return speechletResponse;
    }

    private SpeechletResponse getBrandResponse(final Session session) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("The product's brand is, " + getBrandFromSession(session));
        return SpeechletResponse.newTellResponse(speech);
    }

    private String getBrandFromSession(final Session session) {
        HashMap<String, Object> smartLabelMap = (HashMap<String, Object>) session.getAttribute("currentProduct");
        return smartLabelMap.get("brand").toString();
    }
}
