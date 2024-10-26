DELIMITER //

CREATE TRIGGER after_insert_doctor
    AFTER INSERT ON doctor
    FOR EACH ROW
BEGIN
    DECLARE day_count INT;
    DECLARE day_id INTEGER DEFAULT 1;

    SELECT COUNT(*) INTO day_count FROM day_of_week;

    WHILE day_id <= day_count DO
            INSERT INTO work_schedule (doctor_id, day_of_week_id, work_time_start, work_time_end)
            VALUES (NEW.user_id, day_id, null, null);
            SET day_id = day_id + 1;
    END WHILE;
END;
//

DELIMITER ;
