package com.xhwl.xiyi;

import android.content.Context;

public class InformUpateListenerManager {

private OnClickList informUpdateListener;
private Context mContext;
private static InformUpateListenerManager manager;

public static InformUpateListenerManager getInstance(Context context){
    if(manager==null){
        manager = new InformUpateListenerManager(context);

    }
    return manager;
}
public InformUpateListenerManager(Context context){
    this.mContext =context.getApplicationContext();//使用getAppcationContext()防止单例模式内存泄漏
}
    public void setInformUpdateListener(OnClickList informUpdateListener) {
        this.informUpdateListener = informUpdateListener;
    }

    public void updateData( /*SmartInfoVo.FamilysBean.DeviceInfoBean deviceInfoBean*/) {
        if (informUpdateListener != null) {
            informUpdateListener.onClick(/*deviceInfoBean*/);
        }
    }
}
