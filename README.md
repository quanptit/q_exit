
<img src=":/3c3f48fedbfe4cbfb30b46ff711da234" alt="a1952f818bacbe8029d99a832fef9f11.png" width="287" height="437">

Thực hiện chức năng khi thoát ứng dụng, show Dialog ADS quảng cáo.

Hiện tại mới có Applovin.

## Cách sử dụng
Wrap `ConfirmQuitAppWrap` ra ngoài cái Screen đầu tiên

```

class _HomeWidget extends StatelessWidget {
  const _HomeWidget();

  @override
  Widget build(BuildContext context) {
    return RouteAwareWidget(
      didPush: () {
        AppRateUtils.trakingGoHome(context, isCreate: true, linkContract: KeysRef.LINK_CONSTRACT);
      },
      didPopNext: () => AppRateUtils.trakingGoHome(context, isCreate: false, linkContract: KeysRef.LINK_CONSTRACT),
      child: ConfirmQuitAppWrap(nativeAdUnit: Keys.MAX_NATIVE_EXIT, child: const LevelSelectScreen()),
    );
  }
}

```