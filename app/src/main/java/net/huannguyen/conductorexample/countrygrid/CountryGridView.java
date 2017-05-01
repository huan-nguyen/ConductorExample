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

package net.huannguyen.conductorexample.countrygrid;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.huannguyen.conductorexample.R;
import net.huannguyen.conductorexample.model.Country;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CountryGridView extends RecyclerView {

    // Note: Having the controller implementing an interface and pass its reference to this View to handle navigation
    // upon clicks for demo purposes.
    // A nicer way of doing this is using RxJava to turn view clicks into a stream which is then observed by a Presenter
    // declared in the Controller. The Presenter then determines what should be done to response to clicks.
    private GridEventHandler eventHandler;

    // Assume there is a list of countries that has already been obtained.
    // Data referenced from restcountries.eu. Flag images from flagpedia.net.
    private static final List<Country> COUNTRIES = Arrays.asList(
            new Country("Australia", "Canberra", 24117360, "https://flagpedia.net/data/flags/normal/au.png", "English", "Australian dollar", "UTC+10:00"),
            new Country("Finland", "Helsinki", 5491817, "https://flagpedia.net/data/flags/normal/fi.png", "Finish", "Euro", "UTC+02:00"),
            new Country("France", "Paris", 66710000, "https://flagpedia.net/data/flags/normal/fr.png", "French", "Euro", "UTC+01:00"),
            new Country("Germany", "Berlin", 81770900, "https://flagpedia.net/data/flags/normal/de.png", "German", "Euro", "UTC+01:00"),
            new Country("Greece", "Athens", 10858018, "https://flagpedia.net/data/flags/normal/gr.png", "Greek", "Euro", "UTC+02:00"),
            new Country("Hungary", "Budapest", 9823000, "https://flagpedia.net/data/flags/normal/hu.png", "Hungarian", "Hungarian forint", "UTC+01:00"),
            new Country("Iceland", "Reykjavík", 334300, "https://flagpedia.net/data/flags/normal/is.png", "Icelandic", "Icelandic króna", "UTC+00:00"),
            new Country("Ireland", "Dublin", 6378000, "https://flagpedia.net/data/flags/normal/ie.png", "Irish", "Euro", "UTC+00:00"),
            new Country("Italy", "Rome", 60665551, "https://flagpedia.net/data/flags/normal/it.png", "Italian", "Euro", "UTC+01:00"),
            new Country("Luxembourg", "Luxembourg", 576200, "https://flagpedia.net/data/flags/normal/lu.png", "Luxembourgish", "Euro", "UTC+01:00"),
            new Country("Netherlands", "Amsterdam", 17019800, "https://flagpedia.net/data/flags/normal/nl.png", "Dutch", "Euro", "UTC+01:00"),
            new Country("Norway", "Oslo", 5223256, "https://flagpedia.net/data/flags/normal/no.png", "Norwegian", "Norwegian krone", "UTC+01:00"),
            new Country("Portugal", "Lisbon", 10374822, "https://flagpedia.net/data/flags/normal/pt.png", "Portuguese", "Euro", "UTC+00:00"),
            new Country("United Kingdom", "London", 65110000, "https://flagpedia.net/data/flags/normal/gb.png", "English", "Pound", "UTC+00:00"));

    public CountryGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayoutManager(new GridLayoutManager(getContext(),
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2: 3));
        setAdapter(new CountryAdapter(COUNTRIES));
    }

    private void onCountryClicked(Country country) {
        eventHandler.onCountryClicked(country);
    }

    public void setEventHandler(GridEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    private class CountryAdapter extends Adapter<CountryViewHolder> {

        private List<Country> countries;

        CountryAdapter(List<Country> countries) {
            this.countries = countries;
        }

        @Override
        public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
            return new CountryViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(CountryViewHolder holder, int position) {
            holder.bindData(countries.get(position));
        }

        @Override
        public int getItemCount() {
            return countries == null ? 0 : countries.size();
        }
    }

    class CountryViewHolder extends ViewHolder {
        @BindView(R.id.flag)
        ImageView flagView;

        @BindView(R.id.name)
        TextView nameView;

        private Country country;

        @OnClick(R.id.flag)
        void onItemClicked() {
            onCountryClicked(country);
        }

        CountryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(@NonNull Country country) {
            this.country = country;
            Picasso.with(itemView.getContext())
                   .load(country.getFlag())
                   .into(flagView);
            nameView.setText(country.getName());

            // Set transition name for flag view to enable transition animation.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                flagView.setTransitionName(country.getName());
            }
        }
    }
}
