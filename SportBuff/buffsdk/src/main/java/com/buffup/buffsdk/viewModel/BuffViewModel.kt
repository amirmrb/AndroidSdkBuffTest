package com.buffup.buffsdk.viewModel

import com.buffup.buffsdk.repo.repository


class BuffViewModel() : BaseViewModel() {
    lateinit var repository: repository

    init {
        apiCall({ repository.getBuff(1) }, {

        })
    }
}