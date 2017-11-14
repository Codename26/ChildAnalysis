package com.codename26.childanalysis;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;


public class DataLoaderService extends IntentService {

    private static final String ACTION_GET_ANALYSIS = "com.codename26.childanalysis.action.GET_ANALYSIS";
    private static final String ACTION_BAZ = "com.codename26.childanalysis.action.BAZ";


    private static final String EXTRA_PARAM1 = "com.codename26.childanalysis.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.codename26.childanalysis.extra.PARAM2";

    public DataLoaderService() {
        super("DataLoaderService");
    }

    public static void getAnalysis(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DataLoaderService.class);
        intent.setAction(ACTION_GET_ANALYSIS);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ANALYSIS.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);

            }
            }
        }
    }

