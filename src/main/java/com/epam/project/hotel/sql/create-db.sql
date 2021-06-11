CREATE SCHEMA IF NOT EXISTS `hotel_db` DEFAULT CHARACTER SET utf8;

USE `hotel_db`;

DROP TABLE IF EXISTS `hotel_db`.`payment_check`;
DROP TABLE IF EXISTS `hotel_db`.`room_info`;
DROP TABLE IF EXISTS `hotel_db`.response;

DROP TABLE IF EXISTS `hotel_db`.`request`;
DROP TABLE IF EXISTS `hotel_db`.`user`;
DROP TABLE IF EXISTS `hotel_db`.`user_role`;
DROP TABLE IF EXISTS `hotel_db`.`room`;

CREATE TABLE IF NOT EXISTS `hotel_db`.`user_role`
(
    `role_id`          INT                      NOT NULL AUTO_INCREMENT,
    `role_description` ENUM ('USER', 'MANAGER') NOT NULL,
    PRIMARY KEY (`role_id`)
);

CREATE TABLE IF NOT EXISTS `hotel_db`.`user`
(
    `user_id`       INT         NOT NULL AUTO_INCREMENT,
    `user_login`    VARCHAR(20) NOT NULL UNIQUE,
    `user_password` VARCHAR(32) NOT NULL,
    `user_email`    VARCHAR(32) NOT NULL,
    `role_id`       INT         NOT NULL,
    PRIMARY KEY (`user_id`),
    INDEX `role_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `role`
        FOREIGN KEY (`role_id`)
            REFERENCES `hotel_db`.`user_role` (`role_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);



CREATE TABLE IF NOT EXISTS `hotel_db`.`request`
(
    `request_id`         INT                                  NOT NULL AUTO_INCREMENT,
    `user_id`            INT                                  NOT NULL,
    `request_status`     ENUM ('CREATED',
        'MANAGER_ACCEPTED', 'USER_ACCEPTED',
        'MANAGER_DECLINED', 'USER_DECLINED')                  NOT NULL,
    `request_size`       INT                                  NOT NULL,
    `request_class`      ENUM ('ECONOM', 'PREMIUM', 'LUXURY') NOT NULL,
    `request_arrival`    DATE                                 NOT NULL,
    `request_department` DATE                                 NOT NULL,
    PRIMARY KEY (`request_id`),
    INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `user_id`
        FOREIGN KEY (`user_id`)
            REFERENCES `hotel_db`.`user` (`user_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `hotel_db`.`response`
(
    `response_request_id` INT NOT NULL,
    `room_id`    INT NOT NULL,
    PRIMARY KEY (`response_request_id`),
    CONSTRAINT `request_id`
        FOREIGN KEY (`response_request_id`)
            REFERENCES `hotel_db`.`request` (`request_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `hotel_db`.`room`
(
    `room_id`     INT                                              NOT NULL,
    `room_status` ENUM ('FREE', 'BOOKED', 'IN USE', 'UNAVAILABLE') NOT NULL,
    `room_in`     DATE                                             NOT NULL,
    `room_out`    DATE                                             NOT NULL,
    PRIMARY KEY (`room_id`)
);



CREATE TABLE IF NOT EXISTS `hotel_db`.`payment_check`
(
    `check_id`     INT AUTO_INCREMENT          NOT NULL,
    `user_id`      INT                         NOT NULL,
    `room_id`      INT                         NOT NULL,
    `room_in`      DATE                        NOT NULL,
    `room_out`     DATE                        NOT NULL,
    `check_price`  INT                         NOT NULL,
    `check_status` ENUM ('NOT PAYED', 'PAYED') NOT NULL,
    PRIMARY KEY (`check_id`),
    INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
    INDEX `room_id_idx` (`room_id` ASC) VISIBLE,
    CONSTRAINT `u_id`
        FOREIGN KEY (`user_id`)
            REFERENCES `hotel_db`.`user` (`user_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `r_id`
        FOREIGN KEY (`room_id`)
            REFERENCES `hotel_db`.`room` (`room_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS `hotel_db`.`room_info`
(
    `room_id`          INT                            NOT NULL,
    `room_name`        VARCHAR(50)                    NOT NULL,
    `room_price`       INT                            NOT NULL,
    `room_description` VARCHAR(500)                   NOT NULL,
    `room_size`        INT                            NOT NULL,
    `room_class`       ENUM ('ECONOM', 'LUXE', 'VIP') NOT NULL,
    `room_photo`       VARCHAR(100)                   NOT NULL,
    PRIMARY KEY (`room_id`),
    CONSTRAINT `id`
        FOREIGN KEY (`room_id`)
            REFERENCES `hotel_db`.`room` (`room_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

INSERT INTO user_role(role_id, role_description)
VALUES (1, 'USER');
INSERT INTO user_role(role_id, role_description)
VALUES (2, 'MANAGER');
INSERT INTO user(user_login, user_password, user_email, role_id)
VALUES ('artem', '12345', 'artem@mail.com', 1);
INSERT INTO user(user_login, user_password, user_email, role_id)
VALUES ('manager', '98765', 'manager@mail.com', 2);
INSERT INTO room(room_id, room_status, room_in, room_out)
VALUES ('1', 'FREE', '2000-01-01', '2000-01-01');
INSERT INTO room(room_id, room_status, room_in, room_out)
VALUES ('2', 'BOOKED', '2021-02-06', '2021-10-06');
INSERT INTO room(room_id, room_status, room_in, room_out)
VALUES ('3', 'IN USE', '2021-06-15', '2021-05-06');
INSERT INTO room(room_id, room_status, room_in, room_out)
VALUES ('4', 'UNAVAILABLE', '2021-02-06', '2021-02-10');
INSERT INTO room_info(room_id, room_name, room_price, room_description, room_size, room_class, room_photo)
VALUES ('1', 'Standard Room', '100', 'Standard Room Description', '2', 'ECONOM', 'static/StandardRoom.jpg');
INSERT INTO room_info(room_id, room_name, room_price, room_description, room_size, room_class, room_photo)
VALUES ('2', 'Family Room', '200', 'Family Room Description', '4', 'ECONOM', 'static/FamilyRoom.jpg');
INSERT INTO room_info(room_id, room_name, room_price, room_description, room_size, room_class, room_photo)
VALUES ('3', 'Family Room Sea View', '300', 'Family Room Sea View Description', '4', 'LUXE',
        'static/FamilyRoomSeaView.jpg');
INSERT INTO room_info(room_id, room_name, room_price, room_description, room_size, room_class, room_photo)
VALUES ('4', 'Presidential Room', '500', 'Presidential Room Description', '2', 'VIP', 'static/PresidentialRoom.jpg');
INSERT INTO payment_check(user_id, room_id, room_in, room_out, check_price, check_status)
VALUES (1, 1, '2021-06-01', '2021-06-10', 500, 'PAYED');
INSERT INTO payment_check(user_id, room_id, room_in, room_out, check_price, check_status)
VALUES (2, 1, '2021-06-15', '2021-06-23', 300, 'PAYED');
INSERT INTO payment_check(user_id, room_id, room_in, room_out, check_price, check_status)
VALUES (1, 1, '2021-06-11', '2021-06-11', 300, 'PAYED');
INSERT INTO request(user_id, request_status, request_size, request_class, request_arrival, request_department)
VALUES (1, 'CREATED', 2, 'LUXURY', '2021-06-09', '2021-06-15');
INSERT INTO request(user_id, request_status, request_size, request_class, request_arrival, request_department)
VALUES (2, 'CREATED', 3, 'ECONOM', '2021-06-01', '2021-07-15');