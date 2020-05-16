package com.hobbydobby.service.member

import com.hobbydobby.domain.member.Member
import com.hobbydobby.repository.member.MemberRepository
import com.hobbydobby.util.EncryptUtil
import org.springframework.stereotype.Service

/**
 * Member에 대한 Service
 */
@Service
class MemberService(
        private var memberRepository: MemberRepository

) {
    /**
     * 회원가입
     */
    fun signUp(member : Member): HashMap<String, String> {
        try {
            // salt 메소드 호출하여 salt값 반환
            val salt = EncryptUtil.getSalt()
            // SHA512 패스워드 암호화
            member.password = EncryptUtil.encryptSHA512(member.password+salt)
            member.salt = salt

            // JpaRepository 로 객체를 insert할때는 save 메소드를 씀
            memberRepository.save(member) 
            return HashMap<String, String>().also {
                it["result"] = "success"
            }
        } catch (exception : Exception) {
            return HashMap<String, String>().also {
                it["result"] = "fail"
                it["message"] = exception.message?:""
            }
        }
    }

    /**
     * 이메일 체크
     */
    fun isValidEmail(email : String) = try {
        val member = memberRepository.findByEmail(email)
        member == null
    } catch (ex : Exception) {
        ex.printStackTrace()
        false
    }

    /**
     * 닉네임 체크
     */
    fun isValidNickname(nickname : String) = try {
        val member = memberRepository.findByNickname(nickname)
        member == null
    } catch (ex : Exception) {
        ex.printStackTrace()
        false
    }
}