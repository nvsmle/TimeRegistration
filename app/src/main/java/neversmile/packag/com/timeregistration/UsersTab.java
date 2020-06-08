package neversmile.packag.com.timeregistration;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTab extends Fragment implements View.OnClickListener {

    private ListView listUsers;
    private ArrayList usersArray;
    private ArrayAdapter arrayAdapter;
    private Button btnAddUser;

    public UsersTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);

        listUsers = view.findViewById(R.id.listUsers);

        usersArray = new ArrayList();
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, usersArray);

        btnAddUser = view.findViewById(R.id.btnAddUser);
        btnAddUser.setOnClickListener(this);

        refreshUsersList();

        return view;
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(getContext(), AddUser.class);
        startActivityForResult(intent, 1000);

    }

    public void refreshUsersList() {

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {

                if (e == null) {

                    if (users.size() > 0) {

                        for (ParseUser user : users) {
                            usersArray.add(user.getUsername());
                        }

                        listUsers.setAdapter(arrayAdapter);

                    } else {
                        Toast.makeText(getContext(), "Ошибка при получении списка пользователей", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {

            Toast.makeText(getContext(), "New user added", Toast.LENGTH_SHORT).show();

        }

    }
}
