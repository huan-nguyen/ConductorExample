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
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.changehandler.AnimatorChangeHandler;

import net.huannguyen.conductorexample.countrydetail.CountryDetailView;

public class DetailPopAnimChangeHandler extends AnimatorChangeHandler {

    @NonNull
    @Override
    protected Animator getAnimator(@NonNull ViewGroup container,
                                   @Nullable View from,
                                   @Nullable View to,
                                   boolean isPush,
                                   boolean toAddedToContainer) {

        // Make sure the from view is a CountryDetailView
        if (from == null || !(from instanceof CountryDetailView))
            throw new IllegalArgumentException("The from view must be a CountryDetailView");

        if (to == null)
            throw new IllegalArgumentException("The to view must not be null");

        final CountryDetailView detailView = (CountryDetailView)from;

        AnimatorSet animatorSet = new AnimatorSet();

        // Set the to View's alpha to 0 to hide it at the beginning.
        to.setAlpha(0);

        // Scale down to hide the fab button
        PropertyValuesHolder fabScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0);
        PropertyValuesHolder fabScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0);
        Animator hideFabButtonAnimator = ObjectAnimator.ofPropertyValuesHolder(detailView.favouriteFab, fabScaleX, fabScaleY);

        // Slide up the flag
        Animator flagAnimator = ObjectAnimator.ofFloat(detailView.flagView, View.TRANSLATION_Y, 0, -detailView.flagView.getHeight());

        // Slide down the details
        Animator detailAnimator = ObjectAnimator.ofFloat(detailView.detailGroup, View.TRANSLATION_Y, 0, detailView.detailGroup.getHeight());

        // Show the new view
        Animator showToViewAnimator = ObjectAnimator.ofFloat(to, View.ALPHA, 0, 1);

        animatorSet.playTogether(hideFabButtonAnimator, flagAnimator, detailAnimator, showToViewAnimator);
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new FastOutLinearInInterpolator());

        animatorSet.start();

        return animatorSet;
    }

    @Override
    protected void resetFromView(@NonNull View from) {
        CountryDetailView detailView = (CountryDetailView) from;
        detailView.favouriteFab.setScaleX(1);
        detailView.favouriteFab.setScaleY(1);
        detailView.flagView.setTranslationY(0);
        detailView.detailGroup.setTranslationY(0);
    }
}
