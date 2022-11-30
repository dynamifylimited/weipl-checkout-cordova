# Cordova/Phonegap plugin wrapper for Worldline ePayments India Mobile SDKs

This is official Cordova/Phonegap plugin wrapper to integrate Worldline ePayments India Checkout.

## Supported platforms

- Android
- iOS

## Usage

Install the plugin

**Note**: For Windows users, please run this on Git Bash instead of Command Prompt. You can download Git for Windows [here](https://github.com/git-for-windows/git/releases/latest).

```bash
cd your-project-folder
cordova platform add android      # to add Android platform 
cordova platform add ios          # to add iOS platform
cordova plugin add https://github.com/Worldline-ePayments-India/weipl-checkout-cordova.git --save
```
(or, `phonegap plugin add https://github.com/Worldline-ePayments-India/weipl-checkout-cordova.git --save`)

**Note**:
- Make sure that you set project target for **Frameworks, Libraries, and Embedded Content** option should be **Embed & Sign**. 
- We support Xcode 12+ versions. 

## Integration code

### Checkout Initialisation

Here is a checkout initialisation code sample:

```js
var options = {
	"features": {
		"enableAbortResponse": true,
		"enableExpressPay": true,
		"enableInstrumentDeRegistration": true,
		"enableMerTxnDetails": true
	},
	"consumerData": {
		"deviceId": "ANDROIDSH2",	//supported values "ANDROIDSH1" or "ANDROIDSH2" for Android and supported values "iOSSH1" or "iOSSH2" for iOS
		"token": "007d9fc80400f43c2fe4cb3308db7ffd624c19559f6a6ac044fa4f34d10b1d7ffeee31b573f90f11e58f05cdf588de35ae7d4f9e78c1a30b4abc6c64fc026fad",
		"paymentMode": "all",
		"merchantLogoUrl": "https://www.paynimo.com/CompanyDocs/company-logo-vertical-light.png",  //provided merchant logo will be displayed
		"merchantId": "L3348",
		"currency": "INR",
		"consumerId": "c964634",
		"consumerMobileNo": "9876543210",
		"consumerEmailId": "test@test.com",
		"txnId": "1664951910195",   //Unique merchant transaction ID
		"items": [{
			"itemId": "test",
			"amount": "10",
			"comAmt": "0"
		}],
		"customStyle": {
			"PRIMARY_COLOR_CODE": "#45beaa",   //merchant primary color code
			"SECONDARY_COLOR_CODE": "#FFFFFF",   //provide merchant"s suitable color code
			"BUTTON_COLOR_CODE_1": "#2d8c8c",   //merchant"s button background color code
			"BUTTON_COLOR_CODE_2": "#FFFFFF"   //provide merchant"s suitable color code for button text
		}
	}
};
var paymentCallback = function(response) {
  alert(response);
}
var errorCallback = function(error) {
  alert(error);
}
WLCheckout.open(options, paymentCallback, errorCallback);
```

Change the **options** accordingly. All options for **[Android](https://www.paynimo.com/paynimocheckout/docs/?device=android)** and  **[iOS](https://www.paynimo.com/paynimocheckout/docs/?device=ios)** are available on respective links.


### Response Handling

Please refer detailed response handling & HASH match logic explaination for **[Android](https://www.paynimo.com/paynimocheckout/docs/?device=android)** and  **[iOS](https://www.paynimo.com/paynimocheckout/docs/?device=ios)** from given links.
