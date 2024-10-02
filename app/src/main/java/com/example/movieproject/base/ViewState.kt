package com.example.movieproject.base

open class ViewState<T>(val data:T?=null) {
    class Loaded<T>(data: T) : ViewState<T>(data)
    class Error<T>(val error:BaseException?=null) :ViewState<T>()
    class Empty<T>:ViewState<T>()
}