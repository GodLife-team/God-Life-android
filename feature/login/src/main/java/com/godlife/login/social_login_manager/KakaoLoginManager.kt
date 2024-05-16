package com.godlife.login.social_login_manager

import android.content.Context
import android.util.Log
import com.godlife.domain.LocalPreferenceUserUseCase
import com.godlife.login.LoginViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class KakaoLoginManager @Inject constructor(
    @ActivityContext private val context: Context,
    private val loginViewModel: LoginViewModel
) {
    private lateinit var kakaoLoginState: KaKaoLoginState
    private lateinit var kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit

    fun startKakaoLogin(
        updateSocialToken: (String) -> Unit
    ) {
        kakaoLoginState = getKaKaoLoginState()
        kakaoLoginCallback = getLoginCallback(updateSocialToken)

        when (kakaoLoginState) {
            KaKaoLoginState.KAKAO_TALK_LOGIN -> onKakaoTalkLogin(updateSocialToken)
            KaKaoLoginState.KAKAO_ACCOUNT_LOGIN -> onKakaoAccountLogin()
        }
    }

    private fun getKaKaoLoginState(): KaKaoLoginState =
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            KaKaoLoginState.KAKAO_TALK_LOGIN
        } else KaKaoLoginState.KAKAO_ACCOUNT_LOGIN

    private fun getLoginCallback(updateSocialToken: (String) -> Unit): (OAuthToken?, Throwable?) -> Unit {
        return { token, error ->
            if (error != null) {
                Log.e("KakaoLoginManager","${error.message} 카카오 계정으로 로그인 실패")
            } else if (token != null) {
                updateSocialToken(token.accessToken)
                Log.e("KakaoLoginManager","카카오 계정으로 로그인 성공, token:${token.accessToken}")
                Log.e("KakaoLoginManager","카카오 계정으로 로그인 성공, token:${token.idToken}")
                Log.e("KakaoLoginManager","카카오 계정으로 로그인 성공, token:${token.refreshToken}")

                loginViewModel.saveAccessToken(token.accessToken)

                getUserInfo(context)


            }
        }
    }

    private fun onKakaoTalkLogin(updateSocialToken: (String) -> Unit) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                // 카카오톡으로 로그인 실패
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }
                onKakaoAccountLogin()
            } else if (token != null) {
                updateSocialToken(token.accessToken)
            }
        }
    }

    private fun onKakaoAccountLogin() {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoLoginCallback)
    }
}

enum class KaKaoLoginState {
    KAKAO_TALK_LOGIN, KAKAO_ACCOUNT_LOGIN
}

private fun getUserInfo(
    context:Context
){
    val TAG = "KakaoLoginManager"
    UserApiClient.instance.me { user, error ->

        if (error != null) {
            Log.e(TAG, "사용자 정보 요청 실패", error)
        }
        else if (user != null) {
            var scopes = mutableListOf<String>()

            if (user.kakaoAccount?.emailNeedsAgreement == true) { scopes.add("account_email") }
            if (user.kakaoAccount?.birthdayNeedsAgreement == true) { scopes.add("birthday") }
            if (user.kakaoAccount?.birthyearNeedsAgreement == true) { scopes.add("birthyear") }
            if (user.kakaoAccount?.genderNeedsAgreement == true) { scopes.add("gender") }
            if (user.kakaoAccount?.phoneNumberNeedsAgreement == true) { scopes.add("phone_number") }
            if (user.kakaoAccount?.profileNeedsAgreement == true) { scopes.add("profile") }
            if (user.kakaoAccount?.ageRangeNeedsAgreement == true) { scopes.add("age_range") }
            if (user.kakaoAccount?.ciNeedsAgreement == true) { scopes.add("account_ci") }

            Log.e(TAG, scopes.toString())

            if (scopes.count() > 0) {
                Log.d(TAG, "사용자에게 추가 동의를 받아야 합니다.")

                // OpenID Connect 사용 시
                // scope 목록에 "openid" 문자열을 추가하고 요청해야 함
                // 해당 문자열을 포함하지 않은 경우, ID 토큰이 재발급되지 않음
                // scopes.add("openid")

                //scope 목록을 전달하여 카카오 로그인 요청
                UserApiClient.instance.loginWithNewScopes(context, scopes) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "사용자 추가 동의 실패", error)
                    } else {
                        Log.d(TAG, "allowed scopes: ${token!!.scopes}")

                        // 사용자 정보 재요청
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                Log.e(TAG, "사용자 정보 요청 실패", error)
                            }
                            else if (user != null) {
                                Log.i(TAG, "사용자 정보 요청 성공")
                            }
                        }
                    }
                }
            }
        }
    }
}