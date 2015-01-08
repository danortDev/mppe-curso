package ve.gob.mppe.redba.io;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import ve.gob.mppe.redba.activities.DetailActivity;

/**
 * Created by fernando on 05/01/15.
 * @since 05/01/15
 */
public class ExampleAsyncTask extends AsyncTask<Bundle,Integer,Bundle> {

    public static final String TAG = ExampleAsyncTask.class.getSimpleName();
    private Activity mActivity;

    public ExampleAsyncTask(Activity activity){
        mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        /*
            Se utiliza para hacer las inicializaciones pertinentes antes de ejecutar doInBackground
            en un Thread aparte.

            No es obligatorio hacer Override de esta función si no es necesaria
         */
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        /*
            Para tareas que requieran de actualización del UI mientras
            se está ejecutando doInBackground.

            No es obligatorio hacer Override de esta función si no es necesaria

            Ejemplo:
         */

        ((DetailActivity)mActivity).setOperationProgress(values[0]);

        /*
            Se llama dentro de doInBackground mediante publishProgress();
            Ej: publishProgress(20);
         */
    }

    @Override
    protected Bundle doInBackground(Bundle... params) {
        /*
            El código contenido aqui se ejecuta en un Thread aparte del principal, por lo que
            dentro de él no se puede acceder a elementos de UI y si se accede a componentes
            críticos compartidos con otros componentes de la aplicación es necesario aplicar
            técnicas para evitar problemas de concurrencia y sincronización como utiizar
            funciones synchronized o el uso de banderas booleanas para control de flujo
         */

        return null;
    }

    @Override
    protected void onPostExecute(Bundle bundle) {
        /*
            Se utiliza para procesar el resultado de doInBackground, al igual que onPreExecute y
            onProgressUpdate el código dentro de estas funciones sí se ejecuta en el Thread
            principal (o de UI como se le llama comunmente en Android)
         */
    }
}
