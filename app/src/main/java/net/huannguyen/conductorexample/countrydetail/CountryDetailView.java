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

package net.huannguyen.conductorexample.countrydetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.huannguyen.conductorexample.R;
import net.huannguyen.conductorexample.model.Country;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CountryDetailView extends LinearLayout {

    // Note: Having the controller implementing an interface and pass its reference to this View to handle navigation
    // upon clicks for demo purposes.
    // A nicer way of doing this is using RxJava to turn view clicks into a stream which is then observed by a Presenter
    // declared in the Controller. The Presenter then determines what should be done to response to clicks.
    private DetailEventHandler eventHandler;

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

    // Assign public visibility for the below views for quickly demo view change animation.
    // Production apps should have this done properly.
    @BindView(R.id.flag)
    public ImageView flagView;

    @BindView(R.id.favourite_fab)
    public FloatingActionButton favouriteFab;

    @BindView(R.id.detail_group)
    public ViewGroup detailGroup;

    @OnClick(R.id.flag)
    void onFlagClicked() {
        eventHandler.onFlagClicked();
    }

    public CountryDetailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setEventHandler(DetailEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void setData(@NonNull Country country) {
        Picasso.with(getContext())
               .load(country.getFlag())
               .into(flagView);

        capitalView.setText(country.getCapital());
        populationView.setText(String.valueOf(country.getPopulation()));
        languageView.setText(country.getLanguage());
        currencyView.setText(country.getCurrency());
        timezoneView.setText(country.getTimezone());
    }
}
