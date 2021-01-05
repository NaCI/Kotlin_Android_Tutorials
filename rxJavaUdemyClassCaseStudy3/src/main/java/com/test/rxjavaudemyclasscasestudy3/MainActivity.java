package com.test.rxjavaudemyclasscasestudy3;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.test.rxjavaudemyclasscasestudy3.adapter.ContactsAdapter;
import com.test.rxjavaudemyclasscasestudy3.db.ContactsAppDatabase;
import com.test.rxjavaudemyclasscasestudy3.db.entity.Contact;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ContactsAdapter contactsAdapter;
    private ArrayList<Contact> contactArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContactsAppDatabase contactsAppDatabase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private long idNumberOfInsertedRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" Contacts Manager ");

        recyclerView = findViewById(R.id.recycler_view_contacts);
        contactsAppDatabase = Room.databaseBuilder(getApplicationContext(), ContactsAppDatabase.class, "ContactDB").build();

        contactsAdapter = new ContactsAdapter(this, contactArrayList, MainActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactsAdapter);

        // This flowable is going to act like live data or observable. Whenever db updates, the consumer will be triggered
        compositeDisposable.add(
                contactsAppDatabase.getContactDAO().getContacts()
                        .subscribeOn(Schedulers.computation()) // We used computation instead of io. Computation is using especially for callback scenarios
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Contact>>() {
                            @Override
                            public void accept(List<Contact> contacts) throws Throwable {
                                contactArrayList.clear();
                                contactArrayList.addAll(contacts);
                                contactsAdapter.notifyDataSetChanged();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                Toast.makeText(MainActivity.this, "An error occured! " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAndEditContacts(false, null, -1);
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void addAndEditContacts(final boolean isUpdate, final Contact contact, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.layout_add_contact, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        TextView contactTitle = view.findViewById(R.id.new_contact_title);
        final EditText newContact = view.findViewById(R.id.name);
        final EditText contactEmail = view.findViewById(R.id.email);

        contactTitle.setText(!isUpdate ? "Add New Contact" : "Edit Contact");

        if (isUpdate && contact != null) {
            newContact.setText(contact.getName());
            contactEmail.setText(contact.getEmail());
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(isUpdate ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                                if (isUpdate) {

                                    deleteContact(contact, position);
                                } else {

                                    dialogBox.cancel();

                                }

                            }
                        });


        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(newContact.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter contact name!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }


                if (isUpdate && contact != null) {

                    updateContact(newContact.getText().toString(), contactEmail.getText().toString(), position);
                } else {

                    createContact(newContact.getText().toString(), contactEmail.getText().toString());
                }
            }
        });
    }

    private void deleteContact(final Contact contact, int position) {

//        contactArrayList.remove(position);
//        contactsAppDatabase.getContactDAO().deleteContact(contact);
//        contactsAdapter.notifyDataSetChanged();

        compositeDisposable.add(
                Completable.fromAction(new Action() {
                    @Override
                    public void run() throws Throwable {
                        contactsAppDatabase.getContactDAO().deleteContact(contact);
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                Toast.makeText(MainActivity.this, "Contact deleted successfully", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Toast.makeText(MainActivity.this, "An error occured! " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
        );

    }

    private void updateContact(String name, String email, int position) {

        final Contact contact = contactArrayList.get(position);

        contact.setName(name);
        contact.setEmail(email);

//        contactsAppDatabase.getContactDAO().updateContact(contact);

//        contactArrayList.set(position, contact);

//        contactsAdapter.notifyDataSetChanged();

        compositeDisposable.add(
                Completable.fromAction(new Action() {
                    @Override
                    public void run() throws Throwable {
                        contactsAppDatabase.getContactDAO().updateContact(contact);
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                Toast.makeText(MainActivity.this, "Contact updated successfully", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Toast.makeText(MainActivity.this, "An error occured! " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
        );
    }

    private void createContact(final String name, final String email) {

//        long id = contactsAppDatabase.getContactDAO().addContact(new Contact(0, name, email));


        /*Contact contact = contactsAppDatabase.getContactDAO().getContact(id);

        if (contact != null) {

            contactArrayList.add(0, contact);
            contactsAdapter.notifyDataSetChanged();

        }*/

        compositeDisposable.add(
                Completable.fromAction(new Action() {
                    @Override
                    public void run() throws Throwable {
                        idNumberOfInsertedRow = contactsAppDatabase.getContactDAO().addContact(new Contact(0, name, email));
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                Toast.makeText(MainActivity.this, "Contact added successfully", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Toast.makeText(MainActivity.this, "An error occured! " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
        );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
