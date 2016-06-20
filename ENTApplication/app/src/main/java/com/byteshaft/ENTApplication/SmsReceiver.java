package com.byteshaft.ENTApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver  {
    String number;
    String messageText;
    SmsMessage message;
    String contactName;

    private static final String TAG = "Logtest";

    @Override
    public void onReceive(Context context, Intent intent) {
        Helpers helpers = new Helpers(context);
        if (!helpers.isPopupEnabled()) {
            return;
        }

        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");
        message = SmsMessage.createFromPdu((byte[]) pdus[0]);
        number = message.getOriginatingAddress();
        messageText = message.getMessageBody();
        contactName = helpers.getContactNameFromNumber(number);

        if(number.equals("01087844729")) {
            if (OverlayDialog.isActivityRunning()) {
                OverlayDialog.closeDialog();
                Intent intent1 = new Intent(context, OverlayDialog.class);
                intent1.putExtra("message", messageText);
                intent1.putExtra("name", contactName);
                intent1.putExtra("number", number);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);

            } else if (!OverlayDialog.isActivityRunning()) {
                Intent intent1 = new Intent(context, OverlayDialog.class);
                intent1.putExtra("message", messageText);
                intent1.putExtra("name", contactName);
                intent1.putExtra("number", number);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
        }
    }
}

