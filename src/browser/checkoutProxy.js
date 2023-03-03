function addScript(id, src, callback) {
  var wljs = document.getElementsByTagName('script')[0];
  var js = document.getElementById(id);
  js = document.createElement('script')
  js.id = id;
  js.onload = function () {
    if (typeof callback === 'function') callback(id)
  }

  js.onerror = function () {
    if (typeof callback === 'function') callback({ code: 0, description: 'Network error, please try again!' })
  }

  js.src = src
  wljs.parentNode.insertBefore(js, wljs)
}

function injectWLScript(callback) {
  var js = document.getElementById('wlcheckout-js')
  if (typeof jQuery != 'undefined') {
    window.wl$ = jQuery;
  }

  if (js && wl$) return

  if (!jQuery) {
    var jQuery = document.createElement('script');
    js = document.createElement('script')
    jQuery.id = 'wlcheckout-jQuery';
    addScript('wlcheckout-jQuery', 'https://www.paynimo.com/paynimocheckout/client/lib/jquery.min.js', callback)
  }

  if (js) {
    addScript('wlcheckout-js', 'https://www.paynimo.com/Paynimocheckout/server/lib/checkout.js', callback)
  }

}

injectWLScript(function (error) {
  if (error.code === 0) {
    // options.errorCallback(error)
  } else {
    if (error == 'wlcheckout-jQuery') {
      window.wl$ = jQuery;
    }
  }
})

function open(paymentCallback, errorCallback, args) {
  var options = JSON.parse(args[0]);

  window.wlPaymentCallback = paymentCallback;
  window.wlErrorCallback = errorCallback;
  window.wlOptions = options;

  if (wl$.pnCheckout) {
    openWLCheckout(options)
  } else {
    injectWLScript(function (error) {
      if (error.code === 0) {
        window.wlErrorCallback(error)
      } else {
        openWLCheckout(options)
      }
    })
  }
}

function openWLCheckout(options) {
  options.consumerData['responseHandler'] = handleResponse;
  wl$.pnCheckout(options);
}

function handleResponse(res) {
  if (res && res.merchantResponseString) {
    var response = {
      "msg": res.merchantResponseString,
      "merchant_code": window.wlOptions.consumerData.merchantId
    }

    window.wlPaymentCallback(response);

  } else {
    window.wlPaymentCallback(res);
  }
}

module.exports = {
  open: open
}

require('cordova/exec/proxy').add('WEIPL_checkout', module.exports)