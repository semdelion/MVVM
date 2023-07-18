package com.semdelion.presentaion.core

import com.semdelion.domain.repositories.IRepository

interface BaseApplication {
    val repositories : List<IRepository>
}