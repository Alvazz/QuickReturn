package android.support.p000v4.media.session;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.p000v4.media.MediaBrowserServiceCompat;
import android.view.KeyEvent;
import java.util.List;
import p006ti.modules.titanium.android.AndroidModule;

/* renamed from: android.support.v4.media.session.MediaButtonReceiver */
public class MediaButtonReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Intent queryIntent = new Intent(AndroidModule.ACTION_MEDIA_BUTTON);
        queryIntent.setPackage(context.getPackageName());
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentServices(queryIntent, 0);
        if (resolveInfos.isEmpty()) {
            queryIntent.setAction(MediaBrowserServiceCompat.SERVICE_INTERFACE);
            resolveInfos = pm.queryIntentServices(queryIntent, 0);
        }
        if (resolveInfos.isEmpty()) {
            throw new IllegalStateException("Could not find any Service that handles android.intent.action.MEDIA_BUTTON or a media browser service implementation");
        } else if (resolveInfos.size() != 1) {
            throw new IllegalStateException("Expected 1 Service that handles " + queryIntent.getAction() + ", found " + resolveInfos.size());
        } else {
            ResolveInfo resolveInfo = (ResolveInfo) resolveInfos.get(0);
            intent.setComponent(new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name));
            context.startService(intent);
        }
    }

    public static KeyEvent handleIntent(MediaSessionCompat mediaSessionCompat, Intent intent) {
        if (mediaSessionCompat == null || intent == null || !AndroidModule.ACTION_MEDIA_BUTTON.equals(intent.getAction()) || !intent.hasExtra(AndroidModule.EXTRA_KEY_EVENT)) {
            return null;
        }
        KeyEvent ke = (KeyEvent) intent.getParcelableExtra(AndroidModule.EXTRA_KEY_EVENT);
        mediaSessionCompat.getController().dispatchMediaButtonEvent(ke);
        return ke;
    }
}
