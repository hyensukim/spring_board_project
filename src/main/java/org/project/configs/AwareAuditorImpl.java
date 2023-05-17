package org.project.configs;

import lombok.RequiredArgsConstructor;
import org.project.commons.MemberUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import java.util.Optional;

/**
 * 로그인 회원정보를 수정자로 추가해주기 위한 기능
 */
@Component
@RequiredArgsConstructor
public class AwareAuditorImpl implements AuditorAware<String> {

    private final MemberUtil memberUtil;

    @Override
    public Optional<String> getCurrentAuditor() {
        String memberId = memberUtil.isLogin() ? memberUtil.getMember().getMemberId() : null;
        return Optional.ofNullable(memberId);
    }
}
