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

package net.huannguyen.conductorexample.transition;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.changehandler.TransitionChangeHandler;

import net.huannguyen.conductorexample.countrydetail.CountryDetailView;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class DetailPushTransChangeHandler extends TransitionChangeHandler {

    private static final String KEY_FLAG_TRANSITION_NAME = "key_flag_transition_name";

    private String flagViewTransitionName;

    public DetailPushTransChangeHandler() {}

    public DetailPushTransChangeHandler(String flagViewTransitionName) {
        this.flagViewTransitionName = flagViewTransitionName;
    }

    @Override
    public void saveToBundle(@NonNull Bundle bundle) {
        bundle.putString(KEY_FLAG_TRANSITION_NAME, flagViewTransitionName);
    }

    @Override
    public void restoreFromBundle(@NonNull Bundle bundle) {
        flagViewTransitionName = bundle.getString(KEY_FLAG_TRANSITION_NAME);
    }

    @NonNull
    @Override
    protected Transition getTransition(@NonNull ViewGroup container,
                                       @Nullable View from,
                                       @Nullable View to,
                                       boolean isPush) {

        if (to == null || !(to instanceof CountryDetailView)) {
            throw new IllegalArgumentException("The to view must be a CountryDetailView");
        }

        final CountryDetailView detailView = (CountryDetailView) to;

        detailView.flagView.setTransitionName(flagViewTransitionName);

        ChangeTransform changeTransform = new ChangeTransform();

        // Shared elements (the flag view in this case) are drawn in the window's view overlay during the transition by default.
        // That causes the favourite fab being drawn behind the flag when it is scaled up.
        // Setting the change transform not using overlay addresses this issue.
        changeTransform.setReparentWithOverlay(false);

        return new TransitionSet()
                .addTransition(new TransitionSet()
                        .addTransition(new ChangeBounds())
                        .addTransition(new ChangeClipBounds())
                        .addTransition(changeTransform)
                        .addTransition(new ChangeImageTransform())
                        .setDuration(300))
                .addTransition(new Slide().addTarget(detailView.detailGroup).setStartDelay(150))
                .addTransition(new Scale().addTarget(detailView.favouriteFab).setStartDelay(300))
                .setInterpolator(new FastOutSlowInInterpolator());
    }
}
