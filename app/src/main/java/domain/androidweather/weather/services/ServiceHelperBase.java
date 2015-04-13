package domain.androidweather.weather.services;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public abstract class ServiceHelperBase {
    private final Context context;
    private final int providerId;
    private final String resultAction;

    public ServiceHelperBase(Context context, int providerId, String resultAction) {
        this.context = context;
        this.providerId = providerId;
        this.resultAction = resultAction;
    }

    protected void runMethod(int methodId) {
        runMethod(methodId, null);
    }

    protected void runMethod(int methodId, Bundle bundle) {
        Log.v("HELPER!", "runMethod");
        Intent service = new Intent(context, ProcessorService.class);

        service.putExtra(Extras.PROVIDER, providerId);
        service.putExtra(Extras.METHOD, methodId);
        service.putExtra(Extras.RESULT_ACTION, resultAction);

        if(bundle != null) {
            service.putExtras(bundle);
        }

        context.startService(service);
    }
}
