package com.test.rxjavaudemyclasscasestudy3.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.test.rxjavaudemyclasscasestudy3.db.ContactsAppDatabase
import com.test.rxjavaudemyclasscasestudy3.db.entity.Contact
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableCompletableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class ContactRepository(private val application: Application) {

    private val contactsAppDatabase: ContactsAppDatabase = Room.databaseBuilder(
        application.applicationContext,
        ContactsAppDatabase::class.java,
        "ContactDB"
    ).build()

    private val disposable = CompositeDisposable()
    val contactsLiveData: MutableLiveData<List<Contact>> = MutableLiveData()
    val contactsErrorLiveData: MutableLiveData<String> = MutableLiveData()
    val contactsOperationCompletedLiveData: MutableLiveData<String> = MutableLiveData()
    private var idNumberOfInsertedRow: Long = 0

    init {
        // This flowable is going to act like live data or observable. Whenever db updates, the consumer will be triggered
        disposable.add(
            contactsAppDatabase.contactDAO.contacts
                .subscribeOn(Schedulers.computation()) // We used computation instead of io. Computation is using especially for callback scenarios
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ contactsList ->
                    contactsLiveData.postValue(contactsList)
                }, { exception ->
                    contactsErrorLiveData.postValue(exception.message)
                })
        )
    }

    fun createContact(name: String, email: String) {
        disposable.add(Completable.fromAction {
            idNumberOfInsertedRow =
                contactsAppDatabase.contactDAO.addContact(Contact(0, name, email))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    contactsOperationCompletedLiveData.postValue("Contact added successfully")
                }

                override fun onError(e: @NonNull Throwable?) {
                    contactsErrorLiveData.postValue(e?.message ?: "An error occurred")
                }
            })
        )
    }

    fun updateContact(contact: Contact) {
        disposable.add(Completable.fromAction {
            contactsAppDatabase.contactDAO.updateContact(contact)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    contactsOperationCompletedLiveData.postValue("Contact updated successfully")
                }

                override fun onError(e: @NonNull Throwable?) {
                    contactsErrorLiveData.postValue(e?.message ?: "An error occurred")
                }
            })
        )
    }

    fun deleteContact(contact: Contact) {
        disposable.add(Completable.fromAction {
            contactsAppDatabase.contactDAO.deleteContact(contact)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    contactsOperationCompletedLiveData.postValue("Contact deleted successfully")
                }

                override fun onError(e: @NonNull Throwable?) {
                    contactsErrorLiveData.postValue(e?.message ?: "An error occurred")
                }
            })
        )
    }

    fun clear() {
        disposable.clear()
    }

}