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

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.TransitionChangeHandlerCompat;

import net.huannguyen.conductorexample.R;
import net.huannguyen.conductorexample.controller.BaseController;
import net.huannguyen.conductorexample.countrydetail.CountryDetailController;
import net.huannguyen.conductorexample.model.Country;
import net.huannguyen.conductorexample.transition.DetailPopAnimChangeHandler;
import net.huannguyen.conductorexample.transition.DetailPopTransChangeHandler;
import net.huannguyen.conductorexample.transition.DetailPushAnimChangeHandler;
import net.huannguyen.conductorexample.transition.DetailPushTransChangeHandler;

public class CountryGridController extends BaseController implements GridEventHandler {

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        CountryGridView view = (CountryGridView) inflater.inflate(R.layout.country_grid, container, false);
        view.setEventHandler(this);
        return view;
    }

    @Override
    public void onCountryClicked(@NonNull Country country) {
        // For demo purposes, use animator change handler for countries with name starting with
        // a character before 'i' in the alphabet. For other countries, use transition change handler if the app is running on
        // API level 21+. Use the mentioned animator change handler otherwise.
        boolean countryNameFirstCharBeforeI = country.getName().toLowerCase().charAt(0) < 'i';

        ControllerChangeHandler pushHandler
                = countryNameFirstCharBeforeI ? new DetailPushAnimChangeHandler()
                : new TransitionChangeHandlerCompat(new DetailPushTransChangeHandler(country.getName()), new DetailPushAnimChangeHandler());

        ControllerChangeHandler popHandler
                = countryNameFirstCharBeforeI ? new DetailPopAnimChangeHandler()
                : new TransitionChangeHandlerCompat(new DetailPopTransChangeHandler(country.getName()), new DetailPopAnimChangeHandler());

        getRouter().pushController(RouterTransaction.with(new CountryDetailController(country))
                                               .pushChangeHandler(pushHandler)
                                               .popChangeHandler(popHandler));
    }

    @Override
    protected String getTitle() {
        return getActivity().getString(R.string.countries);
    }
}
