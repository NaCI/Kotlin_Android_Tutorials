package com.test.rxjavaudemyclasscasestudy3.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.test.rxjavaudemyclasscasestudy3.db.entity.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactsAppDatabase extends RoomDatabase {

    public abstract ContactDAO getContactDAO();

}
