ALTER TABLE appointments
    ADD CONSTRAINT appointments_timeslot_check
        CHECK ( (timeslot >= 0) and (timeslot <= 23));