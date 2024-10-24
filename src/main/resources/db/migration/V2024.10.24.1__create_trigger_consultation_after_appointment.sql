DELIMITER //

CREATE TRIGGER create_consultation_after_appointment
    AFTER INSERT
    ON appointment
    FOR EACH ROW
BEGIN
    INSERT INTO consultation (appointment_id)
    VALUES (NEW.id);
END;
//

DELIMITER ;
