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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.huannguyen.conductorexample.R;
import net.huannguyen.conductorexample.controller.BaseController;
import net.huannguyen.conductorexample.misc.BundleBuilder;
import net.huannguyen.conductorexample.model.Country;

public class CountryDetailController extends BaseController implements DetailEventHandler {

    private static final String KEY_COUNTRY = "KEY_COUNTRY";

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
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        CountryDetailView view = (CountryDetailView) inflater.inflate(R.layout.country_detail, container, false);
        view.setEventHandler(this);
        view.setData(country);
        return view;
    }

    @Override
    public void onFlagClicked() {
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("geo:0,0?q=%s", country.getName())));
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    @Override
    protected String getTitle() {
        return country.getName();
    }
}
