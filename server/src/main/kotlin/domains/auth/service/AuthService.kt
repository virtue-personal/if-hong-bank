package org.example.domains.auth.service

import org.example.common.exception.CustomException
import org.example.common.exception.ErrorCode
import org.example.common.jwt.JwtProvider
import org.example.interfaces.OAuthServiceInterface
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val oAuthServices: Map<String, OAuthServiceInterface>,
    private val jwtProvider: JwtProvider
) {
    fun handleAuth(state: String, code: String): String {
        // GOOGLE -> google
        val provider = state.lowercase()

        val callService = oAuthServices[provider] ?: throw CustomException(ErrorCode.PROVIDER_NOT_FOUND, provider)

        val accessToken = callService.getToken(code)
        val userInfo = callService.getUserInfo(accessToken.accessToken)
        val token = jwtProvider.createToken(provider, userInfo.email, userInfo.name, userInfo.id)

        // userInfo
    }

}