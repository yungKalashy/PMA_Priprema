package com.example.pma_priprema;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {

    private String ime;
    private String prezime;
    private String datumRod;
    private String predmet;
    private String profesor;
    private int satiPR;
    private int satiLV;
    private boolean izborni;
    private Uri fotografija;

    public Student(String ime, String prezime, String predmet) {
        this.ime = ime;
        this.prezime = prezime;
        this.predmet = predmet;
    }

    protected Student(Parcel in) {
        ime = in.readString();
        prezime = in.readString();
        datumRod = in.readString();
        predmet = in.readString();
        profesor = in.readString();
        satiPR = in.readInt();
        satiLV = in.readInt();
        izborni = in.readByte() != 0;
        fotografija = (Uri) in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ime);
        dest.writeString(prezime);
        dest.writeString(datumRod);
        dest.writeString(predmet);
        dest.writeString(profesor);
        dest.writeInt(satiPR);
        dest.writeInt(satiLV);
        dest.writeByte((byte) (izborni ? 1 : 0));
        dest.writeParcelable(fotografija, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public String getDatumRod() { return datumRod; }
    public void setDatumRod(String datumRod) { this.datumRod = datumRod; }

    public String getPredmet() { return predmet; }
    public void setPredmet(String predmet) { this.predmet = predmet; }

    public String getProfesor() { return profesor; }
    public void setProfesor(String profesor) { this.profesor = profesor; }

    public int getSatiPR() { return satiPR; }
    public void setSatiPR(int satiPR) { this.satiPR = satiPR; }

    public int getSatiLV() { return satiLV; }
    public void setSatiLV(int satiLV) { this.satiLV = satiLV; }

    public boolean isIzborni() { return izborni; }
    public void setIzborni(boolean izborni) { this.izborni = izborni; }

    public Uri getFotografija() { return fotografija; }
    public void setFotografija(Uri fotografija) { this.fotografija = fotografija; }
}
