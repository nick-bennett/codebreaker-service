create table game
(
    game_id      UUID         not null,
    created      timestamp    not null,
    external_key UUID         not null,
    length       integer      not null check (length <= 20 AND length >= 1),
    pool         varchar(255) not null,
    pool_size    integer      not null,
    game_text    varchar(20)  not null,
    user_id      UUID         not null,
    primary key (game_id)
);
create table guess
(
    guess_id      UUID        not null,
    created       timestamp   not null,
    exact_matches integer     not null,
    external_key  UUID        not null,
    near_matches  integer     not null,
    guess_text    varchar(20) not null,
    game_id       UUID        not null,
    primary key (guess_id)
);
create table user_profile
(
    user_profile_id UUID         not null,
    avatar          varchar(255),
    connected       timestamp    not null,
    created         timestamp    not null,
    display_name    varchar(255) not null,
    external_key    UUID         not null,
    incognito       boolean      not null,
    oauth_key       varchar(30)  not null,
    primary key (user_profile_id)
);
alter table game
    add constraint UK_h5ly13atfey58ueo3eccgihug unique (external_key);
alter table guess
    add constraint UK_91c9f1d56yrtyaj69sprv3ppx unique (external_key);
alter table user_profile
    add constraint UK_j35xlx80xoi2sb176qdrtoy69 unique (display_name);
alter table user_profile
    add constraint UK_22o8v4jg08yk7piojnowil30o unique (external_key);
alter table user_profile
    add constraint UK_6f815wi5o4jq8p1q1w63o4mhd unique (oauth_key);
alter table game
    add constraint FKr01mjyhctq2qcstrix8juol8o foreign key (user_id) references user_profile;
alter table guess
    add constraint FK17wrv62yn4umhcoh8y608l16d foreign key (game_id) references game;
