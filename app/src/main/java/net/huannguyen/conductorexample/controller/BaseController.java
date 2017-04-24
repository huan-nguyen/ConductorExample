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

package net.huannguyen.conductorexample.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.bluelinelabs.conductor.Controller;

import net.huannguyen.conductorexample.activity.MainActivity;

public abstract class BaseController extends Controller {

    protected BaseController() { }
    protected BaseController(Bundle args) {
        super(args);
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);

        // Quick way to access the toolbar for demo purposes. Production app needs to have this done properly
        MainActivity activity = (MainActivity) getActivity();

        // Activity should have already been set after the conductor is attached.
        assert activity != null;

        activity.setToolBarTitle(getTitle());
        activity.enableUpArrow(getRouter().getBackstackSize() > 1);
    }

    protected abstract String getTitle();
}