package com.viacheslav.movieguide.utils

import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.rules.RuleChain

fun viewModelTestingRules(): RuleChain =
    RuleChain
        .outerRule(InstantTaskExecutorRule())
        .around(MainCoroutineRule())