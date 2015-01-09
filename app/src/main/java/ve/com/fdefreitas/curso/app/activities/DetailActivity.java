package ve.com.fdefreitas.curso.app.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import ve.com.fdefreitas.curso.app.Curso;
import ve.com.fdefreitas.curso.app.R;
import ve.com.fdefreitas.curso.app.io.ExampleAsyncTask;
import ve.com.fdefreitas.curso.app.pickers.DatePickerDialogFragment;


public class DetailActivity extends ActionBarActivity implements DatePickerDialogFragment.DatePickerDialogFragmentListener{

    private static final String TAG = DetailActivity.class.getSimpleName();
    public static final int MAX_PROGRESS = 100;
    protected DetailActivity detailActivity;
    private AlertDialog.Builder basicDialogBuilder;
    private AlertDialog.Builder customDialogBuilder;
    private Button showToastButton;
    private TextView dateResultTextView;
    private ProgressBar progressBar;
    private TextView title;
    private TextView subtitle;
    private TextView author;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailActivity = this;
        setupUi();
        processIntent();

    }

    private void setupUi(){
        title = (TextView) findViewById(R.id.title_text_view);
        subtitle = (TextView) findViewById(R.id.subtitle_text_view);
        author = (TextView) findViewById(R.id.author_text_view);
        content = (TextView) findViewById(R.id.content_text_view);
//        showToastButton = (Button) findViewById(R.id.button);
//        showToastButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mainActivity, mainActivity.getString(R.string.news_example_title), Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "Onclick en Botón");
////                DatePickerDialogFragment dialog = new DatePickerDialogFragment();
////                dialog.show(getFragmentManager(), null);
//            }
//        });
//
//        dateResultTextView = (TextView) findViewById(R.id.selected_date_text_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void processIntent(){
        if(getIntent() != null && getIntent().hasExtra(Curso.EXTRA_BUNDLE)){
            String result = getIntent().getBundleExtra(Curso.EXTRA_BUNDLE).getString(Curso.KEY_RESULT);
            try{
                JSONObject jsonObject = new JSONObject(result);
                subtitle.setText(jsonObject.getString(Curso.KEY_MESSAGE_TITLE));
                author.setText(jsonObject.getString(Curso.KEY_MESSAGE_AUTHOR));
            } catch (JSONException e){
                Log.e("Exception", "Exception", e);
            }
        }
    }

    private void setListeners(){
    }

    /**
     * Instancia un {@link AlertDialog.Builder} para obtener instancias de {@link AlertDialog} con titulo y mensaje
     * @return Instancia de AlertDialog
     */
    private AlertDialog getBasicDialog(){
        if (basicDialogBuilder == null) {
            basicDialogBuilder = new AlertDialog.Builder(detailActivity);
            basicDialogBuilder.setPositiveButton(getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG, "getBasicDialog. Positive onClick. Usuario Aceptó");
                }
            });

            basicDialogBuilder.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG, "getBasicDialog. Negative onClick. Usuario Canceló");
                }
            });

            basicDialogBuilder.setTitle("Titulo del dialogo");
            basicDialogBuilder.setMessage("Mensaje del dialogo");
        }

        return basicDialogBuilder.create();
    }

    /**
     * Instancia un DialogBuilder para obtener instancias neutrales de AlertDialog con titulo y mensaje
     * @return Instancia de AlertDialog
     */
    private AlertDialog getBasicNeutralDialog(){
        if (basicDialogBuilder == null) {
            basicDialogBuilder = new AlertDialog.Builder(detailActivity);
            basicDialogBuilder.setNeutralButton(getString(R.string.atras), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG, "onClick. Usuario Visualizó ");
                }
            });
            basicDialogBuilder.setTitle("Titulo del dialogo");
            basicDialogBuilder.setMessage("Mensaje del dialogo");
        }

        return basicDialogBuilder.create();
    }

    /**
     * Instancia un DialogBuilder para obtener instancias de AlertDialog con layout personalizado
     * @return Instancia de AlertDialog
     */
    private AlertDialog getCustomDialog(){
        if(customDialogBuilder == null){
            customDialogBuilder = new AlertDialog.Builder(detailActivity);
            customDialogBuilder.setPositiveButton(getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG, "customDialogBuilder. Positive onClick. Usuario Aceptó");
                }
            });

            customDialogBuilder.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG, "customDialogBuilder. Negative onClick. Usuario Canceló");
                }
            });
        }

        /*
        Inflamos el layout contenido en R.layout.dialog_example y lo almacenamos en
        la variable layout para poder modificar elementos del mismo
         */
        @SuppressLint("InflateParams")
        View layout = getLayoutInflater().inflate(R.layout.dialog_example, null);

        TextView text = (TextView) layout.findViewById(R.id.content_text_view);
        text.setText("Hola este es un nuevo texto");

        //Asignamos el layout inflado al Builder de dialogos
        customDialogBuilder.setView(layout);

        /*
        Devolvemos una nueva instancia de AlertDialog con la configuración especificada
        en el Builder
         */

        return basicDialogBuilder.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_test) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateDialogPositiveClick(DialogFragment dialog, Bundle dateBundle) {
        dialog.dismiss();
        String temp = dateBundle.getString(DatePickerDialogFragment.DATE_STRING);
        dateResultTextView.setText(temp);
    }

    public boolean incrementOperationProgress(int progress){
        return setOperationProgress(progressBar.getProgress() + progress);
    }

    public boolean setOperationProgress(int progress){
        if(progress >= 0 && progress <= MAX_PROGRESS){
            progressBar.setProgress(progress);
            return true;
        } else {
            Log.w(TAG, "Invalid progress value");
            return false;
        }
    }
}
