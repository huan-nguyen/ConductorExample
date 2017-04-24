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

package net.huannguyen.conductorexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;

public class CountryDetailController extends BaseController {

    private static final String KEY_COUNTRY = "KEY_COUNTRY";

    @BindView(R.id.flag)
    ImageView flagView;

    @BindView(R.id.capital)
    TextView capitalView;

    @BindView(R.id.population)
    TextView populationView;

    @BindView(R.id.currency)
    TextView currencyView;

    @BindView(R.id.language)
    TextView languageView;

    @BindView(R.id.timezone)
    TextView timezoneView;

    @OnClick(R.id.flag)
    void onFlagClicked() {
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("geo:0,0?q=%s", country.getName())));
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private Country country = getArgs().getParcelable(KEY_COUNTRY);

    public CountryDetailController(Country country) {
        this(new BundleBuilder(new Bundle())
                .putParcelable(KEY_COUNTRY, country)
                .build());
    }

    public CountryDetailController(Bundle args) {
        super(args);
    }

    @NonNull
    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.country_detail, container, false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        Picasso.with(view.getContext())
               .load(country.getFlag())
               .into(flagView);
        capitalView.setText(country.getCapital());
        populationView.setText(String.valueOf(country.getPopulation()));
        languageView.setText(country.getLanguage());
        currencyView.setText(country.getCurrency());
        timezoneView.setText(country.getTimezone());
    }

    @Override
    protected String getTitle() {
        return country.getName();
    }
}
