package com.me.security.member.repository;

import com.me.security.member.domain.QUser;
import com.me.security.member.domain.QUserAuthority;
import com.me.security.member.domain.User;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

public class UserCustomRepositoryImpl extends QuerydslRepositorySupport implements UserCustomRepository {

    private static final QUser USER = QUser.user;
    private static final QUserAuthority USER_AUTHORITY = QUserAuthority.userAuthority;

    public UserCustomRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(
                userBasedQuery()
                .where(USER.id.eq(id))
                .fetchOne());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(
                userBasedQuery()
                        .where(USER.email.eq(email))
                        .fetchOne());
    }

    private JPQLQuery<User> userBasedQuery() {
        return from(USER)
                .leftJoin(USER.roles, USER_AUTHORITY).fetchJoin();
    }
}
