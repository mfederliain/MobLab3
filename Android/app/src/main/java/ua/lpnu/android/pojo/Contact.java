package ua.lpnu.android.pojo;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static ua.lpnu.android.pojo.Contact.TypePhone.FAMILY;
import static ua.lpnu.android.pojo.Contact.TypePhone.FRIEND;
import static ua.lpnu.android.pojo.Contact.TypePhone.HOME;
import static ua.lpnu.android.pojo.Contact.TypePhone.UNKNOWN;
import static ua.lpnu.android.pojo.Contact.TypePhone.WORK;

public class Contact {
    int idPerson;
    int idPhone;
    private String Name;
    private String Surname;
    private String phone;

    @TypePhone
    private int typePhone;

    public Contact(int idPerson, int idPhone, String name, String surname, String phone, int typePhone) {
        this.idPerson = idPerson;
        this.idPhone = idPhone;
        Name = name;
        Surname = surname;
        this.phone = phone;
        this.typePhone = typePhone;
    }

    public Contact(String name, String surname, String phone, @TypePhone int typePhone) {
        Name = name;
        Surname = surname;
        this.phone = phone;
        this.typePhone = typePhone;
    }

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public String getPhone() {
        return phone;
    }

    public int getTypePhone() {
        return typePhone;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public int getIdPhone() {
        return idPhone;
    }

    public void setIdPhone(int idPhone) {
        this.idPhone = idPhone;
    }

    @IntDef({HOME,WORK,FAMILY,FRIEND, UNKNOWN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TypePhone{
        int HOME=1;
        int WORK=2;
        int FAMILY=3;
        int FRIEND=4;
        int UNKNOWN =0;
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;

        Contact contact = (Contact) o;

        if (idPerson != contact.idPerson) return false;
        if (getTypePhone() != contact.getTypePhone()) return false;
        if (getName() != null ? !getName().equals(contact.getName()) : contact.getName() != null)
            return false;
        if (getSurname() != null ? !getSurname().equals(contact.getSurname()) : contact.getSurname() != null)
            return false;
        return getPhone() != null ? getPhone().equals(contact.getPhone()) : contact.getPhone() == null;
    }

    @Override
    public int hashCode() {
        int result = idPerson;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + getTypePhone();
        return result;
    }
}
