package com.murua.githubissues.core.common

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val issuesDispatcher: IssuesDispatchers)

enum class IssuesDispatchers {
    Default,
    IO,
}