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

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.changehandler.AnimatorChangeHandler;

import net.huannguyen.conductorexample.countrydetail.CountryDetailView;

public class DetailPushAnimChangeHandler extends AnimatorChangeHandler {

    @NonNull
    @Override
    protected Animator getAnimator(@NonNull ViewGroup container,
                                   @Nullable View from,
                                   @Nullable View to,
                                   boolean isPush,
                                   boolean toAddedToContainer) {

        // Make sure the to view is a CountryDetailView
        if (to == null || !(to instanceof CountryDetailView))
            throw new IllegalArgumentException("The to view must be a CountryDetailView");

        final CountryDetailView detailView = (CountryDetailView)to;

        // Set the button scale to 0 to make it invisible at the beginning.
        detailView.favouriteFab.setScaleX(0);
        detailView.favouriteFab.setScaleY(0);

        AnimatorSet animatorSet = new AnimatorSet();

        AnimatorSet flagAndDetailAnim = new AnimatorSet();

        // Hide the old view
        Animator hideFromViewAnim = ObjectAnimator.ofFloat(from, View.ALPHA, 1, 0);

        // Slide down the flag
        Animator flagAnim = ObjectAnimator.ofFloat(detailView.flagView, View.TRANSLATION_Y, -detailView.flagView.getHeight(), 0);

        // Slide up the details
        Animator detailAnim = ObjectAnimator.ofFloat(detailView.detailGroup, View.TRANSLATION_Y, detailView.detailGroup.getHeight(), 0);

        flagAndDetailAnim.playTogether(hideFromViewAnim, flagAnim, detailAnim);
        flagAndDetailAnim.setDuration(300);
        flagAndDetailAnim.setInterpolator(new FastOutSlowInInterpolator());

        // Scale up the favourite fab
        PropertyValuesHolder fabScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0, 1);
        PropertyValuesHolder fabScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0, 1);
        Animator favouriteAnim = ObjectAnimator.ofPropertyValuesHolder(detailView.favouriteFab, fabScaleX, fabScaleY)
                                               .setDuration(200);

        animatorSet.playSequentially(flagAndDetailAnim, favouriteAnim);

        animatorSet.start();

        return animatorSet;
    }

    @Override
    protected void resetFromView(@NonNull View from) {
        from.setAlpha(1);
    }
}
