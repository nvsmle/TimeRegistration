package neversmile.packag.com.timeregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.adminConsole) {

            final AlertDialog.Builder myDialog = new AlertDialog.Builder(MainActivity.this);
            myDialog.setTitle("Введите пароль администратора");

            final EditText edtUsername = new EditText(MainActivity.this);
            edtUsername.setTextColor(Color.BLACK);
            edtUsername.setHint("Пользователь...");
            edtUsername.setHintTextColor(Color.GRAY);

            final EditText edtAdminPassword = new EditText(MainActivity.this);
            edtAdminPassword.setTextColor(Color.BLACK);
            edtAdminPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            edtAdminPassword.setHint("Пароль...");
            edtAdminPassword.setHintTextColor(Color.GRAY);

            LinearLayout linearLayout = new LinearLayout(MainActivity.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(edtUsername);
            linearLayout.addView(edtAdminPassword);

            myDialog.setView(linearLayout);

            myDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    ParseUser.logInInBackground(edtUsername.getText().toString(), edtAdminPassword.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {

                            if (e == null) {

                                if (user != null) {

                                    Intent intent = new Intent(MainActivity.this, Admin.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(MainActivity.this, "Не найден пользователь", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            myDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
