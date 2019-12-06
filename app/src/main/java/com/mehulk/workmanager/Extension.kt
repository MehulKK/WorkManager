package com.mehulk.workmanager

import java.util.concurrent.atomic.AtomicInteger

val c = AtomicInteger(0)
    fun getID(): Int {
        return c.incrementAndGet()
    }