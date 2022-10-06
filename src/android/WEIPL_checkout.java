package com.weipl.cordova_checkout;

import com.weipl.checkout.CheckoutActivity;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WEIPL_checkout
extends CordovaPlugin
implements CheckoutActivity.PaymentResponseListener {

  public CallbackContext callback;

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    CheckoutActivity.preloadData(cordova.getActivity().getApplicationContext());
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
    JSONArray upiIntentResponse = CheckoutActivity.getUPIResponse(this.cordova.getActivity());

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
      CheckoutActivity.setPaymentResponseListener(this);

      JSONObject convertedObject = new JSONObject(args.getString(0));
      CheckoutActivity.open(this.cordova.getActivity(), convertedObject);
    } catch(Exception e) {
      this.callback.error("Something went wrong " + e);
    }
  }

  @Override
  public void onPaymentError(JSONObject jsonObject) {
    this.callback.error(jsonObject);
  }

  @Override
  public void onPaymentResponse(JSONObject jsonObject) {
    this.callback.success(jsonObject);
  }
}