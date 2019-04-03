package com.vrjulianti.dictionary.Class;

import android.os.Parcel;
import android.os.Parcelable;

public class Dictionary implements Parcelable {
        private int id;
        private String word;
        private String translate;

	public Dictionary()
        {
        }

	public Dictionary(String word, String translate)
        {
            this.word = word;
            this.translate = translate;
        }

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getWord()
        {
            return word;
        }

        public void setWord(String word)
        {
            this.word = word;
        }

        public String getTranslate()
        {
            return translate;
        }

        public void setTranslate(String translate)
        {
            this.translate = translate;
        }

        @Override
        public int describeContents()
        {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags)
        {
            dest.writeInt(this.id);
            dest.writeString(this.word);
            dest.writeString(this.translate);
        }

	protected Dictionary(Parcel in)
        {
            this.id = in.readInt();
            this.word = in.readString();
            this.translate = in.readString();
        }

        public static final Parcelable.Creator<Dictionary> CREATOR = new Parcelable.Creator<Dictionary>()
        {
            @Override
            public Dictionary createFromParcel(Parcel source)
            {
                return new Dictionary(source);
            }

            @Override
            public Dictionary[] newArray(int size)
            {
                return new Dictionary[size];
            }
        };
}
