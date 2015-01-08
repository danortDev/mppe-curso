package ve.gob.mppe.redba.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ExampleService extends Service {
    public ExampleService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /*
        Un Service al igual que un Activity puede hacer override de las funciones de su LiveCycle
     */
}
