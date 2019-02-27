package com.github.pksokolowski.gameserver.engine.utils

import java.util.*

fun getDeadline(timeFromNow: Long?) = timeFromNow?.let { now() + timeFromNow }

fun isDeadlineCrossed(deadline: Long?) = deadline?.let { now() > it } ?: false

private fun now() = Calendar.getInstance().timeInMillis