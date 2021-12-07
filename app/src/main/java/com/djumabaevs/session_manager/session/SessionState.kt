package com.djumabaevs.session_manager.session

import java.util.*

data class SessionState(
    val isLoading: Boolean = false,
    val authToken: AuthToken? = null,
    val didCheckForPreviousAuthUser: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)