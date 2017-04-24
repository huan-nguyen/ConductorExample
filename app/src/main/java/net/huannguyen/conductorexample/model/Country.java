/*
 *
 *  * Copyright (C) 2017 Huan Nguyen.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package net.huannguyen.conductorexample.model;

import android.os.Parcel;
import android.os.Parcelable;

public final class Country implements Parcelable {
    private final String name;
    private final String capital;
    private final long population;
    private final String flag;
    private final String language;
    private final String currency;
    private final String timezone;

    public Country(String name, String capital, long population, String flag, String language, String currency, String timezone) {
        this.name = name;
        this.capital = capital;
        this.population = population;
        this.flag = flag;
        this.language = language;
        this.currency = currency;
        this.timezone = timezone;
    }

    private Country(Parcel in) {
        name = in.readString();
        capital = in.readString();
        population = in.readLong();
        flag = in.readString();
        language = in.readString();
        currency = in.readString();
        timezone = in.readString();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public long getPopulation() {
        return population;
    }

    public String getFlag() {
        return flag;
    }

    public String getLanguage() {
        return language;
    }

    public String getCurrency() {
        return currency;
    }

    public String getTimezone() {
        return timezone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(capital);
        dest.writeLong(population);
        dest.writeString(flag);
        dest.writeString(language);
        dest.writeString(currency);
        dest.writeString(timezone);
    }
}