package bd.ac.uiu.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    double[] currencyValues = new double[]{1,0.69881,0.61095,0.93188,0.96680,44.72,73.14,80.55};

    String[] currencyNames;
    Spinner fromSpinner, toSpinner;
    EditText amountEditText;
    Button clearButton, swapButton, convertButton;
    TextView resultTextView;
    static int fromPosition = 0;
    static int toPosition = 6;
    static  double result = 0;
    static double amount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyNames = getResources().getStringArray(R.array.currency_names);
        fromSpinner = (Spinner) findViewById(R.id.fromSpinnerId);
        toSpinner = (Spinner) findViewById(R.id.toSpinnerId);
        amountEditText = (EditText) findViewById(R.id.amountEditTextId);
        amountEditText.setText(""+ (int)amount);
        amountEditText.setSelection(amountEditText.getText().length());

        clearButton = (Button) findViewById(R.id.clearButtonId);
        swapButton = (Button) findViewById(R.id.swapButtonId);
        convertButton = (Button) findViewById(R.id.convertButtonId);
        resultTextView = (TextView) findViewById(R.id.resultTextViewId);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,R.layout.currency_sample_view,R.id.currencyTextView,currencyNames);
        fromSpinner.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,R.layout.currency_sample_view,R.id.currencyTextView,currencyNames);
        toSpinner.setAdapter(adapter2);

        fromSpinner.setSelection(fromPosition);
        toSpinner.setSelection(toPosition);

        //Toast.makeText(MainActivity.this,""+fromSpinner.getSelectedItemPosition(),Toast.LENGTH_SHORT).show();
        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fromPosition = position;
                //Toast.makeText(MainActivity.this,""+fromSpinner.getSelectedItemPosition(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toPosition = position;
                //Toast.makeText(MainActivity.this,""+toSpinner.getSelectedItemPosition(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        convertButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        swapButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String amountText = amountEditText.getText().toString();
        try{
            amount = Double.parseDouble(amountText);
            if(v.getId()== R.id.convertButtonId){
                result = convertMethod(fromPosition,toPosition,amount);
                System.out.println(result);
                resultTextView.setText("" + result + " "+currencyNames[toPosition]);
            }
            if(v.getId()==R.id.swapButtonId){
                System.out.println("Swap Called");
                toPosition = fromSpinner.getSelectedItemPosition();
                fromPosition = toSpinner.getSelectedItemPosition();
                fromSpinner.setSelection(fromPosition);
                toSpinner.setSelection(toPosition);

                if((resultTextView.getText())!=""){
                    resultTextView.setText(""+amount+" "+currencyNames[toPosition]);
                    amountEditText.setText(""+result);
                    result = amount;
                }
            }
        }catch (Exception e){
            Toast.makeText(MainActivity.this,"Please enter amount",Toast.LENGTH_SHORT).show();
        }
        if(v.getId()== R.id.clearButtonId){
            amountEditText.setText("");
            resultTextView.setText("");
        }
        System.out.println("Okay "+amount);
    }

    private double convertMethod(int from,int to, double amount){
        if(from==0)
            return amount*currencyValues[to];
        else if(to==0)
            return amount/currencyValues[from];

        double usAmount = amount / currencyValues[from];
        return usAmount*currencyValues[to];
    }
}
