package com.spring.techpractica.application.session.get.user.sessions;

import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetUserSessionsUseCase {

    private final SessionRepository sessionRepository;

    public Page<Session> execute(GetUserSessionCommand command) {

        PageRequest pageRequest = PageRequest.of(
                command.page(),
                command.size()
        );

        // 1) اجلب الـ Page الأصلي
        Page<Session> page = sessionRepository.getSessionsByUser(
                command.userId(),
                pageRequest
        );

        // 2) رتب الـ content فقط
        List<Session> sortedContent = page.getContent().stream()
                .sorted(Comparator.comparing(Session::getAtCreated))   // تصاعدي
                //.sorted(Comparator.comparing(Session::getAtCreated).reversed())  // تنازلي (الأحدث أولاً)
                .toList();

        // 3) أعد بناء Page جديد بنفس:
        // - page size
        // - page number
        // - total elements
        return new PageImpl<>(
                sortedContent,
                page.getPageable(),
                page.getTotalElements()
        );
    }

}