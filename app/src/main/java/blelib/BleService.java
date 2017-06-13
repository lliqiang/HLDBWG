package blelib;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BleService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    private ReactiveBeacons reactiveBeacons;
//    private Subscription subscription;
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Logger.t("BLE").e("开启蓝牙服务");
//        reactiveBeacons = new ReactiveBeacons(this);
//        if (!canObserveBeacons()) {
//            return;
//        }
//        subscription = reactiveBeacons.observe()
//                .subscribeOn(Schedulers.computation())
//                .subscribe(beacon -> {
//                    if (HdAppConfig.getReceiveNoMode() != 1) {
//                        if (TextUtils.equals(beacon.device.getName(), "HengDa Tag")) {
//                            int major = (beacon.scanRecord[22] & 0xff) * 0x100 + (beacon
//                                    .scanRecord[23] & 0xff);
//                            AutoNoUtil.addAutoNo(major, 5);
//                            int bestAutoNo = AutoNoUtil.getBestAutoNo(5, 3);
//                            if (bestAutoNo != 0) {
//                                RxBus.getDefault().post(new BLENoEvent(bestAutoNo));
//                            }
//                        }
//                    }
//                });
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Logger.t("BLE").e("关闭蓝牙服务");
//        if (subscription != null && !subscription.isUnsubscribed()) {
//            subscription.unsubscribe();
//        }
//    }
//
//    /**
//     * 判断是否支持BLE
//     *
//     * @return
//     */
//    private boolean canObserveBeacons() {
//        if (!reactiveBeacons.isBleSupported()) {
//            Logger.t("BLE").e("不支持BLE");
//            return false;
//        }
//        if (!reactiveBeacons.isBluetoothEnabled()) {
//            Logger.t("BLE").e("蓝牙没打开");
//            return false;
//        }
//        return true;
//    }

}


