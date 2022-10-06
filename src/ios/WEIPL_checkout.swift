import UIKit
import Foundation
import weipl_checkout

@objc(WEIPL_checkout) class WEIPL_checkout: CDVPlugin {

  var callback: String?

  @objc(pluginInitialize)
  override func pluginInitialize() {
    CheckoutViewController().preloadData()    
  }

  @objc(open:)
  func open(_ command: CDVInvokedUrlCommand?) {
    self.callback = command?.callbackId
    let options = command?.arguments[0] as? String ?? "Error"
    let WLCheckout = CheckoutViewController()
    WLCheckout.open(requestObj: options)
    WLCheckout.modalPresentationStyle = .fullScreen

    DispatchQueue.main.async { UIApplication.shared.keyWindow?.rootViewController?.present(WLCheckout, animated: true, completion: nil) }

    NotificationCenter.default.addObserver(self, selector: #selector(self.ReceivedSDKCallBack(result:)), name: Notification.Name("NotificationIdentifier"), object: nil)
  }

  @objc func ReceivedSDKCallBack(result: Notification) {
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