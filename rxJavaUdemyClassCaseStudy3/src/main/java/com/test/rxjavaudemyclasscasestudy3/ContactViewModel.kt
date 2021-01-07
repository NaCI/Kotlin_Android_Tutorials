package com.test.rxjavaudemyclasscasestudy3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.test.rxjavaudemyclasscasestudy3.db.entity.Contact
import com.test.rxjavaudemyclasscasestudy3.repository.ContactRepository

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val contactRepository: ContactRepository = ContactRepository(application)

    fun getAllContacts(): LiveData<List<Contact>> = contactRepository.contactsLiveData

    fun getContactOperationsResult(): LiveData<String> =
        contactRepository.contactsOperationCompletedLiveData

    fun getContactOperationError(): LiveData<String> = contactRepository.contactsErrorLiveData

    fun createContact(name: String, email: String) {
        contactRepository.createContact(name = name, email = email)
    }

    fun updateContact(contact: Contact) {
        contactRepository.updateContact(contact = contact)
    }

    fun deleteContact(contact: Contact) {
        contactRepository.deleteContact(contact = contact)
    }

    override fun onCleared() {
        super.onCleared()
        contactRepository.clear()
    }
}