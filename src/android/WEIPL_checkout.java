package com.weipl.cordova_checkout;

import com.weipl.checkout.WLCheckoutActivity;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WEIPL_checkout
extends CordovaPlugin
implements WLCheckoutActivity.PaymentResponseListener {

  public CallbackContext callback;

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    WLCheckoutActivity.preloadData(cordova.getActivity().getApplicationContext());
  }

  @Override
  public boolean execute(
    String action,
    JSONArray args,
    CallbackContext callbackContext
  )
    throws JSONException {
    this.callback = callbackContext;

    if (action.equals("upiIntentAppsList")) {
      this.upiIntentAppsList();
      return true;
    } else if (action.equals("open")) {
      this.open(args);
      return true;
    }
    return false;
  }

  private void upiIntentAppsList() {
    JSONArray upiIntentResponse = WLCheckoutActivity.getUPIResponse(this.cordova.getActivity());

    if (upiIntentResponse == null) {
      this.callback.error("No response received!");
    } else {
      this.callback.success(upiIntentResponse);
    }
  }
  
  private void open(JSONArray args) {
    if (args == null) {
      this.callback.error("Expected checkout initialisation options");
      return;
    }

    try {
      WLCheckoutActivity.setPaymentResponseListener(this);

      JSONObject convertedObject = new JSONObject(args.getString(0));
      WLCheckoutActivity.open(this.cordova.getActivity(), convertedObject);
    } catch(Exception e) {
      this.callback.error("Something went wrong " + e);
    }
  }

  @Override
  public void wlCheckoutPaymentError(JSONObject jsonObject) {
    this.callback.error(jsonObject);
  }

  @Override
  public void wlCheckoutPaymentResponse(JSONObject jsonObject) {
    this.callback.success(jsonObject);
  }
}