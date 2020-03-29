package com.example.apple.databaseapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apple.databaseapplication.models.Person;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;


public class MainActivity extends AppCompatActivity {
    EditText name, age, salary, organization, department;
    Button create_btn, read_btn, update_btn, delete_btn;
    Person person = new Person();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("Persondb")
                .migration(new MIgration())
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(configuration);

        name = (EditText)findViewById(R.id.name_editText);
        age = (EditText)findViewById(R.id.age_editText);
        salary = (EditText)findViewById(R.id.salary_editText);
        organization = (EditText)findViewById(R.id.organization_editText);
        department = (EditText)findViewById(R.id.department_editText);

        create_btn = (Button)findViewById(R.id.create_btn);
        read_btn = (Button)findViewById(R.id.read_btn);
        update_btn = (Button)findViewById(R.id.update_btn);
        delete_btn = (Button)findViewById(R.id.delete_btn);



        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Realm realm = Realm.getDefaultInstance();
                Person person = new Person();
                person.setName(String.valueOf(name.getText()));
                person.setAge(Integer.parseInt(
                        String.valueOf(age.getText())
                ));
                person.setSalary(Integer.parseInt(
                        String.valueOf(salary.getText())
                ));
                person.setOrganization(String.valueOf(organization.getText()));
                person.setDepartment(String.valueOf(department.getText()));

                Number id = realm.where(Person.class).max("id");
                long Id;
                if(id == null) {
                    Id = 1;
                    person.setId(Id);
                }
                else {
                    Id = id.longValue();
                    person.setId(Id++);
                }

                realm.beginTransaction();
                realm.copyToRealm(person);
                realm.commitTransaction();

                Toast.makeText(MainActivity.this,"Object has been created",Toast.LENGTH_LONG).show();

                name.setText("");
                age.setText("");
                salary.setText("");
                organization.setText("");
                department.setText("");
 }
        });

        read_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm.init(MainActivity.this);
                Realm realm = Realm.getDefaultInstance();
                RealmQuery<Person> query = realm.where(Person.class);
                if(query != null) {
                    person = query.findFirst();
                    name.setText(person.getName());
                    age.setText(String.valueOf(person.getAge()));
                    salary.setText(String.valueOf(person.getSalary()));
                    organization.setText(person.getOrganization());
                    department.setText(person.getDepartment());
                }
                else Toast.makeText(MainActivity.this
                        ,"Nothing to show",Toast.LENGTH_LONG).show();
            }
        });
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm realm = Realm.getDefaultInstance();

                Person person1 = new Person();
                if(person1 == person) {
                    Toast.makeText(MainActivity.this,"There is nothing to update",Toast.LENGTH_LONG).show();
                }
                else {
                    realm.beginTransaction();

                    person.setName(String.valueOf(name.getText()));
                    person.setDepartment(String.valueOf(department.getText()));
                    person.setSalary(Integer.parseInt(String.valueOf(salary.getText())));
                    person.setOrganization(String.valueOf(organization.getText()));
                    person.setAge(Integer.parseInt(String.valueOf(age.getText())));

                    realm.copyToRealmOrUpdate(person);
                    realm.commitTransaction();

                    name.setText("");
                    age.setText("");
                    salary.setText("");
                    organization.setText("");
                    department.setText("");

                    Toast.makeText(MainActivity.this,"Object is updated",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
