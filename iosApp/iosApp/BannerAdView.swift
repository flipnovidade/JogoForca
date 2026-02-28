//
// Created by Felippe Ferreira on 27/02/2026.
//

import SwiftUI
import GoogleMobileAds

struct BannerAdView: View {
    var body: some View {
        BannerView(adUnitID: "ca-app-pub-")
            .frame(width: 320, height: 50)
    }
}

class RewardedAdView: NSObject, ObservableObject, FullScreenContentDelegate {
  @Published var coins = 0
  private var rewardedAd: RewardedAd?

  func loadAd() async {
    do {
      rewardedAd = try await RewardedAd.load(
        with: "ca-app-pub-", request: Request())
      rewardedAd?.fullScreenContentDelegate = self
    } catch {
      print("Failed to load rewarded ad with error: \(error.localizedDescription)")
    }
  }
}
    

@MainActor
final class InterstitialAdView: NSObject, FullScreenContentDelegate {

    private var interstitial: InterstitialAd?

    // 🔹 Carregar anúncio
    func loadInterstitial() async {
        do {
            interstitial = try await InterstitialAd.load(
                with: "ca-app-pub-", // Test ID
                request: Request()
            )

            interstitial?.fullScreenContentDelegate = self

            print("Interstitial loaded")
        } catch {
            print("Failed to load interstitial: \(error.localizedDescription)")
        }
    }

    // 🔹 Mostrar anúncio
    func show(from viewController: UIViewController) {
        guard let interstitial else {
            print("Ad not ready")
            return
        }

        interstitial.present(from: viewController)
    }

    // 🔹 Delegate - recarregar após fechar
    func adDidDismissFullScreenContent(_ ad: FullScreenPresentingAd) {
        print("Interstitial dismissed")

        // Libera a instância antiga
        interstitial = nil

        // Recarrega automaticamente
        Task {
            await loadInterstitial()
        }
    }
}

