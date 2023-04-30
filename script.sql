
create database homefood;

create table address
(
    address_id       int auto_increment
        primary key,
    apartment_number int          null,
    appartment_name  int          null,
    city             varchar(255) not null,
    country_name     varchar(255) not null,
    district         varchar(255) not null,
    house_number     int          null,
    postal_code      varchar(255) not null,
    province         varchar(255) not null,
    state            varchar(255) not null,
    street           varchar(255) not null,
    subdistrict      varchar(255) not null,
    user_details_id  int          null,
    constraint FK3m7k4el535nkwg25ebmjv97wp
        foreign key (user_details_id) references user_details (user_details_id)
);

create table business
(
    business_id     int auto_increment
        primary key,
    account_id      varchar(255) null,
    is_featured     bit          null,
    user_details_id int          null,
    user_id         int          null,
    constraint FKeqqoje49b7l2eni5cej887gqx
        foreign key (user_details_id) references user_details (user_details_id),
    constraint FKtm7f6x8bo3o8pk2kraawyq18u
        foreign key (user_id) references users (user_id)
);

create table business_application
(
    application_id int auto_increment
        primary key,
    remarks        varchar(255) null,
    status         varchar(255) null,
    user_id        int          null,
    constraint FKon8qm352ll0wg07u9actwx8d
        foreign key (user_id) references users (user_id)
);

create table cart
(
    cart_id int auto_increment
        primary key,
    user_id int null,
    constraint UK_9emlp6m95v5er2bcqkjsw48he
        unique (user_id),
    constraint FKg5uhi8vpsuy0lgloxk2h4w5o6
        foreign key (user_id) references users (user_id)
);

create table cart_history
(
    id            bigint         not null
        primary key,
    changes_time  datetime(6)    not null,
    items_added   varchar(255)   not null,
    items_removed varchar(255)   not null,
    total_cost    decimal(10, 2) not null,
    cart_id       int            null,
    constraint FK9lqe9iqghlarl368uw2ma470v
        foreign key (cart_id) references cart (cart_id)
);

create table cart_item
(
    cart_item_id int auto_increment
        primary key,
    quantity     int not null,
    cart_id      int null,
    item_id      int null,
    constraint FK1uobyhgl1wvgt1jpccia8xxs3
        foreign key (cart_id) references cart (cart_id),
    constraint FKnv28s9m82dsdmlr4aifcod3q2
        foreign key (item_id) references menu_items (item_id)
);

create table cart_total
(
    id               bigint         not null
        primary key,
    delivery_charges decimal(10, 2) not null,
    status           varchar(255)   not null,
    taxes            decimal(10, 2) not null,
    total_cost       decimal(10, 2) not null,
    cart_id          int            null,
    constraint FKpm7db384lsp6mdd4l8pr9ewhy
        foreign key (cart_id) references cart (cart_id)
);

create table delivery
(
    delivery_id int auto_increment
        primary key,
    address     varchar(255) not null,
    status      varchar(255) not null,
    order_id    int          null,
    user_id     int          null,
    constraint FK4amm8wkso9p32xo9jkcnc021b
        foreign key (order_id) references `order` (order_id),
    constraint FKl82fx83nyp1om9iejsfba9n9a
        foreign key (user_id) references users (user_id)
);

create table menu_items
(
    item_id          int auto_increment
        primary key,
    booking_required bit            null,
    cuisine          varchar(255)   null,
    description      varchar(255)   not null,
    image_url        varchar(255)   null,
    instant_delivery bit            null,
    is_available     bit            null,
    is_featured      bit            null,
    is_veg           bit            null,
    name             varchar(255)   not null,
    nutritional_info varchar(255)   null,
    price            decimal(10, 2) not null,
    business_id      int            null,
    constraint FKbjigv50nihgp43s3imxpxl64l
        foreign key (business_id) references business (business_id)
);

create table `order`
(
    order_id    int auto_increment
        primary key,
    date        date         not null,
    status      varchar(255) not null,
    customer_id int          null,
    constraint FK8i0eg1fmeed6xqe28akt4mix9
        foreign key (customer_id) references users (user_id)
);

create table order_item
(
    order_item_id    int auto_increment
        primary key,
    nutritional_info varchar(255)   null,
    price            decimal(10, 2) not null,
    quantity         int            not null,
    item_id          int            null,
    order_id         int            null,
    constraint FKmnutcj4r9kc4c952ux3lips27
        foreign key (item_id) references menu_items (item_id),
    constraint FKs234mi6jususbx4b37k44cipy
        foreign key (order_id) references `order` (order_id)
);

create table payment
(
    payment_id     int auto_increment
        primary key,
    method         varchar(255) not null,
    status         varchar(255) not null,
    transaction_id varchar(255) not null,
    order_id       int          null,
    constraint FK33pd2iqamm9gp5c14r1catra2
        foreign key (order_id) references `order` (order_id)
);

create table position_entity
(
    position_id int auto_increment
        primary key,
    latitude    double null,
    longitude   double null,
    address_id  int    null,
    constraint FK8o45td26otn3ej9nouvl7f35e
        foreign key (address_id) references address (address_id)
);

create table transactions
(
    id                    bigint auto_increment
        primary key,
    amount                decimal(38, 2) null,
    transaction_currency  varchar(255)   null,
    transaction_date      date           null,
    transaction_fee       decimal(38, 2) null,
    transaction_method    varchar(255)   null,
    transaction_reference varchar(255)   null,
    transaction_status    varchar(255)   null,
    user_id               int            null,
    business_id           int            null,
    constraint UK_94m9euoxupnom1p8mtwmk9q4g
        unique (transaction_reference),
    constraint FKkoy574xbxxv78lkan9iuhgvh3
        foreign key (business_id) references business (business_id),
    constraint FKqwv7rmvc8va8rep7piikrojds
        foreign key (user_id) references users (user_id)
);

create table user_details
(
    user_details_id int auto_increment
        primary key,
    image_url       varchar(255) null,
    name            varchar(255) not null,
    phone_number    varchar(255) not null,
    user_id         int          null,
    constraint FKicouhgavvmiiohc28mgk0kuj5
        foreign key (user_id) references users (user_id)
);

create table users
(
    user_id  int auto_increment
        primary key,
    email    varchar(255) not null,
    password varchar(255) not null,
    role     varchar(255) not null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7
        unique (email)
);

create table verifications
(
    verification_id int auto_increment
        primary key,
    is_verified     bit          not null,
    token           varchar(255) not null,
    user_id         int          null,
    constraint FKdr2ibpdjbtdj343h2ku3tyw2v
        foreign key (user_id) references users (user_id)
);


