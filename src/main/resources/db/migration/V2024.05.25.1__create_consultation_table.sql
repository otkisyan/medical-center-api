CREATE TABLE consultation (
    appointment_id BIGINT NOT NULL,
    diagnosis VARCHAR(255),
    medical_recommendations VARCHAR(1000),
    symptoms VARCHAR(255),
    PRIMARY KEY (appointment_id),
    CONSTRAINT fk_appointment FOREIGN KEY (appointment_id) REFERENCES appointment(id)
);

