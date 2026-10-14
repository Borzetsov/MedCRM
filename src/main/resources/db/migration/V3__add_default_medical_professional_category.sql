ALTER TABLE medical_service_categories
    ADD COLUMN default_professional_category_id BIGINT
        REFERENCES medical_professionals_categories (id) ON DELETE SET NULL;