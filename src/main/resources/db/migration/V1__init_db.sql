CREATE TABLE medical_service_categories
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE medical_professionals_categories
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE accounts
(
    id            BIGSERIAL PRIMARY KEY,
    login         VARCHAR(32) UNIQUE NOT NULL,
    password_hash TEXT               NOT NULL,
    type          VARCHAR(32)        NOT NULL,
    CHECK ( type IN ('administrator',
                     'patient',
                     'medical_professional',
                     'administrator_crm')
        )
);

CREATE TABLE administrators
(
    id         BIGSERIAL PRIMARY KEY,
    account_id BIGINT REFERENCES accounts (id)
        ON DELETE CASCADE,
    first_name VARCHAR(32) NOT NULL,
    last_name  VARCHAR(32) NOT NULL
);

CREATE TABLE patients
(
    id         BIGSERIAL PRIMARY KEY,
    account_id BIGINT REFERENCES accounts (id)
        ON DELETE CASCADE,
    first_name VARCHAR(32)        NOT NULL,
    last_name  VARCHAR(32)        NOT NULL,
    birth_date DATE               NOT NULL,
    gender     VARCHAR(1)         NOT NULL
        CHECK ( gender IN ('M', 'F') ),
    phone      VARCHAR(16) UNIQUE NOT NULL,
    email      VARCHAR(32) UNIQUE NOT NULL
);

CREATE TABLE medical_professionals
(
    id         BIGSERIAL,
    category   BIGINT REFERENCES medical_professionals_categories (id)
        ON DELETE CASCADE,
    account_id BIGINT REFERENCES accounts (id)
        ON DELETE CASCADE,
    first_name VARCHAR(32) NOT NULL,
    last_name  VARCHAR(32) NOT NULL,

    PRIMARY KEY (id, category)
);

CREATE TABLE medical_services
(
    id                                     BIGSERIAL,
    category                               BIGINT REFERENCES medical_service_categories (id)
        ON DELETE CASCADE,
    required_medical_professional_category BIGINT REFERENCES medical_professionals_categories (id)
        ON DELETE CASCADE,
    name                                   VARCHAR(64) UNIQUE NOT NULL,
    price                                  DECIMAL(10, 2)     NOT NULL
        CHECK ( price >= 0 ),

    PRIMARY KEY (id, required_medical_professional_category)
);

CREATE TABLE appointments
(
    id                            BIGSERIAL PRIMARY KEY,
    patient                       BIGINT REFERENCES patients (id)
        ON DELETE CASCADE,
    medical_service               BIGINT         NOT NULL,
    medical_professional          BIGINT         NOT NULL,
    medical_professional_category BIGINT         NOT NULL,
    appointment_date              DATE           NOT NULL,
    timeslot                      INT            NOT NULL,
    price_fact                    DECIMAL(10, 2) NOT NULL
        CHECK ( price_fact >= 0 ),
    status                        TEXT           NOT NULL
        CHECK ( status IN ('started', 'finished', 'aborted') ),

    CONSTRAINT uk1_appointments_patient_timeslot
        UNIQUE (patient, appointment_date, timeslot),
    CONSTRAINT uk2_appointments_professional_timeslot
        UNIQUE (medical_professional, appointment_date, timeslot),

    CONSTRAINT fk1_appointments_medical_services
        FOREIGN KEY (medical_service, medical_professional_category)
            REFERENCES medical_services (id, required_medical_professional_category)
            ON DELETE CASCADE,
    CONSTRAINT fk2_appointments_medical_professionals
        FOREIGN KEY (medical_professional, medical_professional_category)
            REFERENCES medical_professionals (id, category)
            ON DELETE CASCADE
);