import UIKit
import Foundation
import weipl_checkout

@objc(WEIPL_checkout) class WEIPL_checkout: CDVPlugin {

  var callback: String?
  var WLCheckout: WLCheckoutViewController?

  @objc(pluginInitialize)
  override func pluginInitialize() {
    WLCheckout = WLCheckoutViewController()
    WLCheckout?.preloadData()
  }

  @objc(open:)
  func open(_ command: CDVInvokedUrlCommand?) {
    self.callback = command?.callbackId
    let options = command?.arguments[0] as? String ?? "Error"
    WLCheckout!.open(requestObj: options as String)

    DispatchQueue.main.async { UIApplication.shared.keyWindow?.rootViewController?.present(self.WLCheckout!, animated: true, completion: nil) }

    NotificationCenter.default.addObserver(self, selector: #selector(self.wlCheckoutPaymentResponse(result:)), name: Notification.Name("wlCheckoutPaymentResponse"), object: nil)
    NotificationCenter.default.addObserver(self, selector: #selector(self.wlCheckoutPaymentError(result:)), name: Notification.Name("wlCheckoutPaymentError"), object: nil)
  }

  @objc func wlCheckoutPaymentResponse(result: Notification) {
    DispatchQueue.main.async {
      let pluginResult = CDVPluginResult(
        status: CDVCommandStatus_OK,
        messageAs: "\(result.object!)"
      )

      self.commandDelegate!.send(
        pluginResult,
        callbackId: self.callback
      )
    }
  }

  @objc func wlCheckoutPaymentError(result: Notification) {
    DispatchQueue.main.async {
      let pluginResult = CDVPluginResult(
        status: CDVCommandStatus_OK,
        messageAs: "\(result.object!)"
      )

      self.commandDelegate!.send(
        pluginResult,
        callbackId: self.callback
      )
    }
  }
  
}