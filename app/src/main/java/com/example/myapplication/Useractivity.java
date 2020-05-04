package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class Useractivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String cust_name = " ";
    String cust_email = " ";
    String answerCream = "No";
    String answerChocolate = "No";
    String answerHaveit = "No";
    String answerTakeit = "No";

    String[] coffee = { " ", "Drip Coffee", "Iced Coffee", "Espresso", "Latte", "Macchiato", "Cappuccino", "Mocha", "Americano"};
    int quant = 0;
    int price = 0;

    Button Order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useractivity);

        Order=findViewById(R.id.order);
        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order();
            }
        });

        Spinner spin = findViewById(R.id.spin);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,coffee);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(),coffee[position] , Toast.LENGTH_LONG).show();

        switch(position)
        {
            case 1:price +=5;
                   break;

            case 2:price +=7;
                break;
            case 3:price +=9;
                break;
            case 4:price +=11;
                break;
            case 5:price +=13;
                break;
            case 6:price +=15;
                break;
            case 7:price +=17;
                break;
            case 0:price +=0;
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(getApplicationContext(),"Select" , Toast.LENGTH_LONG).show();
    }

    public void increment(View view) {
        if(quant==100){ return;}

        quant += 1;

        displayquant(quant);
    }

    public void decrement(View view) {
        if(quant==0){ return;}

        quant -= 1;

        displayquant(quant);
    }

    private void displayquant(int quant) {

        TextView qt = findViewById(R.id.qt);
        qt.setText("" + quant);
    }



    public void Order() {

        EditText name = findViewById(R.id.name);
        cust_name = name.getText().toString().trim();

        EditText email = findViewById(R.id.emailId);
        cust_email = email.getText().toString().trim();



        RadioButton haveit_button = findViewById(R.id.haveit);
        boolean wantToHave = haveit_button.isChecked();

        RadioButton takeit_button = findViewById(R.id.takeaway);
        boolean wantToTake = takeit_button.isChecked();

        if (wantToHave) {
            answerHaveit = "Yes";
        }

        if (wantToTake) {
            price+=1;
            answerTakeit = "Yes";
        }

        CheckBox whipped_cream_checkbox = findViewById(R.id.whippedCream);
        boolean hasWhippedCream = whipped_cream_checkbox.isChecked();

        CheckBox chocolate_checkbox = findViewById(R.id.chocolate);
        boolean hasChocolate = chocolate_checkbox.isChecked();

        if (hasWhippedCream) {
            price += 2;
            answerCream = "Yes";
        }

        if (hasChocolate) {
            price += 1;
            answerChocolate = "Yes";
        }

        price = price * quant;

        String msg=Ordersummary(cust_name, cust_email, price, quant, answerCream, answerChocolate, answerHaveit, answerTakeit);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, msg);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

       //if the order is placed again...it should nnotify and give option to continue or not
        //coffee options in hindi
        //diff price for diff types of coffee
        //shall show the entire order summary
        //should allow login via google or facebook
        //should allow login via local database of coffee shop and greet them
        //welcome "name"...how are you....if old cust
        //you are our new customer...hope to treat you well ..if new cust

    }

    public String Ordersummary(String cust_name, String cust_email, int price, int quantity, String answerCream, String answerChocolate, String answerHaveit, String answerTakeit)
    {

        String message = getString(R.string.order_summary_name)+cust_name;

        message +="\n"+getString(R.string.order_summary_email_subject)+cust_email;

        message += "\n"+getString(R.string.order_summary_quantity)+quantity;

        message="\n"+getString(R.string.order_summary_whipped_cream)+answerCream;

        message="\n"+getString(R.string.order_summary_chocolate)+answerChocolate;

        message="\n"+getString(R.string.order_summary_haveit)+answerHaveit;

        message="\n"+getString(R.string.order_summary_takeit)+answerTakeit;

        message += "\n"+getString(R.string.order_summary_price)+ NumberFormat.getCurrencyInstance().format(price);

        message += "\n" + getString(R.string.thanku);

        return message;
    }
}
