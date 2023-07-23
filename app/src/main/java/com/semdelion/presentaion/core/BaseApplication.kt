package com.semdelion.presentaion.core

import com.semdelion.domain.repositories.IRepository

interface BaseApplication {
    var repositories : List<IRepository>
}