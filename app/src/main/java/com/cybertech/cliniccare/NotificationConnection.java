package com.cybertech.cliniccare;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * stagent24@gmail.com
 * Created by CyberTech on 2/20/2018.
 */

public class NotificationConnection extends StringRequest {
    private static final String notificationURL = "https://cybersmart.000webhostapp.com/push_notification.php";
    Map params;

    public NotificationConnection(String message, String title, String type, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, notificationURL, listener, errorListener);
        params = new HashMap();
        params.put("msg", message);
        params.put("title", title);
        params.put("from", "FuelSearch");
        params.put("sendTime", new SimpleDateFormat("hh:mm:ss a dd/MM/yyyy").format(new Date()));
        if (type.equalsIgnoreCase("doctor")) {
            params.put("to", Config.TOPIC_DOCTORS);

        } else if (type.equalsIgnoreCase("Pharmacist")) {
            params.put("to", Config.TOPIC_pharmacist);
        }

    }

    @Override
    public Map getParams() {
        return params;
    }
}
