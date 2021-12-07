package com.djumabaevs.session_manager.interactors

class CheckPreviousAuthUser(
    private val accountDao: AccountDao,
    private val authTokenDao: AuthTokenDao,
) {
    fun execute(
        email: String,
    ): Flow<DataState<AuthToken>> = flow {
        emit(DataState.loading<AuthToken>())
        var authToken: AuthToken? = null
        val entity = accountDao.searchByEmail(email)
        if(entity != null){
            authToken = authTokenDao.searchByPk(entity.pk)?.toAuthToken()
            if(authToken != null){
                emit(DataState.data(response = null, data = authToken))
            }
        }
        if(authToken == null){
            throw Exception(ERROR_NO_PREVIOUS_AUTH_USER)
        }
    }.catch{ e ->
        e.printStackTrace()
        emit(returnNoPreviousAuthUser())
    }

    /**
     * If no user was previously authenticated then emit this error. The UI is waiting for it.
     */
    private fun returnNoPreviousAuthUser(): DataState<AuthToken> {
        return DataState.error<AuthToken>(
            response = Response(
                SuccessHandling.RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE,
                UIComponentType.None(),
                MessageType.Error()
            )
        )
    }
}