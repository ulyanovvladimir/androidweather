package domain.androidweather.weather.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import domain.androidweather.weather.providers.IServiceProvider;
import domain.androidweather.weather.providers.WeatherServiceProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ProcessorService extends Service {

    private Integer lastStartId;
    private final Context context = this;
    private final HashMap<String, ServiceTask> tasks = new HashMap<>();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private String getTaskIdentifier(Bundle extras) {
        String[] keys = extras.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder identifier = new StringBuilder();

        for(int keyIndex = 0; keyIndex < keys.length; keyIndex++) {
            String key = keys[keyIndex];

            if(key.equals(Extras.RESULT_ACTION))
                continue;

            identifier.append("{");
            identifier.append(key);
            identifier.append(":");
            identifier.append(extras.get(key).toString());
            identifier.append("}");
        }

        return identifier.toString();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("Service", "OnStartCommand");
        synchronized (tasks) {
            lastStartId = startId;

            Bundle extras = intent.getExtras();

            String taskIdentifier = getTaskIdentifier(extras);

            ServiceTask task = tasks.get(taskIdentifier);

            if(task == null) {
                task = new ServiceTask(taskIdentifier, extras);
                tasks.put(taskIdentifier, task);

                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
            }

            String resultAction = extras.getString(Extras.RESULT_ACTION);
            if(resultAction != "") {
                task.addResultAction(extras.getString(Extras.RESULT_ACTION));
            }
        }

        return START_STICKY;
    }

    private IServiceProvider GetProvider(int providerId) {
        switch (providerId) {
            case Providers.WEATHER_PROVIDER:
                return new WeatherServiceProvider(this);
        }
        return null;
    }

    public class Providers {
        public static final int WEATHER_PROVIDER = 1;

    }

    public class ServiceTask extends AsyncTask<Void, Void, Boolean> {

        private final Bundle extras;
        private final ArrayList<String> resultActions = new ArrayList<>();
        private final String taskIdentifier;

        public ServiceTask(String taskIdentifier, Bundle extras) {
            this.taskIdentifier = taskIdentifier;
            this.extras = extras;
        }

        public void addResultAction(String resultAction) {
            if(!resultActions.contains(resultAction)) {
                resultActions.add(resultAction);
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result = false;
            final int providerId = extras.getInt(Extras.PROVIDER);
            final int methodId = extras.getInt(Extras.METHOD);

            if(providerId != 0 && methodId != 0) {
                final IServiceProvider provider = GetProvider(providerId);

                if(provider != null) {
                    try {
                        result = provider.RunTask(methodId, extras);
                    }
                    catch (Exception e) {
                        result = false;
                    }
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            synchronized (tasks) {
                for(int i = 0; i < resultActions.size(); i++) {
                    Intent resultIntent = new Intent(resultActions.get(i));
                    resultIntent.putExtra(Extras.RESULT, result.booleanValue());
                    resultIntent.putExtras(extras);

                    resultIntent.setPackage(context.getPackageName());
                    context.sendBroadcast(resultIntent);
                }

                tasks.remove(taskIdentifier);

                if(tasks.size() < 1) {
                    stopSelf(lastStartId);
                }
            }
        }
    }
}
