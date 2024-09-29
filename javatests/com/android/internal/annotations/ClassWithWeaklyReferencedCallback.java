/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.internal.annotations;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

public class ClassWithWeaklyReferencedCallback {

    // This field should be kept despite being write-once, as the type has
    // the necessary annotation.
    private final AnnotatedCallback mKeptCallback = new AnnotatedCallback();

    // This field should be stripped as it's write-once and doesn't have the necessary annotation.
    private final UnannotatedCallback mStrippedCallback = new UnannotatedCallback();

    public ClassWithWeaklyReferencedCallback(List<WeakReference<Object>> weakRefs) {
        weakRefs.add(new WeakReference<>(mKeptCallback));
        weakRefs.add(new WeakReference<>(mStrippedCallback));
    }

    @WeaklyReferencedCallback
    public static class AnnotatedCallback {
        public void onCallback() {
            Log.i("AnnotatedCallback", "onCallback");
        }
    }

    public static class UnannotatedCallback {
        public void onCallback() {
            Log.i("UnannotatedCallback", "onCallBack");
        }
    }
}
