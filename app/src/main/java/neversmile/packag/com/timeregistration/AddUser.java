package neversmile.packag.com.timeregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class AddUser extends AppCompatActivity {

    private EditText edtUsername, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

    }

    public void btnOkUserClicked(View v) {

        if (edtUsername.getText().toString().equals("") || edtPassword.getText().toString().equals("")) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        ParseUser parseUser = new ParseUser();

        parseUser.setUsername(edtUsername.getText().toString());
        parseUser.setPassword(edtPassword.getText().toString());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Создание пользователя...");
        progressDialog.show();

        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {
                    ParseUser.getCurrentUser().logOut();
                    setResult(RESULT_OK, new Intent());
                } else {
                    setResult(RESULT_CANCELED);
                    Toast.makeText(AddUser.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                progressDialog.dismiss();
            }
        });

        finish();
    }

    public void btnCancelUserClicked(View view) {

        setResult(RESULT_CANCELED, new Intent());
        finish();
    }
}
